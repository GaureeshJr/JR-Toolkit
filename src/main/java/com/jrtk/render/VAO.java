package com.jrtk.render;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class VAO {
    public int ID;
    private List<Integer> attribs;

    public VAO() {
        ID = glGenVertexArrays();
        attribs = new ArrayList<>();
    }

    public void LinkVBO(VBO vbo, int layout, int size, int stride, int pointer){
        vbo.Bind();
        glVertexAttribPointer(layout, size, GL_FLOAT, false, stride * Float.BYTES, (long)pointer * Float.BYTES);
        attribs.add(layout);
        vbo.UnBind();
    }

    public void Bind(){
        glBindVertexArray(ID);
    }

    public void UnBind(){
        glBindVertexArray(0);
    }

    public void EnableAttributes(){
        for(int i : attribs){
            glEnableVertexAttribArray(i);
        }
    }

    public void DisableAttributes(){
        for(int i : attribs){
            glDisableVertexAttribArray(i);
        }
    }

    public void Delete(){

    }
}
