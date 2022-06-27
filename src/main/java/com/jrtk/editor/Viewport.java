package com.jrtk.editor;

import com.Sandbox.SandoxLayer;
import com.jrtk.client.Application;
import com.jrtk.render.FrameBuffer;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiWindowFlags;

public class Viewport extends editorWindow{

    private FrameBuffer viewBuffer;
    public Viewport(Application a, FrameBuffer buffer)
    {
        super(a);
        this.viewBuffer = buffer;
    }

    private static float LX, RX, TY, BY;

    private static ImVec2 getLargestSizeForViewport()
    {
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);
        windowSize.x -= ImGui.getScrollX();
        windowSize.y -= ImGui.getScrollY();

        float aspectW = windowSize.x;
        float aspectH = aspectW/(2.0f);
        if(aspectH > windowSize.y){
            //We bust switch to pillarbox mode
            aspectH = windowSize.y;
            aspectW = aspectH*(2.0f);
        }

        return new ImVec2(aspectW, aspectH);
    }

    private static ImVec2 getCentredPosForViewport(ImVec2 aspectSize)
    {
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);
        windowSize.x -= ImGui.getScrollX();
        windowSize.y -= ImGui.getScrollY();

        float viewportX = (windowSize.x/2.0f) - (aspectSize.x/2.0f);
        float viewportY = (windowSize.y/2.0f) - (aspectSize.y/2.0f);

        return new ImVec2(viewportX + ImGui.getCursorPosX(), viewportY + ImGui.getCursorPosY());
    }

    @Override
    public void Show() {
        int ID = viewBuffer.getOutputTex();
        ImGui.begin("Viewport", ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse);

        ImVec2 windowSize = getLargestSizeForViewport();
        ImVec2 windowPos = getCentredPosForViewport(windowSize);

        ImGui.setCursorPos(windowPos.x, windowPos.y);

        ImVec2 topleft = new ImVec2();
        ImGui.getCursorScreenPos(topleft);
        topleft.x -= ImGui.getScrollX();
        topleft.y -= ImGui.getScrollY();

        LX = topleft.x;
        BY = topleft.y;
        RX = topleft.x + windowSize.x;
        TY = topleft.y + windowSize.y;


        ImGui.image(ID, windowSize.x, windowSize.y, 0, 1, 1, 0);


        ImGui.end();
    }


}
