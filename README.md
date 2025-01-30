# SistemaBackend

## Para rodar a aplicação localmente, siga os passos a seguir:
- Se você estiver no diretório do container, primeiro deve garantir que a imagem do Angular e Quarkus não estejam em execução:
```shell script
docker-compose down -v
```
- Entrar no diretório raiz do sistema-backend:
```shell script
cd sistema-backend
```
- Neste repositório, deve-se iniciar o Postgres e Keycloak em ambiente local/desenvolvimento através do comando abaixo. No repositório acima da pasta _container_, o docker-compose tem a configuração criar a imagem Angular e Quarkus, este inclui apenas Keycloak e PostgreSQL.
```shell script
docker-compose -f docker-compose.dev.yml up -d
```


- E então executar o projeto Quarkus no em ambiente de desenvolvimento/local:
```shell script
.\mvnw quarkus:dev -Pdev
```

### Estes passos são suficientes para rodar o projeto Quarkus localmente.
#### Acesse através da url <http://localhost:8081> utilizando as credenciais
#### Usuário: __universidade__
#### Senha: __123__

#### O console de administrador do Keycloak é acessado em <http://localhost:8080>
#### Usuário: __admin__
#### Senha: __admin__

###### Dúvidas entre em contato no Whatsapp ou E-mail:
- (88) 9 9651 - 0001
- tallys.prado@gmail.com
