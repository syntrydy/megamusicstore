package cm.gasmyr.it.app.aam.acceptancetest.steps.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import cm.gasmyr.it.app.aam.acceptancetest.ApplicationDriverFactory;
import cm.gasmyr.it.app.aam.acceptancetest.LoginPage;

public abstract class AbstractPage {
	protected WebDriver webDriver = ApplicationDriverFactory.getDriver();
	
	
	public void waitImplicite(long mSeconds){
		
	}
	
	public void navigate(final String value) {
		WebDriver driver = ApplicationDriverFactory.getDriver();
		driver.manage().window().maximize();
		driver.navigate().to(value);
	}
	
	protected LoginPage gotoLoginPage(){
		navigate("http://localhost:8080");
		return new LoginPage();
	}

	protected String pageTitle() {
		return ApplicationDriverFactory.getDriver().getTitle();
	}

	protected void clickId(final String id) {
		final WebElement button = ApplicationDriverFactory.getDriver().findElement(By.id(id));
		final JavascriptExecutor executor = (JavascriptExecutor) ApplicationDriverFactory.getDriver();
		executor.executeScript("arguments[0].click();", button);
	}

	public void quit() {
		ApplicationDriverFactory.getDriver().quit();
	}

	protected void editText(final String id, final String value) {
		final WebElement element = (new FluentWait<>(ApplicationDriverFactory.getDriver()))
				.withTimeout(10, TimeUnit.SECONDS).pollingEvery(10, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
		element.sendKeys(value);
	}

	protected boolean hasErrors() {
		final List<WebElement> errors = ApplicationDriverFactory.getDriver().findElements(By.className("error"));
		return (errors.size() > 0) && errors.get(0).isDisplayed();
	}

}
