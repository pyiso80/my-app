package me.pyisoe.javatut.myapp;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyAppApiController {

    private final ContactRepo contactRepo;

    public MyAppApiController(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    @PostMapping(path="/api", produces="application/json")
    public List<Contact> addNewContact(@RequestBody Contact contact) {
        contactRepo.insert(contact);
        return contactRepo.findAll();
    }

    @GetMapping(path="/api/contacts", produces="application/json")
    public List<Contact> searchContact(@RequestParam String keyword) {
        return contactRepo.findByName(keyword);
    }

    @DeleteMapping(path="/api/contacts/{id}", produces="application/json")
    public void deleteContact(@PathVariable Long id) {
        contactRepo.deleteById(id);
    }
}
