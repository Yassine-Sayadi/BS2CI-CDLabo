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

import java.util.ArrayList;
import java.util.List;

public class validHTML {

    WebDriver driver;
    String validName = "Bassine";
    ArrayList<String> commands = new ArrayList<>();

    @Before
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();


        commands.add("get_home");
        commands.add("get_overview");
        commands.add("get_add");
        commands.add("get_search");
        commands.add("get_confirm_delete");
    }

    @After
    public void cleanUp() {
        driver.quit();
    }
    
    @Test
    public void HTMLisValid (){
        for (String c :
                commands) {
            driver.get("https://validator.w3.org/");
            WebElement url = driver.findElement(By.id("uri"));
            url.sendKeys("http://cyclone3.uclllabs.be:8081/project_web2_Yassine_Sayadi_war_exploded/Controller?command=" + c);

            driver.findElement(By.cssSelector("a.submit")).click();

            assertTrue(driver.findElement(By.cssSelector("p.succes")) != null);
        }
    }

}
