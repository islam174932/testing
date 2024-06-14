package StepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

import static StepDefinitions.WebDriverManager.driverThreadLocal;

public class hooks {

    WebDriver driver = WebDriverManager.getDriver();
    private List<Scenario> failedScenarios = new ArrayList<>();

    @Before
    public void setup() {
        System.out.println("We start our test cases via the feature files");
    }

    @After
    public void tearDown(Scenario scenario) {
        driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
        if (scenario.isFailed()) {
            failedScenarios.add(scenario);
        }
    }

    @AfterStep
    public void takeScreenshot(Scenario scenario) {
        driver = driverThreadLocal.get();
        if (driver != null && scenario.isFailed()) {
            // Take a screenshot and attach it to the report
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot");
        }
    }


}
