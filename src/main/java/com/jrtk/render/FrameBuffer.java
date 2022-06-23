package com.jrtk.render;
import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {

    private int fboID = 0;

    private Texture outputTex;


    public int getFboID() {
        return fboID;
    }

    public int getOutputTex() {
        return outputTex.getTexID();
    }
    public void Bind()
    {
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);
    }
    public void Unbind()
    {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
    public FrameBuffer(int width, int height, int filtering)
    {
        //Generate frameBuffer
        fboID = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);

        //Create the texture to render the data to and attach to framBuffer
        this.outputTex = new Texture(width, height, filtering);

        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.outputTex.getTexID(), 0);

        //Create the renderbuffer stores the depth

        int rboID = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rboID);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);

        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rboID);

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE){
            assert false: "Err frame buffer is not complete";
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
}