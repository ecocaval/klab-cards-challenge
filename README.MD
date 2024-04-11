# Como Usar

Para começar a API basta ter o docker instalado em sua máquina e rodar o seguinte comando:

``docker-compose up -d``

O docker será responsável por subir o banco de dados PostgreSQL localmente, permitindo com que o backend se conecte ao banco.

# Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.4
    - Spring Boot Starter Data JPA
    - Spring Boot Starter Web
    - Spring Boot DevTools
    - Spring Boot Starter Test
- Spring Cloud 2023.0.1
    - Spring Cloud Starter OpenFeign
- Flyway Core
- PostgreSQL Driver (Banco de dados upado através do Docker)
- Lombok
- Ehcache
- JCache (Java Cache API)
- H2 Database (para testes)
- JavaFaker (para testes)
- Mockito Core (para testes)

# Endpoints

Atualmente os seguintes endpoints são fornecidos pela API:

### Listar Todos os Jogos

- **GET** `/v1/games`
    - **Descrição**: Retorna uma lista paginada de todos os jogos.
    - **Parâmetros**: Aceita parâmetros padrão de paginação.
    - **Resposta**: `Page<GameResponse>`

### Buscar Jogo por ID

- **GET** `/v1/games/{gameId}`
    - **Descrição**: Busca um jogo específico pelo seu ID.
    - **Parâmetros**: `gameId` (ID do jogo)
    - **Resposta**: `GameResponse`
    - **Erros**: Retorna erro se o `gameId` não for um UUID válido.

### Criar um Novo Jogo

- **POST** `/v1/games`
    - **Descrição**: Cria um novo jogo.
    - **Resposta**: `GameResponse`

### Listar Todos os Jogadores

- **GET** `/v1/players`
    - **Descrição**: Retorna todos os jogadores.
    - **Resposta**: `Set<PlayerResponse>`

### Buscar Jogos Vencidos por ID do Jogador

- **GET** `/v1/players/{playerId}/games/won`
    - **Descrição**: Retorna uma lista paginada de jogos vencidos por um jogador específico.
    - **Parâmetros**:
        - `playerId` (ID do jogador): UUID do jogador.
        - Parâmetros padrão de paginação.
    - **Resposta**: `Page<GameResponse>`
    - **Erros**: Retorna erro se o `playerId` não for um UUID válido.

# Classes de Resposta

### GameResponse

Representa a resposta de um jogo.

- `id`: UUID do jogo
- `creationDate`: Data de criação do jogo
- `winners`: Lista de vencedores (`List<WinnerDto>`)
- `hands`: Lista de mãos no jogo (`List<HandResponse>`)

### WinnerDto

Representa um vencedor no jogo.

- `id`: UUID do jogador
- `name`: Nome do jogador
- `score`: Pontuação do jogador

### HandResponse

Representa uma mão no jogo.

- `player`: Dados do jogador (`PlayerResponse`)
- `cards`: Lista de cartas (`List<CardResponse>`)
- `score`: Pontuação da mão

### PlayerResponse

Representa a resposta de um jogador.

- `id`: UUID do jogador
- `name`: Nome do jogador

### CardResponse

Representa a resposta de uma carta.

- `rank`: Naipe da carta
- `rankValue`: Valor da carta
