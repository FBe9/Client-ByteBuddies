package encrypt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * This class provides methods for asymmetric encryption using the RSA
 * algorithm.
 *
 * @author irati
 */
public class AsimetricaClient {

    /**
     * Encrypts the provided password using the ECC public key.
     *
     * @param password The password to be encrypted.
     * @return The encrypted data.
     */
    public static byte[] encryptedData(String password) {
        byte[] encryptedData = null;
        try {

            // Load ECC Public Key
            InputStream fis = AsimetricaClient.class.getResourceAsStream("publickey.der");
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

}
