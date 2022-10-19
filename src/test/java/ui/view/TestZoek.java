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

import java.util.List;

public class TestZoek {
    WebDriver driver;
    String validName = "Bassine";

    @Before
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get("http://localhost:8080/project_web2_Yassine_Sayadi_war_exploded/Controller?command=get_search");
    }

    @After
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void Search_ExistentItemByFirstOrLastName() {
        WebElement search = driver.findElement(By.name("query"));
        search.sendKeys(validName);

        WebElement button = driver.findElement(By.cssSelector("input[type=submit]"));
        button.click();

        assertEquals("Search", driver.getTitle());

        List<WebElement> result = driver.findElements(By.tagName("tr"));

        assertTrue(result.size() > 0);
    }


}