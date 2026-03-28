package me.pyisoe.javatut.myapp;

import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ContactRepo {

    private final Jdbi jdbi;

    public ContactRepo(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public Integer insert(Contact contact) {
        String sql = """
                INSERT INTO contacts (first_name, last_name, phone, email)
                VALUES (:firstName, :lastName, :phone, :email)
                """;
        return jdbi.withHandle(handle -> handle.createUpdate(sql)
                .bindBean(contact)
                .executeAndReturnGeneratedKeys()
                .mapTo(Integer.class)
                .one());
    }

    public List<Contact> findAll() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT id, first_name, last_name, phone, email FROM contacts")
                        .mapToBean(Contact.class)
                        .list()
        );
    }

    public List<Contact> findByName(String name) {
        return jdbi.withHandle(handle ->
                handle.createQuery("""
                                SELECT
                                    id, first_name, last_name, phone, email
                                FROM contacts
                                WHERE first_name LIKE '%' || :name || '%'
                                      OR last_name LIKE '%' || :name || '%'
                                """)
                        .bind("name", name)
                        .mapToBean(me.pyisoe.javatut.myapp.Contact.class)
                        .list()
        );
    }

    public int deleteById(Long id) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM contacts WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
    }

    public Optional<Contact> findById(Long id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("""
                                SELECT
                                    id, first_name, last_name, phone, email
                                FROM contacts
                                WHERE :id = id
                                """)
                        .bind("id", id)
                        .mapToBean(Contact.class)
                        .findOne()
        );
    }

    public int update(Long id, Contact contact) {
        return jdbi.withHandle(handle -> handle.createUpdate("""
                UPDATE contacts SET 
                    first_name=:firstName,
                    last_name=:lastName,
                    phone=:phone,
                    email=:email 
                WHERE
                    id = :id
                """).bindBean(contact).bind("id", id).execute());
    }
}
