package me.pyisoe.javatut.myapp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
