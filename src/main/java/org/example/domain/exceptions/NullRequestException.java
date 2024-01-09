package org.example.domain.exceptions;

public class NullRequestException extends RuntimeException{
    public NullRequestException(){}
    public NullRequestException(String msg){
        super(msg);
    }
}
