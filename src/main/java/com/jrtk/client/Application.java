package com.jrtk.client;

import com.jrtk.core.LayerStack;
import com.jrtk.utils.Time;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11.*;

//Abstract application class to be inherited by the Client
public abstract class Application {

    public Window window;
    public LayerStack layerStack;

    public  Application()
    {
        this.layerStack = new LayerStack();
    }
    public abstract void OnImGui(); //Editor UI function call

    protected abstract void OnInit(); //Custom Client defined methods

    protected abstract void OnUpdate(); //Custom Client defined methods

    protected abstract void OnDelete(); //Custom Client defined methods


    public void Init()
    {
        window.init();

        this.OnInit();
    }

    public void Run()
    {
        //deltaTime vars
        float begintime = (float)glfwGetTime();
        float endtime;

        while(window.shouldNotClose())
        {
            glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            this.OnUpdate();
            layerStack.Update();
            window.update();

            endtime = (float) glfwGetTime();
            Time.deltaTime = endtime - begintime;
            begintime = endtime;
        }


        //application on close
        this.OnDelete();
        layerStack.Delete();
        window.delete();
    }

}
