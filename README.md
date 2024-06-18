# Cadastro de Pessoa, com lista de Contatos

## Tecnologias Utilizadas

Este projeto utiliza:

- Java 17 + SpringBoot para o backend
- PostgreSQL + liquibase para integração com o banco de dados e migrations
- React para o frontend(atualmente, incompleto as funcionalidades porém visual esta montado)

As migrações liquibase proporcionam o versionamento e controle de alterações em schema do banco de dados por meio de changeset.
Após serem executadas, registros de alterações realizadas serão salvas em tabela databasechangelog.

## Configuração do Banco de Dados

Para que o aplicativo acesse o banco de dados e funcione corretamente o manuseio de dados, é necessário configurar as propriedades de conexão com o banco de dados no arquivo `application.properties`. Você deve especificar a URL do banco de dados, o nome de usuário e a senha.

Exemplo de configuração no `application.properties`:

spring.datasource.url=jdbc:postgresql://localhost:5432/nome-do-banco
spring.datasource.username=usuario
spring.datasource.password=senha

Substitua `nome-do-banco`, `usuario` e `senha` pelos valores correspondentes ao seu ambiente de banco de dados de interesse.

## Criação das tabelas

As tabelas `Person` e `Constact` são criadas utilizando as migrations de Liquibase, pelos changeSets com id 1 e 2.
Caso configuração de banco esteja incorreta ou alguma pré-condição, quando existir, não for verdadeira será registrado em tabela databasechangelog que houve fala em executar changeset e ao buildar o projeto será apresentado um resumo de registros executados 
para registros existentes em changeSets.

## Executando o Projeto

# Frontend (React)
1. Navegue até o diretório do frontend chamado 'front'
2. Execute o comando `npm install` para instalar as dependências
3. Execute o comando `npm run dev` para iniciar o servidor de desenvolvimento do React

# Backend (Spring Boot)

1. Abra o diretório 'back' do backend com a IDE IntelliJ
2. Certifique-se de ter o Maven configurado corretamente para baixar as dependências ou execute mvn install
3. Execute a aplicação Spring Boot

# Testes Unitários
Foram criados testes unitários para cada método na service PersonService. Estes testes são criados para simular cenários em que rotinas criadas são realizadas com sucesso ou retornam erro.
