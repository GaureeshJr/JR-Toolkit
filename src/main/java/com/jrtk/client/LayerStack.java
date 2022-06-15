package com.jrtk.client;

import java.util.ArrayList;
import java.util.List;

public class LayerStack {

    private List<Layer> layers;

    public LayerStack()
    {
        this.layers = new ArrayList<Layer>();
    }

    public void Update()
    {
        for(Layer l : layers)
        {
            l.OnUpdate();
        }
    }

    public void Delete()
    {
        for(Layer l : layers)
        {
            l.OnDetach();
        }
    }

    public void AddLayer(Layer l){
        this.layers.add(l);
        l.OnAttach();
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
    public <T extends Layer> void removeLayer(Class<T> layerClass){
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
