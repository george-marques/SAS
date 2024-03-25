# Tutorial: Comunicação Cliente-Servidor com Criptografia RSA em Java
Neste tutorial, você aprenderá como configurar uma comunicação segura entre um cliente e um servidor usando criptografia RSA em Java. O RSA é um algoritmo de criptografia assimétrica amplamente utilizado para comunicações seguras na Internet.

## Pré-requisitos
- Java Development Kit (JDK) instalado em seu sistema.

## Passo 1: Configurando o Servidor
A classe Server (Alice) é responsável por iniciar o servidor e aguardar a conexão do cliente (Bob). Aqui estão os passos básicos:

- Geração de Chave RSA: A classe Server gera um par de chaves RSA usando um tamanho mínimo de 1024 bits.

- Aguardando Conexão do Cliente: O servidor aguarda a conexão do cliente em uma porta específica.

- Configurando Streams de Entrada e Saída: Uma vez que o cliente se conecta, são configurados os fluxos de entrada e saída para comunicação.

- Envio da Chave Pública: O servidor envia sua chave pública para o cliente.

- Recebimento e Descriptografia da Mensagem: Após receber a mensagem criptografada do cliente, o servidor a descriptografa usando sua chave privada.

## Passo 2: Configurando o Cliente
A classe Client é responsável por iniciar a comunicação com o servidor. Aqui estão os passos básicos:

- Conexão com o Servidor: O cliente se conecta ao servidor usando o endereço IP e a porta específicos.

- Recebimento da Chave Pública do Servidor: O cliente recebe a chave pública do servidor.

- Criptografia da Mensagem: O cliente criptografa sua mensagem usando a chave pública do servidor.

- Envio da Mensagem Criptografada: A mensagem criptografada é enviada para o servidor.

## Passo 3: Execução do Código
Para executar o código, siga estas etapas:

- Execute o servidor (Alice) em um terminal:
- Em outro terminal, execute o cliente (Bob):
