package com.jrtk.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Quad {
    private VAO vao;
    private VBO vbo;
    private Shader shader;

    private int texID;

    public Quad(int x1, int y1, int x2, int y2, int ID)
    {
        float[] vertices =
                {
                        -1, -1, 0,  0, 0,
                         1,  1, 0,  1, 1,
                        -1,  1, 0,  0, 1,
                        -1, -1, 0,  0, 0,
                         1, -1, 0,  1, 0,
                         1,  1, 0,  1, 1,
                };

        this.vao = new VAO();
        this.vao.Bind();

        this.vbo = new VBO(vertices);

        vao.LinkVBO(vbo, 0, 3, 5, 0);
        vao.LinkVBO(vbo, 1, 2, 5, 3);

        vao.UnBind();
        vbo.UnBind();

        this.shader = new Shader("assets/shaders/ScreenShader.glsl");
        this.shader.compile();

        shader.detach();

        this.texID = ID;
    }

    public void Draw()
    {
        vao.Bind();
        vao.EnableAttributes();


        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texID);

        shader.UploadTexture2d("quadTex", 0);

        shader.use();
        glDrawArrays(GL_TRIANGLES, 0, 6);
        shader.detach();

        vao.DisableAttributes();

        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
