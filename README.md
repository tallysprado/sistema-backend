# backend

## 1. Executar imagem Keycloak no Docker
#### O arquivo `docker-compose.yml` possui configuracão para importar o tema customizado
```shell script
docker-compose -p sistema-backend up
```
### Utilizar credenciais para logar na aplicacão frontend
#### **_usuário: tallys_**
#### **_senha: teste123_**
> **_NOTA:_**  A aplicacão do frontend deve executar na URL <https://localhost:4200>.

### Utilizar credenciais para logar no console admin Keycloak ``
#### **_usuário: admin_**
#### **_senha: admin_**
> **_NOTA:_**  O console de administrador do Keycloak foi configurado para acessar em <https://localhost:9081>. Nesta 
> página é possível criar novos usuários para aplicacão no menu `Users` com o real `sistema-backend` selecionado.

## 2. Executar o Quarkus em terminal separado

Pode executar a aplicacão no modo de desenvolvimento através de:

```shell script
./mvnw quarkus:dev
```g

Dúvidas entre em contato no Whatsapp ou E-mail:
- (88) 9 9651 - 0001
- tallys.prado@gmail.com
