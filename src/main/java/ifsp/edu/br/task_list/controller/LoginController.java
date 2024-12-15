package ifsp.edu.br.task_list.controller;

import ifsp.edu.br.task_list.model.Usuario;
import ifsp.edu.br.task_list.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String loginPage(@ModelAttribute("error") String error) {
        System.out.println("Erro recebido: " + error);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
            @RequestParam("password") String password) {
        Usuario usuario = usuarioService.findByEmail(email);

        if (usuario != null && usuarioService.validarSenha(usuario, password)) {
            return "redirect:/projetos";
        }

        return "redirect:/login?error=true";
    }

}
