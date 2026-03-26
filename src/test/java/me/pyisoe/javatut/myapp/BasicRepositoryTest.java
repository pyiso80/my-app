package me.pyisoe.javatut.myapp;

import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

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
    void canFindAllContacts() {
        contactRepo.insert(new Contact("Pyi", "Soe", "+9595005312", "pyisoe@gmail.com"));
        contactRepo.insert(new Contact("Jason", "Soe", "+9595005333", "jasonsoe@gmail.com"));

        List<Contact> contacts = contactRepo.findAll();

        assertEquals(2, contacts.size());
        assertEquals("jasonsoe@gmail.com", contacts.get(1).getEmail());
    }

    @Test
    @Sql(scripts = "classpath:contacts-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void canFindContactByName() {
        List<Contact> contacts = contactRepo.findByName("Pyi");
        assertEquals(1, contacts.size());
        assertEquals("Pyi", contacts.getFirst().getFirstName());
    }

}
