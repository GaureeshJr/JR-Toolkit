package com.Sandbox;

import com.jrtk.client.Application;
import com.jrtk.client.Window;
import com.jrtk.editor.FileExplorer;
import com.jrtk.editor.ImGuiLayer;
import com.jrtk.editor.Viewport;

public class SandboxApp extends Application {

    private SandoxLayer sandboxLayer;

    public SandboxApp()
    {
        super();
        this.window = new Window("Sandbox.SandboxApp", 1600, 900);

        sandboxLayer = new SandoxLayer("gameLayer", 0, this.window);
    }

    @Override
    protected void OnInit() {
        this.layerStack.pushLayer(new SandoxLayer("gameLayer", 0, this.window));

        this.layerStack.pushLayer(new ImGuiLayer("uiLayer", 1, this.window, this));

        this.layerStack.getLayer(ImGuiLayer.class).addEditorWindow(new FileExplorer(this, "src/"));
        this.layerStack.getLayer(ImGuiLayer.class).addEditorWindow(new Viewport(this));
    }

    @Override
    public void OnImGui(){
    }

    @Override
    protected void OnUpdate() {

    }

    @Override
    protected void OnDelete() {
        
    }
}
