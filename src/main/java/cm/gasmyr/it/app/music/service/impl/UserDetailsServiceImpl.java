package cm.gasmyr.it.app.music.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cm.gasmyr.it.app.music.core.User;
import cm.gasmyr.it.app.music.repository.UserRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;
	private Converter<User, UserDetails> userUserDetailsConverter;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private LoginAttemptService loginAttemptService;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	@Qualifier(value = "userToUserDetails")
	public void setUserUserDetailsConverter(Converter<User, UserDetails> userUserDetailsConverter) {
		this.userUserDetailsConverter = userUserDetailsConverter;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String ip = getClientIP();
		if (loginAttemptService.isBlocked(ip)) {
			throw new RuntimeException("blocked");
		}
		User user = userRepository.findByUsername(username);
		return userUserDetailsConverter.convert(user);

	}

	private String getClientIP() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}

}
