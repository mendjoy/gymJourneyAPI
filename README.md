# GymJourney API

API para gerenciamento de treinos, permitindo o cadastro e controle de usuários, treinos, seções e exercícios. Desenvolvida com Java + Spring Boot.

## ⚙️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Security** (com JWT)
- **Hibernate / JPA**
- **MySQL**
  
## 📌 Endpoints da API
### 🔐 Autenticação

- `POST /auth/login`  
  Autentica o usuário e retorna um token JWT.

- `POST /auth/register`  
  Registra um novo usuário no sistema.

### 👤 Usuários

- `PATCH /users/{id}/grant-admin`  
  Promove o usuário ao papel de ADMIN.  
  **Requer autenticação com perfil ADMIN.**

- `PATCH /users/{id}/update`  
  Atualiza os dados do usuário autenticado, como nome e telefone.  
  **Campos atualizáveis:** `name`, `phone` 
  **Requer autenticação.**

- `PATCH /users/{id}/update/password`  
  Atualiza a senha do usuário.
  
### 🏋️ Exercícios
- `POST /exercises/register`
###### 📥 Exemplo de requisição
  ```json
  {
    "name": "Remada Curvada",
    "description": "Exercício para costas que também ativa os bíceps e deltoides posteriores.",
    "muscleGroup": "Costas"
  }
  ```
###### 📤 Exemplo de resposta (201 CREATED):
  ```json
  {
    "status": "CREATED",
    "success": true,
    "data": {
        "id": 5,
        "name": "Remada Curvada",
        "description": "Exercício para costas que também ativa os bíceps e deltoides posteriores.",
        "muscleGroup": "Costas"
    }
  }
  ```


- `GET /exercises/{id}`
- `GET /exercises`
- `GET /exercises/search`

### 💪 Treinos
 - `POST /workout/register`  
  Cria um novo treino para um usuário. Somente usuários com perfil ADMIN podem cadastrar treinos.

- `DELETE /workout/{id}`
- `POST /workout/finish/{id}/{endDate}`
- `GET /workout/all/{userId}`
- `GET /workout/user/all/{userId}`
- `GET /workout/current/{userId}`
- `GET /workout/current/user`

### 📅 Frequência de Treinos
- `POST /workout/frequency/{idWorkout}/{date}`
 
  
