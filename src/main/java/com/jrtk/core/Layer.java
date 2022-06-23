package com.jrtk.core;

public abstract class Layer
{
    protected int index;
    protected String name;

    public abstract void OnAttach();
    public abstract void OnUpdate();
    public abstract void OnDetach();

    public Layer(String name, int index)
    {
        this.name = name;
        this.index = index;
    }
}
