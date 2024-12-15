package ifsp.edu.br.task_list.controller;

import ifsp.edu.br.task_list.model.Projeto;
import ifsp.edu.br.task_list.model.Tarefa;
import ifsp.edu.br.task_list.service.ProjetoService;
import ifsp.edu.br.task_list.service.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projetos/{idProjeto}/kanban/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;
    private final ProjetoService projetoService;

    public TarefaController(TarefaService tarefaService, ProjetoService projetoService) {
        this.tarefaService = tarefaService;
        this.projetoService = projetoService;
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listarPorProjeto(@PathVariable Long idProjeto) {
        return ResponseEntity.ok(tarefaService.listarPorProjeto(idProjeto));
    }

    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa, @PathVariable Long idProjeto) {
        if (tarefa.getUsuario() == null || tarefa.getUsuario().getIdUsuario() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Projeto projeto = projetoService.buscarPorId(idProjeto);
        tarefa.setProjeto(projeto);

        Tarefa novaTarefa = tarefaService.salvar(tarefa);
        return ResponseEntity.ok(novaTarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(
            @PathVariable Long idProjeto, @PathVariable Integer id, @RequestBody Tarefa tarefa) {
        tarefa.setIdTarefa(id);
        tarefa.getProjeto().setIdProjeto(idProjeto);
        return ResponseEntity.ok(tarefaService.salvar(tarefa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long idProjeto, @PathVariable Integer id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Tarefa> atualizarStatus(
            @PathVariable Long idProjeto, @PathVariable Integer id, @RequestParam String status) {
        return ResponseEntity.ok(tarefaService.atualizarStatus(id, status));
    }
}
