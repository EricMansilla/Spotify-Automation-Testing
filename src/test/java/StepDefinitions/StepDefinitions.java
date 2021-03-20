package StepDefinitions;

import Functions.CreateDriver;
import Functions.SeleniumFunctions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StepDefinitions {

    private static Properties prop = new Properties();
    private static InputStream in = CreateDriver.class.getResourceAsStream("../test.properties");
    WebDriver driver;

    /******** Log Attribute ********/
    Logger log = Logger.getLogger(StepDefinitions.class);

    public StepDefinitions() {
        driver = Hooks.driver;
    }

    @Given("^I am in App main site")
    public void iAmInAppMainSite() throws IOException {
        prop.load(in);
        String url = prop.getProperty("MainAppUrlBase");
        log.info("Navigate to: " + url);
        driver.get(url);
    }

    @Given("^I navigate to (.*)$")
    public void navigateTo(String url) {
        log.info("Navigate to: " + url);
        driver.get(url);
    }

    @Then("^I load the DOM Information (.*)$")
    public void iLoadTheDOMInformation(String file) throws Exception {
        SeleniumFunctions.FileName = file;
        SeleniumFunctions.readJson();
        log.info("initialize file: " + file );
    }

    @And("I click on element (.*)")
    public void iClickOnElement(String locator) throws Exception {
        By SeleniumElem = SeleniumFunctions.getCompleteElement(locator);
        driver.findElement(SeleniumElem).click();
        log.info("Element " + locator + " clicked.");
    }

    @And("^I set (.*) with text (.*)$")
    public void iSetWithText(String element, String text) throws Exception {
        By SeleniumElem = SeleniumFunctions.getCompleteElement(element);
        driver.findElement(SeleniumElem).sendKeys(text);
        log.info("Sended " + text + "to element " + element);
    }


    @Then("I close the window")
    public void iCloseTheWindow() {
        driver.quit();
    }
}
