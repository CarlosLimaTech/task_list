DELETE FROM Tarefa;
DELETE FROM Projeto;
DELETE FROM Usuario;

INSERT INTO Usuario (username, email, password)
VALUES 
('Carlos Silva', 'carlos.silva@example.com', '123'),
('Ana Souza', 'ana.souza@example.com', '456');

INSERT INTO Projeto (id_usuario, nome_projeto, descricao_projeto, data_inicio, data_fim)
VALUES
(1, 'Projeto A', 'Descrição do Projeto A', '2024-01-01', '2024-06-01'),
(2, 'Projeto B', 'Descrição do Projeto B', '2024-01-01', '2024-06-01'),
(1, 'Projeto C', 'Descrição do Projeto C', '2024-01-01', '2024-06-01'),
(2, 'Projeto D', 'Descrição do Projeto D', '2024-01-01', '2024-06-01'),
(1, 'Projeto E', 'Descrição do Projeto E', '2024-01-01', '2024-06-01');

INSERT INTO Tarefa (id_projeto, id_usuario, nome, descricao_tarefa, prioridade, status, data_inicio, data_fim)
VALUES
(1, 1, 'Tarefa 1', 'Descrição da Tarefa 1', 'Alta', 'Em Andamento', '2024-01-10', '2024-01-20'),
(1, 1, 'Tarefa 2', 'Descrição da Tarefa 2', 'Média', 'Concluído', '2024-01-15', '2024-01-25'),
(1, 1, 'Tarefa 3', 'Descrição da Tarefa 3', 'Média', 'Pendente', '2024-01-15', '2024-01-25');
