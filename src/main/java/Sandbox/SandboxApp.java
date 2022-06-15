package Sandbox;

import com.jrtk.client.Application;
import com.jrtk.client.Window;
import com.jrtk.editor.ImGuiLayer;
import com.jrtk.editor.Viewport;
import com.jrtk.engine.Key;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

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

        this.layerStack.getLayer(ImGuiLayer.class).addEditorWindow(new Viewport(this));


    }

    @Override
    protected void OnUpdate() {
        if(Key.isKeyPressed(GLFW_KEY_R))
            this.layerStack.getLayer(ImGuiLayer.class).getEditorWindow(Viewport.class).enabled = false;
        if(Key.isKeyPressed(GLFW_KEY_Q))
            this.layerStack.getLayer(ImGuiLayer.class).getEditorWindow(Viewport.class).enabled = true;


    }

    @Override
    protected void OnDelete() {

    }
}
