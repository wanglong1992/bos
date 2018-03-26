package cn.itcast.bos.web.action;

import cn.itcast.bos.utils.MailUtils;
import cn.itcast.bos.utils.SmsDemoUtils;
import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.crm.domain.Customer;
import cn.itcast.domain.constants.Constants;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Controller
@Scope(value = "prototype")
@ParentPackage("json-default")
@Namespace(value = "/")
public class CustomerAction extends BaseAction<Customer> {
    private static final long serialVersionUID = -1800914268889979433L;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 发送短信验证码
     *
     * @return
     */
    @Action(value = "customer_sendsms", results = {@Result(type = "json", name = SUCCESS)})
    public String sendSMS() {
        //获取手机号
        String telephone = model.getTelephone();
        //生成验证码
        String number = SmsDemoUtils.getNumber();
        ServletActionContext.getRequest().getSession().setAttribute(telephone, number);
        ServletActionContext.getRequest().getSession().setMaxInactiveInterval(1 * 60);
        jmsTemplate.send("bos_sms", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("telephone", telephone);
                mapMessage.setString("number", number);
                return mapMessage;
            }
        });
        String code = "OK";
        if ("OK".equals(code)) {
            pushValueStack(number);
        } else {
            throw new RuntimeException("短信发送失败!");
        }
        return SUCCESS;

    }

    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    /**
     * 客户注册
     *
     * @return
     */
    @Action(value = "customer_regiest", results = {@Result(type = "redirect", name = "success", location = "signup-success.html"), @Result(name = INPUT, type = "redirect", location = "signup.html")})
    public String customerRegist() {
        String sessionCheckcode = (String) ActionContext.getContext().getSession().get(model.getTelephone());
        if (sessionCheckcode == null || !checkcode.equals(sessionCheckcode)) {
            System.out.println("验证码过期或错误");
            return INPUT;
        }
        Customer customer = WebClient.create(Constants.CRM_MANAGEMENT_URL + "/services/customerService/customer/" + model.getTelephone()).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(Customer.class);
        if (customer != null) {
            System.out.println("手机号已存在,请更换手机号,或者联系管理员");
            return INPUT;
        }

        WebClient.create(Constants.CRM_MANAGEMENT_URL + "/services/customerService/customer/save").type(MediaType.APPLICATION_JSON).post(model);
        String mailNumber = RandomStringUtils.randomNumeric(32);
        redisTemplate.opsForValue().set(model.getTelephone(), mailNumber, 24, TimeUnit.HOURS);
        String mailContent = "尊敬的客户您好，请于24小时内，进行邮箱账户的绑定，点击下面地址完成绑定:<br/><a href='"
                + MailUtils.activeUrl + "?telephone=" + model.getTelephone()
                + "&activecode=" + mailNumber + "'>速运快递邮箱绑定地址</a>";
        MailUtils.sendMail("速运快递激活邮件", mailContent, model.getEmail(), "");
        System.out.println("客户注册成功");
        return SUCCESS;
    }

    private String activecode;

    public void setActivecode(String activecode) {
        this.activecode = activecode;
    }

    /**
     * 通过邮箱激活账户
     *
     * @return
     * @throws IOException
     */
    @Action(value = "customer_activeMail"/*,results = {@Result(name = "success",type = "redirect",location = "")}*/)
    public String activeMail() throws IOException {
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        String telephone = model.getTelephone();
        if (StringUtils.isBlank(activecode) || StringUtils.isBlank(telephone)) {
            ServletActionContext.getResponse().getWriter().println("激活码无效，请登录系统，重新绑定邮箱！");
            return NONE;
        }

        String number = redisTemplate.opsForValue().get(model.getTelephone());
        if (number == null || !number.equals(activecode)) {
            ServletActionContext.getResponse().getWriter().println("激活码已过期或错误，请登录系统，重新绑定邮箱！");
            return NONE;
        }

        Customer customer = WebClient.create(Constants.CRM_MANAGEMENT_URL + "/services/customerService/customer/" + model.getTelephone()).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(Customer.class);
        if (customer != null && customer.getType() == 1) {
            ServletActionContext.getResponse().getWriter().println("邮箱已经绑定过，无需重复绑定！");
            return NONE;
        } else {
            customer.setType(1);
            WebClient.create(Constants.CRM_MANAGEMENT_URL + "/services/customerService/customer/save").type(MediaType.APPLICATION_JSON).post(customer);
            ServletActionContext.getResponse().getWriter().println("邮箱绑定成功！");
        }
        //删除redis中该用户对应的激活码
        redisTemplate.delete(telephone);
        return NONE;
    }

    /**
     * 登录
     */
    @Action(value = "customer_login", results = {@Result(name = "success", type = "redirect", location = "./index.html#/myhome"), @Result(name = "input", type = "redirect", location = "./login.html")})
    public String login() {
        String telephone = model.getTelephone();
        String password = model.getPassword();
        Customer customer = WebClient.create(Constants.CRM_MANAGEMENT_URL + "/services/customerService/customer/telephoneAndPassword/" + telephone + "/" + password).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(Customer.class);
        if (customer == null) {
            System.out.println("用户名或密码错误");
            return INPUT;
        }
        if (customer.getType() == 1) {
            System.out.println("用户未激活");
            return INPUT;
        }

        ServletActionContext.getRequest().getSession().setAttribute("customer", customer);
        return SUCCESS;
    }
}
