package com.Sandbox;

import com.jrtk.client.Application;
import com.jrtk.client.Window;
import com.jrtk.editor.ImGuiLayer;

public class SandboxApp extends Application {

    private SandoxLayer sandboxLayer;

    public SandboxApp()
    {
        super();
        this.window = new Window("SandboxApp", 1600, 900);

        sandboxLayer = new SandoxLayer("gameLayer", 0, this);
    }

    @Override
    protected void OnInit() {
        this.layerStack.pushLayer(sandboxLayer);

        this.layerStack.pushLayer(new ImGuiLayer("uiLayer", 1, this));

    }

    @Override
    public void OnImGui(){
        sandboxLayer.EditorUI();
    }

    @Override
    protected void OnUpdate() {

    }

    @Override
    protected void OnDelete() {
        
    }
}
