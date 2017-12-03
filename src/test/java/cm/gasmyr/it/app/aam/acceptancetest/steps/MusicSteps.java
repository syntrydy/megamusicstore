package cm.gasmyr.it.app.aam.acceptancetest.steps;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cm.gasmyr.it.app.aam.acceptancetest.MusicListPage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MusicSteps {
	@Autowired
	private MusicListPage musicListPage;

	@Then("^I should see a music titled '(.+)' sung by '(.+)'$")
	public void checkMusicPresence(String musicName, String singer) {
		musicListPage.checkMusicPresence(musicName, singer);
	}

	@When("^I delete the current music$")
	public void deleteMusic() {
		musicListPage.deleteCurrent();
	}

	@Then("^The music '(.+)' will no longer be visible on music list$")
	public void assertMusicNotExistAnyMore(String musicName) {
		musicListPage.assertNotExist(musicName);
	}

}
