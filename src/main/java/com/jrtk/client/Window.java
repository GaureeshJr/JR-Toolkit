package com.jrtk.client;

import com.jrtk.core.Key;
import com.jrtk.core.Mouse;
import com.jrtk.utils.Time;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private long windowPtr;
    private String title;
    private Vector2i resolution;

    public Window(String t, int w, int h)
    {
        this.title = t;
        this.resolution = new Vector2i(w, h);
    }

    public Vector2i getResolution() {
        return resolution;
    }

    public void init()
    {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        windowPtr = glfwCreateWindow(resolution.x, resolution.y, title, NULL, NULL);
        if ( windowPtr == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetWindowSizeCallback(windowPtr, (w, newWidth, newHeight)->{
            this.resolution = new Vector2i(newWidth, newHeight);
            glViewport(0, 0, resolution.x, resolution.y);
        });

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowPtr);
        // disable   v-sync
        glfwSwapInterval(0);

        // Make the window visible
        glfwShowWindow(windowPtr);


        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glfwSetCursorPosCallback           (windowPtr , Mouse::MousePosCallback);
        glfwSetMouseButtonCallback         (windowPtr , Mouse::MouseButtonCallback);
        glfwSetScrollCallback              (windowPtr , Mouse::MouseScrollCallback);
        glfwSetKeyCallback                 (windowPtr ,   Key::KeyCallBack);


    }

    public void update()
    {
        //Final frame update
        glfwSwapBuffers(windowPtr);
        glfwPollEvents();
    }

    public void delete()
    {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(windowPtr);
        glfwDestroyWindow(windowPtr);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public boolean shouldNotClose() {
        return !glfwWindowShouldClose(windowPtr);
    }

    public long getWindowPtr() {
        return windowPtr;
    }
}
