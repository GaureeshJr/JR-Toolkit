package com.jrtk.core;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

//  The engine's design is based on using 'Layers' for the render and other functions,
//  currently the layers are used in the order that you PUSHED then into the LAYER-STACK.
//  But in the future, we will use the used defined 'index' of a layer to arrange them in
//  order before their methods are called.

public class LayerStack
{

    private List<Layer> layers;

    public LayerStack()
    {
        this.layers = new ArrayList<Layer>();
    }

    public void Update()
    {
        for(Layer l : layers)
        {
            float beginTime = (float)glfwGetTime();
            l.OnUpdate();
            float endTime = (float)glfwGetTime();
            float fT = endTime - beginTime;
            l.frameTime = fT;
        }
    }

    public void Delete()
    {
        for(Layer l : layers)
        {
            l.OnDetach();
        }
    }

    public void pushLayer(Layer l){
        this.layers.add(l);

        l.OnAttach();
        System.out.println("Layer: " + l.name + " attached!");
    }

    public <T extends Layer> T getLayer(Class<T> componentClass){
        for (Layer l: layers) {
            if(componentClass.isAssignableFrom(l.getClass())){
                try{
                    return componentClass.cast(l);
                }catch (ClassCastException r){
                    r.printStackTrace();
                    assert false : "Err: Casting component";
                }
            }
        }
        return null;
    }
    public <T extends Layer> void popLayer(Class<T> layerClass){
        for (int i = 0 ; i < layers.size(); i++){
            Layer l = layers.get(i);
            if(layerClass.isAssignableFrom(l.getClass())){
                layers.get(i).OnDetach();
                layers.remove(i);
                return;
            }
        }
    }
}
