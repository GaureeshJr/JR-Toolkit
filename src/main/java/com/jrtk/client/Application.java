package com.jrtk.client;

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
        while(window.shouldNotClose())
        {
            this.OnUpdate();
            layerStack.Update();
            window.update();
        }


        this.OnDelete();
        layerStack.Delete();
        window.delete();
    }

}
