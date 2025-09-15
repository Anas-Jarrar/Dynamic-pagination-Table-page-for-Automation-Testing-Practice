package org.example;

import Pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.time.Duration;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public static final String MAIN_URL="https://practice.expandtesting.com/dynamic-pagination-table";
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public void navigate(String url){
        driver.get(url);

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
//       driver.quit();
    }
}
