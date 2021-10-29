package libraryPackage;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;



public class CipherUtil {

    /**

     * Create a 1024-bit RSA key pair.

     */

    public static KeyPair genRSAKeyPair() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator gen;
        
        gen = KeyPairGenerator.getInstance("RSA");

        gen.initialize(1024, secureRandom);

        KeyPair keyPair = gen.genKeyPair();
        return keyPair;

    }

    /**

     * Perform RSA encryption with the Public key.

     * @param plainText : the statement to be encrypted.

     * @param publicKey : public key

     * @return

     */

    public static String encryptRSA(String plainText, PublicKey publicKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
                      BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] bytePlain = cipher.doFinal(plainText.getBytes());

        String encrypted = Base64.getEncoder().encodeToString(bytePlain);
    	return encrypted;

    }
    
    /**

     * Perform RAS decryption with the Private Key.

     * @param encoded This is a base64 encoded string of encrypted old data.

     * This is a private key for @param privateKey decryption.
     
     */

    public static String decryptRSA(String encrypted, PrivateKey privateKey)

    		throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,

    		         BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {



        Cipher cipher = Cipher.getInstance("RSA");
        byte[] byteEncrypted = Base64.getDecoder().decode(encrypted.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytePlain = cipher.doFinal(byteEncrypted);
        String decrypted = new String(bytePlain, "utf-8");
        
        return decrypted;

    }

}

