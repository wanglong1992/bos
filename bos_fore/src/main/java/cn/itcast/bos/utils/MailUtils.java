package cn.itcast.bos.utils;

import cn.itcast.domain.constants.Constants;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static cn.itcast.domain.constants.Constants.*;

public class MailUtils {

	private static String smtp_host = "smtp.163.com";		//网易
	private static String username = "itcast_ly@163.com";	//邮箱
	private static String password = "itcast123";		//授权码（登录邮箱-->设置-->邮箱安全设置-->客户端授权密码），这里不是邮箱的密码，切记！
	private static String from = "itcast_ly@163.com";		//来源邮箱，使用当前账号	
	public static String activeUrl = BOS_FORE_URL+"/customer_activeMail.action";		//激活地址
	
	/**发送邮件
	 * String subject：邮件主题
	 * String content：邮件内容
	 * String to：发送给谁（邮箱）
	   String activecode：激活码（激活码也可以放置到content下）
	 * */
	public static void sendMail(String subject, String content, String to,
			String activecode) {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", smtp_host);
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(props);
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
//			message.setContent("<h3>请点击地址激活:<a href=" + activeUrl
//					+ "?activecode=" + activecode + ">" + activeUrl
//					+ "</a></h3>", "text/html;charset=utf-8");
			message.setContent(content, "text/html;charset=utf-8");
			Transport transport = session.getTransport();
			transport.connect(smtp_host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("邮件发送失败...");
		}
	}
	
	public static void main(String[] args) {
		// 生成32位激活码
		String activecode = RandomStringUtils.randomNumeric(32);
		String content = "<h3>请点击地址激活:<a href=" + activeUrl
				+ "?activecode=" + activecode + ">" + activeUrl
				+ "</a></h3>";
		sendMail("测试邮件", content, "itcast_ly@163.com", "");
	}
}
