function confirmDelete(event) {
    event.preventDefault();
    const confirmed = confirm("Tem certeza de que deseja deletar este projeto?");
    if (confirmed) {
        event.target.submit();
    } else {
        return false;
    }
}
