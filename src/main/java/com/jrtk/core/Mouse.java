package com.jrtk.core;



import static org.lwjgl.glfw.GLFW.*;
public class Mouse { //Input callback class [can be used by itself, but is recommended to be used with the wrapper class 'Input']

    private static Mouse instance;
    private double scrollX , scrollY;
    private double xPos , yPos , lastY , lastX;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean dragging;

    private Mouse(){
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this. yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
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

        return (float)get().yPos;
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
    public static boolean isDragging(){
        return get().dragging;
    }

    public static boolean isMouseBUttonPressed(int button){
        if(button < get().mouseButtonPressed.length) {
            return get().mouseButtonPressed[button];
        }else
        {
            return false;
        }
    }

}
