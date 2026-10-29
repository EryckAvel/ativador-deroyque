# ACTIVANTIO REQUEST - TESTE TÉCNICO

## COMO RODAR O PROJETO

Pensando em facilitar o fluxo para poder realizar o teste de maneira mais rápida. Implementei um docker-compose, aonde temos a criação de um banco PostgreSQL
quer sera gerado juntamente. Acabei não implementando a questão da integração mais explicarei como implementaria esse ponto mais a frente.

1. Rodar o docker compose 

Rode o seguinte comando para rodar o docker compose

```bash
docker-compose up
```
 
2. Realizar as devidas chamadas passadas no teste usando postman ou outra ferramenta.

## DECISÃO TÉCNICA

A primeira tomada de decisão foi a mais óbvia usar as tecnologias passadas na documentação do teste, Spring Boot 3.x.x, como era opcional, JPA, banco de dados relacionar e optei em usar um PostgresSQL,
optei em usar o flyaway para migration para facilitar a criação do banco, junto dela implementei uma chave unica no externalId, tendo uma garantia a mais para impedir uma duplicação de solicitações.
Além disso, a escolha de usar docker-compose veio de facilitar o processo de inicialização do ambiente de desenvolvimento. Acabei não implementando a solução de integração por realmente não saber como prosseguir e o tempo para implementar.
Mas uma solução robusta seria uma segunda aplicação, utilizaria RabbitMQ ou Kafka para comunicação assíncrona entre os serviços. Garantindo uma comunicação mais eficiente e confiável.

- HTTP 200 : Deve mudar o Status da solicitação para COMPLETED.
- HTTP 400 : deve mudar o Status do pedido para FAILED, cabendo a regra de negócio caso o FAILED possa tentar novamente e quantas vezes ele pode tentar.
- HTTP 500 : Fica a definição de regra de negócio, ele tem a permissão de realizar retries ate um limite estipulado aonde esse limite ser estourado entra em FAILED.
- TIMEOUT : Deve entrar em uma fila de espera longa para que realize novas tentativas porem com um intervalo de tempo e quantidade de tentativas maior para que ele consiga acessar, porem caso estoure as tentativas muda o status para FAILED.

### Tratamento de Exceptions

Realize a implementação de um GlobalExceptionHandler da maneira que foi passada, e cumprindo o Problem Details (RFC 9457), seguindo a estrutura de JSON passada na documentação do teste. Realizei a implementação
para tratar exceptions relacionadas a validations, EntityNotFoundExceptions e EntityExistsException, tratando cada um com os status codes correto.

### Segurança

Infelizmente não tive experiência com OAuth, mais já trabalhei bastante usando JWT e Spring Security, a implementação seria usar o UserDetails e UserDetailsService, implementaria uma classe de TokenUtil,
para implementar a parte de geração do token JWT. Alem disso seria necessario implementar uma classe de configuração para o spring security, implementar uma nova tabela de usuario para cadastro e autenticação.
Para finalizar seria necessario criar um AuthenticationFiler implementando o OncePerRequestFilter, e realizando a configuração de autenticação final, adicionando na classe de configuração do Spring security os caminhos
livres para poder acessar sem autenticação, como login e um possivel cadastro caso necessario.

## CENÁRIO DE ANÁLISE

1. Investigaria primeiro o próprio select que estivesse sendo chamado, já que foi identificado que ha uma lentidão na consulta, e analisaria o seu fluxo de consulta usando um EXPLAIN ANALYZER (Caso fosse um banco PostgreSQL)
2. Para eliminar um fator mais rápido, seria removendo a dúvida se e no banco, assim já realizando a consulta no banco simulando um cenário de produção com uma base de cópia. Caso não fosse realizaria a validação na integração externa,
validando o tempo de resposta, claro isso estivermos-se falando de uma comunicação síncrona, caso assíncrona teríamos que analisar as filas para entender os gargalos, e por fim validaríamos a propria API.
3. Logs de retorno da aplicação, caso seja uma comunicação síncrona, informando o retorno da requisição, logs referentes a erros e exceções. Logs de erros da propria mensageria, caso tivéssemos um cenário assíncrono.
4. Uma melhoria, caso estive num cenário aonde tenhamos uma comunicação síncrona, recomendaria a implementação de uma mensageria para que erros e problemas com timeout ou até mesmo demora de retorno ao cliente pudessem ser resolvidas, reduzindo o impacto da demora.
Alem disso, visto problemas relacionado a lentidão nas consultas, se faria recomendado a implementação caso possivel, a criação de um index para agilizar nas consultas, e a análise do select em si para que houvesse uma refatoração voltada para desempenho.
   
## PRODUÇÃO

Em produção, eu criaria um pipeline do github, para implementar um CI/CD, além de hospedar ela numa nuvem. Implementaria dentro do docker-compose as definições do Grafana e Prometheus, além de implementar o actuator na aplicação para que a observabilidade funcione.

## PONTOS PENDENTES

Como comentei antes, o ponto que não implementei foi a integração com a aplicação externa. Minha ideia seria criar uma segunda API só para essa comunicação, usando mensageria: ao registrar uma ativação,
a API principal enviaria uma mensagem para uma fila, a outra API faria a chamada ao sistema externo e devolveria o resultado para atualizar o status.

## MELHORIAS FUTURAS

Realizaria a implementação, como disse na parte da produção, que implementaria observabilidade com Grafana e Prometheus. E a implementação da autenticação com JWT e o OAuth.

## Uso de IA

O uso da ia nesse projeto, se deve a agilização de determinados projetos como a modelagem e criação do repository. Criação de pastas básicas do projeto. Análise de dependence corretas para a versão do Spring Boot. Usei skills propias criadas ja com um padrão definidas por min,
para me auxiliar na criação de projeto pessoais e se possivel ate auxiliar na criação de projetos profissionais acelerando desenvolvimento.



