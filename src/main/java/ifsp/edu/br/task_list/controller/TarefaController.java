package ifsp.edu.br.task_list.controller;

import ifsp.edu.br.task_list.model.Tarefa;
import ifsp.edu.br.task_list.service.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping("/projeto/{idProjeto}")
    public ResponseEntity<List<Tarefa>> listarPorProjeto(@PathVariable Long idProjeto) {
        return ResponseEntity.ok(tarefaService.listarPorProjeto(idProjeto));
    }

    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        return ResponseEntity.ok(tarefaService.salvar(tarefa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Integer id, @RequestBody Tarefa tarefa) {
        tarefa.setIdTarefa(id);
        return ResponseEntity.ok(tarefaService.salvar(tarefa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Integer id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Tarefa> atualizarStatus(@PathVariable Integer id, @RequestParam String status) {
        return ResponseEntity.ok(tarefaService.atualizarStatus(id, status));
    }
}
