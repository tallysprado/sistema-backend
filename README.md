# backend
#### Este projeto utiliza Java, Quarkus e Docker
## 1. Executar imagem Keycloak no Docker
#### O arquivo `docker-compose.yml` possui configuracão para importar o tema customizado, roles, realm e client que o front-end irá acessar.
```shell script
docker-compose -p sistema-backend up
```
> **_NOTA:_**  O console de administrador do Keycloak foi configurado para acessar em <https://localhost:9081>. Nesta 
> página é possível criar novos usuários para aplicacão no menu `Users` com o realm `sistema-backend` selecionado.
> Para acessar, utilize as credenciais abaixo:
#### **_usuário: admin_**
#### **_senha: admin_**

### Com o realm sistema-backend selecionado, criar usuário no Keycloak em localhost:9081 e associar a role (coluna role-mapping) coordenador, necessário para visualizar itens de menu do sistema.

> **_NOTA:_**  A aplicacão do frontend deve executar na URL <https://localhost:4200>.

## 2. Executar o Quarkus em terminal separado

Pode executar a aplicacão no modo de desenvolvimento através de:

```shell script
./mvnw quarkus:dev
```

Dúvidas entre em contato no Whatsapp ou E-mail:
- (88) 9 9651 - 0001
- tallys.prado@gmail.com
