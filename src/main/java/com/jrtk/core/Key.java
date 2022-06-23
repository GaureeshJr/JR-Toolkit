package com.jrtk.core;

import static org.lwjgl.glfw.GLFW.*;

public class Key {  //Input callback class [can be used by itself, but is recommended to be used with the wrapper class 'Input']
    private static Key instance;
    private boolean keyPressed[] = new boolean[350];

    private Key(){

    }
    public static Key get(){
        if(Key.instance == null){
            Key.instance= new Key();
        }
        return Key.instance;
    }
    public static void KeyCallBack(long window, int key , int scanCode , int action , int mods){
        boolean pressed = false;
        if(action == GLFW_PRESS && pressed == false){
            get().keyPressed[key] = true;
            pressed = true;
        } else if(action == GLFW_RELEASE){
            get().keyPressed[key] = false;
            pressed = false;
        }
    }
    public static boolean isKeyPressed(int keyCode){
        if(keyCode < get().keyPressed.length) {
            return get().keyPressed[keyCode];
        }else{
            return false;
        }
    }
}