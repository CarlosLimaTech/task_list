document.addEventListener("DOMContentLoaded", function () {
    const kanbanBoard = document.querySelector(".kanban-board");
    const projetoId = kanbanBoard.getAttribute("data-projeto-id");

    console.log("ID do Projeto:", projetoId);

    fetch("/tarefas/projeto/" + projetoId)
        .then((response) => {
            console.log("Status da Resposta:", response.status);
            return response.json();
        })
        .then((tarefas) => {
            console.log("Tarefas retornadas:", tarefas);
            tarefas.forEach((tarefa) => {
                const column = document.querySelector(
                    `.kanban-column.${tarefa.status.toLowerCase().replace(" ", "-")}`
                );
                if (!column) {
                    console.error(`Coluna não encontrada para status: ${tarefa.status}`);
                    return;
                }
                const taskCard = document.createElement("div");
                taskCard.className = "task-card";
                taskCard.textContent = tarefa.nome;
                taskCard.dataset.id = tarefa.idTarefa;
                column.appendChild(taskCard);
            });
        })
        .catch((err) => console.error("Erro ao buscar tarefas:", err));
});
