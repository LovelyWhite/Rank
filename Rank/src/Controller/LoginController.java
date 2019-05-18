package Controller;

import Listener.dragListener;
import Model.ComData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController
{
    @FXML
    private TextField account;
    @FXML
    private PasswordField password;
    @FXML
    private CheckBox loginAuto;
    @FXML
    private Hyperlink register;
    @FXML
    private Hyperlink findPassword;
    @FXML
    private CheckBox rememberPassword;
    @FXML
    private Label databaseInformation;
    @FXML
    private ImageView head;

    SystemController systemController;
    ComData comData;
    ViewModelController viewModelController;
    SettingController settingController;
    DataBaseController dataBaseController;
    Stage stage;
    String sAccount;
    String sPassword;
    public LoginController() { }


    public void show()
    {
        //init
        init();
        //show Scene
       stage.show();
    }
    public void login() throws SQLException
    {


        if (comData.isLoad())
        {
            sAccount = account.getText();
            sPassword = password.getText();
            //There should be a password validation function
            //
            //
            System.out.println(sAccount+"xxx"+sPassword);
            if (sAccount.length() != 0 && sPassword.length() != 0)
            {
                if (dataBaseController.testLink())
                {
                    if (loginAuto.isSelected())
                    {
                        rememberPassword.setSelected(true);
                        comData.setLoginAuto(true);
                        comData.setLastLogin(sAccount);//set last login;
                        comData.setLastLoginPw(sPassword);
                    }
                    //if select auto login , select remember password
                    if (rememberPassword.isSelected())
                    {
                        comData.addProfile(sAccount, sPassword);
                    } else if (!rememberPassword.isSelected())
                    {
                        comData.removeProfile(sAccount);
                    }
                    ResultSet rs = dataBaseController.SearchData("SELECT Account,Password From Users WHERE Account=" + sAccount + " AND Password=" + sPassword);
                    if (rs != null && rs.next())
                    {
                        comData.postAccountData();
                        systemController = (SystemController) viewModelController.loadScene(new File("/View/system.fxml"));
                        stage.setScene(viewModelController.getScene("system.fxml"));
                        systemController.show();
                    } else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("登录失败");
                        alert.setHeaderText(null);
                        alert.setContentText("请检查账号或密码是否正确!");
                        alert.showAndWait();
                    }
                } else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误");
                    alert.setHeaderText(null);
                    alert.setContentText("请检查数据库设置是否正确!");
                    alert.showAndWait();
                }
            } else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("登录失败");
                alert.setHeaderText(null);
                alert.setContentText("请输入账号和密码!");
                alert.showAndWait();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("登录失败");
            alert.setHeaderText(null);
            alert.setContentText("无数据库数据载入!");
            alert.showAndWait();
        }
    }

    public void minimize()
    {
        stage.setIconified(true);
    }

    public void setting()
    {
        settingController = (SettingController)viewModelController.loadScene(new File("/View/setting.fxml"));
        stage.setScene(viewModelController.getScene("setting.fxml"));
        settingController.show();
    }

    public void close()
    {
        stage.close();
    }

    public void init()
    {
        viewModelController = new ViewModelController();//add SceneController
        dataBaseController = new DataBaseController();
        stage = viewModelController.getStage("primary");//
        stage.getScene().setOnMousePressed(new dragListener(stage));// add listener to move of scene
        stage.getScene().setOnMouseDragged(new dragListener(stage));//
        comData  = new ComData();// local data model
        if(comData.isLoginAuto())
        {
            sAccount = comData.getLastLogin();
            comData.setNowLogin(sAccount);
            account.setText(sAccount);
            sPassword = comData.getLastLoginPw();
            password.setText(sPassword);
            try
            {
                login(); //auto login
            } catch (SQLException e)
            {e.printStackTrace();
            }
        }

    }
    public void register()
    {if(comData.isLoad())
        {
            RegisterController registerController = (RegisterController) viewModelController.loadScene(new File("/View/register.fxml"));
            stage.setScene(viewModelController.getScene("register.fxml"));
            registerController.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("请检查数据库设置是否正确!");
            alert.showAndWait();
        }
    }
    public void findPassword()
    {

    }
}
