function confirmDelete(event, formElement) {
    event.preventDefault(); // Impede a submissão padrão do formulário
    const confirmed = confirm("Tem certeza de que deseja deletar este projeto?");
    if (confirmed) {
        // Submete o formulário de exclusão
        fetch(formElement.action, {
            method: "POST", // Método utilizado pelo Spring Boot para exclusão
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }
        })
        .then(response => {
            if (response.ok) {
                // Recarrega a página se a exclusão for bem-sucedida
                window.location.reload();
            } else {
                alert("Erro ao deletar o projeto.");
            }
        })
        .catch(error => {
            console.error("Erro ao processar a solicitação:", error);
            alert("Erro ao deletar o projeto.");
        });
    }
}
