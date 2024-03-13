import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try {
            // Geração de chave RSA
            KeyPair keyPair = generateKeyPair();

            // Inicialização do servidor
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor aguardando conexão...");

            // Aguarda conexão do cliente
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado!");

            // Configuração dos streams de entrada e saída
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            // Envio da chave pública para o cliente
            byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
            outputStream.writeInt(publicKeyBytes.length);
            outputStream.write(publicKeyBytes);

            // Recebe mensagem criptografada do cliente
            int encryptedMessageLength = inputStream.readInt();
            byte[] encryptedMessage = new byte[encryptedMessageLength];
            inputStream.readFully(encryptedMessage);

            // Descriptografa a mensagem
            String decryptedMessage = decrypt(encryptedMessage, keyPair.getPrivate());

            System.out.println("Mensagem recebida de cliente: " + decryptedMessage);

            // Fechar recursos
            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    private static String decrypt(byte[] encryptedMessage, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedMessage);
        return new String(decryptedBytes);
    }
}
