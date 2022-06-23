package com.jrtk.render;

import static org.lwjgl.opengl.GL15.*;

public class VBO {

    public int ID;

    public VBO(float[] vertices){
        ID = glGenBuffers();
        Bind();
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
    }

    public void Bind(){
        glBindBuffer(GL_ARRAY_BUFFER,ID);
    }

    public void UnBind(){
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }

    public void Delete(){
        glDeleteBuffers(ID);
    }

}
