document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");

    if (form) {
        form.addEventListener("submit", function () {
            const modalContent = document.querySelector(".modal-content");

            // Exibe a mensagem de "Processando..." antes de enviar o formulário
            if (modalContent) {
                modalContent.innerHTML = "<p>Processando...</p>";
            }

            // Aguarda o envio do formulário e deixa o redirecionamento do servidor cuidar do resto
            form.submit();
        });
    }
});
