package cm.gasmyr.it.app.music.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cm.gasmyr.it.app.music.core.User;
import cm.gasmyr.it.app.music.repository.UserRepository;
import cm.gasmyr.it.app.music.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;


	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
		
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> listAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User getById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public User saveOrUpdate(User userToSaveOrUpdate) {
		if (userToSaveOrUpdate.getUsername() != null) {
			User user = userRepository.findByUsername(userToSaveOrUpdate.getUsername());
			if (user != null) {
				user.updateInternal(userToSaveOrUpdate);
				userRepository.save(user);
			} else {
				userRepository.save(userToSaveOrUpdate);
			}
		}
		return userRepository.findByUsername(userToSaveOrUpdate.getUsername());
	}

	@Override
	public void delete(Long id) {
		userRepository.delete(id);
	}

}
