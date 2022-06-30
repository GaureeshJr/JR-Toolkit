package com.Sandbox;


import com.jrtk.client.Application;
import com.jrtk.client.Window;
import com.jrtk.core.Input;
import com.jrtk.core.Layer;
import com.jrtk.render.*;
import com.jrtk.utils.Load;
import com.jrtk.utils.Time;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class SandoxLayer extends Layer {

    private Window window;
    private Application application;

    private Mesh[] Scene;
    private Shader defaultShader;

    private Camera mainCamera;

    private Texture checker;

    private FBO framebuffer;

    private Quad q;
    public SandoxLayer(String name, int index, Application a) {
        super(name, index);
        this.application = a;
        this.window = a.window;
    }

    @Override
    public void OnAttach() {
        framebuffer = new FBO(1280, 720, 2, GL_LINEAR);


        Scene = Load.Meshes("assets/models/Sponza.obj");

        for(Mesh m : Scene)
        {
            m.setScale(0.5f, 0.5f, 0.5f);
        }

        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();

        mainCamera = new Camera(16.0f/9);
        mainCamera.setFOV(60);
        mainCamera.setPosition(new Vector3f(0, 1, 2));

        framebuffer = new FBO(1920, 1080, 1, GL_NEAREST);

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        q = new Quad(-1, -1, 1, 1, framebuffer.getColourTexture());
    }

    private float[] fog = new float[] {0.01f};
    private float[] fov = new float[] {60};

    private float[] fogColor = new float[] {0.7f, 0.7f, 1.0f, 1.0f};


    @Override
    public void OnUpdate() {

        framebuffer.bindFrameBuffer();

        glClearColor(fogColor[0], fogColor[1], fogColor[2], fogColor[3]);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        mainCamera.setAspectRatio((float) window.getResolution().x/window.getResolution().y);
        mainCamera.setFOV(fov[0]);


        defaultShader.UploadMat4f("camMatrix", mainCamera.CalculateMatrix());
        defaultShader.UploadVec3f("cameraPos", mainCamera.getPosition());
        defaultShader.UploadFloat("fogThickness", fog[0]/10);
        defaultShader.UploadVec3f("fogColor", new Vector3f(fogColor[0], fogColor[1], fogColor[2]));



        for(int i = 0; i < Scene.length; i++)
        {
            Scene[i].Draw(defaultShader, GL_TRIANGLES);
        }


        if(Input.getKey(GLFW_KEY_W))
        {
            mainCamera.addPosition(
                    mainCamera.cameraFront.x * Time.deltaTime * 180,
                    mainCamera.cameraFront.y * Time.deltaTime * 180,
                    mainCamera.cameraFront.z * Time.deltaTime * 180
            );
        }
        if(Input.getKey(GLFW_KEY_S))
        {
            mainCamera.addPosition
                    (
                            -mainCamera.cameraFront.x * Time.deltaTime * 180,
                            -mainCamera.cameraFront.y * Time.deltaTime * 180,
                            -mainCamera.cameraFront.z * Time.deltaTime * 180
                    );
        }


        if(Input.getKey(GLFW_KEY_RIGHT))
            mainCamera.getRotation().y += Time.deltaTime * 60;
        if(Input.getKey(GLFW_KEY_LEFT))
            mainCamera.getRotation().y -= Time.deltaTime * 60;
        if(Input.getKey(GLFW_KEY_DOWN))
            mainCamera.getRotation().x -= Time.deltaTime * 45;
        if(Input.getKey(GLFW_KEY_UP))
            mainCamera.getRotation().x += Time.deltaTime * 45;
        if(Input.getKey(GLFW_KEY_Q))
            mainCamera.getPosition().y -= Time.deltaTime * 180;
        if(Input.getKey(GLFW_KEY_E))
            mainCamera.getPosition().y += Time.deltaTime * 180;


        framebuffer.unbindFrameBuffer();

        glDisable(GL_DEPTH_TEST);
        glViewport(0, 0,window.getResolution().x, window.getResolution().y);
        q.Draw();
        glEnable(GL_DEPTH_TEST);

    }

    @Override
    public void OnDetach() {


    }

    public void EditorUI()
    {

        ImGui.begin("Sandbox Editor");
        ImGui.textWrapped("FPS: " + (int)(1/Time.deltaTime));
        ImGui.dragFloat("Fog Intensity ", fog, 0.001f, 0, 2);
        ImGui.dragFloat("FOV ", fov, 1);
        ImGui.colorPicker3("Fog Color", fogColor);
        ImGui.end();
    }
}
