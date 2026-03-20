package me.pyisoe.javatut.myapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionalTests {

    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void shouldReturnHtmlForRootPath() {
        driver.get("http://localhost:8080");
        assertThat(driver.getTitle()).contains("My App");
    }

    @Test
    void canPostSomeTextViaHome() {
        try {
            driver.get("http://localhost:8080");

            WebElement input = driver.findElement(By.id("name-input"));
            input.sendKeys("Pyi Soe");

            WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
            button.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement result = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("result-text"))
            );

            assertThat(result.getText()).contains("Pyi Soe");

        } finally {
            driver.quit();
        }
    }

}
