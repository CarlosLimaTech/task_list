document.addEventListener("DOMContentLoaded", function () {
    const usuarioId = document.querySelector(".user-info").getAttribute("data-usuario-id");
    const projetoId = document.querySelector(".kanban-board").getAttribute("data-projeto-id");

    carregarTarefas();

    function abrirModal(tarefa = null) {
        const modal = document.getElementById('tarefaModal');
        const modalTitle = document.getElementById('modalTitle');
        const form = document.getElementById('formTarefa');

        if (tarefa) {
            modalTitle.textContent = 'Editar Tarefa';
            document.getElementById('tarefaId').value = tarefa.idTarefa;
            document.getElementById('nome').value = tarefa.nome;
            document.getElementById('descricao').value = tarefa.descricaoTarefa;
            document.getElementById('prioridade').value = tarefa.prioridade;
            document.getElementById('status').value = tarefa.status;
            document.getElementById('dataInicio').value = tarefa.dataInicio;
            document.getElementById('dataFim').value = tarefa.dataFim;
        } else {
            modalTitle.textContent = 'Nova Tarefa';
            form.reset();
            document.getElementById('tarefaId').value = '';
        }

        modal.style.display = 'block';
    }

    function fecharModal() {
        document.getElementById('tarefaModal').style.display = 'none';
    }

    async function carregarTarefas() {
        try {
            const response = await fetch(`/projetos/${projetoId}/kanban/tarefas`);
            const tarefas = await response.json();

            document.querySelectorAll('.kanban-column .tarefas').forEach(column => {
                column.innerHTML = ''; // Limpa as colunas
            });

            tarefas.forEach(tarefa => adicionarTarefaAoKanban(tarefa));
        } catch (err) {
            console.error("Erro ao carregar tarefas:", err);
        }
    }

    function adicionarTarefaAoKanban(tarefa) {
        const statusClasses = {
            "Pendente": "pending",
            "Em Andamento": "in-progress",
            "Concluído": "completed"
        };

        const columnClass = statusClasses[tarefa.status];
        const column = document.querySelector(`.kanban-column.${columnClass} .tarefas`);

        if (column) {
            const taskCard = document.createElement('div');
            taskCard.className = 'task-card';

            // Ajusta a formatação das datas
            const dataInicioFormatada = formatarDataISO(tarefa.dataInicio);
            const dataFimFormatada = formatarDataISO(tarefa.dataFim);

            taskCard.innerHTML = `
                <span>${tarefa.nome}</span>
                <span>${dataInicioFormatada} - ${dataFimFormatada}</span>
                <span>${tarefa.prioridade}</span>
            `;

            column.appendChild(taskCard);
        } else {
            console.error(`Coluna para status "${tarefa.status}" não encontrada.`);
        }
    }



    document.getElementById('formTarefa').addEventListener('submit', async (event) => {
        event.preventDefault();

        const tarefaId = document.getElementById('tarefaId').value;
        const nome = document.getElementById('nome').value;
        const descricao = document.getElementById('descricao').value;
        const prioridade = document.getElementById('prioridade').value;
        const status = document.getElementById('status').value;
        const dataInicio = document.getElementById('dataInicio').value; // Já no formato YYYY-MM-DD
        const dataFim = document.getElementById('dataFim').value; // Sem alterações


        const url = tarefaId ? `/projetos/${projetoId}/kanban/tarefas/${tarefaId}` : `/projetos/${projetoId}/kanban/tarefas`;
        const method = tarefaId ? 'PUT' : 'POST';

        try {
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    idTarefa: tarefaId || null,
                    nome: nome,
                    descricaoTarefa: descricao,
                    prioridade: prioridade,
                    status: status,
                    dataInicio: dataInicio,
                    dataFim: dataFim,
                    projeto: { idProjeto: projetoId },
                    usuario: { idUsuario: usuarioId }
                }),
            });

            if (response.ok) {
                const novaTarefa = await response.json();

                if (tarefaId) {
                    carregarTarefas(); // Atualiza todas as tarefas no caso de edição
                } else {
                    adicionarTarefaAoKanban(novaTarefa); // Adiciona a nova tarefa ao DOM
                }

                fecharModal();
            } else {
                alert('Erro ao salvar a tarefa!');
            }
        } catch (err) {
            console.error('Erro ao salvar tarefa:', err);
        }
    });

    async function excluirTarefa(tarefaId) {
        if (confirm('Deseja realmente excluir esta tarefa?')) {
            try {
                const response = await fetch(`/projetos/${projetoId}/kanban/tarefas/${tarefaId}`, { method: 'DELETE' });

                if (response.ok) {
                    carregarTarefas();
                } else {
                    alert('Erro ao excluir a tarefa!');
                }
            } catch (err) {
                console.error('Erro ao excluir tarefa:', err);
            }
        }
    }

    document.querySelector('.nova-tarefa-btn').addEventListener('click', () => abrirModal());

    window.onclick = function (event) {
        const modal = document.getElementById('tarefaModal');
        if (event.target === modal) {
            fecharModal();
        }
    };

    function formatarDataISO(dataISO) {
        // Garantir que a data esteja no formato YYYY-MM-DD sem horário
        const partes = dataISO.split("T")[0].split("-");
        const [ano, mes, dia] = partes;
        return `${dia}/${mes}/${ano}`;
    }

});
