package ifsp.edu.br.task_list.controller;

import ifsp.edu.br.task_list.model.Projeto;
import ifsp.edu.br.task_list.model.Tarefa;
import ifsp.edu.br.task_list.model.Usuario;
import ifsp.edu.br.task_list.service.ProjetoService;
import ifsp.edu.br.task_list.service.TarefaService;
import ifsp.edu.br.task_list.service.UsuarioService;

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
    private final TarefaService tarefaService;

    public ProjetoController(ProjetoService projetoService, UsuarioService usuarioService,
            TarefaService tarefaService) {
        this.projetoService = projetoService;
        this.usuarioService = usuarioService;
        this.tarefaService = tarefaService;
    }

    @PostMapping("/criar")
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

    @PostMapping("/deletar/{id}")
    public String deletarProjeto(@PathVariable Long id) {
        projetoService.deletarProjeto(id);
        return "redirect:/projetos";
    }

    @GetMapping
    public String listarProjetosDoUsuarioLogado(Principal principal, Model model) {
        String email = principal.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email);
        List<Projeto> projetos = projetoService.listarProjetosPorUsuario(usuario.getIdUsuario());
        model.addAttribute("usuario", usuario);
        model.addAttribute("projetos", projetos);
        return "projetos";
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Projeto buscarProjetoPorId(@PathVariable Long id) {
        Projeto projeto = projetoService.buscarPorId(id);
        if (projeto == null) {
            throw new RuntimeException("Projeto não encontrado");
        }
        return projeto;
    }

    @PostMapping("/editar")
    public String salvarEdicaoProjeto(
            @RequestParam("id") Long id,
            @RequestParam("nomeProjeto") String nomeProjeto,
            @RequestParam("descricaoProjeto") String descricaoProjeto,
            @RequestParam("dataInicio") String dataInicio,
            @RequestParam("dataFim") String dataFim) {
        projetoService.atualizarProjeto(id, nomeProjeto, descricaoProjeto, dataInicio, dataFim);
        return "redirect:/projetos";
    }

    @GetMapping("/{idProjeto}/kanban")
    public String mostrarKanban(@PathVariable Long idProjeto, Model model) {
        Projeto projeto = projetoService.buscarPorId(idProjeto);
        if (projeto == null) {
            throw new RuntimeException("Projeto não encontrado");
        }

        List<Tarefa> tarefas = tarefaService.listarPorProjeto(idProjeto);
        model.addAttribute("usuario", projeto.getUsuario());
        model.addAttribute("projeto", projeto);
        model.addAttribute("tarefas", tarefas);

        return "kanban";
    }

}
