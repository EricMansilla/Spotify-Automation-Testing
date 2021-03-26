package StepDefinitions;

import Functions.SeleniumFunctions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;

public class StepDefinitions {
    WebDriver driver;
    SeleniumFunctions functions = new SeleniumFunctions();

    /******** Log Attribute ********/
    Logger log = Logger.getLogger(StepDefinitions.class);

    public StepDefinitions() {
        driver = Hooks.driver;
    }

    @Given("^I am in App main site")
    public void iAmInAppMainSite() throws IOException {
        String url = functions.readProperties("MainAppUrlBase");
        log.info("Navigate to: " + url);
        functions.WindowsHandle("Principal");
        driver.get(url);
        functions.pageHasLoaded();
    }

    @Given("^I navigate to (.*)$")
    public void navigateTo(String url) {
        log.info("Navigate to: " + url);
        driver.get(url);
        functions.pageHasLoaded();
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
        functions.waitForElementClickable(locator);
        driver.findElement(SeleniumElem).click();
        log.info("Element " + locator + " clicked.");
    }

    @And("^I set (.*) with text (.*)$")
    public void iSetWithText(String element, String text) throws Exception {
        By SeleniumElem = SeleniumFunctions.getCompleteElement(element);
        driver.findElement(SeleniumElem).sendKeys(text);
        log.info("Sended " + text + "to element " + element);
    }

    @Given("I set (.*) value in Data Scenario")
    public void iSetUserEmailValueInDataScenario(String parameter) throws IOException {
        functions.RetrieveTestData(parameter);
    }

    @And("^I Save text of (.*?) as Scenario Context$")
    public void iSaveTextOfElementAsScenarioContext(String element) throws Exception {
        By SeleniumElem = SeleniumFunctions.getCompleteElement(element);
        String ScenarioElementText = driver.findElement(SeleniumElem).getText();
        functions.SaveInScenario(element+".text", ScenarioElementText);
    }

    @And("^I set (.*?) with key value (.*?)$")
    public void iSetWithKeyValue(String element, String key) throws Exception {
        functions.iSetElementWithKeyValue(element, key);
    }

    @And("I set text (.*) in dropdown (.*)")
    public void iSetTextInDropdown(String option, String element) throws Exception {
        Select opt = functions.selectOption(element);
        opt.selectByVisibleText(option);
    }

    @And("I set index (.*) in dropdown (.*)")
    public void iSetIndexInDropdown(int index, String element) throws Exception {
        Select opt = functions.selectOption(element);
        opt.selectByIndex(index);
    }

    /** Wait for an element to be present for a specific period of time */
    @And("^I wait for element (.*) to be present$")
    public void waitForElementPresent(String element) throws Exception
    {
        functions.waitForElementPresent(element);
    }

    /** Wait for an element to be visible for a specific period of time */
    @And("^I wait element (.*?) to be visible$")
    public void waitForElementVisible(String element) throws Exception
    {
        functions.waitForElementVisible(element);
    }

    @Then("I check if (.*) error message is displayed=(.*)")
    public void iCheckIfErrorMessageIs(String element, String state) throws Exception {
        boolean actual = functions.isElementDisplayed(element);
        Assert.assertEquals("El estado es diferente al esperado", actual, Boolean.valueOf(state));
    }

    @And("^I switch to Frame: (.*)")
    public void iSwitchToFrame(String frame) throws Exception {
        functions.switchToFrame(frame);
    }

    @And("I switch to parent frame")
    public void iSwitchToParentFrame() {
        functions.switchToParentFrame();
    }

    /** Check an option from a checkbox */
    @When("^I check the checkbox (.*?)$")
    public void checkCheckbox(String element) throws Exception
    {
        functions.checkCheckbox(element);
    }

    /** Check an option from a checkbox */
    @When("^I uncheck the checkbox (.*?)$")
    public void UncheckCheckbox(String element) throws Exception
    {
        functions.UncheckCheckbox(element);
    }

    @And("^I click in JS element (.*)$")
    public void iClickInJSElementSobreAmazon(String element) throws Exception {
        functions.clickJSElement(element);
    }

    @And("^I scroll to element (.*)$")
    public void iScrollToElement(String element) throws Exception {
        functions.scrollPage(element);
    }

    @And("^I scroll to (top|bottom) of page$")
    public void iScrollToTopOfPage(String to) throws Exception {
        functions.scrollPage(to);
    }

    @And("^I open new tab with URL (.*)$")
    public void iOpenNewTabWithURL(String url) {
        functions.OpenNewTabWithURL(url);
    }

    @When("I switch to new window")
    public void iSwitchToNewWindow() {
        System.out.println(driver.getWindowHandles());
        for(String winHandle : driver.getWindowHandles()){
            System.out.println(winHandle);
            log.info("Switching to new window");
            driver.switchTo().window(winHandle);
        }
    }

    /** Switch to a new windows */
    @When("^I go to (.*?) window$")
    public void switchNewNamedWindow(String WindowsName)
    {
        functions.WindowsHandle(WindowsName);
    }

    @And("I wait (.*) seconds")
    public void iWaitSeconds(int seconds) throws InterruptedException {
        int secs = seconds * 1000;
        Thread.sleep(secs);
    }

    @Then("^I (accept|dismiss) alert$")
    public void iAcceptAlert(String req) {
        functions.handleAlert(req);
    }

    @And("^I take screenshot: (.*?)$")
    public void iTakeScreenshot(String TestCaptura) throws IOException {
        functions.ScreenShot(TestCaptura);
    }

    @Then("^Assert if (.*) contains text (.*)$")
    public void assertIfElementContainsText(String element, String text) throws Exception {
        functions.checkPartialTextElementPresent(element, text);
    }

    @Then("Assert if (.*) is equal to (.*)")
    public void assertIfElementIsEqualToText(String element, String text) throws Exception {
        functions.checkTextElementEqualTo(element, text);
    }

    @Then("Check if (.*) NOT contains text (.*)")
    public void checkIfElementNOTContainsText(String element, String text) throws Exception {
        functions.checkPartialTextElementNotPresent(element, text);
    }

    @Then("Assert if (.*) is Displayed")
    public void assertIfElementIsDisplayed(String element) throws Exception {
        boolean isDisplayed = functions.isElementDisplayed(element);
        Assert.assertTrue("Element is not present: " + element, isDisplayed);
    }

    @Then("Check if (.*) is NOT Displayed")
    public void checkIfEmailErrorIsNOTDisplayed(String element) throws Exception {
        boolean isDisplayed = functions.isElementDisplayed(element);
        Assert.assertFalse("Element is present: " + element, isDisplayed);
    }

}
