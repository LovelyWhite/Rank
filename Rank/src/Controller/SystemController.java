package Controller;

import Listener.dragListener;
import Model.ComData;
import Model.DataBase;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SystemController
{
    @FXML
    private TabPane table;
    @FXML
    private Tab tab1,tab2,tab3,tab4,tab5,tab6;
    @FXML
    private TextField grid1,grid2,grid3,grid4,grid5,grid6,grid7,grid8,grid9,grid10,grid11,grid12;
    private Stage stage;
    ViewModelController viewModelController;
    String s[]={"","","","","","","","","","","",""};
    ComData c = new ComData();
    public SystemController()
    {
    }
    public void show()
    {
        init();
        stage.show();
    }
    public void close()
    {
        stage.setScene(viewModelController.getScene("login.fxml"));
    }
    public void minimize()
    {
        stage.setIconified(true);
    }
    public void init()
    {
        viewModelController= new ViewModelController();
        stage=viewModelController.getStage("primary");
        stage.getScene().setOnMouseClicked(new dragListener(stage));
        stage.getScene().setOnMouseDragged(new dragListener(stage));

    }
   public void button1()
    {
        tab1.setDisable(true);
        tab2.setDisable(false);
    }
    public void button2()
    {
        tab2.setDisable(true);
        tab3.setDisable(false);
        s[0]=grid1.getText();
        s[1]=grid2.getText();
        s[2]=grid3.getText();
        s[3]=grid4.getText();
        s[4]=grid5.getText();
        s[5]=grid6.getText();
        s[6]=grid7.getText();
        s[7]=grid8.getText();
        s[8]=grid9.getText();
        s[9]=grid10.getText();
        s[10]=grid11.getText();
        s[11]=grid12.getText();
        DataBase dataBase = new DataBase();
      //  dataBase.updateData("INSERT INTO "+ +" VALUE("'s[0]','s[1]','s[2]','s[3]','s[4]','s[5]','s[6]','s[7]','s[8]','s[9]',"'s[10]'" +",'s[11]'",true) ;
    }
    public void button3()
    {
        tab3.setDisable(true);
        tab4.setDisable(false);
    }
    public void button4()
    {
        tab4.setDisable(true);
        tab5.setDisable(false);
    }
    public void button5()
    {
        tab5.setDisable(true);
        tab6.setDisable(false);
    }
}
