package me.pyisoe.javatut.myapp;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MyAppApiController {

    @PostMapping(path="/api", produces="application/json")
    public List<Contact> submitText(@RequestBody Contact contact) {
        var contacts = new ArrayList<Contact>();
        contacts.add(contact);
        return contacts;
    }
}
