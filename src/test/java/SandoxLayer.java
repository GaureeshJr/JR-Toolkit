import com.jrtk.client.Layer;
import com.jrtk.client.Window;
import com.jrtk.utils.Random;
import com.jrtk.utils.Time;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
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


    float timer = 0;
    float posx, posy;
    @Override
    public void OnUpdate() {
        glViewport(0, 0, window.getResolution().x, window.getResolution().y);

        timer += Time.deltaTime;

        if(timer >= 1)
        {
            posx = (float) Random.Range(-1, 1);
            posy = (float) Random.Range(-1, 1);

            timer = 0;
        }


        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(0.1f, 0.1f, 0.12f, 1.0f);

        glBegin(GL_TRIANGLES);

        glColor3f(1, 0, 0);
        glVertex2f(-posx, -posy);

        glColor3f(0, 1, 0);
        glVertex2f( posx, -posy);

        glColor3f(0, 0, 1);
        glVertex2f( posx,  posy);

        glEnd();



    }

    @Override
    public void OnDetach() {

    }
}
