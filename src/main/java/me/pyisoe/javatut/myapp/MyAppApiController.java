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

    @PostMapping(path = "/api/contacts", produces = "application/json")
    public List<Contact> addNewContact(@RequestBody Contact contact) {
        contactRepo.insert(contact);
        return contactRepo.findAll();
    }

    @PutMapping(path = "/api/contacts/{id}")
    public ResponseEntity<Contact> updateContact(@RequestBody Contact contact, @PathVariable Long id) {
        int rows = contactRepo.update(id, contact);

        if (rows == 0) {
            return ResponseEntity.notFound().build();
        }

        // fetch updated object
        return contactRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
