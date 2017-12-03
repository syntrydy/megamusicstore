package cm.gasmyr.it.app.music.mail;

import cm.gasmyr.it.app.music.core.User;

public interface MailService {

	boolean sendEmail(Mail message);

	boolean sendHtmlMail(Mail message);

	boolean sendMessageWithAttachment(Mail message, String pathToAttachment);

	boolean sendNewUserInviteMail(Mail mail, User user);
}