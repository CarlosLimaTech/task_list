INSERT INTO Usuario (id_usuario, username, email, password)
VALUES 
(1, 'Carlos Silva', 'carlos.silva@example.com', 'senha123'),
(2, 'Ana Souza', 'ana.souza@example.com', 'senha456');

INSERT INTO Projeto (id_projeto, id_usuario, nome_projeto, descricao_projeto, data_inicio, data_fim)
VALUES
(1, 1, 'Projeto A', 'Descrição do Projeto A', '2024-01-01', '2024-06-01'),
(2, 2, 'Projeto B', 'Descrição do Projeto B', '2024-03-01', '2024-09-01');

INSERT INTO Tarefa (id_tarefa, id_projeto, id_usuario, nome, descricao_tarefa, prioridade, status, data_inicio, data_fim)
VALUES
(1, 1, 1, 'Tarefa 1', 'Descrição da Tarefa 1', 'Alta', 'Em andamento', '2024-01-10', '2024-01-20'),
(2, 1, 1, 'Tarefa 2', 'Descrição da Tarefa 2', 'Média', 'Concluída', '2024-01-15', '2024-01-25'),
(3, 2, 2, 'Tarefa 3', 'Descrição da Tarefa 3', 'Baixa', 'Pendente', '2024-04-01', '2024-04-10');
