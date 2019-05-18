package Model;

import java.util.EventListener;
import java.util.EventObject;

public class DataChangeEvent extends EventObject {

    private double nowProgressRead;
    private double nowProgressWrite;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public DataChangeEvent(Object source ,double nowProgressRead,double nowProgressWrite) {
        super(source);
        this.nowProgressRead = nowProgressRead;
        this.nowProgressWrite=nowProgressWrite;

    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
    public double getNowProgressRead()
    {
        return nowProgressRead;
    }
    public double getNowProgressWrite()
    {
        return nowProgressWrite;
    }
}
