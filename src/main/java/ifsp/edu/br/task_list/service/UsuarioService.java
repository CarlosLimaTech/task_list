package ifsp.edu.br.task_list.service;

import ifsp.edu.br.task_list.model.Usuario;
import ifsp.edu.br.task_list.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public boolean validarSenha(Usuario usuario, String senha) {
        return usuario != null && usuario.getPassword().equals(senha);
    }    
}
