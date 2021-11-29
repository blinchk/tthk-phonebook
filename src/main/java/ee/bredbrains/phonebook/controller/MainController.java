package ee.bredbrains.phonebook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping
    public RedirectView redirect() {
        return new RedirectView("https://phonebook.laus.me/");
    }
}
