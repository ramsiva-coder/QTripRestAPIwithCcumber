package cucumberapi.runnerer;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features={"src/test/resources/Funtionaltest"},
    glue={"cucumberapi/stepDefinitions"},
    plugin = {"pretty", "json:target/cucumber-reports.json"},
    monochrome=true,
    dryRun=false,
    tags="not @Exclude"
    

)



public class Testrunner {
    
}
