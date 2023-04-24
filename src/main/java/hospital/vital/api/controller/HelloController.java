package hospital.vital.api.controller;

/**
 * @author Breno
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String olaMundo(){
        return "Hello World Spring!";
    }
}
