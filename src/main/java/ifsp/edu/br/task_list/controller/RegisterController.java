package ifsp.edu.br.task_list.controller;

import ifsp.edu.br.task_list.model.Usuario;
import ifsp.edu.br.task_list.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private final UsuarioRepository usuarioRepository;

    public RegisterController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
        // Verifica se o email já está registrado
        if (usuarioRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Email já cadastrado!");
            return "register";
        }

        // Salva o novo usuário no banco de dados sem o prefixo {noop}
        Usuario usuario = new Usuario();
        usuario.setUsername(name);
        usuario.setEmail(email);
        usuario.setPassword(password); // Apenas salva a senha pura
        usuarioRepository.save(usuario);

        // Redireciona para a página de login após o cadastro
        return "redirect:/login?success=true";
    }

}
