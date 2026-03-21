package me.pyisoe.javatut.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyAppApiController {

    @Autowired
    ContactRepo contactRepo;

    @PostMapping(path="/api", produces="application/json")
    public List<Contact> submitText(@RequestBody Contact contact) {
        contactRepo.insert(contact);
        return contactRepo.findAll();
    }
}
