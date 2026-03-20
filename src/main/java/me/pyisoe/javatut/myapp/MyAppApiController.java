package me.pyisoe.javatut.myapp;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MyAppApiController {

    @PostMapping("/")
    public Map<String, String> submitText(@RequestBody JsonNode json) {
        String name = json.get("name").asText();
        return Map.of("name", name);
    }
}
