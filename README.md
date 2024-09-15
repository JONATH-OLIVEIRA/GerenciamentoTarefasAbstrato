# Sistema de Gerenciamento de Tarefas

Este projeto é uma API RESTful para gerenciamento de tarefas, desenvolvida com Java 17, utilizando Spring Boot, PostgreSQL como banco de dados e Swagger para documentação.

### Tecnologias Utilizadas
*Java 17: Linguagem de programação usada no backend.
Spring Boot: Framework usado para a criação da API.
PostgreSQL: Banco de dados relacional utilizado para persistência de dados.
Swagger: Utilizado para documentar a API e facilitar a visualização dos endpoints.
JUnit 5: Ferramenta para testes unitários.
Mockito: Framework para mock de dependências nos testes.
Maven : Gerenciamento de dependencias.
Configurações de Banco de Dados
Este projeto utiliza o PostgreSQL. Certifique-se de que o banco de dados esteja configurado corretamente. No arquivo application.properties, as configurações de conexão estão definidas como:


### Configure o banco de dados PostgreSQL e ajuste as propriedades do banco no arquivo application.properties.
spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciamento_tarefas
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update

### Documentação da API
A documentação da API está disponível via Swagger. Para acessá-la, inicie o servidor e navegue até:
http://localhost:8080/swagger-ui.html

### Clone o repositório:
git clone https://github.com/seu-usuario/sistema-gerenciamento-tarefas.git

### Acesse a documentação Swagger no navegador:
http://localhost:8080/swagger-ui.html

## Endpoints

### 1. **Listar Todos os Itens**
- **Método**: `GET`
- **URL**: `/api/itens`
- **Descrição**: Retorna uma lista de todos os itens.

### 2. **Buscar Itens por Lista**
- **Método**: `GET`
- **URL**: `/api/itens?listaId={listaId}`
- **Parâmetro**: `listaId` - O ID da lista para filtrar os itens.
- **Descrição**: Retorna todos os itens de uma lista específica.

### 3. **Criar um Novo Item**
- **Método**: `POST`
- **URL**: `/api/itens`
- **Corpo da Requisição**:
  json
  {
    "descricao": "Descrição do item",
    "estado": "NEUTRO",
    "prioridade": "BAIXA"
  }
### 4. Atualizar um Item
**Método**: PUT
**URL**: /api/itens/{id}
**Parâmetro**: id - O ID do item a ser atualizado.
**Corpo da Requisição:**
json
{
  "descricao": "Nova descrição do item",
  "estado": "INICIADO",
  "prioridade": "ALTA"
}

### 5. Deletar um Item
**Método**: DELETE
**URL**: /api/itens/{id}
**Parâmetro**: id - O ID do item a ser deletado.
**Descrição**: Exclui um item da lista.

