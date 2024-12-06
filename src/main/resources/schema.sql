CREATE TABLE IF NOT EXISTS Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45),
    email VARCHAR(45),
    password VARCHAR(45)
);

CREATE TABLE IF NOT EXISTS Projeto (
    id_projeto INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    nome_projeto VARCHAR(45),
    descricao_projeto VARCHAR(45),
    data_inicio DATE,
    data_fim DATE,
    CONSTRAINT fk_projeto_usuario FOREIGN KEY (id_usuario)
    REFERENCES Usuario (id_usuario)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS Tarefa (
    id_tarefa INT AUTO_INCREMENT PRIMARY KEY,
    id_projeto INT NOT NULL,
    id_usuario INT NOT NULL,
    nome VARCHAR(45),
    descricao_tarefa VARCHAR(45),
    prioridade VARCHAR(45),
    status VARCHAR(45),
    data_inicio DATE,
    data_fim DATE,
    CONSTRAINT fk_tarefa_projeto FOREIGN KEY (id_projeto)
    REFERENCES Projeto (id_projeto)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT fk_tarefa_usuario FOREIGN KEY (id_usuario)
    REFERENCES Usuario (id_usuario)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
