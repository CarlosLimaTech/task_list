package ifsp.edu.br.task_list.controller;

import ifsp.edu.br.task_list.model.Projeto;
import ifsp.edu.br.task_list.service.ProjetoService;
import ifsp.edu.br.task_list.service.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;
    private final UsuarioService usuarioService;

    public ProjetoController(ProjetoService projetoService, UsuarioService usuarioService) {
        this.projetoService = projetoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Projeto> listarProjetosPorUsuario(@PathVariable Long idUsuario) {
        return projetoService.listarProjetosPorUsuario(idUsuario);
    }

    @PostMapping
    public String criarProjeto(
            @RequestParam("nomeProjeto") String nomeProjeto,
            @RequestParam("descricaoProjeto") String descricaoProjeto,
            @RequestParam("dataInicio") String dataInicio,
            @RequestParam("dataFim") String dataFim,
            Principal principal) {
        String email = principal.getName();
        projetoService.criarProjeto(email, nomeProjeto, descricaoProjeto, dataInicio, dataFim);
        return "redirect:/projetos";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProjeto(@PathVariable Long id) {
        projetoService.deletarProjeto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public String listarTodosProjetos(Model model) {
        List<Projeto> projetos = projetoService.listarTodos();
        model.addAttribute("projetos", projetos);
        return "projetos"; // Nome da página HTML que será renderizada
    }
}
