package com.lovepink.test;

//sigleton class
public final class exSingleton {
    //INSTANCE is singleton Object
    private static final exSingleton INSTANCE = new exSingleton();
    //restrict Object creation with private constructors
    private exSingleton(){}
    public static exSingleton getInstance(){
        //public access to Singleton Object
        return INSTANCE;
    }
}
