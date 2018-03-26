package cn.itcast.bos.sms.courier;


import cn.itcast.bos.sms.utils.SmsDemoUtils2;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

// Queue消费者
@Component("smsCourierQueueConsumer")
public class SmsCourierQueueConsumer implements MessageListener {

	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage)message;
		try {
			/***
			 * mapMessage.setString("telephone",order.getCourier().getTelephone());
				// 参数
				mapMessage.setString("num",smsNumber);
				mapMessage.setString("sendAddres",order.getSendAddress());
				mapMessage.setString("sendName",order.getSendName());
				mapMessage.setString("sendMobile",order.getSendMobile());
				mapMessage.setString("sendMobileMsg",order.getSendMobileMsg());
			 */
			String telephone = mapMessage.getString("telephone");
			String num = mapMessage.getString("num");
			String sendAddress = mapMessage.getString("sendAddress");
			String sendName = mapMessage.getString("sendName");
			String sendMobile = mapMessage.getString("sendMobile");
			String sendMobileMsg = mapMessage.getString("sendMobileMsg");
			// 调用阿里大鱼发送短信
			SendSmsResponse sendSms = SmsDemoUtils2.sendSms(telephone, num,sendAddress,sendName,sendMobile,sendMobileMsg);
			String code = sendSms.getCode();
			// String code = "OK";
			if(code.equals("OK")){
				System.out.println("使用短信平台bos_sms发送短信成功！快递员的电话号是："+telephone+"，手机验证码是："+num+"，寄件人姓名："+sendName+"，寄件人地址："+sendAddress+"，寄件人的手机号："+sendMobile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
