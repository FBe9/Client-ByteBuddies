package encrypt;

import static com.google.common.io.ByteStreams.toByteArray;
import exceptions.EncryptException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
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
    public byte[] encryptedData(String password) throws EncryptException {
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
           throw new EncryptException();
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
