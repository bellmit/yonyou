package com.yonyou.dms.common.Util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

class MailObject {

	public Logger log = Logger.getLogger(MailObject.class);

	private MimeMessage mimeMsg;

	private Session session;
	private Properties props;

	private String username;
	private String password;

	private Multipart mp;

	public MailObject(String smtp) {
		setSmtpHost(smtp);
		createMimeMessage();
	}

	public void setSmtpHost(String hostName) {
		props = new Properties();
		props.put("mail.smtp.host", hostName); // 设置SMTP主机
	}

	public boolean createMimeMessage() {
		try {
			session = Session.getDefaultInstance(props, null);
		} catch (Exception e) {
			log.error(e);
			return false;
		}
		try {
			mimeMsg = new MimeMessage(session);
			mp = new MimeMultipart();
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	// 设置smtp身份认证
	public void setNeedAuth(boolean need) {
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	public boolean setSubject(String mailSubject) {
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	public boolean setBody(String mailBody) {
		try {
			BodyPart bp = new MimeBodyPart();
			bp.setContent(
					 mailBody, "text/html;charset=GB2312");
			mp.addBodyPart(bp);

			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}
	
	public boolean addFileAffix(String filename[]) {
		try {
			for (int i = 0; i < filename.length; i++) {
				BodyPart bp = new MimeBodyPart();
				FileDataSource fileds = new FileDataSource(filename[i]);
				bp.setDataHandler(new DataHandler(fileds));
				bp.setFileName(fileds.getName());

				mp.addBodyPart(bp);
			}
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	// 增加邮件附件
	public boolean addFileAffix(String filename) {
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());

			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	// 设置发信人
	public boolean setFrom(String from) {
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	public boolean setToList(String to[]) {
		if (to == null || to.length == 0)
			return false;

		InternetAddress[] address = new InternetAddress[to.length]; // 群发地址
		for (int i = 0; i < to.length; i++) {
			try {
				address[i] = new InternetAddress(to[i]);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				log.error(e);
				return false;
			}
		}
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO, address);
		} catch (MessagingException e) {
			log.error(e);
			return false;
		}
		return true;
	}

	public boolean setTo(String to) {
		if (to == null)
			return false;

		try {
			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(to));
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}

	}

	// 抄送
	public boolean setCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC,
					(Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}
	//多人抄送
	public boolean setCopyToList(String copyto[]) {
		if (copyto == null || copyto.length == 0)
			return false;

		InternetAddress[] address = new InternetAddress[copyto.length]; // 群发地址
		for (int i = 0; i < copyto.length; i++) {
			try {
				address[i] = new InternetAddress(copyto[i]);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				log.error(e);
				return false;
			}
		}
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC, address);
		} catch (MessagingException e) {
			log.error(e);
			return false;
		}
		return true;
	}
	
	
	public boolean sendout() {
		boolean isSendSuccess = true;
		Transport transport = null;
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			Session mailSession = Session.getInstance(props, null);
			transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,
					password);
			log.info("############################# 开始发送邮件 ############################### ");
//			transport.sendMessage(mimeMsg, mimeMsg
//					.getRecipients(Message.RecipientType.TO));
//			transport.sendMessage(mimeMsg, mimeMsg
//					.getRecipients(Message.RecipientType.CC));
			transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
			log.info("############################# 邮件发送成功  ############################### ");
			
			transport.close();
			// transport.send(mimeMsg);
		} catch (Exception e) {
			isSendSuccess = false;
			log.error(e.getMessage(),e);
		}
		return isSendSuccess;
	}

}
