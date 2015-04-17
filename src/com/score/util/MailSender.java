package com.score.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender 
{
	String mailaddress;
	
	String password;
	
	String host;
	
	class MyAuthenticator extends Authenticator{   
	    String userName=null;   
	    String password=null;   
	        
	    public MyAuthenticator(){   
	    }   
	    public MyAuthenticator(String username, String password) {    
	        this.userName = username;    
	        this.password = password;    
	    }    
	    protected PasswordAuthentication getPasswordAuthentication(){   
	        return new PasswordAuthentication(userName, password);   
	    }   
	}   

	public void postMail(EmailData emailData) throws MessagingException, Exception 
	{
		String from = mailaddress;
		String[] recipients = emailData.getRecipients(); 
		String subject = emailData.getSubject(); 
		String content = emailData.getContent(); 
		String contentType = emailData.getContentType(); 
		//String fileName  = emailData.getFileName(); 
		if (recipients != null && recipients.length > 0) { 
			Properties props = new Properties(); 
			//设置邮件服务器地址，连接超时时限等信息 
			props.put("mail.smtp.auth", "true");
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.connectiontimeout", "10000");
			props.put("mail.smtp.timeout", "10000");
	     
			MyAuthenticator authenticator = new MyAuthenticator(mailaddress, password);
			//创建缺省的session对象 
			Session session = Session.getInstance(props, authenticator); 
	  
			//创建message对象 
			Message msg = new MimeMessage(session); 
	  
			//设置发件人和收件人 
			InternetAddress addressFrom = new InternetAddress(from); 
			msg.setFrom(addressFrom);   
			InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
			for (int i = 0; i < recipients.length; i++){ 
				addressTo[i] = new InternetAddress(recipients[i]); 
			} 
			msg.setRecipients(Message.RecipientType.TO, addressTo); 
	   
			//设置邮件标题，中文编码 
			//subject = MimeUtility.encodeText(new String(subject.getBytes(), "UTF-8"), "UTF-8", "B"); 
			msg.setSubject(subject); 
	     
			//设置邮件内容,区分文本格式和HTML格式 
			if (contentType == null || contentType.equals("text")) { 
				msg.setText(content); 
			} else if (contentType.equals("html")) { 
				//给消息对象设置内容 
				BodyPart bodyPart1 = new MimeBodyPart(); //新建一个存放信件内容的BodyPart对象 
				bodyPart1.setContent(content, "text/html;charset=utf-8");//给BodyPart对象设置内容和格式/编码方式 
				Multipart mmp = new MimeMultipart();//新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个) 
				/*//设置邮件附件 
				BodyPart bodyPart2 = new MimeBodyPart();  
				FileDataSource fileDataSource = new FileDataSource(fileName); 
	   			bodyPart2.setDataHandler(new DataHandler(fileDataSource));        
	   			bodyPart2.setFileName("=?GB2312?B?"+enc.encode(fileName.getBytes())+"?="); */

				Multipart multipart = new MimeMultipart(); 
				multipart.addBodyPart(bodyPart1); 
				//multipart.addBodyPart(bodyPart2); 
	    
				mmp.addBodyPart(bodyPart1);//将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart) 
				msg.setContent(mmp);//把mm作为消息对象的内容 
			} 
	      
			//设置邮件发送时间 
	        msg.setSentDate(new java.util.Date()); 
	        //调用抽象类的静态方法send()发送邮件 
	        Transport.send(msg); 
	    } 
	}

	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
