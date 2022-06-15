package com.jrtk.engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;
public class Mouse {
    private static Mouse instance;
    private double scrollX , scrollY;
    private double xPos , yPos , lastY , lastX;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean dragging;
    private Vector2f gameViewPos;
    private Vector2f gameViewSize;

    private Mouse(){
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this. yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
        gameViewPos = new Vector2f();
        gameViewSize = new Vector2f();
    }

    public static Mouse get()
    {
        if(Mouse.instance == null){
            instance = new Mouse();
        }
        return Mouse.instance;
    }
    public static void MousePosCallback(long window, double xpos , double ypos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;
        get().dragging  = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }
    public static void MouseButtonCallback(long window , int button, int action, int mods)
    {
        if(action == GLFW_PRESS) {
            if(button <get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = true;
            }
        }else if(action == GLFW_RELEASE){
            if(button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = false;
                get().dragging = false;
            }
        }
    }
    public static void MouseScrollCallback(long window , double Xoffset , double YOffset){
        get().scrollX = Xoffset;
        get().scrollY = YOffset;
    }
    public static void EndFrame(){
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX(){
        return(float)get().xPos;
    }
    public static float getY(){
        return(float)get().yPos;
    }
    public static float getDeltaX(){
        return (float) (get().lastX - get().xPos);
    }
    public static float getDeltaY(){
        return (float) (get().lastY - get().yPos);
    }
    public static float getScrollX(){
        return (float)get().scrollX;
    }
    public static float getScrollY(){
        return (float)get().scrollY;
    }
    public static boolean isDraggin(){
        return get().dragging;
    }

    public static boolean isMouseButtonDown(int button){
        if(button < get().mouseButtonPressed.length) {
            return get().mouseButtonPressed[button];
        }else
        {
            return false;
        }
    }

    public static void setGameViewPos(Vector2f gameViewPos) {
        get().gameViewPos.set(gameViewPos);
    }

    public static void setGameViewSize(Vector2f gameViewSize) {
        get().gameViewSize.set(gameViewSize);
    }
}
