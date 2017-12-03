package cm.gasmyr.it.app.aam.acceptancetest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ApplicationDriverFactory {

	private ApplicationDriverFactory() {
	}

	static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>() {
		@Override
		protected WebDriver initialValue() {
			return new ChromeDriver();
		}
	};


	public static WebDriver getDriver() {
		driver.get().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return driver.get();
	}

	public static void release() {
		driver.get().quit();
		driver.remove();
	}
	
}
