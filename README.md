# SistemaBackend


## A conteinerização da aplicação está em andamento na branch docker/card01. Objetivo de reunir todos os projetos num único container sendo executado pelo compose no backend. Enquanto isto a aplicação deve ser executada manualmente.

#### Este projeto utiliza Java, Quarkus e Docker
## 1. Executar imagem do Keycloak e Postgresql no Docker
#### O arquivo `docker-compose.yml` possui configuracão para importar o tema customizado, roles, realm e client que o front-end irá acessar. Também possui configuração para iniciar banco de dados Postgresql.
```shell script
docker-compose -p sistema-backend up -d
```
> **_NOTA:_**  O console de administrador do Keycloak foi configurado para acessar em <https://localhost:9081>. Nesta 
> página é possível criar novos usuários para aplicacão no menu `Users` com o realm `sistema-backend` selecionado.
> Para acessar, utilize as credenciais abaixo:
#### **_usuário: admin_**
#### **_senha: admin_**

## 2. Configurar usuário
### Para efetuar login na aplicacão, existe uma credencial já predefinida com ROLE_COORDENADOR:
#### Usuário: __universidade__
#### Senha: __123__
#### Os usuários criados dentro do sistema-frontend podem logar na aplicacão utilizando a matricula gerada e a senha "123".
#### Mas se preferir, configure seguindo os passos abaixo:
### Com o _realm_ sistema-backend selecionado, criar usuário no Keycloak (menu _Users_) em localhost:9081 e associar a role de coordenador (coluna _role-mapping_, após selecionar usuário criado). Esta role é necessário para visualizar itens de menu do sistema.

> **_NOTA:_**  A aplicacão do frontend deve executar na URL <https://localhost:4200>.

## 3. Executar o Quarkus em terminal separado
Pode executar a aplicacão no modo de desenvolvimento através de:

```shell script
./mvnw quarkus:dev
```

##### Configurações do banco de dados:
- Host: 0.0.0.0:5433
- Banco de dados: sistema
- Usuário: postgres
- Senha: postgres

Sobre:
- Foi utilizado _Repository Design Pattern_
- Testes de cobertura da classe UsuarioController
- Rotas protegidas por papéis (coordenador ou aluno)
- O uso de constraints é didático apenas para utilizar como exemplo no Exception Handler.
  A depender da quantidade de dados é preferível uma consulta antes ao invés de usar constraint unique para colunas CPF e nome.


###### Dúvidas entre em contato no Whatsapp ou E-mail:
- (88) 9 9651 - 0001
- tallys.prado@gmail.com
