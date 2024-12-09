package ifsp.edu.br.task_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ifsp.edu.br.task_list.model.Projeto;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    List<Projeto> findByUsuarioIdUsuario(Long idUsuario);

}


