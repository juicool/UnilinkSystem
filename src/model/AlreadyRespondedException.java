package model;

public class AlreadyRespondedException extends Exception{
    public AlreadyRespondedException(String reason){super(reason);}
}
