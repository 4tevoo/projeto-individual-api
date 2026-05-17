# API Concessionária & Gestão de Clientes

API de desenvolvimento/gerenciamento de clientes e veículos de uma concessionária, feito pro SerraTec para as aulas de API Restful do professor Carlos Eduardo Mattos. 

## Tecnologias Utilizadas
* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA**
* **PostgreSQL**
* **Bean Validation** (Jakarta Validation)

## Como Executar o Projeto
* Clone o repositório: `git clone https://github.com/4tevoo/projeto-individual-api.git`
* Abra o projeto no IntelliJ.
* Aguarde o Maven carregar as dependências.
* Crie o Database com o nome `Concessionaria` ou o nome que preferir, mas, se for outro, edite o nome em `spring.datasource.url=jdbc:postgresql://localhost:5432/concessionaria ` no application.properties.
* Adicione sua Variável de Ambiente:
  * Vai na setinha do do lado do botão de rodar lá em cima no Application
  * Vai em Edit Configuration
  * Vai em Modify Options
  * Vai em Environment Variables
  * Ao clicar vai abrir um campo onde vc coloca:
  * USER_BD=seu_user;SENHA_BD=sua senha 
  * ou clica na lateral do campo, num ícone de documento
  * e coloca USER_BD no name, e seu user do postgre no value e o mesmo pra senha
* Execute a classe principal `ConcessionariaApplication.java`.
* A API estará disponível em: `http://localhost:8080`
* Use o endpoint `http://localhost:8080/api/v1` com `/cliente` ou `/veiculo`

### Clientes
* `POST /api/v1/cliente` - Cadastrar um novo cliente.
* `GET /api/v1/cliente` - Listar clientes (com filtros opcionais).
* `DELETE /api/v1/cliente/{id}` - Remover cliente (bloqueado se houver veículos vinculados).

### Veículos
* `POST /api/v1/veiculo` - Cadastrar veículo.
* `GET /api/v1/veiculo` - Listar veículos (filtros por placa, marca e modelo).
* `PUT /api/v1/veiculo/{id}` - Atualizar dados do veículo / Efetuar venda.
* `DELETE /api/v1/veiculo/{id}` - Remover veículo.

_Made by Estêvão Viana Cunha, aka, Tevo (quase todos os professores não me identificam pelo meu user do github então deixei aqui de aviso)_

