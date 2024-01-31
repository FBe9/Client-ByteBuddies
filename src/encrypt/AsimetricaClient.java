package encrypt;

import static com.google.common.io.ByteStreams.toByteArray;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public byte[] encryptedData(String password) {
        byte[] encryptedData = null;
        try {
            
            InputStream input = getClass().getResourceAsStream("public.der");
            byte fileKey[] = toByteArray(input);
            input.close();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(fileKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedData = c.doFinal(password.getBytes());

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
