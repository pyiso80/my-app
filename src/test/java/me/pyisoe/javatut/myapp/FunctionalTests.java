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
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FunctionalTests {


    @LocalServerPort
    int port;

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
        });
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void shouldReturnHtmlForRootPath() {
        driver.get("http://localhost:" + port);
        assertThat(driver.getTitle()).contains("My App");
    }

    @Test
    void canAddNewContact() {
        driver.get("http://localhost:" + port);

        WebElement firstNameInput = driver.findElement(By.name("firstName"));
        WebElement lastNameInput = driver.findElement(By.name("lastName"));
        WebElement phoneInput = driver.findElement(By.name("phone"));
        WebElement emailInput = driver.findElement(By.name("email"));
        firstNameInput.sendKeys("Pyi");
        lastNameInput.sendKeys("Soe");
        phoneInput.sendKeys("+9595005312");
        emailInput.sendKeys("pyisoe@gmail.com");

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contact-table tbody tr td:nth-child(1)")));


        List<WebElement> rows = driver.findElements(
                By.cssSelector("#contact-table tbody tr")
        );

        assertEquals(1, rows.size());
        String text = rows.getFirst().getDomProperty("innerHTML");
        assertTrue(text != null && text.contains("Pyi") && text.contains("pyisoe@gmail.com"));
    }

    @Test
    void canDisplayAllContacts() {
        driver.get("http://localhost:" + port);

        WebElement firstNameInput = driver.findElement(By.name("firstName"));
        WebElement lastNameInput = driver.findElement(By.name("lastName"));
        WebElement phoneInput = driver.findElement(By.name("phone"));
        WebElement emailInput = driver.findElement(By.name("email"));
        firstNameInput.sendKeys("Pyi");
        lastNameInput.sendKeys("Soe");
        phoneInput.sendKeys("+9595005312");
        emailInput.sendKeys("pyisoe@gmail.com");

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contact-table tbody tr td:nth-child(1)")));


        firstNameInput.clear();
        firstNameInput.sendKeys("Jason");
        lastNameInput.clear();
        lastNameInput.sendKeys("Soe");
        phoneInput.clear();
        phoneInput.sendKeys("+9595005333");
        emailInput.clear();
        emailInput.sendKeys("jasonsoe@gmail.com");

        button.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contact-table tbody tr td:nth-child(2)")));

        List<WebElement> rows = driver.findElements(
                By.cssSelector("#contact-table tbody tr")
        );
        assertEquals(2, rows.size());
        String text = rows.getFirst().getDomProperty("innerHTML");
        assertTrue(text != null && text.contains("Pyi") && text.contains("pyisoe@gmail.com"));
        text = rows.get(1).getDomProperty("innerHTML");
        assertTrue(text != null && text.contains("Jason") && text.contains("jasonsoe@gmail.com"));
    }

    @Test
    @Sql(scripts = "/contacts-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void canSearchContactsByFirstName() {
        driver.get("http://localhost:" + port);

        WebElement searchInput = driver.findElement(By.name("searchInput"));
        searchInput.sendKeys("Pyi");
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contact-table tbody tr")));

        List<WebElement> rows = driver.findElements(
                By.cssSelector("#contact-table tbody tr")
        );

        assertFalse(rows.isEmpty(), "No results found");

        // Verify at least one row contains "Pyi"
        boolean matchFound = rows.stream()
                .anyMatch(row -> row.getText().contains("Pyi"));

        assertTrue(matchFound, "Expected first name not found in results");
    }

    void canSearchContactsByLastName() {
        driver.get("http://localhost:" + port);

        WebElement searchInput = driver.findElement(By.name("searchInput"));
        searchInput.sendKeys("Pyi");
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contact-table tbody tr td:nth-child(1)")));

        List<WebElement> rows = driver.findElements(
                By.cssSelector("#contact-table tbody tr")
        );

        assertFalse(rows.isEmpty(), "No results found");

        // Verify at least one row contains "Pyi"
        boolean matchFound = rows.stream()
                .anyMatch(row -> row.getText().contains("Pyi"));

        assertTrue(matchFound, "Expected first name not found in results");
    }

}
