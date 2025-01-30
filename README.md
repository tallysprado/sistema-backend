# SistemaBackend

## Para rodar a aplicação localmente, siga os passos a seguir:
- 1. Garantir que não tenha nenhum container em execução fora o do ambiente de desenvolvimento do repositório sistema-backend.
  - 1.1 No repositório sistema-backend, deve-se iniciar o Postgres e Keycloak em ambiente local/desenvolvimento através de:
    - ```shell script
      docker-compose -f docker-compose.dev.yml up
      ```
- 2. Agora neste repositório do __BACKEND__, executar o quarkus no perfil de desenvolvimento:
  - ```shell script
      .\mvnw quarkus:dev -Pdev
    ```

### Estes passos são suficientes para rodar o projeto Quarkus localmente.
#### Acesse através da url <http://localhost:8081> utilizando as credenciais


#### O console de administrador do Keycloak é acessado em <http://localhost:8080>
#### Usuário: __admin__
#### Senha: __admin__

###### Dúvidas entre em contato no Whatsapp ou E-mail:
- (88) 9 9651 - 0001
- tallys.prado@gmail.com
