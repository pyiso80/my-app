package me.pyisoe.javatut.myapp;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MyAppApiController {

    @PostMapping("/")
    public Map<String, String> submitText(@RequestBody String name) {
        return Map.of("name", name);
    }
}
