package ifsp.edu.br.task_list.repository;

import ifsp.edu.br.task_list.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {
    List<Tarefa> findByProjetoIdProjetoAndStatus(Long idProjeto, String status);
}

