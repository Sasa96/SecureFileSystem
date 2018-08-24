/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securefilesys;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javax.crypto.Cipher;


/**
 *
 * @author ASUS
 */
public class SecureFileSys extends Application {
    Stage window;
    Scene mainView , interfacesView;
    @FXML
     public JFXButton uploadBtn;
   
    
     public void UploadFile() {
         try{
     FileChooser fc = new FileChooser();
       fc.setTitle("Choose the file you want to Upload");

        File selectedFile = fc.showOpenDialog(null);
        String fileName = selectedFile.getAbsolutePath();
if (selectedFile != null) {
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV


        String text = securefilesystem.FileHandler.ReadFile(fileName,false);
 
        String encrypted = securefilesystem.AES.encrypt(key,initVector,new String(text));
        securefilesystem.FileHandler.WriteFile(encrypted,"encryption.kksasa",true);
 
        String test_encryption = securefilesystem.FileHandler.ReadFile("encryption.kksasa",true); 
        String secret = securefilesystem.AES.decrypt(key, initVector, test_encryption);
        securefilesystem.FileHandler.WriteFile(secret,"decryption.txt",false);
}
         }catch(Exception e)
         {};
         
         
     }
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setResizable(false);
        window.setMaxWidth(1070);
        window.setMaxHeight(850);
        window.setTitle("Secure File System");
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        
        Scene mainView = new Scene(root);
        window.setScene(mainView);
        window.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
   
    }
    
}
