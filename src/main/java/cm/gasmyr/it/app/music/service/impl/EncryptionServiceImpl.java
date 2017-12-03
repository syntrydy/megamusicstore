package cm.gasmyr.it.app.music.service.impl;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cm.gasmyr.it.app.music.service.EncryptionService;

@Service
public class EncryptionServiceImpl implements EncryptionService {

	private final StrongPasswordEncryptor strongEncryptor;

	@Autowired
	public EncryptionServiceImpl(StrongPasswordEncryptor strongEncryptor) {
		this.strongEncryptor = strongEncryptor;
	}

	@Override
	public String encryptString(String input) {
		return strongEncryptor.encryptPassword(input);
	}

	@Override
	public boolean checkPassword(String plainPassword, String encryptedPassword) {
		return strongEncryptor.checkPassword(plainPassword, encryptedPassword);
	}

}
