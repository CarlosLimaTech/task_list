package ifsp.edu.br.task_list.service;

import ifsp.edu.br.task_list.model.Tarefa;
import ifsp.edu.br.task_list.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<Tarefa> listarPorProjeto(Long idProjeto) {
        List<Tarefa> tarefas = tarefaRepository.findByProjetoIdProjeto(idProjeto);
        System.out.println("Tarefas carregadas: " + tarefas.size());
        return tarefas;
    }
    

    public Tarefa salvar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public void deletar(Integer id) {
        tarefaRepository.deleteById(id);
    }

    public Tarefa atualizarStatus(Integer id, String status) {
        Optional<Tarefa> optionalTarefa = tarefaRepository.findById(id);
        if (optionalTarefa.isPresent()) {
            Tarefa tarefa = optionalTarefa.get();
            tarefa.setStatus(status);
            return tarefaRepository.save(tarefa);
        } else {
            throw new RuntimeException("Tarefa não encontrada!");
        }
    }
}
