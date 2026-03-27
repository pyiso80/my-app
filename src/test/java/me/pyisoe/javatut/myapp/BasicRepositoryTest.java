package me.pyisoe.javatut.myapp;

import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import({ContactRepo.class, JdbiConfiguration.class })
public class BasicRepositoryTest {

    @Autowired
    ContactRepo contactRepo;

    @Autowired
    private Jdbi jdbi; //

    @Test
    @Sql(scripts = "classpath:cleanup.sql")
    void shouldSaveAContact() {
        contactRepo.insert(new Contact("Pyi", "Soe", "+9595005312", "pyisoe@gmail.com"));
        jdbi.useHandle(handle -> {
            var count = handle.createQuery("SELECT COUNT(*) FROM contacts WHERE first_name = :first_name AND last_name = :last_name")
                    .bind("first_name", "Pyi")
                    .bind("last_name", "Soe")
                    .mapTo(Integer.class)
                    .one();
            assert(count == 1);
        });
    }

    @Test
    @Sql(scripts = "classpath:contacts-data.sql")
    void canFindAllContacts() {
        List<Contact> contacts = contactRepo.findAll();

        assertEquals(6, contacts.size());
        assertEquals("john.doe@example.com", contacts.get(1).getEmail());
    }

    @Test
    @Sql(scripts = "classpath:contacts-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void canFindContactByName() {
        List<Contact> contacts = contactRepo.findByName("Pyi");
        assertEquals(1, contacts.size());
        assertEquals("Pyi", contacts.getFirst().getFirstName());
    }

    @Test
    @Sql(scripts = "classpath:contacts-data.sql")
    void canDeleteById() {
        var results1 = contactRepo.findAll();
        contactRepo.deleteById(results1.get(2).getId());
        var result2 = contactRepo.findAll();
        assertEquals(1, results1.size() - result2.size());
    }

}
