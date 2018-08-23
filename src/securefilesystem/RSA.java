/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securefilesystem;

import javax.crypto.*;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RSA {

    public static KeyPair keypair;

    public static void buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);

        keypair = keyPairGenerator.genKeyPair();

    }

    public static byte[] encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keypair.getPublic());

        return cipher.doFinal(message.getBytes());
    }

    public static byte[] decrypt(byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keypair.getPrivate());

        return cipher.doFinal(encrypted);
    }
}
