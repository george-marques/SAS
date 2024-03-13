import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try {
            // Inicialização do cliente
            Socket socket = new Socket(SERVER_IP, PORT);

            // Configuração dos streams de entrada e saída
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            // Recebe a chave pública do servidor
            int publicKeyLength = inputStream.readInt();
            byte[] publicKeyBytes = new byte[publicKeyLength];
            inputStream.readFully(publicKeyBytes);

            PublicKey serverPublicKey = KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(publicKeyBytes));

            // Mensagem a ser enviada para o servidor
            String message = "Olá, Alice! Esta é uma mensagem segura.";

            // Criptografa a mensagem com a chave pública do servidor
            byte[] encryptedMessage = encrypt(message, serverPublicKey);

            // Envia a mensagem criptografada para o servidor
            outputStream.writeInt(encryptedMessage.length);
            outputStream.write(encryptedMessage);

            // Fechar recursos
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] encrypt(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes());
    }
}
