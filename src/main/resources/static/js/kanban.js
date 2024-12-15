document.addEventListener("DOMContentLoaded", function () {
    // Captura os IDs do projeto e do usuário diretamente do DOM
    const usuarioId = document.querySelector(".user-info").getAttribute("data-usuario-id");
    const projetoId = document.querySelector(".kanban-board").getAttribute("data-projeto-id");

    // Inicializa o carregamento das tarefas
    carregarTarefas();

    // Abrir o modal para criar ou editar tarefa
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
    
            // Limpa as colunas antes de adicionar as tarefas
            document.querySelectorAll('.kanban-column .tarefas').forEach(column => {
                column.innerHTML = ''; // Garante que as colunas estejam vazias
            });
    
            // Adiciona as tarefas nas colunas correspondentes
            tarefas.forEach((tarefa) => {
                const columnClass = tarefa.status.toLowerCase().replace(' ', '-');
                const column = document.querySelector(`.kanban-column.${columnClass} .tarefas`);
    
                if (column) {
                    const taskCard = document.createElement('div');
                    taskCard.className = 'task-card';
                    taskCard.innerHTML = `
                        <strong>${tarefa.nome}</strong>
                        <p>${tarefa.descricaoTarefa || ''}</p>
                        <p>Prioridade: ${tarefa.prioridade || 'Sem prioridade'}</p>
                        <p>${tarefa.dataInicio} - ${tarefa.dataFim}</p>
                        <button onclick="abrirModal(${JSON.stringify(tarefa)})">Editar</button>
                        <button onclick="excluirTarefa(${tarefa.idTarefa})">Excluir</button>
                    `;
    
                    column.appendChild(taskCard);
                }
            });
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
        const column = document.querySelector(`.kanban-column.${columnClass}`);

        if (column) {
            const taskCard = document.createElement('div');
            taskCard.className = 'task-card';
            taskCard.dataset.id = tarefa.idTarefa;

            // Conteúdo do card
            const taskContent = `
                <p><strong>${tarefa.nome}</strong></p>
                <p>${tarefa.descricaoTarefa}</p>
                <p>Prioridade: ${tarefa.prioridade}</p>
                <p>${tarefa.dataInicio} - ${tarefa.dataFim}</p>
            `;
            taskCard.innerHTML = taskContent;

            // Botões
            const editButton = document.createElement('button');
            editButton.textContent = 'Editar';
            editButton.onclick = () => abrirModal(tarefa);

            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Excluir';
            deleteButton.onclick = () => excluirTarefa(tarefa.idTarefa);

            taskCard.appendChild(editButton);
            taskCard.appendChild(deleteButton);

            column.appendChild(taskCard);
        } else {
            console.error(`Coluna para status "${tarefa.status}" não encontrada.`);
        }
    }

    // Enviar dados para criar ou editar uma tarefa
    document.getElementById('formTarefa').addEventListener('submit', async (event) => {
        event.preventDefault();
    
        const tarefaId = document.getElementById('tarefaId').value;
        const nome = document.getElementById('nome').value;
        const descricao = document.getElementById('descricao').value;
        const prioridade = document.getElementById('prioridade').value;
        const status = document.getElementById('status').value;
        const dataInicio = document.getElementById('dataInicio').value;
        const dataFim = document.getElementById('dataFim').value;
    
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
                const novaTarefa = await response.json(); // Recupera a nova tarefa salva no backend
    
                if (tarefaId) {
                    // Se for edição, recarrega a lista completa
                    carregarTarefas();
                } else {
                    // Adiciona apenas a nova tarefa ao DOM
                    adicionarTarefaAoKanban(novaTarefa);
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

    const novaTarefaBtn = document.querySelector('.nova-tarefa-btn');
    if (novaTarefaBtn) {
        novaTarefaBtn.addEventListener('click', () => abrirModal());
    }

    window.onclick = function (event) {
        const modal = document.getElementById('tarefaModal');
        if (event.target === modal) {
            fecharModal();
        }
    };
});
