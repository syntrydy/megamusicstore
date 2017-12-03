package cm.gasmyr.it.app.aam.acceptancetest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

import cm.gasmyr.it.app.aam.acceptancetest.steps.pages.AbstractPage;

public class HomePage extends AbstractPage {

	public void gotoUserList() {
		WebElement navBar = webDriver.findElement(By.id("navbarId"));
		navBar.click();
		WebElement adminMenu = navBar.findElement(By.id("adminMenuId"));
		adminMenu.click();
	}

	public void gotoMusicList() {
		WebElement navBar = webDriver.findElement(By.id("navbarId"));
		navBar.click();
		WebElement musicMenu = navBar.findElement(By.id("musicMenuId"));
		musicMenu.click();
		Wait<WebDriver> stubbornWait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		WebElement musics = stubbornWait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.id("musicList"));
			}
		});
		musics.click();
	}

}
