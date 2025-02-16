# <img src="https://roadmap.sh/images/gifs/rocket.gif" width="25px">  Projeto de Chat Simples com Socket Programming em Java

Este projeto é um Chat Simples utilizando Socket Programming em Java, implementando tanto o servidor quanto o cliente. Abaixo estão explicações e conceitos principais para ajudar a entender e relembrar como o Socket Programming funciona.

1. ## O que é Socket Programming?

- **Socket Programming** é uma forma de comunicação entre dois ou mais sistemas via rede. Em Java, usamos a API de Sockets para criar conexões TCP/IP entre um cliente e um servidor.
- **Socket**: Ponto final de uma comunicação.
- **TCP/IP**: Protocolo confiável que garante entrega e ordem correta dos pacotes.

2. ## Estrutura do Projeto

- **ServerMain.java** : Código do Servidor, que aguarda conexões e distribui mensagens.
- **ClientMain.java**: Código do Cliente, que conecta ao servidor e envia/recebe mensagens.

## Instalação
1. **Clone o Repositório:**
```bash
git clone https://github.com/matheushug0/socket-chat-cli.git
```
2. **Faça o Build do Projeto (Versão recomendada do Maven: 3.6.3):**
```bash
cd socket-chat-cli/
mvn clean package install
```
3. **Execute a Aplicação:**
```bash
cd target/
java -cp socket-chat-1.0-SNAPSHOT.jar ChatServer.ServerMain # Execute primeiro o Servidor
java -cp socket-chat-1.0-SNAPSHOT.jar ChatServer.ClientMain # Execute em um ou mais terminais um Cliente
```
---

3. ## Classes Principais de Sockets em Java

- ### ServerSocket

Cria o servidor e escuta conexões.

Exemplo:

```java
ServerSocket server = new ServerSocket(12345);
Socket client = server.accept();
 ```
- ### Socket

Cria o lado do cliente e conecta ao servidor.

Exemplo:

```java
Socket socket = new Socket("localhost", 12345);
```

- ### InputStream e OutputStream

Usados para enviar e receber dados.
Em geral, usamos BufferedReader e PrintWriter para facilitar:

```java
BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
```

4. ## Fluxo Básico do Programa

- Servidor:

>>Cria um ServerSocket.
> <br>
>>Fica em loop aceitando conexões.
> <br>
> Cria uma nova thread para cada cliente conectado.

- Cliente:
 
> Cria um Socket conectado ao servidor.
> <br>
> Envia mensagens pelo OutputStream.
> <br>
> Recebe mensagens pelo InputStream.

5. ## Multithreading

- O servidor usa multithreading para lidar com múltiplos clientes simultaneamente.
- Cada cliente roda em uma thread separada para evitar bloqueios.

6. ## Dicas e Desafios

- Sincronizar recursos compartilhados (como a lista de clientes).
- Lidar com erros de conexão (cliente desconectado).
- Implementar lógica de mensagens (como evitar que o remetente receba sua própria mensagem).

7. ## Lógica Adicional Implementada

- O cliente que envia uma mensagem não a recebe de volta, mas recebe uma confirmação de envio.
- Utiliza synchronized para gerenciar o acesso à lista de clientes.