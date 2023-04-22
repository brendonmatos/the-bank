# TheBank

Projeto para estudos em cima de arquitetura de microserviços.
Meu playground de tecnologias 🚀.

## Rodando o Projeto

Para rodar o projeto, basta executar o comando abaixo na raiz do projeto:

```bash
docker-compose up
```

> Apenas funciona com docker-compose < 2.0

Ou se preferir, pode utilizar o script da raiz do projeto e navegar nas opções:

```bash
./up.sh
```

## Status

| Nome microserviço | Funcionando |   Tecnologia  | Banco de dados | API Rest | gRPC | Pub/Sub | Monitoring |
|-------------------|-------------|---------------|----------------|----------|------|---------|------------|
| clientes-service | ✅ | Java + Spring | ✅ MySQL| ✅ | ❌ | ✅ RabbitMQ | ❌ |
| contas-service | ✅ | Java + Spring | ✅ MySQL | ✅ | ❌ | ✅ RabbitMQ | ❌ |
| gerentes-service| ✅ | Java + Spring | ✅ MySQL | ✅ | ❌ | ✅ RabbitMQ | ❌ |
| auth-service | ✅ | Java + Spring | ✅ MySQL | ✅ | ❌ | ✅ RabbitMQ | ❌ |
| cartoes-service | 💤 | 💤 | 💤 | 💤 | 💤 | 💤 | 💤 |
| api-gateway | ✅ | NodeJS + Express | ❌ | ✅ | ❌ | ✅ RabbitMQ | ❌ |
| tests-gun | ✅ | NodeJS + Vitest | ❌ | ❌ | ❌ | ❌ | ❌ |

## Urls uteis

### phpmyadmin (visualizar todos os bds)

> - url: [http://localhost:8081/](http://localhost:8081/)
> - user: `root`
> - pass: `root`

### rabbitmq admin (filas e mensagens)

> - url: [http://localhost:15672/](http://localhost:15672/)
> - user: `root`
> - pass: `root`

### vitest (testes automatizados)

> - url: [http://localhost:51204/__vitest__](http://localhost:51204/__vitest__)
> - só dar play

## Future

- <https://github.com/authelia/authelia> (Auth)
- <https://github.com/redpanda-data/redpanda> (Messaging)
- <https://github.com/surrealdb/surrealdb> (Database)
- <https://github.com/hasura/graphql-engine> (GraphQL)
- <https://github.com/phoenixframework/phoenix> (API Rest / Admin)
- <https://github.com/zinclabs/zinc> (Search)
- <https://github.com/elastic/beats> (Monitoring)
- <https://github.com/grafana/grafana> (Monitoring)
