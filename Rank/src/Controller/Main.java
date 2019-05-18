package Controller;

import Model.ComData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.util.Optional;


public class Main extends Application
{

    //load view switch controller
    ViewModelController viewModelController;

    //load view

    LoginController loginController;
    DataBaseController dataBaseController;

    @Override
    public void start(Stage primaryStage)
    {
        init();
        //add stage to hMap
        viewModelController.addStage("primary",primaryStage);
        //setting primary parameter
        primaryStage.setResizable(false);
        primaryStage.setTitle("请登录");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        //load Login Scene to primaryStage

        primaryStage.setScene(viewModelController.getScene("login.fxml"));
        loginController.show();
    }
    //init
    public void init()
    {
        viewModelController = new ViewModelController();
        loginController = (LoginController) viewModelController.loadScene(new File("/View/login.fxml"));
        ComData c = new ComData();
        c.init();
        boolean initOK = true;
        if (c.isLoad())//load local date no load later
        {
            dataBaseController = new DataBaseController();
            if (!dataBaseController.initDatabase())// init database
            {
                initOK = false;
            }
        }
        if (initOK == false)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("警告");
            alert.setHeaderText("程序即将退出！");
            alert.setContentText("错误,文件或数据库加载失败，请联系管理员！");
            Optional<ButtonType> button = alert.showAndWait();
            if (button.isPresent())
            {
                Platform.exit();

            }
        }

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}