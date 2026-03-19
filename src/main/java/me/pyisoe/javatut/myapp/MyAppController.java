package me.pyisoe.javatut.myapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyAppController {
    @GetMapping(value = "/")
    @ResponseBody
    public String home() {
        return """
            <html>
                <head>
                    <title>My App</title>
                </head>
            </html>
        """;
    }
}
