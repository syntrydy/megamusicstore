package cm.gasmyr.it.app.aam.acceptancetest;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import cm.gasmyr.it.app.aam.acceptancetest.steps.pages.AbstractPage;

@Component
public class MusicListPage extends AbstractPage {

	private WebElement row;

	public void checkMusicPresence(String musicName, String singer) {
		WebElement table = webDriver.findElement(By.tagName("table"));
		table.click();
		row = table.findElement(By.xpath("//tr/td[contains(text(), musicName)]"));
		Assert.assertNotNull(row);
	}

	public void deleteCurrent() {
		WebElement deleteButton = row.findElement(By.xpath("//*[@id='deleteItemFromList']"));
		deleteButton.click();
		switchToModalDialog(webDriver);
		WebElement modal=webDriver.findElement(By.xpath("//*[@id='delete']"));
		modal.click();
		modal.findElements(By.tagName("button")).get(0).click();
	}
	
	private void switchToModalDialog(WebDriver driver) { 
    if (driver.getWindowHandles().size() == 2) {
        for (String window : driver.getWindowHandles()) {
            if (!window.equals(driver.getWindowHandle())) {
                driver.switchTo().window(window);
                System.out.println("Modal dialog found");
                break;
            }
        }
    }
}

	public void assertNotExist(String musicName) {
		WebElement table = webDriver.findElement(By.tagName("table"));
		table.click();
		WebElement row = table.findElement(By.xpath("//tr/td[contains(text(), musicName)]"));
		Assert.assertNull(row);
	}

}
