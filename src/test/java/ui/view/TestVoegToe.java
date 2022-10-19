package ui.view;

import static org.junit.Assert.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestVoegToe {
    private WebDriver driver;
    private final String validfName = "Bassine";
    private final String validlName = "Banani";
    private final String validStartDate = "2022-01-01";

    @Before
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get("http://localhost:8080/project_web2_Yassine_Sayadi_war_exploded/Controller?command=get_add");
    }

    @After
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void VoegToe_CorrectInput_RedirectToOverviewWithItemAdded() {
        WebElement fName = driver.findElement(By.name("firstname"));
        WebElement lName = driver.findElement(By.name("lastname"));
        WebElement startDate = driver.findElement(By.name("startdate"));

        WebElement button = driver.findElement(By.cssSelector("input[type=submit]"));

        fName.sendKeys(validfName);
        lName.sendKeys(validlName);
        startDate.sendKeys(validStartDate);

        button.click();

        assertEquals("Overview", driver.getTitle());

        assertTrue(driver.findElement(By.cssSelector("tr#" + validfName + "_" + validlName)) != null);
    }

    @Test
    public void VoegToe_FirstAnLastNameContainNumericAndAlphabeticCharacters_RedirectToAddPageWithErrorBanner() {
        WebElement fName = driver.findElement(By.name("firstname"));
        WebElement lName = driver.findElement(By.name("lastname"));
        WebElement startDate = driver.findElement(By.name("startdate"));

        WebElement button = driver.findElement(By.cssSelector("input[type=submit]"));

        fName.sendKeys("42affafaf0");
        lName.sendKeys("sfdgdfs4354fdgfg");
        startDate.sendKeys(validStartDate);

        button.click();

        assertEquals("Add", driver.getTitle());

        Boolean error = !(driver.findElement(By.id("error-banner")) == null);

        assertTrue(error);
    }

    @Test
    public void VoegToe_EmptyFirstAndLastName_RedirectToAddPageWithErrorBanner() {
        WebElement fName = driver.findElement(By.name("firstname"));
        WebElement lName = driver.findElement(By.name("lastname"));
        WebElement startDate = driver.findElement(By.name("startdate"));
        WebElement button = driver.findElement(By.cssSelector("input[type=submit]"));

        fName.sendKeys("");
        lName.sendKeys("");
        startDate.sendKeys(validStartDate);

        button.click();

        assertEquals("Add", driver.getTitle());

        Boolean error = !(driver.findElement(By.id("error-banner")) == null);

        assertTrue(error);
    }

    @Test
    public void VoegToe_FirstAnLastNameArePurelyNumeric_RedirectToAddPageWithErrorBanner() {
        WebElement fName = driver.findElement(By.name("firstname"));
        WebElement lName = driver.findElement(By.name("lastname"));
        WebElement startDate = driver.findElement(By.name("startdate"));
        WebElement button = driver.findElement(By.cssSelector("input[type=submit]"));

        fName.sendKeys("420");
        lName.sendKeys("23443254325345");
        startDate.sendKeys(validStartDate);

        button.click();

        assertEquals("Add", driver.getTitle());

        Boolean error = !(driver.findElement(By.id("error-banner")) == null);

        assertTrue(error);
    }

    @Test
    public void VoegToe_IncorrectDateOutOfBounds_RedirectToAddPageWithErrorBanner() {
        WebElement fName = driver.findElement(By.name("firstname"));
        WebElement lName = driver.findElement(By.name("lastname"));
        WebElement startDate = driver.findElement(By.name("startdate"));

        WebElement button = driver.findElement(By.cssSelector("input[type=submit]"));

        fName.sendKeys(validfName);
        lName.sendKeys(validlName);
        startDate.sendKeys("0000-00-00");

        button.click();

        assertEquals("Add", driver.getTitle());

        Boolean error = !(driver.findElement(By.id("error-banner")) == null);

        assertTrue(error);
    }

    @Test
    public void VoegToe_IncorrectDateEmpty_RedirectToAddPageWithErrorBanner() {
        WebElement fName = driver.findElement(By.name("firstname"));
        WebElement lName = driver.findElement(By.name("lastname"));
        WebElement startDate = driver.findElement(By.name("startdate"));

        WebElement button = driver.findElement(By.cssSelector("input[type=submit]"));

        fName.sendKeys(validfName);
        lName.sendKeys(validlName);
        startDate.sendKeys("");

        button.click();

        assertEquals("Add", driver.getTitle());

        Boolean error = !(driver.findElement(By.id("error-banner")) == null);

        assertTrue(error);
    }
}
