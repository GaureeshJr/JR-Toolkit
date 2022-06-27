package com.jrtk.editor;

import com.jrtk.client.Application;
import com.jrtk.core.Layer;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ImGuiLayer extends Layer {

    private Application application;
    private final ImGuiImplGlfw imguiGLFW= new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiImplGl3= new ImGuiImplGl3();

    private String glslVersion = null;
    private long windowPtr;

    private List<editorWindow> editorWindows;

    public ImGuiLayer(String name, int index, Application application) {
        super(name, index);
        this.application = application;
        this.glslVersion = "#version 130";
        this.windowPtr = application.window.getWindowPtr();
        this.editorWindows = new ArrayList<>();
    }

    @Override
    public void OnAttach() {
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);

        imguiGLFW.init(windowPtr, true);
        imGuiImplGl3.init(glslVersion);
    }

    @Override
    public void OnUpdate() {
        imguiGLFW.newFrame();
        ImGui.newFrame();

        drawUI();

        ImGui.render();
        imGuiImplGl3.renderDrawData(ImGui.getDrawData());

        if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable))
        {
            final long backupWIndowPtr = org.lwjgl.glfw.GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWIndowPtr);
        }
    }

    @Override
    public void OnDetach() {
        imGuiImplGl3.dispose();
        imguiGLFW.dispose();
        ImGui.destroyContext();
    }


    private void drawUI()
    {

        this.application.OnImGui();

        for(editorWindow e : editorWindows)
        {
            if(e.enabled)
            {
                e.Show();
            }
        }
    }

    public void addEditorWindow(editorWindow w)
    {
        if(getEditorWindow(w.getClass()) == null)
            this.editorWindows.add(w);
    }

    public <T extends editorWindow> T getEditorWindow(Class<T> windowClass){
        for (editorWindow e: editorWindows) {
            if(windowClass.isAssignableFrom(e.getClass())){
                try{
                    return windowClass.cast(e);
                }catch (ClassCastException r){
                    r.printStackTrace();
                    assert false : "Err: Casting component";
                }
            }
        }
        return null;
    }
}
