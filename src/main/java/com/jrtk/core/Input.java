package com.jrtk.core;



public class Input {    //Input wrapper class for easier and more advanced Input handling

    //--------------------------------------KEYBOARD-----------------------------------------------
    public static boolean getKey(int KEYCODE){
        return Key.isKeyPressed(KEYCODE);
    }


    //--------------------------------------------MOUSE--------------------------------------------
    public static boolean getMouseButton(int MOUSEBUTTON){
        return Mouse.isMouseBUttonPressed(MOUSEBUTTON);
    }

    public static boolean isDragging(){
        return Mouse.isDragging();
    }

    public static float getMouseScrollX(){
        return Mouse.getScrollX();
    }

    public static float getMouseScrollY(){
        return Mouse.getScrollY();
    }

    public static float cursorDeltaX(){
        return Mouse.getDeltaX();
    }

    public static float cursorDeltaY(){
        return Mouse.getDeltaY();
    }

    public static float getCursorX(){
        return Mouse.getX();
    }
    public static float getCursorY(){
        return Mouse.getY();
    }

    public static float getCursorXnormalised(){
        return Mouse.getX();
    }

    //---------------------------------------------------------------------------------------------
}
