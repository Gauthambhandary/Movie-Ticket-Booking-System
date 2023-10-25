/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package movieticketbookingsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Gauthu
 */
public class FXMLDocumentController implements Initializable {
    
        @FXML
    private TextField sgnup_username;

    @FXML
    private Hyperlink signin_createaccount;

    @FXML
    private TextField signin_email;

    @FXML
    private AnchorPane signin_form;

    @FXML
    private Button signin_login;

    @FXML
    private PasswordField signin_password;

    @FXML
    private Hyperlink signup_alreadyhave;

    @FXML
    private Button signup_button;

    @FXML
    private TextField signup_email;

    @FXML
    private PasswordField signup_password;

    @FXML
    private AnchorPane singup_form;
    
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement stm;
    private ResultSet rst;
    
    //checking for email validation
    public boolean  ValidEmail(){
        Pattern patter=Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-z0-9]+([.][a-zA-Z]+)+");
        Matcher match=patter.matcher(signup_email.getText());
        Alert alert;
        
        if(match.find() && match.group().matches(signup_email.getText())){
            return true;
        }else{
            alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid emil");
            alert.showAndWait();
            return false;
        }
        
    }
    
    //for signup button
    public void signup(){
        String sql="INSERT INTO `admin`( `username`, `email`, `password`) VALUES (?,?,?)";
        
        String sql1="SELECT * FROM `admin`";
        
        
        connect=database.ConnectDb();
        try{
            prepare=connect.prepareStatement(sql);
            prepare.setString(1, sgnup_username.getText());
            prepare.setString(2, signup_email.getText());
            prepare.setString(3, signup_password.getText());
            
            
            Alert alert;
            
            if(sgnup_username.getText().isEmpty() || signup_email.getText().isEmpty() || signup_password.getText().isEmpty() ){
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please  fill blank fields");
                alert.showAndWait();
                
            }
            else if(signup_password.getText().length() <8){
                  alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid password");
                alert.showAndWait();
            }else{
                
                if(ValidEmail()){
                    
//                    prepare=connect.prepareStatement(sql1);
//                    rst=prepare.executeQuery();
                        
                    
//                    if(rst.next()){
//                        alert=new Alert(Alert.AlertType.INFORMATION);
//                        alert.setTitle("Error Message");
//                        alert.setHeaderText(sgnup_username.getText() + "was alerady exist!");
//                        alert.showAndWait();
//                    }
//                    else{
                    
                        prepare.execute();
                        alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfull create a new account!");
                        alert.showAndWait();

                        //clear the sigup field
                        sgnup_username.setText("");
                        signup_email.setText("");
                        signup_password.setText("");

                        singup_form.setVisible(false);
                        signin_form.setVisible(true);
//                    }
                 }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    //for signin button 
    public void signin(){
        String sql="SELECT * FROM `admin` where email=? and password=?";
        
        connect=database.ConnectDb();
        try{
            prepare=connect.prepareStatement(sql);
            prepare.setString(1, signin_email.getText());
            prepare.setString(2, signin_password.getText());
            
            rst=prepare.executeQuery();
            Alert alert;
            
            if(signin_email.getText().isEmpty() || signin_password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.CONFIRMATION.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("please fill all bank fields");
                alert.showAndWait();
                
            }else{
                if(rst.next()){
               //     alert=new Alert(Alert.AlertType.INFORMATION);
                 //   alert.setTitle("Information Message");
                   // alert.setHeaderText(null);
                    //alert.setContentText("Successfull login!");
                    //alert.showAndWait();
                    
                    //to hide the login page
                    signin_login.getScene().getWindow().hide();
                    
                    Parent root=FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Stage stage=new Stage();
                    Scene scene=new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    
                }else{
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
             
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void switchForm(ActionEvent event){
        if(event.getSource()== signin_createaccount){
            signin_form.setVisible(false);
            singup_form.setVisible(true);
        }else if(event.getSource()== signup_alreadyhave){
            signin_form.setVisible(true);
            singup_form.setVisible(false);
        }
    }

    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
