package Model;

import javafx.stage.Stage;

import java.util.HashMap;

public class Stages
{
    static private HashMap<String, Stage> stages = new HashMap<>();
    public void addStage(String name ,Stage stage)
    {
        stages.put(name,stage);
    }
    public Stage getStage(String name)
    {
        return stages.get(name);
    }
    public void removeStage(String name)
    {
        stages.remove(name);
    }
}
