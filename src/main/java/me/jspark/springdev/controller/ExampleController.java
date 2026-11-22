package me.jspark.springdev.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleController {
    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(){
        return "example";
    }

    @GetMapping("/thymeleaf/exmaple2")
    public String thymeleafExample2(Model model){
        Person person = new Person();
        person.setName("jspark");
        person.setAge(20);

        model.addAttribute(person);
        return "example";
    }
}

@Getter
@Setter
class Person {
    private String name;
    private int age;
    private String address;
}
