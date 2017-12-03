package cm.gasmyr.it.app.music.mail;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import cm.gasmyr.it.app.music.core.User;

@Service
public class MailServiceImpl implements MailService {

	final JavaMailSender mailSender;
	final SpringTemplateEngine templateEngine;

	@Autowired
	public MailServiceImpl(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
	}

	@Override
	public boolean sendEmail(Mail message) {
		boolean result = false;
		MimeMessagePreparator preparator = getMessagePreparator(message);
		try {
			mailSender.send(preparator);
			result = true;
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
		return result;
	}

	private MimeMessagePreparator getMessagePreparator(final Mail message) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setFrom(message.getFrom());
				mimeMessage.setRecipient(RecipientType.TO, new InternetAddress(message.getTo()));
				mimeMessage.setText(message.getContent());
				mimeMessage.setSubject(message.getSubject());
			}
		};
		return preparator;
	}

	@Override
	public boolean sendHtmlMail(Mail mesg) {
		boolean result = false;
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(mesg.getTo());
			helper.setReplyTo(mesg.getFrom());
			helper.setFrom(mesg.getFrom());
			helper.setSubject(mesg.getSubject());
			helper.setText(mesg.getContent(), true);
			mailSender.send(message);
			result = true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public boolean sendMessageWithAttachment(Mail mail, String pathToAttachment) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(mail.getTo());
			helper.setSubject(mail.getSubject());
			helper.setText(mail.getContent());
			FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
			helper.addAttachment("Invoice", file);
			mailSender.send(message);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean sendNewUserInviteMail(Mail mail, User user) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			final Context context = new Context();
			context.setVariable("name", user.getFullName());
			context.setVariable("username", user.getEmail());
			context.setVariable("password", user.getPassword());
			context.setVariable("location", "Cameroon/Bangangte");
			context.setVariable("signature", "megamusicstore.herokuapp.com");
			String html = templateEngine.process("SignInMailTemplate", context);
			helper.setTo(mail.getTo());
			helper.setText(html, true);
			helper.setSubject(mail.getSubject());
			helper.setFrom(mail.getFrom());
			mailSender.send(message);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}

	}
}