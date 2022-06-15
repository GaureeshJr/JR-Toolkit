package Sandbox;

import com.jrtk.client.Layer;
import com.jrtk.client.Window;
import com.jrtk.engine.FrameBuffer;
import com.jrtk.utils.Random;
import com.jrtk.utils.Time;
import org.joml.Vector2i;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.opengl.GL11.*;

public class SandoxLayer extends Layer {

    private Window window;
    public FrameBuffer outputBuffer;

    private Vector2i layerResolution;

    public SandoxLayer(String name, int index, Window window) {
        super(name, index);
        this.window = window;
        this.layerResolution = new Vector2i(300, 150);
        this.outputBuffer = new FrameBuffer(layerResolution.x, layerResolution.y, GL_NEAREST);

    }

    @Override
    public void OnAttach() {
        System.out.println("Layer: " + this.name + " attached!");
    }


    float posx = 0.5f, posy = 0.5f;
    @Override
    public void OnUpdate() {

        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        glViewport(0, 0, this.layerResolution.x, this.layerResolution.y);

        outputBuffer.Bind();

        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(0.14f, 0.14f, 0.14f, 1.0f);




        glBegin(GL_TRIANGLES);

        glColor3f(1, 0, 0);
        glVertex2f(-posx, -posy);

        glColor3f(0, 1, 0);
        glVertex2f( posx, -posy);

        glColor3f(0, 0, 1);
        glVertex2f( 0,  posy);

        glEnd();

        outputBuffer.Unbind();

        glViewport(0,0, window.getResolution().x, window.getResolution().y);

    }

    @Override
    public void OnDetach() {

    }
}
