function confirmDelete(event, formElement) {
    event.preventDefault();
    const confirmed = confirm("Tem certeza de que deseja deletar este projeto?");
    if (confirmed) {
        fetch(formElement.action, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }
        })
        .then(response => {
            if (response.ok) {
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
