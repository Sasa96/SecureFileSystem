/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securefilesystem;

import java.io.*;
import java.nio.file.*;
import javax.crypto.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RSA {

    private static KeyPair keypair;

    public static void buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);

        keypair = keyPairGenerator.genKeyPair();
        saveKeypair(keypair.getPublic(),keypair.getPrivate());
        

    }

    public static byte[] encrypt(String message, PublicKey pub) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pub);

        return cipher.doFinal(message.getBytes());
    }

    public static byte[] decrypt(byte[] encrypted,PrivateKey pvt) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pvt);

        return cipher.doFinal(encrypted);
    }

    public static void saveKeypair(PublicKey pub, PrivateKey pvt) {
        FileOutputStream out = null;
        try {
            String outFile = "mykey";
            out = new FileOutputStream(outFile + ".key");
            out.write(pvt.getEncoded());
            out.close();
            out = new FileOutputStream(outFile + ".pub");
            out.write(pub.getEncoded());
            out.close();
        } catch (Exception ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static PublicKey LoadPub(String PubPath) {
        try {
            Path path = Paths.get(PubPath);
            byte[] bytes = Files.readAllBytes(path);

            /* Generate public key. */
            X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pub = kf.generatePublic(ks);
                    return pub;

        } catch (Exception ex) {
        }
        
        return null;
    }
    
     public static PrivateKey LoadPvt(String PvtPath) {
        try {
            Path path = Paths.get(PvtPath);
            byte[] bytes = Files.readAllBytes(path);

            /* Generate public key. */
            PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey pvt = kf.generatePrivate(ks);
            return pvt;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
}
