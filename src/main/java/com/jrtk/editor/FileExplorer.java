package com.jrtk.editor;

import com.jrtk.client.Application;
import com.Sandbox.SandoxLayer;
import com.jrtk.render.Texture;
import imgui.ImGui;
import imgui.ImVec2;


import java.io.File;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;

public class FileExplorer extends editorWindow{

    private int folderIcon, fileIcon;

    private File rootFolder;

    private Texture texture;

    public FileExplorer(Application a, String rootFolder)
    {
        super(a);
        folderIcon = new Texture("assets/icons/folder.png", GL_LINEAR).getTexID();
        fileIcon   = new Texture("assets/icons/file.png", GL_LINEAR).getTexID();
        this.rootFolder = new File(rootFolder);
    }

    @Override
    public void Show() {
        ImGui.begin("Explorer");
        calculateExplorer();
        ImGui.end();
    }

    private void calculateExplorer(){

        File[] content = rootFolder.listFiles();

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpace = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpace);
        float WindowX2 = windowPos.x + windowSize.x;

        float posX = 0;
        if(ImGui.button("..")){
            File dir= new File(rootFolder.getParent());
            rootFolder = dir;
        }

        float thumbnailSize = 120;
        float cellSize = thumbnailSize;
        float panelWidth = ImGui.getContentRegionAvailX();
        int columnCount = (int) (panelWidth/cellSize);

        if(columnCount < 1){
            columnCount = 1;
        }

        ImGui.columns(columnCount, "", false);


        for(int i = 0; i < content.length; i++)
        {
            File dirObj = content[i];
            if(dirObj.isDirectory())
            {
                ImGui.imageButton(folderIcon, thumbnailSize, thumbnailSize);
                if(ImGui.isItemHovered() && ImGui.isMouseDoubleClicked(0)){
                    rootFolder = dirObj;
                }
            }
            else {
                ImGui.imageButton(fileIcon, thumbnailSize, thumbnailSize);
                if(ImGui.isItemHovered() && ImGui.isMouseDoubleClicked(0)) {
                    System.out.println("clicked " + dirObj.getName());
                }
            }


            ImGui.textWrapped(dirObj.getName());
            ImGui.nextColumn();
        }

    }
}
