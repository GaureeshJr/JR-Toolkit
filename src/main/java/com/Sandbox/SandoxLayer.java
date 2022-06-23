package com.Sandbox;

import com.jrtk.core.Input;
import com.jrtk.core.Layer;
import com.jrtk.client.Window;
import com.jrtk.render.FrameBuffer;
import com.jrtk.render.Shader;
import com.jrtk.render.VAO;
import com.jrtk.render.VBO;
import com.jrtk.utils.Time;
import imgui.internal.ImGui;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class SandoxLayer extends Layer {

    private Window window;
    public FrameBuffer outputBuffer;

    private VAO vao;
    private VBO vbo;
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
        vao = new VAO();
        vao.Bind();
        vbo = new VBO(Quad.vertices);
        vao.LinkVBO(vbo, 0, 3, 6, 0);
        vao.LinkVBO(vbo, 1, 3, 6, 3);

        vao.UnBind();
        vbo.UnBind();

        model.identity().scale(0.5f).translate(0, 0, -3);
        camera.identity();

    }


    @Override
    public void OnUpdate() {

        glViewport(0, 0, 400, 200);
        outputBuffer.Bind();

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

        model.rotate(Time.deltaTime, 0, 0, 1)
             .rotate(Time.deltaTime, 1, 0, 0);
        camera.identity().perspective(Math.toRadians(60), 2.0f, 0.00f, 10000f);

        shader.UploadMat4f("modelMatrix", model);
        shader.UploadMat4f("camMatrix", camera);

        vao.Bind();
        vao.EnableAttributes();

        shader.use();
        glDrawArrays(GL_TRIANGLES, 0, 6);
        shader.detach();


        vao.DisableAttributes();
        vao.UnBind();


        outputBuffer.Unbind();
        glViewport(0, 0,window.getResolution().x, window.getResolution().y);

    }

    public void EditorUI(){
        ImGui.begin("Inspector");

        ImGui.end();
    }

    @Override
    public void OnDetach() {
        vao.Delete();
        vbo.Delete();
    }
}
