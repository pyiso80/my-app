package me.pyisoe.javatut.myapp;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactRepo {

    @Autowired
    private Jdbi jdbi;

    public Integer insert(Contact contact) {
        String sql = """
                INSERT INTO contacts (name)
                VALUES (:name)
                """;
        return jdbi.withHandle(handle -> handle.createUpdate(sql)
                .bindBean(contact)
                .executeAndReturnGeneratedKeys()
                .mapTo(Integer.class)
                .one());
    }

    public List<Contact> findAll() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT id, name FROM contacts")
                        .mapToBean(Contact.class)
                        .list()
        );
    }
}
