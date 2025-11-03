# GymJourneyAPI 

API para gerenciamento de **Treinos** em uma academia.

### Base URL
### `http://localhost:8080`

---

### Endpoints

### `/users`

---

### `/muscle-group`

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

---

### `/exercises`

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

---

### `/workouts`

---
### `/workout-sections`

---
### `/workout-exercises`
