/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securefilesystem;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
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
import java.security.SecureRandom;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


/**
 *
 * @author ASUS
 */
public class SecureFileSys extends Application {
    Stage window;
    Scene mainView , interfacesView;
    @FXML
     public JFXButton uploadBtn;
   
    @FXML 
     public JFXButton publicButton;
     @FXML
     public Label publicLabel = new Label("");
    @FXML 
     public JFXButton rngButton;
    @FXML 
    public Label rngLabel= new Label("");
    @FXML 
     public JFXButton encryptRngButton;
     @FXML 
     public JFXButton privateButton;
      @FXML
     public Label privateLabel= new Label("");
    @FXML 
     public JFXButton rng2Button;
    
     @FXML 
     public Label rng2Label= new Label("");
    @FXML 
     public JFXButton decryptRngButton;
    
    public File publicFile = null;
    public File rng1File = null;
    public File privateFile =null;
    public File rng2File = null;
     
   public void EncryptRng() throws Exception
   {
         System.out.println(publicFile);
    PublicKey pubk = RSA.LoadPub(publicFile.getAbsolutePath());
    
    byte[] rngKey = RSA.LoadFileKey(rng1File);
   
    RSA.encrypt(rngKey, pubk/*,RSA.LoadPvt(privateFile.getAbsolutePath())*/);
   
   }
   
   public void DecryptRng() throws Exception
   {
       PrivateKey privk = RSA.LoadPvt(privateFile.getAbsolutePath());
       System.out.println(rng2File);
    byte[] rngKey = RSA.LoadFileKey(rng2File);
    RSA.decrypt(rngKey, privk);
   }
    
    public void SelectPublicKey ()
    {
        publicFile =  SelectFile(publicLabel);
    } 
    
    public void SelectPrivateKey()
    {
        privateFile = SelectFile(privateLabel);
    }
    
    public void SelectRng1(){
        rng1File= SelectFile(rngLabel);
    }
    
    public void SelectRng2(){
       rng2File = SelectFile(rng2Label);
    }
    
    
     
     
  
   
    public void GenerateKey ()throws Exception 
    {
        RSA.buildKeyPair();
    }
     public void UploadFile() {
         try{
     FileChooser fc = new FileChooser();
       fc.setTitle("Choose the file you want to Upload");
       
      
  
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fc.setInitialDirectory(new File(currentPath));
        
        File selectedFile = fc.showOpenDialog(this.window);
        String fileName = selectedFile.getAbsolutePath();
      
if (selectedFile != null) {
        
         
        String initVector = "RandomInitVector"; // 16 bytes IV
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        //keyGen.init(128); // for example
        
      SecretKey secretKey = keyGen.generateKey(); // 128 bit key
       
    // 128 bits are converted to 16 bytes;
       
        AES.saveKey(secretKey.getEncoded(), selectedFile.getName());
        String text = securefilesystem.FileHandler.ReadFile(fileName,false);
        
      
     
        String encrypted = securefilesystem.AES.encrypt(secretKey,initVector,new String(text));
     
        securefilesystem.FileHandler.WriteFile(encrypted,"encryption.kksasa",true);
 
        String test_encryption = securefilesystem.FileHandler.ReadFile("encryption.kksasa",true); 
        String secret = securefilesystem.AES.decrypt(secretKey, initVector, test_encryption);
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
    
     public File SelectFile(Label label )
     { 
         File selectedFile=null;
         try{
     FileChooser fc = new FileChooser();
       fc.setTitle("Choose the file you want to Upload");
       
      
  
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fc.setInitialDirectory(new File(currentPath));
        
        selectedFile = fc.showOpenDialog(this.window);
        String fileName = selectedFile.getAbsolutePath();
      
if (selectedFile != null) {
      
      
        System.out.println(label);
        System.out.println(selectedFile.getName());
        label.setText(selectedFile.getName());
        
    }

  
     }catch(Exception e){
     };
        return selectedFile;
    
    }
     
}