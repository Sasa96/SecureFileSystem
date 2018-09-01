/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securefilesystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */

public class ManageFileController {
    Stage window;
    
public static String userId;
public static String userEmail;

public JFXTextField userIdtext= new JFXTextField();

public JFXTextField useremailtext = new JFXTextField();


@FXML public ListView listView;
    @FXML
    private Label label;
    @FXML
    private JFXButton uploadBtn;
    @FXML
    private JFXButton uploadBtn2;
    @FXML
    private JFXButton uploadBtn21;
    @FXML
    private JFXButton uploadBtn24;
    @FXML
    private AnchorPane rootPane;

    @FXML
     public void goBack (ActionEvent event) throws Exception
   {
       
Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
Scene manageView = new Scene(root);
 window = (Stage)((Node)event.getSource()).getScene().getWindow();
window.setScene(manageView);
window.show();
   }
     
    @FXML
       public void List() throws IOException
   {
           FileHandler.clearFile("files_names.txt");
        FileHandler.clearFile("dropbox_order.txt");
            FileHandler.WriteFile("listfiles", "dropbox_order.txt", false);

               Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\dropboxLauncher.exe", null, new File(System.getProperty("user.dir")));
        try {
            TimeUnit.SECONDS.sleep(2); //stupid solution, should be fixed
        } catch (InterruptedException ex) {
            Logger.getLogger(SecureFileSys.class.getName()).log(Level.SEVERE, null, ex);
        }

               String filesAvailable = FileHandler.ReadFile("files_names.txt", false);
               String lines[] = filesAvailable.split("\\r?\\n");
               
              listView.getItems().setAll(lines);
                 JFXSnackbar snackbar = new JFXSnackbar(rootPane);
               
    snackbar.show("Refreshed File List", 2000);
   }
    
    @FXML
    public void RequestFileKey()
{
     if(listView.getSelectionModel().getSelectedItem()==null)
        {    JFXSnackbar snackbar = new JFXSnackbar(rootPane);
    snackbar.show("File must be selected to request File key", 2000);
        return ;
        }
    JFXSnackbar snackbar = new JFXSnackbar(rootPane);
    snackbar.show("Requested File Key", 2000);
}
    @FXML
    public void Upload(ActionEvent event) throws IOException
   {
       try
       {
   userId =userIdtext.getText().trim();
   userEmail = useremailtext.getText().trim();
  
   
    if(userId.equals("") || userEmail.equals(""))
    {
         JFXSnackbar snackbar = new JFXSnackbar(rootPane);
    snackbar.show("Must enter both Id and Email before you can upload file", 2000);
    return ;
    }
   window = (Stage)((Node)event.getSource()).getScene().getWindow();
 
        FileHandler.clearFile("upload_data.txt");
        FileHandler.clearFile("upload_name.txt");
        FileHandler.clearFile("dropbox_order.txt");
       FileChooser fc = new FileChooser();
       fc.setTitle("Choose the file you want to Upload");
       
      
  
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fc.setInitialDirectory(new File(currentPath));
        
        File selectedFile = fc.showOpenDialog(this.window);
        String filePath = selectedFile.getAbsolutePath();
        String fileName = selectedFile.getAbsolutePath().substring(selectedFile.getAbsolutePath().lastIndexOf("\\")+1);

        String fileContent = FileHandler.ReadFile(selectedFile.getPath(), false);
        FileHandler.WriteFile(fileContent, "upload_data.txt", true);
        FileHandler.WriteFile(fileName, "upload_name.txt", false);
        FileHandler.WriteFile("upload","dropbox_order.txt", false);        
        Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\dropboxLauncher.exe", null, new File(System.getProperty("user.dir")));

       JFXSnackbar snackbar = new JFXSnackbar(rootPane);
    snackbar.show("Uploaded Successfully", 1000);
       }catch(Exception e){}
       
       
   }
   
    @FXML
   public void Download() throws IOException
   {
       System.out.println(2);
       
       FileHandler.clearFile("download_name.txt");
       FileHandler.clearFile("download_location.txt");
        FileHandler.clearFile("dropbox_order.txt");
      
        Scanner sc = new Scanner(System.in);
        if(listView.getSelectionModel().getSelectedItem()==null)
        {    JFXSnackbar snackbar = new JFXSnackbar(rootPane);
    snackbar.show("File must be selected to Download", 2000);
    return ;
        }
            
        String fileName = listView.getSelectionModel().getSelectedItem().toString();
        
        String downloadPath = System.getProperty("user.dir")+"\\DropboxDownloads\\";
     //   System.out.println(downloadPath);
        FileHandler.WriteFile(fileName, "download_name.txt", false);
        FileHandler.WriteFile(downloadPath, "download_location.txt", false);
        FileHandler.WriteFile("download", "dropbox_order.txt", false);
        
                       Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\dropboxLauncher.exe", null, new File(System.getProperty("user.dir")));
                       JFXSnackbar snackbar = new JFXSnackbar(rootPane);
    snackbar.show("Downloaded File to folder DropBoxDownloads", 2000);
   }
    
}
