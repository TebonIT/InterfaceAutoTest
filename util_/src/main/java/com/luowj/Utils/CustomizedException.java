package com.luowj.Utils;

public class CustomizedException extends RuntimeException{
    public CustomizedException(){
        super();
    }
    public CustomizedException(String message){
        super(message);
    }
}
