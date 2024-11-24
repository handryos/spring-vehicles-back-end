# Desafio Técnico | Back-end | Java Pleno

## Configuração do ambiente
Para rodar o projeto sem problemas, é necessário ter instalado o Maven e o JDK (Recomendo o 17) e configurar as variáveis de ambiente, como [JAVA_HOME](https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux) e [MAVEN_HOME](https://mkyong.com/maven/how-to-install-maven-in-windows/).


## Dependências
O projeto utiliza as seguintes dependências principais:

- **Flyway**: Para o gerenciamento de migrações de banco de dados.
- **JPA (Java Persistence API)**: Para persistência de dados no banco de dados.
- **Spring Data JPA**: Para manipulação dos dados no banco utilizando repositórios.
- **PostgreSQL**: Banco de dados utilizado para persistir as informações.
- **Lombok**: Para reduzir a verbosidade do código, gerando automaticamente getters, setters, construtores, entre outros.
- **JUnit e Mockito**: Para a realização de testes unitários.

## Como rodar

```bash 
git clone https://github.com/handryos/spring-vehicles-back-end
``` 
Dentro da pasta do clone
```bash 
cd demo
``` 
Já dentro do diretório "demo"
```bash 
mvn clean package
docker-compose up --build
``` 

## Estrutura do Projeto

### Pacotes

- **com.example.demo.models**: Contém as classes de modelo (entidades) do sistema, como `Vehicle`, `Tire`, `TirePosition`.
- **com.example.demo.repository**: Contém os repositórios JPA que são usados para acessar o banco de dados.
- **com.example.demo.services**: Contém as interfaces dos serviços que definem os contratos de operações.
- **com.example.demo.implementations**: Contém as implementações dos serviços definidos nas interfaces.
- **com.example.demo.controller**:  Contém as implementações dos controllers para gerenciar as url's.

### Entidades

#### Vehicle (Veículo)
Representa um veículo que pode ter vários pneus associados a ele. Possui os seguintes atributos:
- `id`: Identificador único do veículo.
- `plate`: Placa do veículo.
- `brand`: Marca do veículo.
- `km`: Quilometragem do veículo.
- `status`: Status do veículo.
- `tire`: Lista de pneus associados ao veículo, representada por `TirePosition`.

#### Tire (Pneu)
Representa um pneu, com as seguintes propriedades:
- `id`: Identificador único do pneu.
- `fireNumber`: Número de série do pneu.
- `pressure`: Pressão do pneu.
- `status`: Status do pneu.

#### TirePosition (Posição do Pneu)
Representa a posição de um pneu dentro de um veículo. A posição pode ser `front-left`, `front-right`, `rear-left`, `rear-right`, etc.
- `vehicle`: O veículo ao qual o pneu está associado.
- `tire`: O pneu associado.
- `position`: A posição do pneu no veículo.

### Serviços

#### VehicleService
Interface que define as operações disponíveis para os veículos, como:
- `create`: Criação de um novo veículo.
- `getVehicles`: Recuperação de todos os veículos.
- `getVehiclesById`: Recuperação de um veículo pelo ID.
- `addTireToVehicle`: Adição de pneus a um veículo.
- `update`: Atualização de um veículo.
- `removeTireFromVehicle`: Remoção de um pneu de um veículo.
- `delete`: Exclusão de um veículo.

#### TireService
Interface que define as operações disponíveis para os pneus, como:
- `create`: Criação de um novo pneu.
- `getTires`: Recuperação de todos os pneus.
- `getTireById`: Recuperação de um pneu pelo ID.
- `update`: Atualização de um pneu.
- `delete`: Exclusão de um pneu.

### Implementações

- **VehicleServiceImplementation**: Implementação do `VehicleService`. Contém a lógica para criar veículos, adicionar pneus, atualizar veículos, entre outras operações.
- **TireServiceImplementation**: Implementação do `TireService`. Contém a lógica para criar pneus, atualizar pneus e excluí-los, garantindo que não existam pneus associados a um veículo antes de sua exclusão.

## Testes

A aplicação possui testes automatizados para os seguintes pontos principais:

- **Testes de CRUD**: Verifica as operações básicas de CRUD para as entidades **Veículo** e **Pneu**.
- **Testes de Associação de Pneus a Veículos**: Verifica se a lógica de adicionar e remover pneus de veículos está funcionando corretamente.
- **Testes de Validação**: Garante que os dados inseridos sejam válidos e que os erros sejam tratados corretamente, como na tentativa de adicionar um pneu que já está associado a um veículo.

Para rodar os testes automatizados, utilize o comando:

```bash
mvn test
```

# Swagger 
- **Link para acessar a documentação**: Garante que os dados inseridos sejam válidos e que os erros sejam tratados corretamente, como na tentativa de adicionar um pneu que já está associado a um veículo.](http://localhost:8080/swagger-ui.html.)

