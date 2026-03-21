package me.pyisoe.javatut.myapp;

import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import({ContactRepo.class, JdbiConfiguration.class })
public class BasicRepositoryTest {

    @Autowired
    ContactRepo contactRepo;

    @Autowired
    private Jdbi jdbi; //

    @Test
    void shouldSaveAContact() {
        contactRepo.insert(new Contact("Pyi Soe"));
        jdbi.useHandle(handle -> {
            var count = handle.createQuery("SELECT COUNT(*) FROM contacts WHERE name = :name")
                    .bind("name", "Pyi Soe")
                    .mapTo(Integer.class)
                    .one();

            assert(count == 1);
        });
    }

}
