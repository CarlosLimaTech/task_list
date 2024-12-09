document.addEventListener("DOMContentLoaded", () => {
    // Seleciona os elementos do modal
    const openModalButton = document.getElementById("openModalButton");
    const closeModalButton = document.getElementById("closeModalButton");
    const modal = document.getElementById("createProjectModal");

    // Adiciona evento de clique para abrir o modal
    if (openModalButton) {
        openModalButton.addEventListener("click", () => {
            modal.style.display = "block";
        });
    }

    // Adiciona evento de clique para fechar o modal
    if (closeModalButton) {
        closeModalButton.addEventListener("click", () => {
            modal.style.display = "none";
        });
    }

    // Fecha o modal ao clicar fora do conteúdo do modal
    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
});
