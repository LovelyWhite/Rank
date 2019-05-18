package Controller;

import Listener.dragListener;
import Model.ComData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.File;
import java.util.Optional;
public class SettingController
{

    @FXML
    private TextField databaseName;
    @FXML
    private TextField ip;
    @FXML
    private TextField databasePort;
    @FXML
    private TextField databaseAccount;
    @FXML
    private PasswordField databasePassword;
    @FXML
    private TextField encoding;
    @FXML
    private Pane dragPane;//dragPane mouse drag
    @FXML
    private CheckBox useSSL;
    @FXML
    private Label messageIP;
    @FXML
    private Label messagePassword;
    @FXML
    private Label messageName;
    @FXML
    private Label messageAccount;
    @FXML
    private Label messageEncoding;
    @FXML
    private Label messagePort;
    @FXML
    //private ProgressIndicator readProgressIndicator;
    ComData comData;
    Stage stage;

    ViewModelController viewModelController;

    public SettingController() { }

    public void show()
    {
        init();//init
        databaseName.setText(comData.getDatabaseName());
        ip.setText(comData.getDatabaseIP());
        databasePassword.setText(comData.getDatabasePassword());
        databaseAccount.setText(comData.getDatabaseAccount());
        encoding.setText(comData.getDatabaseCharacterEncoding());
        databasePort.setText(comData.getDatabasePort());
        useSSL.setSelected(comData.isDatabaseUseSSL());
        stage.show();
    }

    public void post()
    {
        int x[] = {0, 0, 0, 0, 0, 0};
        String message = "*此项为必填项";
        String s[] = {"", "", "", "", "", ""};
        boolean i;

        s[0] = databaseName.getText();
        x[0] = s[0].length();
        if (x[0] == 0)
        {
            messageName.setText(message);
        }

        s[1] = ip.getText();
        x[1] = s[1].length();
        if (x[1] == 0)
        {
            messageIP.setText(message);
        }

        s[2] = databasePassword.getText();
        x[2] = s[2].length();
        if (x[2] == 0)
        {
            messagePassword.setText(message);
        }

        s[3] = databaseAccount.getText();
        x[3] = s[3].length();
        if (x[3] == 0)
        {
            messageAccount.setText(message);
        }

        s[4] = encoding.getText();
        x[4] = s[4].length();
        if (x[4] == 0)
        {
            messageEncoding.setText(message);
        }

        s[5] = databasePort.getText();
        x[5] = s[5].length();
        if (x[5] == 0)
        {
            messagePort.setText(message);
        }
        i = useSSL.isSelected();
        if (s[0] == null || s[1] == null || s[2] == null || s[3] == null || s[4] == null || s[5] == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("失败");
            alert.setHeaderText(null);
            alert.setContentText("请勿修改配置文件");
            alert.showAndWait();
            File file = new File("E:/Config.properties");
            if (file.exists())
            {
                TextInputDialog dialog = new TextInputDialog("E:" + File.separator + "Config.properties.bak");
                dialog.setTitle("文件备份");
                dialog.setHeaderText("即将删除错误的配置文件");
                dialog.setContentText("请输入文件备份位置:");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent())
                {
                    File backFile = new File(result.get());
                    file.renameTo(backFile);
                } else
                {
                }
            }
        }
        if (x[0] == 0 || x[1] == 0 || x[2] == 0 || x[3] == 0 || x[4] == 0 || x[5] == 0)
        {
        } else
        {
            comData.setDatabaseName(s[0]);
            comData.setDatabaseIP(s[1]);
            comData.setDatabasePassword(s[2]);
            comData.setDatabaseAccount(s[3]);
            comData.setDatabaseCharacterEncoding(s[4]);
            comData.setDatabasePort(s[5]);
            comData.setDatabaseUseSSL(i);
            if (comData.postDatabaseData())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("提示");
                alert.setHeaderText(null);
                alert.setContentText("提交成功");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK)
                {
                    stage.setScene(viewModelController.getScene("login.fxml"));
                } else
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("失败");
                    alert.setHeaderText(null);
                    alert.setContentText("请检查是否有写文件的权限!");
                    alert.showAndWait();
                }
            }
        }
    }

    public void clear()
    {
        databaseName.setText("");
        ip.setText("");
        databasePassword.setText("");
        databaseAccount.setText("");
        encoding.setText("");
        databasePort.setText("");
        useSSL.setSelected(false);
    }

    public void close()
    {
        if (!comData.isInit())
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("确认");
            alert.setHeaderText("确认离开？");
            alert.setContentText("请先完善数据库信息，否则系统将无法使用!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                stage.setScene(viewModelController.getScene("login.fxml"));
            }
        }
        else
        {
            stage.setScene(viewModelController.getScene("login.fxml"));
        }
    }

    public void minimize()
    {
        stage.setIconified(true);
    }

    public void init()
    {
        viewModelController = new ViewModelController();
        comData = new ComData();//load local data
        this.stage = viewModelController.getStage("primary");
        dragPane.setOnMouseDragged(new dragListener(stage));//add listener to dragPane
        dragPane.setOnMousePressed(new dragListener(stage));//

    }
}
