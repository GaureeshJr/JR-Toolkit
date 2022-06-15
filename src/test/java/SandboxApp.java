import com.jrtk.client.Application;
import com.jrtk.client.Window;

import static org.lwjgl.opengl.GL11.*;

public class SandboxApp extends Application {


    public SandboxApp()
    {
        super();
        this.window = new Window("SandboxApp", 1280, 720);
    }

    @Override
    protected void OnInit() {
        this.layerStack.AddLayer(new SandoxLayer("gameLayer", 0, this.window));
    }

    @Override
    protected void OnUpdate() {


    }

    @Override
    protected void OnDelete() {

    }
}
