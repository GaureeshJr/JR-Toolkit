package Sandbox;

import com.jrtk.client.Application;
import com.jrtk.client.Window;
import com.jrtk.editor.ImGuiLayer;

public class SandboxApp extends Application {


    public SandboxApp()
    {
        super();
        this.window = new Window("Sandbox.SandboxApp", 1600, 900);
    }

    @Override
    protected void OnInit() {
        this.layerStack.AddLayer(new SandoxLayer("gameLayer", 0, this.window));
        this.layerStack.AddLayer(new ImGuiLayer("Imgui Layer", 1, this.window, this));
    }

    @Override
    protected void OnUpdate() {

    }

    @Override
    protected void OnDelete() {

    }
}
