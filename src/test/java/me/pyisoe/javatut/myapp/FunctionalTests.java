package me.pyisoe.javatut.myapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jdbi.v3.core.Jdbi;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FunctionalTests {


    WebDriver driver;
    @Autowired
    Jdbi jdbi;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        jdbi.useHandle(handle -> {
            handle.execute("TRUNCATE TABLE contacts");
            long count = handle.createQuery("SELECT COUNT(*) FROM contacts")
                    .mapTo(Long.class)
                    .one();
            System.out.println("DEBUG: Row count after truncate: " + count);
        });
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void shouldReturnHtmlForRootPath() {
        driver.get("http://localhost:3000");
        assertThat(driver.getTitle()).contains("My App");
    }

    @Test
    void canAddNewContact() {
        driver.get("http://localhost:3000");

        var expectedContact1 = "Pyi Soe";

        WebElement input = driver.findElement(By.id("name-input"));
        input.sendKeys(expectedContact1);

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Wait until at least one row appears
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#contact-table tbody tr")
        ));

        List<WebElement> rows = driver.findElements(
                By.cssSelector("#contact-table tbody tr")
        );

        assertEquals(1, rows.size());
        assertEquals(expectedContact1, rows.getFirst().findElements(By.tagName("td")).getFirst().getText());
    }

    @Test
    void canDisplayAllContacts() {
        driver.get("http://localhost:3000");

        var expectedContact1 = "Pyi Soe";
        var expectedContact2 = "Jason Soe";

        WebElement input = driver.findElement(By.id("name-input"));
        input.sendKeys(expectedContact1);

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        input.clear();
        input.sendKeys(expectedContact2);
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Wait until at least one row appears
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#contact-table tbody tr")
        ));

        List<WebElement> rows = driver.findElements(
                By.cssSelector("#contact-table tbody tr")
        );

        // Assert
        assertEquals(2, rows.size());
        assertEquals(expectedContact1,
                rows.getFirst().findElements(By.tagName("td")).getFirst().getText());
        assertEquals(expectedContact2,
                rows.get(1).findElements(By.tagName("td")).getFirst().getText());

    }

}
