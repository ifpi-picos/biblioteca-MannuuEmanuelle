# Biblioteca de Empréstimos
Este projeto implementa um sistema simples de gerenciamento de empréstimos de livros em uma biblioteca. O sistema permite adicionar livros, listar livros disponíveis e emprestados, registrar empréstimos e devoluções, e visualizar o histórico de empréstimos.

## Funcionalidades
### Cadastrar livros: 
Adicione novos livros à biblioteca com informações como título, autor, gênero, editora, ano de publicação, status (disponível ou emprestado), ISBN e código.

### Pegar livros emprestados: 
Realize o empréstimo de um livro, alterando seu status para "Emprestado" e registrando as datas de empréstimo e devolução.
### Devolver livros: 
Devolva um livro, alterando seu status para "Disponível" e atualizando a data de devolução.
### Listar livros: 
Exibe todos os livros da biblioteca ou filtra por livros emprestados ou disponíveis.
### Histórico de empréstimos: 
Exibe o histórico completo de empréstimos realizados, com informações sobre o livro, data de empréstimo e data de devolução.

## Estrutura do Projeto
O projeto é composto pelas seguintes classes principais:

### Biblioteca: 
Gerencia os livros e os empréstimos. Inclui métodos para adicionar livros, listar livros, pegar livros emprestados, devolvê-los e manter o histórico de empréstimos.
### Emprestimo: 
Representa o empréstimo de um livro, armazenando o nome do livro, data de empréstimo e data de devolução.
### Livro: 
Representa um livro na biblioteca, com informações sobre o título, autor, gênero, editora, ano de publicação, status (disponível ou emprestado), ISBN e código.
### Usuario: 
Representa um usuário do sistema, com informações como nome, CPF e e-mail. Em um aprimoramento futuro, será possível associar os empréstimos aos usuários.
### App: 
Interface principal do usuário, onde é possível interagir com o sistema de biblioteca. Apresenta um menu no console para o usuário escolher as ações.