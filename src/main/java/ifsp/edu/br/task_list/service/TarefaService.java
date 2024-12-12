package ifsp.edu.br.task_list.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ifsp.edu.br.task_list.model.Tarefa;
import ifsp.edu.br.task_list.repository.TarefaRepository;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<Tarefa> listarPorProjetoEStatus(Long idProjeto, String status) {
        return tarefaRepository.findByProjetoIdProjetoAndStatus(idProjeto, status);
    }
}
