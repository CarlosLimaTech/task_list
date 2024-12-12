document.addEventListener("DOMContentLoaded", () => {
    const openModalButton = document.getElementById("openModalButton");
    const closeModalButton = document.getElementById("closeModalButton");
    const createModal = document.getElementById("createProjectModal");

    const editModal = document.getElementById("editProjectModal");
    const closeEditModalButton = document.getElementById("closeEditModalButton");

    if (openModalButton) {
        openModalButton.addEventListener("click", () => {
            createModal.style.display = "block";
        });
    }

    if (closeModalButton) {
        closeModalButton.addEventListener("click", () => {
            createModal.style.display = "none";
        });
    }

    if (closeEditModalButton) {
        closeEditModalButton.addEventListener("click", () => {
            editModal.style.display = "none";
        });
    }

    window.addEventListener("click", (event) => {
        if (event.target === createModal) {
            createModal.style.display = "none";
        }
        if (event.target === editModal) {
            editModal.style.display = "none";
        }
    });

    document.querySelectorAll(".edit-btn").forEach(button => {
        button.addEventListener("click", async (event) => {
            event.preventDefault();
            const url = button.getAttribute("href");

            try {
                const response = await fetch(url, { method: 'GET' });
                if (!response.ok) throw new Error("Erro ao carregar os dados do projeto.");

                const projeto = await response.json();

                document.getElementById("editProjectId").value = projeto.idProjeto;
                document.getElementById("editNomeProjeto").value = projeto.nomeProjeto;
                document.getElementById("editDescricaoProjeto").value = projeto.descricaoProjeto;
                document.getElementById("editDataInicio").value = projeto.dataInicio.split('T')[0];
                document.getElementById("editDataFim").value = projeto.dataFim.split('T')[0];

                editModal.style.display = "block";
            } catch (error) {
                alert(error.message);
            }
        });
    });
});
