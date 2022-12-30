package org.example.exception;

public class DBConnectionException extends Throwable {
    //we could add more fields

    public DBConnectionException(String message){
        super(message);
    }
}
