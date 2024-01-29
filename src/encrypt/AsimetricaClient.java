package encrypt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

/**
 * This class provides methods for asymmetric encryption using the RSA
 * algorithm.
 *
 * @author irati
 */
public class AsimetricaClient {

    /**
     * Encrypts the provided password using the RSA public key.
     *
     * @param password The password to be encrypted.
     * @return The encrypted data.
     */
    public static byte[] encryptedData(String password) {
        byte[] encryptedData = null;
        try {
            keyGenerator();
            // Load Private Key
            Path workingDirectory = Paths.get(System.getProperty("user.home") + "/ByteBuddies/security/asymmetric/publickey.der");
            FileInputStream fis = new FileInputStream(workingDirectory.toFile());
            byte[] publicKeyBytes = new byte[fis.available()];
            fis.read(publicKeyBytes);
            fis.close();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedData = cipher.doFinal(password.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedData;
    }

    /**
     * Converts encrypted data to hexadecimal format.
     *
     * @param encryptedText The encrypted data.
     * @return The hexadecimal representation of the encrypted data.
     */
    public static String hexadecimal(byte[] encryptedText) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer buf = new StringBuffer();

        for (int j = 0; j < encryptedText.length; j++) {
            buf.append(hexDigit[(encryptedText[j] >> 4) & 0x0f]);
            buf.append(hexDigit[encryptedText[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * Generates an RSA key pair and saves the public and private keys in the
     * specified directory. If keys already exist, the method does not
     * regenerate them.
     */
    public static void keyGenerator() {
        try {
            // Creates the directory if not exists
            Path workingDirectory = Files.createDirectories(Paths.get(System.getProperty("user.home") + "/ByteBuddies/security/asymmetric"));

            // Thats the paths of public key and private key
            Path publicKeyFind = workingDirectory.resolve("publickey.der");
            Path privateKeyFind = workingDirectory.resolve("privatekey.der");
            //Verifies that the key doesn't exits and creates it.
            if (!Files.exists(publicKeyFind) && !Files.exists(privateKeyFind)) {
                try {
                    // Especificamos el algoritmo de encriptación
                    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                    // Especificamos el tamaño en bits de las claves
                    keyPairGenerator.initialize(2048);
                    // Se genera el par de claves
                    KeyPair keyPair = keyPairGenerator.generateKeyPair();

                    PublicKey publicKeyAndMore = keyPair.getPublic();
                    byte[] publicKeyBytes = publicKeyAndMore.getEncoded();

                    Path publicKeyPath = workingDirectory.resolve("publickey.der");
                    Path privateKeyPath = workingDirectory.resolve("privatekey.der");
                    try (FileOutputStream publicKeyFile = new FileOutputStream(publicKeyPath.toFile())) {
                        publicKeyFile.write(publicKeyBytes);
                    }

                    PrivateKey privateKey = keyPair.getPrivate();
                    byte[] privateKeyBytes = privateKey.getEncoded();
                    try (FileOutputStream privateKeyFile = new FileOutputStream(privateKeyPath.toFile())) {
                        privateKeyFile.write(privateKeyBytes);
                    }

                } catch (Exception e) {
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AsimetricaClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
