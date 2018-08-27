/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securefilesystem;

import java.io.*;
import java.nio.charset.Charset;
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
        saveKey(keypair.getPublic(), keypair.getPrivate(), null,null);

    }

    public static byte[] encrypt(byte[] message, PublicKey pub/*,PrivateKey pvt*/) throws Exception {
    
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pub);
        byte[] efk = cipher.doFinal(message);
        
      /*  cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, pvt);
        byte[] dfk = cipher.doFinal(efk);*/
        
        
        saveKey(null,null,efk,null);
        return efk;
    }

    public static byte[] decrypt(byte[] encrypted, PrivateKey pvt) throws Exception {
        System.out.println(encrypted.length);
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, pvt);
        byte[] dfk = cipher.doFinal(encrypted);
        saveKey(null,null,null,dfk);
        return dfk;
    }

    public static void saveKey(PublicKey pub, PrivateKey pvt, byte[] efk,byte[] dfk) {
        FileOutputStream out = null;
        try {

            if (pub != null) {
                out = new FileOutputStream("mykey" + ".private");
                out.write(pvt.getEncoded());
                out.close();
            }
            if (pvt != null) {
                out = new FileOutputStream("mykey" + ".public");
                out.write(pub.getEncoded());
                out.close();
            }

            if (efk != null) {
                out = new FileOutputStream("efk" + ".fk");
                ObjectOutputStream outobj = new ObjectOutputStream(out);
                outobj.writeObject(efk);
                outobj.close();
                out.close();

            }
            
            if (dfk != null) {
                out = new FileOutputStream("dfk" + ".fk");
                ObjectOutputStream outobj = new ObjectOutputStream(out);
                outobj.writeObject(dfk);
                outobj.close();
                out.close();

            }

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

    public static byte[] LoadFileKey(File file) {
        
        byte[] fileContent = null;
        
        try {
            fileContent = Files.readAllBytes(file.toPath());
 
                
        } catch (Exception i) {
            System.out.println("L");
            i.printStackTrace();
        }
        
        return fileContent;
    }

}
