package me.pyisoe.javatut.myapp;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Controller
public class MyAppController {
    @GetMapping(value = "/")
    public ResponseEntity<String> home() throws Exception {
        ClassPathResource resource = new ClassPathResource("static/index.html");

        try (InputStream is = resource.getInputStream()) {
            String html = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return ResponseEntity.ok(html);
        }
    }
}
