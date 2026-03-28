package me.pyisoe.javatut.myapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/cleanup.sql", executionPhase = BEFORE_TEST_METHOD)
public class FunctionalTests {


    @LocalServerPort
    int port;

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
    @Sql(scripts = "/contacts-data.sql")
    void search_by_name_found_only_matching_contacts() {
        driver.get("http://localhost:" + port + "/contacts");

        WebElement searchInput = driver.findElement(By.name("keyword"));
        searchInput.sendKeys("Soe");
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
                .anyMatch(row -> row.getText().contains("Soe"));

        assertTrue(matchFound, "Expected first name not found in results");
        assertEquals(2, rows.size());
    }

    @Test
    @Sql(scripts = "/contacts-data.sql")
    void search_by_name_found_all_contacts_for_empty_string() {
        driver.get("http://localhost:" + port + "/contacts");

        WebElement searchInput = driver.findElement(By.name("keyword"));
        searchInput.sendKeys("");
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contact-table tbody tr")));

        List<WebElement> rows = driver.findElements(
                By.cssSelector("#contact-table tbody tr")
        );
        assertEquals(6, rows.size());
    }

    @Test
    @Sql(scripts = "/cleanup.sql")
    void search_by_name_show_correct_message_if_no_contact_found() {

        driver.get("http://localhost:" + port + "/contacts");

        WebElement searchInput = driver.findElement(By.name("keyword"));
        searchInput.sendKeys("Vladimir");
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-result-msg")));

        List<WebElement> elements = driver.findElements(
                By.id("search-result-msg")
        );
        assertTrue(elements.getFirst().getText().toLowerCase().contains("no result"));
    }

    @Test
    @Sql(scripts = "/contacts-data.sql")
    void search_by_name_should_not_show_no_result_message_if_any_found() {
        driver.get("http://localhost:" + port + "/contacts");

        WebElement searchInput = driver.findElement(By.name("keyword"));
        searchInput.sendKeys("Soe");
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contact-table tbody tr")));

        List<WebElement> noResult = driver.findElements(
                By.id("search-result-msg")
        );
        assertTrue(noResult.isEmpty());
    }

    @Test
    @Sql(scripts = "/contacts-data.sql")
    void delete_existing_contact() {
        driver.get("http://localhost:" + port + "/contacts");

        WebElement searchInput = driver.findElement(By.name("keyword"));
        searchInput.sendKeys("");
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contact-table tbody tr")));

        WebElement rowToDelete = driver.findElement(
                By.xpath("//table[@id='contact-table']//tbody/tr[3]")
        );
        var deleteButton = rowToDelete.findElement(By.cssSelector("[data-testid='delete-contact']"));

        // switch to confirm dialog and accept
        deleteButton.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contact-table tbody tr")));
        List<WebElement> rows = driver.findElements(
                By.cssSelector("#contact-table tbody tr")
        );

        assertFalse(rows.isEmpty(), "No results found");
        assertEquals(5, rows.size());
        boolean matchFound = rows.stream()
                .noneMatch(row -> row.getText().contains("jane.doe@example.com"));
        assertTrue(matchFound, "Delete row still in table!");
    }

    @Test
    @Sql(scripts = "/contacts-data.sql")
    void update_contact() {
        driver.get("http://localhost:" + port + "/contacts");

        WebElement searchInput = driver.findElement(By.name("keyword"));
        searchInput.sendKeys("");
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contact-table tbody tr")));

        WebElement rowToDelete = driver.findElement(
                By.xpath("//table[@id='contact-table']//tbody/tr[3]")
        );
        var updateButton = rowToDelete.findElement(By.cssSelector("[data-testid='update-contact']"));

        updateButton.click();

        // 2. Wait for modal and update Name field
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-name")));
        nameInput.clear();
        nameInput.sendKeys("New Name");
        driver.findElement(By.id("save-btn")).click();

        // 3. Verify the 3rd row now shows "New Name"
        WebElement updatedCell = driver.findElement(By.xpath("//table[@id='contact-table']//tr[3]/td[2]"));
        assertEquals("New Name", updatedCell.getText());

    }


}
