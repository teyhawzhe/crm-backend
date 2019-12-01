package com.crm.utils;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${file.path}")
	private String filePath;

	public void sendSimpleEmail(String to, String title, String content) {
		this.sendSimpleEmail(to, null, title, content);
	}

	public void sendSimpleEmail(String[] to, String title, String content) {
		this.sendSimpleEmail(null, to, title, content);
	}

	public void sendSimpleEmail(String to, String[] toArr, String title, String content) {
		SimpleMailMessage msg = new SimpleMailMessage();
		if (StringUtils.isBlank(to)) {
			msg.setTo(toArr);
		} else {
			msg.setTo(to);
		}
		msg.setSubject(title);
		msg.setText(content);
		javaMailSender.send(msg);
	}

	public void sendAttachmentEmail(String to, String title, String content, String FileName)
			throws MessagingException, IOException {
		this.sendAttachmentEmail(to, null, title, content, FileName, null);
	}

	public void sendAttachmentEmail(String to, String title, String content, String FileName[])
			throws MessagingException, IOException {
		this.sendAttachmentEmail(to, null, title, content, null, FileName);
	}

	public void sendAttachmentEmail(String[] to, String title, String content, String FileName)
			throws MessagingException, IOException {
		this.sendAttachmentEmail(null, to, title, content, FileName, null);
	}

	public void sendAttachmentEmail(String[] to, String title, String content, String FileName[])
			throws MessagingException, IOException {
		this.sendAttachmentEmail(null, to, title, content, null, FileName);
	}

	public void sendAttachmentEmail(String to, String[] toArr, String title, String content, String FileName,
			String[] FileNameArr) throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		if (StringUtils.isBlank(to)) {
			helper.setTo(toArr);
		} else {
			helper.setTo(to);
		}
		helper.setSubject(title);
		helper.setText(content, true);
		if (StringUtils.isBlank(FileName)) {
			for (String index : FileNameArr) {
				FileSystemResource file = new FileSystemResource(new File(filePath + index));
				helper.addAttachment(file.getFilename(), file);
			}
		} else {
			FileSystemResource file = new FileSystemResource(new File(filePath + FileName));
			helper.addAttachment(file.getFilename(), file);
		}

		javaMailSender.send(msg);
	}

}
