import java.util.*;
import java.time.*;

abstract class Source{
    //* default values
    protected Integer sourceId = 0;

    public Source(){
        sourceId = setSourceId;
    }
    
    public void setSourceId(){
        sourceId++;
    }
    public abstract getSourceId();

    public abstract showSourceInformation();
    public abstract showSourceContent();
}