document.addEventListener("DOMContentLoaded", () => {
    const openModalButton = document.getElementById("openModalButton");
    const closeModalButton = document.getElementById("closeModalButton");
    const modal = document.getElementById("createProjectModal");

    if (openModalButton) {
        openModalButton.addEventListener("click", () => {
            modal.style.display = "block";
        });
    }

    if (closeModalButton) {
        closeModalButton.addEventListener("click", () => {
            modal.style.display = "none";
        });
    }

    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
});
