package com.jrtk.editor;

import com.jrtk.client.Application;
import com.jrtk.core.Layer;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.flag.*;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;

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
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);

        ImGuiStyle style = ImGui.getStyle();

        style.setColor(ImGuiCol.Border, 1.0f,1.0f, 0.0f, 1.0f);
        style.setWindowPadding(3.0f, 3.0f);

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

    int[] dockX= new int[] {0};
    int[] dockY= new int[] {0};

    private void setupDocking() {
        int windowFlags = ImGuiWindowFlags.NoDocking;

        glfwGetWindowPos(application.window.getWindowPtr(), dockX, dockY);

        ImGui.setNextWindowPos(dockX[0], dockY[0], ImGuiCond.Always);
        ImGui.setNextWindowSize(application.window.getResolution().x, application.window.getResolution().y);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding , 0.0f);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize , 0.0f);

        windowFlags |= ImGuiWindowFlags.NoTitleBar| ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize| ImGuiWindowFlags.NoMove |ImGuiWindowFlags.NoBringToFrontOnFocus
                |ImGuiWindowFlags.NoNavFocus;


        ImGui.begin("Dockspace Demo", new ImBoolean(true), windowFlags);
        ImGui.popStyleVar(2);

        //Dockspace
        ImGui.dockSpace(ImGui.getID("Dockspace"));
    }
}
