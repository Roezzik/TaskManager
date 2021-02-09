package shared.utils;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


public class Command {
    
    private int    commandId;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    private Object content;
    
    @JsonCreator
    public Command(@JsonProperty("commandId") int commandId, @JsonProperty("content") Object content) {
        this.commandId = commandId;
        this.content = content;
    }
    
    public int getCommandId() {
        return commandId;
    }
    
    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }
    
    public Object getContent() {
        return content;
    }
    
    public void setContent(Object content) {
        this.content = content;
    }
}
