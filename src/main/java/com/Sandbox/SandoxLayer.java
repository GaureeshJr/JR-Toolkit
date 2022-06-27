package com.Sandbox;

import com.jrtk.client.Application;
import com.jrtk.core.Layer;
import com.jrtk.client.Window;
import com.jrtk.editor.ImGuiLayer;
import com.jrtk.editor.Viewport;
import com.jrtk.render.*;
import com.jrtk.utils.Time;
import imgui.ImGui;
import org.joml.*;

import static org.lwjgl.opengl.GL11.*;

public class SandoxLayer extends Layer {

    private Window window;
    public FrameBuffer outputBuffer;
    private Application application;

    private Vector2i layerResolution;

    public SandoxLayer(String name, int index, Window window, Application application) {
        super(name, index);
        this.window = window;
        this.application = application;
        this.layerResolution = new Vector2i(800, 400);
    }

    @Override
    public void OnAttach() {

        layerResolution = new Vector2i(1920, 1080);

        outputBuffer = new FrameBuffer(layerResolution.x, layerResolution.y, GL_NEAREST);

    }

    boolean firstTime = true;

    @Override
    public void OnUpdate() {
        if(firstTime)
        {
            firstTime = false;

            this.application.layerStack.getLayer(ImGuiLayer.class).addEditorWindow(new Viewport(application, outputBuffer));
            System.out.println("First Update");
        }


        outputBuffer.Bind();
        glViewport(0, 0, layerResolution.x, layerResolution.y);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);





        outputBuffer.Unbind();

    }

    float timer = 1;
    float FT;

    public void EditorUI(){
        timer += Time.deltaTime;

        ImGui.begin("Sandbox Layer");
        if(timer >= 1)
        {
            FT = frameTime;
            timer = 0;
        }
        ImGui.text("FRAME TIME: " + FT);

        ImGui.end();
    }

    @Override
    public void OnDetach() {

    }
}
