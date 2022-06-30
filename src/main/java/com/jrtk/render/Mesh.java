package com.jrtk.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

public class Mesh {

    private Vector3f position;
    private Vector3f scale;
    private Vector3f rotation;

    public Matrix4f transform;
    public VAO vertexArrayObject;
    public VBO  vertexBuffer;


    public int numVertices;
    public int vertexBufferLength;

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y , float z) {
        this.position.set(x, y, z);
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(float x, float y, float z) {
        this.scale.set(x, y, z);
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.set(x, y, z);
    }

    public Mesh(float[] vertices, int vertexSize)
    {

        vertexArrayObject = new VAO();
        vertexArrayObject.Bind();

        vertexBuffer = new VBO(vertices);

        vertexArrayObject.LinkVBO(vertexBuffer, 0, 3, vertexSize, 0);
        vertexArrayObject.LinkVBO(vertexBuffer, 1, 3, vertexSize, 3);
        vertexArrayObject.LinkVBO(vertexBuffer, 2, 2, vertexSize, 6);

        vertexArrayObject.UnBind();
        vertexBuffer.UnBind();

        vertexBufferLength = vertices.length;
        numVertices = vertexBufferLength/vertexSize;

        this.position = new Vector3f();
        this.scale = new Vector3f(1, 1, 1);
        this.rotation = new Vector3f(0, 0, 0);
        this.transform = new Matrix4f().identity().translate(position);
    }

    public void ActivateRenderer(){
        vertexArrayObject.Bind();
        vertexArrayObject.EnableAttributes();

        this.transform.identity().translate(position)
                .scale(scale)
                .rotate(rotation.x, 1, 0, 0)
                .rotate(rotation.y, 0, 1, 0)
                .rotate(rotation.z, 0, 0, 1);
    }

    public void Delete(){
        vertexArrayObject.Delete();
        vertexBuffer.Delete();
    }

    public void Draw(Shader shader, int DrawMode)
    {
        this.ActivateRenderer();
        shader.UploadMat4f("transform", transform);
        shader.use();
        glDrawArrays(DrawMode, 0, numVertices);
        shader.detach();
    }

}
