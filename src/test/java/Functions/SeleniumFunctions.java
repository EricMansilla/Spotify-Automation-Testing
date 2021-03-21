package Functions;

import StepDefinitions.Hooks;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SeleniumFunctions {

    WebDriver driver;

    public SeleniumFunctions() {
        driver = Hooks.driver;
    }

    /******** Page path ********/
    public static String FileName = "";
    public static String PagesFilePath = "src/test/resources/Pages/";

    /******** Scenario Test Data ********/
    public static Map<String, String> ScenarioData = new HashMap<>();
    public static String Environment = "";

    /******** Test Properties Config ********/
    public static Properties prop = new Properties();
    public static InputStream in = SeleniumFunctions.class.getResourceAsStream("../test.properties");

    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(SeleniumFunctions.class);

    private static String GetFieldBy = "";
    private static String ValueToFind = "";
    public static final int EXPLICIT_TIMEOUT = 5;
    public static boolean isDisplayed = Boolean.parseBoolean(null);

    public static Object readJson() throws Exception {
        FileReader reader = new FileReader(PagesFilePath + FileName);
        try {
            if (reader != null) {
                JSONParser jsonParser = new JSONParser();
                return jsonParser.parse(reader);
            } else {
                return null;
            }
        } catch (FileNotFoundException | NullPointerException e) {
            log.error("ReadEntity: No existe el archivo " + FileName);
            throw new IllegalStateException("ReadEntity: No existe el archivo " + FileName, e);
        }
    }

    public static JSONObject ReadEntity(String element) throws Exception {
        JSONObject Entity = null;
        JSONObject jsonObject = (JSONObject) readJson();
        Entity = (JSONObject) jsonObject.get(element);
        log.info(Entity.toJSONString());
        return Entity;

    }

    public static By getCompleteElement(String element) throws Exception {
        By result = null;
        JSONObject Entity = ReadEntity(element);

        GetFieldBy = (String) Entity.get("GetFieldBy");
        ValueToFind = (String) Entity.get("ValueToFind");

        if ("className".equalsIgnoreCase(GetFieldBy)) {
            result = By.className(ValueToFind);
        } else if ("cssSelector".equalsIgnoreCase(GetFieldBy)) {
            result = By.cssSelector(ValueToFind);
        } else if ("id".equalsIgnoreCase(GetFieldBy)) {
            result = By.id(ValueToFind);
        } else if ("linkText".equalsIgnoreCase(GetFieldBy)) {
            result = By.linkText(ValueToFind);
        } else if ("name".equalsIgnoreCase(GetFieldBy)) {
            result = By.name(ValueToFind);
        } else if ("link".equalsIgnoreCase(GetFieldBy)) {
            result = By.partialLinkText(ValueToFind);
        } else if ("tagName".equalsIgnoreCase(GetFieldBy)) {
            result = By.tagName(ValueToFind);
        } else if ("xpath".equalsIgnoreCase(GetFieldBy)) {
            result = By.xpath(ValueToFind);
        }
        return result;
    }

    public String readProperties(String property) throws IOException {
        prop.load(in);
        return prop.getProperty(property);
    }

    public void SaveInScenario(String key, String text) {
        if (!this.ScenarioData.containsKey(key)) {
            this.ScenarioData.put(key,text);
            log.info(String.format("Save as Scenario Context key: %s with value: %s ", key,text));
            System.out.println((String.format("Save as Scenario Context key: %s with value: %s ", key,text)));
        } else {
            this.ScenarioData.replace(key,text);
            log.info(String.format("Update Scenario Context key: %s with value: %s ", key,text));
        }
    }

    public void RetrieveTestData(String parameter) throws IOException {
        Environment = readProperties("Environment");
        try {
            SaveInScenario(parameter, readProperties(parameter + "." + Environment));
            System.out.println(parameter + ": " + this.ScenarioData.get(parameter));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iSetElementWithKeyValue(String element, String key) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        boolean exist = this.ScenarioData.containsKey(key);
        if (exist){
            String text = this.ScenarioData.get(key);
            driver.findElement(SeleniumElement).sendKeys(text);
            log.info(String.format("Set on element %s with text %s", element, text));
            System.out.println((String.format("Set on element %s with text %s", element, text)));
        }else{
            Assert.assertTrue(String.format("The given key %s do not exist in Context", key), this.ScenarioData.containsKey(key));
        }
    }

    public void selectOptionDropdownByText(String element, String option) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Waiting Element: %s", element));
        Select opt = new Select(driver.findElement(SeleniumElement));
        log.info("Select option: " + option + "by text");
        opt.selectByVisibleText(option);
    }

    public Select selectOption(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Waiting Element: %s", element));
        Select opt = new Select(driver.findElement(SeleniumElement));
        return opt;
    }

    public void waitForElementPresent(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait w = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        log.info("Waiting for the element: " + element + " to be present");
        w.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement));
    }

    public void waitForElementVisible(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait w = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        log.info("Waiting for the element: " + element+ " to be visible");
        w.until(ExpectedConditions.visibilityOfElementLocated(SeleniumElement));
    }

    public void waitForElementClickable(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait w = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        log.info("Waiting for the element: " + element+ " to be visible");
        w.until(ExpectedConditions.elementToBeClickable(SeleniumElement));
    }

    public boolean isElementDisplayed(String element) throws Exception {
        try {
            By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
            log.info(String.format("Waiting Element: %s", element));
            WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
            isDisplayed = wait.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement)).isDisplayed();
        } catch (NoSuchElementException | TimeoutException e){
            isDisplayed = false;
            log.info(e);
        }
        log.info(String.format("%s visibility is: %s", element, isDisplayed));
        return isDisplayed;
    }

}
