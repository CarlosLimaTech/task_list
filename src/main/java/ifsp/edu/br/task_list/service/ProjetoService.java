package ifsp.edu.br.task_list.service;

import ifsp.edu.br.task_list.model.Projeto;
import ifsp.edu.br.task_list.model.Usuario;
import ifsp.edu.br.task_list.repository.ProjetoRepository;
import ifsp.edu.br.task_list.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;

    public ProjetoService(ProjetoRepository projetoRepository, UsuarioRepository usuarioRepository) {
        this.projetoRepository = projetoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Projeto> listarProjetosPorUsuario(Integer idUsuario) {
        return projetoRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public void criarProjeto(String email, String nomeProjeto, String descricaoProjeto, String dataInicio, String dataFim) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date inicio = formatter.parse(dataInicio);
            Date fim = formatter.parse(dataFim);

            Projeto projeto = new Projeto();
            projeto.setNomeProjeto(nomeProjeto);
            projeto.setDescricaoProjeto(descricaoProjeto);
            projeto.setDataInicio(inicio);
            projeto.setDataFim(fim);
            projeto.setUsuario(usuario);

            projetoRepository.save(projeto);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao converter as datas.", e);
        }
    }

    public void deletarProjeto(Long id) {
        projetoRepository.deleteById(id);
    }

    public List<Projeto> listarTodos() {
        return projetoRepository.findAll();
    }

    public Projeto buscarPorId(Long id) {
        return projetoRepository.findById(id).orElse(null);
    }

    public void atualizarProjeto(Long id, String nomeProjeto, String descricaoProjeto, String dataInicio, String dataFim) {
        Projeto projeto = projetoRepository.findById(id).orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            projeto.setNomeProjeto(nomeProjeto);
            projeto.setDescricaoProjeto(descricaoProjeto);
            projeto.setDataInicio(formatter.parse(dataInicio));
            projeto.setDataFim(formatter.parse(dataFim));

            projetoRepository.save(projeto);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao converter as datas.", e);
        }
    }
}
