package com.yonyou.dms.common.Util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class MailSender {

	public Logger log = Logger.getLogger(MailSender.class);
	public static MailSender INSTANCE = new MailSender();

	private String name = "mail.properties";
	private static String SMTPSERVER;
	private static String FROM;
	private static String USER;
	private static String PASSWORD;

	private MailSender() {
		Properties props = new Properties();
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(name));
			SMTPSERVER = props.getProperty("mail.smtp.host");
			FROM = props.getProperty("from");
			USER = props.getProperty("user");
			PASSWORD = props.getProperty("password");
		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
	 * 发送邮件给单个收件人
	 * 
	 * @param to
	 *            收件人EMAIL地址
	 * @param subject
	 *            邮件主题
	 * @param body
	 *            邮件内容
	 * @param files[]
	 *            附件
	 * @return 返回值 false 失败 true 成功
	 */
	public static boolean sendMail(String to, String subject, String body,
			String files[]) {

		if (SMTPSERVER == null)
			return false;

		MailObject themail = new MailObject(SMTPSERVER);
		themail.setNeedAuth(true);

		if (themail.setSubject(subject) == false)
			return false;// 设置邮件主题
		if (themail.setBody(body) == false)
			return false;// 内容
		if (themail.setTo(to) == false)
			return false;// 收件人
		if (themail.setFrom(FROM) == false)
			return false;// 发件人
		themail.setNamePass(USER, PASSWORD);// 设置发送邮件服务器smtp中你的登录名及密码
		if (files != null && files.length > 0)
			if (themail.addFileAffix(files) == false)
				return false;
		if (themail.sendout() == false)
			return false;
		return true;
	}

	/**
	 * 发送邮件给多个收件人
	 * 
	 * @param to
	 *            收件人EMAIL地址数组
	 * @param subject
	 *            邮件主题
	 * @param body
	 *            邮件内容
	 * @param files[]
	 *            附件
	 * @return 返回值 false 失败 true 成功
	 */
	public static boolean sendMail(String to[], String subject, String body,
			String files[]) {

		if (SMTPSERVER == null)
			return false;

		MailObject themail = new MailObject(SMTPSERVER);
		themail.setNeedAuth(true);

		if (themail.setSubject(subject) == false)
			return false;
		if (themail.setBody(body) == false)
			return false;
		if (themail.setToList(to) == false)
			return false;
		if (themail.setFrom(FROM) == false)
			return false;
		themail.setNamePass(USER, PASSWORD);
		if (files != null && files.length > 0)
			if (themail.addFileAffix(files) == false)
				return false;
		if (themail.sendout() == false)
			return false;
		return true;
	}

	/**
	 * 发送邮件给多个收件人
	 * 
	 * @param smtpserver
	 *            SMTP邮件服务器
	 * @param from
	 *            发件人
	 * @param user
	 *            认证用户名
	 * @param password
	 *            认证密码
	 * @param to
	 *            收件人EMAIL地址数组
	 * @param subject
	 *            邮件主题
	 * @param body
	 *            邮件内容
	 * @param files[]
	 *            附件
	 * @return 返回值 false 失败 true 成功
	 */
	public boolean sendMail(String smtpserver, String from, String user,
			String password, String to[], String subject, String body,
			String files[]) {

		if (smtpserver == null)
			return false;
		MailObject themail = new MailObject(smtpserver);
		themail.setNeedAuth(true);

		if (themail.setSubject(subject) == false)
			return false;
		if (themail.setBody(body) == false)
			return false;
		if (themail.setToList(to) == false)
			return false;
		if (themail.setFrom(from) == false)
			return false;
		themail.setNamePass(user, password);
		if (files != null && files.length > 0)
			if (themail.addFileAffix(files) == false)
				return false;
		if (themail.sendout() == false)
			return false;
		return true;
	}
	
	/**
	 * 发送邮件并抄送给多人
	 * 
	 * @param to
	 *            
	 * @param copyTo
	 *            抄送人EMAIL地址数组
	 * @param subject
	 *            邮件主题
	 * @param body
	 *            邮件内容
	 * @param files[]
	 *            附件
	 * @return 返回值 false 失败 true 成功
	 */
	public boolean sendMail(String[] to, String copyTo[], String subject, String body,
			String files[]) {

		if (SMTPSERVER == null)
			return false;

		MailObject themail = new MailObject(SMTPSERVER);
		themail.setNeedAuth(true);

		if (themail.setSubject(subject) == false)
			return false;
		if (themail.setBody(body) == false)
			return false;
		if (themail.setToList(to) == false)
			return false;
		if (themail.setCopyToList(copyTo) == false)
			return false;
		if (themail.setFrom(FROM) == false)
			return false;
		themail.setNamePass(USER, PASSWORD);
		if (files != null && files.length > 0)
			if (themail.addFileAffix(files) == false)
				return false;
		if (themail.sendout() == false)
			return false;
		return true;
	}
	
	
	
}
