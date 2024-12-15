package ifsp.edu.br.task_list.controller;

import ifsp.edu.br.task_list.model.Usuario;
import ifsp.edu.br.task_list.repository.UsuarioRepository;
import ifsp.edu.br.task_list.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public RegisterController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {

        if (usuarioRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Email já cadastrado!");
            return "register";
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(name);
        usuario.setEmail(email);
        usuario.setPassword(password);

        usuarioService.salvarUsuario(usuario);

        return "redirect:/login?success=true";
    }

}
