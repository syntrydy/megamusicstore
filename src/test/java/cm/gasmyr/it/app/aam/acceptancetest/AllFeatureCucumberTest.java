package cm.gasmyr.it.app.aam.acceptancetest;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "cm.gasmyr.it.app.aam.acceptancetest.steps" }, features = {
		"classpath:bdd" })

@ActiveProfiles(value = "test")
public class AllFeatureCucumberTest {

}
