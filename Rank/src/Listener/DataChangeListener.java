package Listener;
import Model.*;
import javafx.scene.control.ProgressIndicator;

import java.util.EventListener;

/*读入一个指示器,改变它的值*/
public class DataChangeListener implements EventListener
{
    ProgressIndicator progressRead;
    double nowProgress;

    public DataChangeListener(ProgressIndicator progress)
    {
        this.progressRead = progress;
    }

    public void handle(DataChangeEvent e)
    {
        nowProgress = e.getNowProgressRead();
        progressRead.setProgress(nowProgress);
    }
}
