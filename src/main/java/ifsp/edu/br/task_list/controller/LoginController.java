package ifsp.edu.br.task_list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if ("admin".equals(username) && "123456".equals(password)) {
            return "redirect:/dashboard";
        }
        return "redirect:/login?error=true";
    }
}
