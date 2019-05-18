package Controller;

import Listener.dragListener;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.stage.Stage;

import java.security.KeyStore;

public class RegisterController
{
    @FXML
    private TextField account;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordRepeat;
    @FXML
    private Label labelPasswordR;
    ViewModelController viewModelController;
    Stage stage;

    public RegisterController()
    {
    }

    public void minimize()
    {
        stage.setIconified(true);
    }

    public void close()
    {
        stage.setScene(viewModelController.getScene("login.fxml"));
    }

    public void register()
    {
        if (password.getText().equals(passwordRepeat.getText()))
        {
            DataBaseController dataBaseController = new DataBaseController();
            dataBaseController.updateData("INSERT INTO Users VALUES('" + account.getText() + "','" + password.getText() + "')", true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("成功");
            alert.setHeaderText(null);
            alert.setContentText("注册成功!");
            alert.showAndWait();
        }
    }

    public void init()
    {
        viewModelController = new ViewModelController();
        this.stage = viewModelController.getStage("primary");
        stage.getScene().setOnMouseDragged(new dragListener(stage));
    }

    public void show()
    {
        init();
        stage.show();
    }

    public void compare()
    {
        if (!(password.getText().equals(passwordRepeat.getText())))
        {
            labelPasswordR.setText("密码不一致");
        } else
        {
            labelPasswordR.setText("");
        }

    }
}
