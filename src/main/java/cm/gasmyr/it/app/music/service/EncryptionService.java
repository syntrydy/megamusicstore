package cm.gasmyr.it.app.music.service;

public interface EncryptionService {
	
	String encryptString(String input);
	
	boolean checkPassword(String plainPassword, String encryptedPassword);
}
