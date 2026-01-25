# GymJourneyAPI
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.6-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6.x-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-42.7.7-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT_Authentication-Auth0-EB5424?style=for-the-badge&logo=auth0&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-1.6.3-007ACC?style=for-the-badge&logo=java&logoColor=white)

API REST para gerenciamento de **treinos, exercícios, grupos musculares e usuários**
em uma academia, com autenticação JWT e controle de permissões.

## Índice

- [Base URL](#base-url)
- [Autenticação](#autenticação)
- [Códigos HTTP de Retorno da API](#códigos-http-de-retorno-da-api)
- [Endpoints](#endpoints)
  - [Login](#login)
  - [Usuários](#usuários)
  - [Grupo Muscular](#grupo-muscular)
  - [Exercícios](#exercicios)
  - [Treinos](#treinos)
  - [Seções de Treino](#seções-de-treino)
  - [Exercícios do Treino](#exercícios-do-treino)

## Base URL
### `http://localhost:8080`

## Autenticação

A maioria das rotas da API requer autenticação via **JWT (JSON Web Token)**.

O token é gerado após o login do usuário e deve ser enviado em todas as requisições protegidas por meio do header `Authorization`.
### `Authorization: Bearer {jwt_token}`

## Códigos HTTP de Retorno da API
<br>

| Código | Status | Descrição |
|------:|--------|-----------|
| 200 | OK | Requisição realizada com sucesso |
| 201 | Created | Recurso criado com sucesso |
| 400 | Bad Request | Requisição inválida ou dados informados incorretamente |
| 401 | Unauthorized | Usuário não autenticado ou token JWT inválido |
| 403 | Forbidden | Acesso negado por falta de permissão |
| 404 | Not Found | Recurso não encontrado |
| 409 | Conflict | Conflito de dados, como tentativa de cadastro duplicado |
| 500 | Internal Server Error | Erro interno inesperado no servidor |


## Endpoints

### Login
`POST /auth/login`

Realiza a autenticação do usuário e retorna um token JWT válido.
```json
{
  "email": "joao.silva@example.com",
  "password": "SenhaForte@123"
}
```
Response (200 Ok)
```json
{
    "token": "eyJpc3MiOiJHeW1Kb3VybmV5Q...."
}
```
##
### Usuários
`/users`

Endpoints responsáveis pelo gerenciamento de usuários.

Funcionalidades:
- Cadastro e verificação de contas de usuários
- Atualização de dados pessoais e senha
- Consulta de informações do usuário autenticado ou por ID
- Ativação e inativação de usuários
- Gerenciamento de papéis (roles), como USER, TRAINER e ADMIN

Algumas rotas são protegidas por autenticação JWT e podem exigir permissões específicas de acordo com o papel do usuário.

##### 1. Registrar Usuário
***POST*** `/users/register`
```json
{
    "email": "joao.silva@example.com",
    "name": "João Silva",
    "phone": "+55 11 98765-4321",
    "birthDate": "1990-05-15",
    "password": "SenhaForte@123"
}
```
Response (201 Created)
```json
{
    "id": 1,
    "email": "joao.silva@example.com",
    "name": "João Silva",
    "phone": "+55 11 98765-4321",
    "birthDate": "1990-05-15",
    "roles": [
        { "roleName": "USER" }
    ]
}
```
##### 2. Verificar conta
***GET*** `/users/verify-account?token={token}`

Response (200 Ok)
```json
{
    "status": 200,
    "message": "Conta verificada com sucesso!"
}
```
##### 3. Atualizar Dados
***PUT*** `/users/update`

```json
{
  "name": "João Silva Atualizado",
  "phone": "+55 11 99999-8888",
  "birthDate": "1990-05-15"
}
```
Response (200 Ok)
```json
{
    "id": 1,
    "email": "joao.silva@example.com",
    "name": "João Silva Atualizado",
    "phone": "+55 11 99999-8888",
    "birthDate": "1990-05-15",
    "roles": [
        { "roleName": "USER" }
    ]
}
```

##### 4. Alterar senha
***PATCH*** `/users/change-password`

```json
{
    "currentPassword": "SenhaForte@123",
    "newPassword": "NovaSenha@456",
    "confirmPassword": "NovaSenha@456"
}
```
Response (200 Ok)
```json
{
    "status": 200,
    "message": "Senha alterada com sucesso"
}
```

##### 5. Buscar Dados do usuário autenticado
***GET*** `/users`

Response (200 Ok)
```json
{
    "id": 1,
    "email": "joao.silva@example.com",
    "name": "João Silva",
    "phone": "+55 11 98765-4321",
    "birthDate": "1990-05-15",
    "roles": [
        { "roleName": "USER" }
    ]
}
```

##### 6. Buscar usuário por Id
***GET*** `/users/{id}`

Response (200 Ok)
```json
{
    "id": 1,
    "email": "joao.silva@example.com",
    "name": "João Silva",
    "phone": "+55 11 98765-4321",
    "birthDate": "1990-05-15",
    "roles": [
        { "roleName": "USER" }
    ]
}
```
##### 7. Adicionar papel ao usuário
***PATCH*** `/users/add-role/{id}`
```json
{
    "roleName": "TRAINER"
}
```

Response (200 Ok)
```json
{
    "status": 200,
    "message": "Papel de usuário inserido com sucesso"
}
```
##### 8. Remover papel ao usuário
***PATCH*** `/users/remove-role/{id}`
```json
{
    "roleName": "TRAINER"
}
```

Response (200 Ok)
```json
{
    "status": 200,
    "message": "Papel de usuário removido com sucesso"
}
```
##### 9. Inativar Usuário
***PATCH*** `/users/disable/{id}`
Response (200 Ok)
```json
{
    "status": 200,
    "message": "Usuário inativado com sucesso"
}
```
##### 10. Ativar Usuário
***PATCH*** `/users/enable/{id}`
Response (200 Ok)
```json
{
    "status": 200,
    "message": "Usuário ativado com sucesso"
}
```
##

### Grupo Muscular
`/muscle-group`

##### 1. Cadastrar Grupo muscular
***POST*** `/muscle-group/register`

Endpoint responsável por cadastrar um ou mais grupos musculares.
Permite o envio de uma lista de objetos, onde cada item representa um grupo muscular distinto.

- **Cada Grupo muscular deve:**
  - Possuir um nome único (nomes duplicados não são permitidos).

**Request Body (JSON)**
```json
[
  {
    "name": "Posterior de coxa"
  }
]

```

Response (201 Created)
```json
[
  {
    "id": 1,
    "name": "Posterior de coxa"
  }
]
```
##### 2. Atualizar Grupo muscular
***PUT*** `/muscle-group/update`

**Request Body (JSON)**
```json
{
  "id": 1,
  "name": "Posterior Coxa"
}
```

Response (200 Ok)
```json
{
  "id": 1,
  "name": "Posterior Coxa"
}

```

##### 3. Consultar Grupo muscular
***GET*** `/muscle-group/{id}`

Retorna os detalhes de um grupo muscular específico, com base no seu ID.

***GET*** `/muscle-group/search?name=posterior&page=0&size=5`

Retorna uma lista paginada de todos os grupos musculares cadastrados.

- **Parâmetros de query:**
  - name (opcional) - Termo utilizado para filtrar grupos musculares pelo nome.
    A busca retorna todos os registros que contenham o termo informado, de forma parcial e sem distinção entre maiúsculas e minúsculas.
  - page (opcional, default = 0) – Página a ser retornada
  - size (opcional, default = 10) – Número de exercícios por página

##

### Exercicios
`/exercises`

##### 1. Cadastrar exercício
***POST*** `/exercises/register`

Endpoint responsável por cadastrar um ou mais exercícios.
Permite o envio de uma lista de objetos, onde cada item representa um exercício distinto.

- **Cada exercício deve:**
  - Possuir um nome único (nomes duplicados não são permitidos).
  - Incluir uma descrição clara do movimento.
  - Estar associado a um ou mais grupos musculares já cadastrados.

**Request Body (JSON)**
```json
[
    {
      "name": "Agachamento",
      "description": "Exercício para pernas e glúteos, focando quadríceps e posterior de coxa",
      "muscleGroupIds": [1]
    }
]
```

Response (201 Created)
```json
{
  "id": 1,
  "name": "Agachamento",
  "description": "Exercício para pernas e glúteos, focando quadríceps e posterior de coxa",
  "muscleGroups": [
    {
      "id": 1,
      "name": "Posterior Coxa"
    }
  ]
}
```
##### 2. Atualizar Exercicio

***PUT*** `/exercises/update`

**Request Body (JSON)**
```json
{
  "id": 1,
  "name": "Posterior Coxa",
  "description": "Exercício para pernas e glúteos"
}
```

Response (200 Ok)
```json
{
  "id": 1,
  "name": "Agachamento",
  "description": "Exercício para pernas e glúteos",
  "muscleGroups": [
    {
      "id": 1,
      "name": "Posterior Coxa"
    }
  ]
}
```

### 3. Consultar Exercicios
**GET** `/exercises/{id}`

Retorna os detalhes de um exercicio, com base no seu ID.

**GET** `/exercises/search?name=elevacao&page=0&size=5`

- **Parâmetros de query:**
  - name (opcional) - Termo utilizado para filtrar grupos musculares pelo nome.
    A busca retorna todos os registros que contenham o termo informado, de forma parcial e sem distinção entre maiúsculas e minúsculas.
  - page (opcional, default = 0) – Página a ser retornada
  - size (opcional, default = 10) – Número de exercícios por página

##

### Treinos
`/workouts`

##
### Seções de Treino
`/workout-sections`

##
### Exercícios do Treino
`/workout-exercises`