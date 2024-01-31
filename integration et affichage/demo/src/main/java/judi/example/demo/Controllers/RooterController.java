package judi.example.demo.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * TestControllers
 */
@Controller
public class RooterController {

    @GetMapping("/")
    public String index() {
        return "index"; // Ceci renvoie "index.jsp"
    }

    


}