package cm.gasmyr.it.app.aam.acceptancetest.steps;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cm.gasmyr.it.app.aam.acceptancetest.HomePage;
import cucumber.api.java.en.And;
@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeSteps {
	
	@Autowired
	private HomePage homePage;

	@And("^I show the music list$")
	public void goToMusicList() {
		homePage.gotoMusicList();
	}

}
