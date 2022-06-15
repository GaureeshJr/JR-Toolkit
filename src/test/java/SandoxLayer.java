import com.jrtk.client.Layer;
import com.jrtk.client.Window;

import static org.lwjgl.opengl.GL11.*;

public class SandoxLayer extends Layer {

    private Window window;

    public SandoxLayer(String name, int index, Window window) {
        super(name, index);
        this.window = window;
    }

    @Override
    public void OnAttach() {
        System.out.println("Layer: " + this.name + " attached!");
    }

    @Override
    public void OnUpdate() {
        glViewport(0, 0, window.getResolution().x, window.getResolution().y);


        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(0.1f, 0.1f, 0.12f, 1.0f);

        glBegin(GL_TRIANGLES);

        glColor3f(1, 0, 0);
        glVertex2f(-0.5f, -0.5f);

        glColor3f(0, 1, 0);
        glVertex2f( 0.5f, -0.5f);

        glColor3f(0, 0, 1);
        glVertex2f( 0.0f,  0.5f);

        glEnd();
    }

    @Override
    public void OnDetach() {

    }
}
