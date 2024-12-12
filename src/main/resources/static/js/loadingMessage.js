document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");

    if (form) {
        form.addEventListener("submit", function () {
            const modalContent = document.querySelector(".modal-content");

            if (modalContent) {
                modalContent.innerHTML = "<p>Processando...</p>";
            }

            form.submit();
        });
    }
});
