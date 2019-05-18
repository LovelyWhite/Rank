package Controller;

import Model.Stages;
import javafx.scene.Scene;

import java.io.File;
import  Model.Scenes;
import javafx.stage.Stage;

public class ViewModelController
{
    Scenes scenes = new Scenes();
    Stages stages = new Stages();
    public ViewModelController()
    {

    }
    public void removeScene(String name)
    {
        scenes.removeScene(name);
    }

    public Scene getScene(String name)
    {
        return scenes.getScene(name);
    }

    public Object loadScene(File f)
    {
        return scenes.loadScene(f);
    }
    public void addStage(String name ,Stage stage)
    {
        stages.addStage(name,stage);
    }
    public Stage getStage(String name)
    {
        return stages.getStage(name);
    }
    public void removeStage(String name)
    {
        stages.removeStage(name);
    }
}
