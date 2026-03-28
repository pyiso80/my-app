package me.pyisoe.javatut.myapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyAppApiController {

    private final ContactRepo contactRepo;

    public MyAppApiController(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    @PostMapping(path = "/api", produces = "application/json")
    public List<Contact> addNewContact(@RequestBody Contact contact) {
        contactRepo.insert(contact);
        return contactRepo.findAll();
    }

    @GetMapping(path = "/api/contacts", produces = "application/json")
    public List<Contact> searchContact(@RequestParam String keyword) {
        return contactRepo.findByName(keyword);
    }

    @DeleteMapping(path = "/api/contacts/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        int cnt = contactRepo.deleteById(id);

        if (cnt == 1) {
            // Returns 204 No Content (standard for successful deletes)
            return ResponseEntity.noContent().build();
        } else {
            // Returns 404 Not Found if the ID doesn't exist
            return ResponseEntity.notFound().build();
        }
    }
}
