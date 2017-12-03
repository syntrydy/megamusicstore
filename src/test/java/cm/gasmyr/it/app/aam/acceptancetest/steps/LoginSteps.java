package cm.gasmyr.it.app.aam.acceptancetest.steps;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import cm.gasmyr.it.app.aam.acceptancetest.ApplicationDriverFactory;
import cm.gasmyr.it.app.aam.acceptancetest.HomePage;
import cm.gasmyr.it.app.aam.acceptancetest.LoginPage;
import cm.gasmyr.it.app.aam.acceptancetest.MusicListPage;
import cm.gasmyr.it.app.music.MegaMusicApplication;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MegaMusicApplication.class, LoginPage.class, HomePage.class, MusicListPage.class })
@SpringBootTest
public class LoginSteps {

	@Autowired
	private LoginPage loginPage;

	@When("^I sign in with user name '(.+)' and password '(.+)'$")
	public void signIn(String userName, String password) {
		loginPage.doLogin(userName, password);
	}

	@Then("^I should be redirect to '(.+)' page$")
	public void assertCurrentPageIsAdminPage(String page) {
		WebDriver driver = ApplicationDriverFactory.getDriver();
		WebElement element;
		if (page.equalsIgnoreCase("admin")) {
			element = driver.findElement(By.id("homeId"));
		} else {
			element = driver.findElement(By.id("userId"));
		}
		Assert.assertNotNull(element);
	}

}
