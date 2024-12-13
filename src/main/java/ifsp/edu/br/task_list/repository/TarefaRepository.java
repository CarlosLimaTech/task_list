package ifsp.edu.br.task_list.repository;

import ifsp.edu.br.task_list.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {
    List<Tarefa> findByProjetoIdProjeto(Long idProjeto);
}
