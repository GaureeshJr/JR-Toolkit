package com.jrtk.render;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

public class Mesh {
    public VAO vertexArrayObject;
    public VBO  vertexBuffer;

    public int numVertices;
    public int vertexBufferLength;

    public Mesh(float[] positions, int vertexSize)
    {

        vertexArrayObject = new VAO();
        vertexArrayObject.Bind();

        vertexBuffer = new VBO(positions);

        vertexArrayObject.LinkVBO(vertexBuffer, 0, 3, vertexSize, 0);
        vertexArrayObject.LinkVBO(vertexBuffer, 1, 3, vertexSize, 3);

        vertexArrayObject.UnBind();
        vertexBuffer.UnBind();

        vertexBufferLength = positions.length;
        numVertices = vertexBufferLength/vertexSize;
    }

    public void ActivateRenderer(){
        vertexArrayObject.Bind();
        vertexArrayObject.EnableAttributes();
    }

    public void Delete(){
        vertexArrayObject.Delete();
        vertexBuffer.Delete();
    }

    public void Draw(Shader shader, int DrawMode)
    {
        shader.use();
        this.ActivateRenderer();
        glDrawArrays(DrawMode, 0, numVertices);
        shader.detach();
    }

}
