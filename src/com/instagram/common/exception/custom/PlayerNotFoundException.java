package com.instagram.common.exception.custom;

public class PlayerNotFoundException extends Exception{
    public PlayerNotFoundException(String s){
        super(s); //"Invalid player id"
    }
}
