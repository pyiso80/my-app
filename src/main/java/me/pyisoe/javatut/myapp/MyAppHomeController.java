package me.pyisoe.javatut.myapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyAppHomeController {
    @GetMapping(value = {
            "/{path:^(?!api$)[^.]*}",
            "/**/{path:^(?!api$)[^.]*}"
    })
    public String home() throws Exception {
        return "forward:/index.html";
    }
}
