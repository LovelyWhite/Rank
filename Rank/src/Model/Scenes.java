package Model;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Scenes {
    static private HashMap<String,Scene> scenes = new HashMap<>();
    private void addScene(String name,Scene s) {
        scenes.put(name,s);
    }
    public void removeScene(String name) {
        scenes.remove(name);
    }
    public Scene getScene(String name)
    {
        return scenes.get(name);
    }
    public Object loadScene(File f) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(f.getPath().replaceAll("\\\\","/")));
        Scene s = null;
        try {
            Parent parent = loader.load();
            s = new Scene(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addScene(f.getName(),s);
        return loader.getController();
    }
}
