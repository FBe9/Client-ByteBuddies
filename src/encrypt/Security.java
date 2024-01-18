package encrypt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Alex
 */
public class Security {

    /**
     * Criptografía Simétrica
     *
     * Esta clase permite cifrar un texto y lo guarda en un fichero. La única
     * forma de descifrar el texto es mediante una clave, que tanto el emisor
     * como el receptor conocen.
     *
     * PASOS: 1 Creacion de la clave, denominada derivada, porque se obtiene a
     * partir de un password usando para ello la función PBKDF2 además añadimos
     * un salt o semilla para hacerlo más random Hecho lo anterior usamos
     * SecretKeyFactory para generar la clave simétrica y SecretKeySpec para
     * "adaptarla" al algoritmo de cifrado: AES 2 Empezamos el proceso de
     * cifrado definiendo el algoritmoa usar: AES 3 Como el AES cifra dividiendo
     * el mensaje en bloques de 16 bytes definición del procedimiento de padding
     * o relleno del ultimo bloque: PKCS5Padding Para añadir más seguridad se
     * utiliza el modo de operación CBC, o sea, XOR entre los bloques 4 Cifrado.
     * Añadimos un vector de inicialización, o sea, que al texto cifrado se le
     * añade un byte[] random para añadir más seguridad
     */

    // Fíjate que el String es de exactamente 16 bytes
    private static byte[] salt = "esta es la salt!".getBytes();

    /**
     * Cifra un texto con AES, modo CBC y padding PKCS5Padding (simétrica) y lo
     * retorna
     *
     * @param clave La clave del usuario
     * @param mensaje El mensaje a cifrar
     * @return Mensaje cifrado
     */
    public String cifrarTexto(String clave, String mensaje) {
        String ret = null;
        KeySpec derivedKey = null;
        SecretKeyFactory secretKeyFactory = null;
        try {

            /* Usamos Password-based encryption (PBE) para obtener una clave derivada (derivedKey)
            * a partir de la que proporciona el emisor.
            * 
            * "salt"(Semilla). En criptografía, un salt es un dato aleatorio que se
            * usa como una entrada adicional de cifrado. En este caso, vamos a utilizar
            * salt para crear una clave de exactamente 16 bytes. Generalmente un salt se 
            * genera aleatoriamente cuando creas la clave, así que necesitas guardar la 
            * clave y su salt para poder cifrar y descifrar.
            * 
            * iterations. Ver https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html#pbkdf2
            * It refers to the number of times the underlying cryptographic hash function 
            * is applied to the input data during the key derivation process. 
            * It is a security parameter that influences the computational cost of deriving
            * a key from a password.
            * A higher iteration count makes the key derivation process more computationally 
            * expensive and time-consuming. This intentional slowness is designed to make 
            * brute-force attacks more difficult and resource-intensive for an attacker. 
            * The idea is to slow down the process of trying different passwords 
            * */
            derivedKey = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128);
            // AES tiene los siguientes tmños de key: 128, 192, or 256 bits

            // SecretKeyFactory is used to obtain a secret key from the key specification using the PBKDF2 algorithm 
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] derivedKeyPBK = secretKeyFactory.generateSecret(derivedKey).getEncoded();

            SecretKey derivedKeyPBK_AES = new SecretKeySpec(derivedKeyPBK, 0, derivedKeyPBK.length, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, derivedKeyPBK_AES);
            byte[] encodedMessage = cipher.doFinal(mensaje.getBytes()); // Mensaje cifrado !!!
            byte[] iv = cipher.getIV(); // vector de inicializaci�n  

            // Añadimos el vector de inicialización
            byte[] combined = concatArrays(iv, encodedMessage);

            fileWriter("c:\\security\\EjemploAE.dat", combined);

            ret = new String(encodedMessage);

        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {

        }
        return ret;
    }

    /**
     * Descifra un texto con AES, modo CBC y padding PKCS5Padding (simétrica) y
     * lo retorna
     *
     * @param clave La clave del usuario
     */
    private String descifrarTexto(String clave) {
        String ret = null;

        // Fichero leído
        byte[] fileContent = fileReader("c:\\security\\EjemploAE.dat");
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16)); // La IV est� aqu�
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));
            ret = new String(decodedMessage);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {

        }
        return ret;
    }

    /**
     * Retorna una concatenaci�n de ambos arrays
     *
     * @param array1
     * @param array2
     * @return Concatenaci�n de ambos arrays
     */
    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }

    /**
     * Escribe un fichero
     *
     * @param path Path del fichero
     * @param text Texto a escibir
     */
    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna el contenido de un fichero
     *
     * @param path Path del fichero
     * @return El texto del fichero
     */
    private byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void main(String[] args) {
        Security ejemploAES = new Security();
        String mensajeCifrado = ejemploAES.cifrarTexto("Clave", "Mensaje super secreto");
        System.out.println("Cifrado! -> " + mensajeCifrado);
        System.out.println("-----------");
        System.out.println("Descifrado! -> " + ejemploAES.descifrarTexto("Clave"));
        System.out.println("-----------");
    }
}
