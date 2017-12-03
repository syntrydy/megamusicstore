package cm.gasmyr.it.app.aam.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import cm.gasmyr.it.app.music.core.Role;

@RunWith(SpringRunner.class)
public class RoleTest {
	
	Role objectToTest;

	@Before
	public void setup() {
		objectToTest = Role.builder().build();
	}

	@SuppressWarnings("static-access")
	@Test
	public void newInstanceTest() {

		objectToTest = Role.builder().build();

		Assert.assertEquals(true, objectToTest.builder().isCreatingNewInstance());
	}

	@Test
	public void builderTest() {
		String name = "name";
		String description = "descripion";

		objectToTest = Role.builder().named(name).withDescription(description).build();

		Assert.assertEquals(name, objectToTest.getName());
		Assert.assertEquals(description, objectToTest.getDescription());
	}


}
