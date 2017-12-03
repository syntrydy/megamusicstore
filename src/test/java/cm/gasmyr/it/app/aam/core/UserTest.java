package cm.gasmyr.it.app.aam.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import cm.gasmyr.it.app.music.core.User;

@RunWith(SpringRunner.class)
public class UserTest {
	User objectToTest;

	@Before
	public void setup() {
		objectToTest = User.builder().build();
	}

	@SuppressWarnings("static-access")
	@Test
	public void newInstanceTest() {

		objectToTest = User.builder().build();

		Assert.assertEquals(true, objectToTest.builder().isCreatingNewInstance());
	}
	
	@Test
	public void builderTest() {
		String userName="gasmyr";
		String email = "email";
		String password = "password";
		String fullName="fullName";

		objectToTest = User.builder().withUserName(userName).withEmail(email).withPassword(password).withFullName(fullName).build();
		
		Assert.assertEquals(userName, objectToTest.getUsername());
		Assert.assertEquals(fullName, objectToTest.getFullName());
		Assert.assertEquals(email, objectToTest.getEmail());
		Assert.assertEquals(password, objectToTest.getPassword());
	}


}
