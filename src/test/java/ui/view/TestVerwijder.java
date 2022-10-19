package ui.view;

import static org.junit.Assert.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class TestVerwijder {
    WebDriver driver;
    @Before
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get("http://localhost:8080/project_web2_Yassine_Sayadi_war_exploded/Controller?command=get_overview");
    }

    @After
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void Delete_ExistentItem_NavigateToConfirmAndOverview(){
        List<WebElement> tableBeforeDelete = driver.findElements(By.tagName("tr"));
        int numberOfItemsBeforeDeletion = tableBeforeDelete.size();

        WebElement delete = driver.findElement(By.cssSelector("input[type=button]"));
        delete.click();

        assertEquals("Confirm", driver.getTitle());

        WebElement confirm = driver.findElement(By.cssSelector("input[type=submit]"));
        confirm.click();

        assertEquals("Overview", driver.getTitle());

        List<WebElement> tableAfterDelete = driver.findElements(By.tagName("tr"));
        int numberOfItemsAfterDeletion = tableAfterDelete.size();

        assertTrue(numberOfItemsBeforeDeletion - numberOfItemsAfterDeletion == 1);
    }


}
