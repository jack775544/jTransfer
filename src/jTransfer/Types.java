package jTransfer;

import java.sql.Connection;
import java.util.*;
import java.sql.*;

/**
 * Created by JasonHughes on 26/05/2016.
 */

public class Types {
    // instantiate logging_session database metadata types
    private String id;
    private String timestamp;
    private String sessionId;
    private String log;

    // constructor
    public Types() {
        this.id = null;
        this.timestamp = null;
        this.sessionId = null;
        this.log = null;
    }

    // getter method for id variable
    public String getId() { return id; }

    // setter method for id variable
    public void setId(String id) {
        this.id = id;
    }

    // getter method for timestamp variable
    public String getTimestamp() {
        return timestamp;
    }

    // setter method for timestamp variable
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    // getter method for sessionId variable
    public String getSessionId() {
        return sessionId;
    }

    // setter method for sessionId variable
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    // getter method for log variable
    public String getLog() {
        return log;
    }

    // setter method for log variable
    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "Types{" +
                "id='" + id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", log='" + log + '\'' +
                '}';
    }
    
    public String toHTML(){
        return "<td>" + id + "</td>" +
                "<td>" + timestamp + "</td>" +
                "<td>" + sessionId + "</td>" +
                "<td>" + log + "</td>";
    }


}

