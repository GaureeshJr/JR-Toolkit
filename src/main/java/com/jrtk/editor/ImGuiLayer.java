package com.jrtk.editor;

import com.jrtk.client.Application;
import com.jrtk.core.Layer;
import com.jrtk.client.Window;
import com.jrtk.core.Key;
import com.jrtk.core.Mouse;
import com.jrtk.utils.Time;
import imgui.*;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.flag.*;
import imgui.gl3.ImGuiImplGl3;
import imgui.type.ImBoolean;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class ImGuiLayer extends Layer {

    private Window glfwWindow;
    private Application application;

    private List<editorWindow> editorWindows;

    private final long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];

    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();


    public ImGuiLayer(String name, int index, Window window, Application app) {
        super(name, index);
        this.glfwWindow = window;
        this.application = app;
        this.editorWindows = new ArrayList<>();
    }

    @Override
    public void OnAttach() {
        // IMPORTANT!!
        // This line is critical for Dear ImGui to work.
        ImGui.createContext();



        // ------------------------------------------------------------
        // Initialize ImGuiIO config
        final ImGuiIO io = ImGui.getIO();

        io.setIniFilename("defaultLayout.ini"); // We don't want to save .ini file
        io.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard); // Navigation with keyboard
        io.setConfigFlags(ImGuiConfigFlags.DockingEnable);
        io.setBackendFlags(ImGuiBackendFlags.HasMouseCursors); // Mouse cursors to display while resizing windows etc.
        io.setBackendPlatformName("imgui_java_impl_glfw");
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        // ------------------------------------------------------------
        // Keyboard mapping. ImGui will use those indices to peek into the io.KeysDown[] array.
        final int[] keyMap = new int[ImGuiKey.COUNT];
        keyMap[ImGuiKey.Tab] = GLFW_KEY_TAB;
        keyMap[ImGuiKey.LeftArrow] = GLFW_KEY_LEFT;
        keyMap[ImGuiKey.RightArrow] = GLFW_KEY_RIGHT;
        keyMap[ImGuiKey.UpArrow] = GLFW_KEY_UP;
        keyMap[ImGuiKey.DownArrow] = GLFW_KEY_DOWN;
        keyMap[ImGuiKey.PageUp] = GLFW_KEY_PAGE_UP;
        keyMap[ImGuiKey.PageDown] = GLFW_KEY_PAGE_DOWN;
        keyMap[ImGuiKey.Home] = GLFW_KEY_HOME;
        keyMap[ImGuiKey.End] = GLFW_KEY_END;
        keyMap[ImGuiKey.Insert] = GLFW_KEY_INSERT;
        keyMap[ImGuiKey.Delete] = GLFW_KEY_DELETE;
        keyMap[ImGuiKey.Backspace] = GLFW_KEY_BACKSPACE;
        keyMap[ImGuiKey.Space] = GLFW_KEY_SPACE;
        keyMap[ImGuiKey.Enter] = GLFW_KEY_ENTER;
        keyMap[ImGuiKey.Escape] = GLFW_KEY_ESCAPE;
        keyMap[ImGuiKey.KeyPadEnter] = GLFW_KEY_KP_ENTER;
        keyMap[ImGuiKey.A] = GLFW_KEY_A;
        keyMap[ImGuiKey.C] = GLFW_KEY_C;
        keyMap[ImGuiKey.V] = GLFW_KEY_V;
        keyMap[ImGuiKey.X] = GLFW_KEY_X;
        keyMap[ImGuiKey.Y] = GLFW_KEY_Y;
        keyMap[ImGuiKey.Z] = GLFW_KEY_Z;
        io.setKeyMap(keyMap);

        // ------------------------------------------------------------
        // Mouse cursors mapping
        mouseCursors[ImGuiMouseCursor.Arrow] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.TextInput] = glfwCreateStandardCursor(GLFW_IBEAM_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeAll] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNS] = glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeEW] = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNESW] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNWSE] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.Hand] = glfwCreateStandardCursor(GLFW_HAND_CURSOR);
        mouseCursors[ImGuiMouseCursor.NotAllowed] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);

        // ------------------------------------------------------------
        // GLFW callbacks to handle user input

        glfwSetKeyCallback(glfwWindow.getWindowPtr(), (w, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                io.setKeysDown(key, true);
            } else if (action == GLFW_RELEASE) {
                io.setKeysDown(key, false);
            }

            if(!io.getWantCaptureKeyboard()){
                Key.KeyCallBack(w, key, scancode, action, mods);
            }

            io.setKeyCtrl(io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
            io.setKeyShift(io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
            io.setKeyAlt(io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT));
            io.setKeySuper(io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER));
        });

        glfwSetCharCallback(glfwWindow.getWindowPtr(), (w, c) -> {
            if (c != GLFW_KEY_DELETE) {
                io.addInputCharacter(c);
            }
        });

        glfwSetMouseButtonCallback(glfwWindow.getWindowPtr(), (w, button, action, mods) -> {
            final boolean[] mouseDown = new boolean[5];

            mouseDown[0] = button == GLFW_MOUSE_BUTTON_1 && action != GLFW_RELEASE;
            mouseDown[1] = button == GLFW_MOUSE_BUTTON_2 && action != GLFW_RELEASE;
            mouseDown[2] = button == GLFW_MOUSE_BUTTON_3 && action != GLFW_RELEASE;
            mouseDown[3] = button == GLFW_MOUSE_BUTTON_4 && action != GLFW_RELEASE;
            mouseDown[4] = button == GLFW_MOUSE_BUTTON_5 && action != GLFW_RELEASE;

            io.setMouseDown(mouseDown);

            if (!io.getWantCaptureMouse() && mouseDown[1]) {
                ImGui.setWindowFocus(null);
            }

            if(!io.getWantCaptureMouse()){
                Mouse.MouseButtonCallback(w, button, action, mods);
            }
        });

        glfwSetScrollCallback(glfwWindow.getWindowPtr(), (w, xOffset, yOffset) -> {
            io.setMouseWheelH(io.getMouseWheelH() + (float) xOffset);
            io.setMouseWheel(io.getMouseWheel() + (float) yOffset);

            if(!io.getWantCaptureMouse()){
                Mouse.MouseScrollCallback(w, xOffset, yOffset); 
            }

        });

        io.setSetClipboardTextFn(new ImStrConsumer() {
            @Override
            public void accept(final String s) {
                glfwSetClipboardString(glfwWindow.getWindowPtr(), s);
            }
        });

        io.setGetClipboardTextFn(new ImStrSupplier() {
            @Override
            public String get() {
                final String clipboardString = glfwGetClipboardString(glfwWindow.getWindowPtr());
                if (clipboardString != null) {
                    return clipboardString;
                } else {
                    return "";
                }
            }
        });

        // ------------------------------------------------------------
        // Fonts configuration

        final ImFontAtlas fontAtlas = io.getFonts();
        final ImFontConfig fontConfig = new ImFontConfig(); // Natively allocated object, should be explicitly destroyed

        // Glyphs could be added per-font as well as per config used globally like here
        fontConfig.setGlyphRanges(fontAtlas.getGlyphRangesDefault());

        fontConfig.setPixelSnapH(true);
        fontAtlas.addFontFromFileTTF("assets/fonts/Segoe UI.ttf", 16, fontConfig);
        fontConfig.destroy(); // After all fonts were added we don't need this config more

        // ------------------------------------------------------------
        // Use freetype instead of stb_truetype to build a fonts texture
        fontAtlas.setFlags(ImGuiFreeTypeBuilderFlags.LightHinting);
        fontAtlas.build();

        imGuiGl3.init("#version 330 core");

        //------------------------------------theme----------------------------------------------------

        ImGuiStyle style = ImGui.getStyle();
        style.setWindowPadding(0,0);
        style.setColor(ImGuiCol.Text,                 1.00f, 1.00f, 1.00f, 1.00f);
        style.setColor(ImGuiCol.TextDisabled,         0.50f, 0.50f, 0.50f, 1.00f);
        style.setColor(ImGuiCol.WindowBg,             0.13f, 0.14f, 0.15f, 0.90f);
        style.setColor(ImGuiCol.ChildBg,              0.13f, 0.14f, 0.15f, 1.00f);
        style.setColor(ImGuiCol.PopupBg,              0.13f, 0.14f, 0.15f, 1.00f);
        style.setColor(ImGuiCol.Border,               0.43f, 0.43f, 0.50f, 0.50f);
        style.setColor(ImGuiCol.BorderShadow,         0.00f, 0.00f, 0.00f, 0.00f);
        style.setColor(ImGuiCol.FrameBg,              0.25f, 0.25f, 0.25f, 1.00f);
        style.setColor(ImGuiCol.FrameBgHovered,       0.38f, 0.38f, 0.38f, 1.00f);
        style.setColor(ImGuiCol.FrameBgActive,        0.67f, 0.67f, 0.67f, 0.39f);
        style.setColor(ImGuiCol.TitleBg,              0.08f, 0.08f, 0.09f, 1.00f);
        style.setColor(ImGuiCol.TitleBgActive,        0.08f, 0.08f, 0.09f, 1.00f);
        style.setColor(ImGuiCol.TitleBgCollapsed,     0.00f, 0.00f, 0.00f, 0.51f);
        style.setColor(ImGuiCol.MenuBarBg,            0.14f, 0.14f, 0.14f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarBg,          0.02f, 0.02f, 0.02f, 0.53f);
        style.setColor(ImGuiCol.ScrollbarGrab,        0.31f, 0.31f, 0.31f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.41f, 0.41f, 0.41f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrabActive,  0.51f, 0.51f, 0.51f, 1.00f);
        style.setColor(ImGuiCol.CheckMark,            0.11f, 0.64f, 0.92f, 1.00f);
        style.setColor(ImGuiCol.SliderGrab,           0.11f, 0.64f, 0.92f, 1.00f);
        style.setColor(ImGuiCol.SliderGrabActive,     0.08f, 0.50f, 0.72f, 1.00f);
        style.setColor(ImGuiCol.Button,               0.25f, 0.25f, 0.25f, 1.00f);
        style.setColor(ImGuiCol.ButtonHovered,        0.38f, 0.38f, 0.38f, 1.00f);
        style.setColor(ImGuiCol.ButtonActive,         0.67f, 0.67f, 0.67f, 0.39f);
        style.setColor(ImGuiCol.Header,               0.22f, 0.22f, 0.22f, 1.00f);
        style.setColor(ImGuiCol.HeaderHovered,        0.25f, 0.25f, 0.25f, 1.00f);
        style.setColor(ImGuiCol.HeaderActive,         0.67f, 0.67f, 0.67f, 0.39f);
        style.setColor(ImGuiCol.Separator,            style.getColor(ImGuiCol.Border).x,style.getColor(ImGuiCol.Border).y,style.getColor(ImGuiCol.Border).z,style.getColor(ImGuiCol.Border).w);
        style.setColor(ImGuiCol.SeparatorHovered,     0.41f, 0.42f, 0.44f, 1.00f);
        style.setColor(ImGuiCol.SeparatorActive,      0.26f, 0.59f, 0.98f, 0.95f);
        style.setColor(ImGuiCol.ResizeGrip,           0.00f, 0.00f, 0.00f, 0.00f);
        style.setColor(ImGuiCol.ResizeGripHovered,    0.29f, 0.30f, 0.31f, 0.67f);
        style.setColor(ImGuiCol.ResizeGripActive,     0.26f, 0.59f, 0.98f, 0.95f);
        style.setColor(ImGuiCol.Tab,                  0.08f, 0.08f, 0.09f, 0.83f);
        style.setColor(ImGuiCol.TabHovered,           0.33f, 0.34f, 0.36f, 0.83f);
        style.setColor(ImGuiCol.TabActive,            0.23f, 0.23f, 0.24f, 1.00f);
        style.setColor(ImGuiCol.TabUnfocused,         0.08f, 0.08f, 0.09f, 1.00f);
        style.setColor(ImGuiCol.TabUnfocusedActive,   0.13f, 0.14f, 0.15f, 1.00f);
        style.setColor(ImGuiCol.DockingPreview,       0.26f, 0.59f, 0.98f, 0.70f);
        style.setColor(ImGuiCol.DockingEmptyBg,       0.20f, 0.20f, 0.20f, 1.00f);
        style.setColor(ImGuiCol.PlotLines,            0.61f, 0.61f, 0.61f, 1.00f);
        style.setColor(ImGuiCol.PlotLinesHovered,     1.00f, 0.43f, 0.35f, 1.00f);
        style.setColor(ImGuiCol.PlotHistogram,        0.90f, 0.70f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.PlotHistogramHovered, 1.00f, 0.60f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.TextSelectedBg,       0.26f, 0.59f, 0.98f, 0.35f);
        style.setColor(ImGuiCol.DragDropTarget,       0.11f, 0.64f, 0.92f, 1.00f);
        style.setColor(ImGuiCol.NavHighlight,         0.26f, 0.59f, 0.98f, 1.00f);
        style.setColor(ImGuiCol.NavWindowingHighlight,1.00f, 1.00f, 1.00f, 0.70f);
        style.setColor(ImGuiCol.NavWindowingDimBg,    0.80f, 0.80f, 0.80f, 0.20f);
        style.setColor(ImGuiCol.ModalWindowDimBg,     0.80f, 0.80f, 0.80f, 0.35f);
        style.setGrabRounding(2.3f);
        style.setWindowRounding(5f);
        //---------------------------------------------------------------------------------
    }

    @Override
    public void OnUpdate() {
        startFrame(Time.deltaTime);

        ImGui.newFrame();

        setupDocking();

        drawUI();

        ImGui.end();

        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable))
        {
            final long backupWindowPtr = org.lwjgl.glfw.GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            org.lwjgl.glfw.GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }

        endFrame();
    }



    private void startFrame(final float deltaTime) {

        // Get window properties and mouse position
        float[] winWidth = {glfwWindow.getResolution().x};
        float[] winHeight = {glfwWindow.getResolution().y};

        double[] mousePosX = {0};
        double[] mousePosY = {0};



        glfwGetCursorPos(glfwWindow.getWindowPtr(), mousePosX, mousePosY);

        // We SHOULD call those methods to update Dear ImGui state for the current frame
        final ImGuiIO io = ImGui.getIO();
        io.setDisplaySize(winWidth[0], winHeight[0]);
        io.setDisplayFramebufferScale(1, 1);
        io.setMousePos((float) mousePosX[0], (float) mousePosY[0]);
        io.setDeltaTime(deltaTime);

        // Update the mouse cursor
        final int imguiCursor = ImGui.getMouseCursor();
        glfwSetCursor(glfwWindow.getWindowPtr(), mouseCursors[imguiCursor]);
        glfwSetInputMode(glfwWindow.getWindowPtr(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    private void endFrame() {
        // After Dear ImGui prepared a draw data, we use it in the LWJGL3 renderer.
        // At that moment ImGui will be rendered to the current OpenGL context.
        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

    @Override
    public void OnDetach() {
        imGuiGl3.dispose();
        ImGui.destroyContext();
    }



    private void drawUI()
    {

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

    private void setupDocking() {
        int windowFlags =  ImGuiWindowFlags.NoDocking;

        ImGui.setNextWindowPos(0.0f, 0.0f, ImGuiCond.Always);
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
