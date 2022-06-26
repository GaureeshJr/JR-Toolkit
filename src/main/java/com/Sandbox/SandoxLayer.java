package com.Sandbox;

import com.jrtk.core.Input;
import com.jrtk.core.Layer;
import com.jrtk.client.Window;
import com.jrtk.render.*;
import com.jrtk.utils.Load;
import com.jrtk.utils.ModelLoader;
import com.jrtk.utils.Time;
import org.joml.*;
import org.joml.Math;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class SandoxLayer extends Layer {

    private Window window;
    public FrameBuffer outputBuffer;

    private Mesh[] meshes;
    private Shader shader;
    private Matrix4f model, camera;



    public SandoxLayer(String name, int index, Window window) {
        super(name, index);
        this.window = window;
        this.model = new Matrix4f();
        this.camera = new Matrix4f();
    }

    @Override
    public void OnAttach() {

        shader = new Shader("assets/shaders/default.glsl");
        shader.compile();


        outputBuffer = new FrameBuffer(400, 200, GL_NEAREST);

        meshes = Load.Meshes("assets/models/Scene.obj");

        model.identity().translate(0, -0.1f, -1.25f).scale(1f);
        camera.identity();

        lightDirection = new Vector3f(0, 1, 1);
    }

    private Vector3f lightDirection;



    @Override
    public void OnUpdate() {

        outputBuffer.Bind();
        glViewport(0, 0, 400, 200);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


        if(Input.getKey(GLFW_KEY_D))
            model.rotate(Time.deltaTime, 0, 1, 0);
        if(Input.getKey(GLFW_KEY_A))
            model.rotate(-Time.deltaTime, 0, 1, 0);

        camera.identity().perspective(Math.toRadians(50), 2, 0.001f, 1000f).
                lookAt(0, 1, 1,
                        0, 0, -1.25f,
                        0, 1, 0);

        shader.UploadMat4f("modelMatrix", model);
        shader.UploadMat4f("camMatrix", camera);
        shader.UploadVec3f("lightDirection", lightDirection);


        for(int i = 0; i < meshes.length; i++)
        {
            meshes[i].Draw(shader, GL_TRIANGLES);
        }

        outputBuffer.Unbind();

    }

    public void EditorUI(){
    }

    @Override
    public void OnDetach() {

    }
}
