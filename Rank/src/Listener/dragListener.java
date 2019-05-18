package Listener;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class dragListener implements EventHandler<MouseEvent>
{

    Stage dStage;

    public dragListener(Stage dStage)
    {
        this.dStage = dStage;
    }

    @Override
    public void handle(MouseEvent event)
    {
        double x = 180;
        double y = 10;
         event.consume();
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED)
        {

            x = event.getSceneX();
            y = event.getSceneY();
        }
        if (event.getEventType() == MouseEvent.MOUSE_DRAGGED)
        {
            dStage.setX(event.getScreenX() - x);
            dStage.setY(event.getScreenY() - y);
        }
    }
}


