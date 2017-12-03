package cm.gasmyr.it.app.music.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import cm.gasmyr.it.app.music.util.Utils;

@Entity
@Table(name = "_user")
public class User extends MusicDomainObject<Long> {

	public static class Builder extends MusicDomainObject.Builder<User, User.Builder> {

		private Builder() {
			super();
		}

		private Builder(User instance) {
			super(instance);
		}

		public Builder withUserName(String username) {
			instance.username = username;
			return this;
		}

		public Builder withEncryptPassword(String encryptPassword) {
			instance.encryptedpassword = encryptPassword;
			return this;
		}

		public Builder withPassword(String password) {
			instance.password = password;
			return this;
		}

		public Builder withFullName(String fullName) {
			instance.fullName = fullName;
			return this;
		}

		public Builder withEmail(String email) {
			instance.email = email;
			return this;
		}

		public Builder withPhoneNUmber(String phone) {
			instance.phone = phone;
			return this;
		}

		public Builder isActive(boolean value) {
			instance.active = value;
			return this;
		}

		@Override
		protected User newInstance() {
			return new User();
		}

	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builder(User instance) {
		return new Builder(instance);
	}

	private User() {
	}

	private String username;

	@Transient
	private String password;
	@Transient
	private String passwordConfirm;
	private String encryptedpassword;
	private String email;
	private String phone;
	private boolean active;
	private String fullName;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), name = "user_role")
	private Set<Role> roles = new HashSet<>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getEncryptedpassword() {
		return encryptedpassword;
	}

	public void setEncryptedpassword(String encryptedpassword) {
		this.encryptedpassword = encryptedpassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		if (!this.roles.contains(role)) {
			this.roles.add(role);
		}
	}

	public void removeRole(Role role) {
		this.roles.remove(role);
	}

	public void updateInternal(User user) {
		this.username = user.getUsername();
		this.active = user.isActive();
		this.encryptedpassword = user.getEncryptedpassword();
		this.roles = user.getRoles();
		this.email = user.getEmail();
		this.phone = user.getPhone();

	}

	public boolean canBeSave() {
		return Utils.canSave(Arrays.asList(username, email,password, passwordConfirm));
	}
}
