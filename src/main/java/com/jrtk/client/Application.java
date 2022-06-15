package com.jrtk.client;

import com.jrtk.utils.Time;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public abstract class Application {

    public Window window;
    public LayerStack layerStack;

    public  Application()
    {
        this.layerStack = new LayerStack();
    }

    protected abstract void OnInit();

    protected abstract void OnUpdate();

    protected abstract void OnDelete();



    public void Init()
    {
        window.init();

        this.OnInit();
    }

    public void Run()
    {
        float begintime = (float)glfwGetTime();
        float endtime;

        while(window.shouldNotClose())
        {

            this.OnUpdate();
            layerStack.Update();
            window.update();

            endtime = (float) glfwGetTime();
            Time.deltaTime = endtime - begintime;
            begintime = endtime;
        }


        this.OnDelete();
        layerStack.Delete();
        window.delete();
    }

}
