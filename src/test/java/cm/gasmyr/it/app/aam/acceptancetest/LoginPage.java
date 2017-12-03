package cm.gasmyr.it.app.aam.acceptancetest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import cm.gasmyr.it.app.aam.acceptancetest.steps.pages.AbstractPage;

@Component
@ConfigurationProperties(prefix = "/")
public class LoginPage extends AbstractPage {
	
	public void doLogin(final String userName, final String password) {
		gotoLoginPage();
		fillForm(userName, password).AnSubmit();
		assertThat(hasErrors(), is(false));
	}

	private void AnSubmit() {
		WebElement signInButton = webDriver.findElement(By.id("signIn"));
		signInButton.click();
	}

	private LoginPage fillForm(String userName, String password) {
		WebElement UElement = webDriver.findElement(By.xpath("//*[@id='username']"));
		UElement.sendKeys(userName);
		WebElement PElement = webDriver.findElement(By.xpath("//*[@id='password']"));
		PElement.sendKeys(password);
		return this;
	}

	public void releaseDriver() {
		ApplicationDriverFactory.release();
	}

}
