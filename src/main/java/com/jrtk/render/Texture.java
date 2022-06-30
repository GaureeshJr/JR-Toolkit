package com.jrtk.render;

import org.lwjgl.BufferUtils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture {
    private String filePath;
    private transient int texID;
    private int width, height;

    public Texture(String filePath, int filter){    //Normal texture loading function

        this.filePath = filePath;

        this.Init(filePath, filter);
    }

    public Texture(int width, int height, int filtering) {  //Generated texture which does not load in texture using stb
        this.filePath = "Generated";

        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filtering);

        glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGB ,width, height, 0, GL_RGB, GL_UNSIGNED_BYTE , 0);
    }

    private Texture Init(String filepath, int filtering){
        this.filePath = filepath;
        //Gen texture on GPU
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);
        //set texture parameters
        //Repeat image in both directions
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D , GL_TEXTURE_WRAP_T , GL_REPEAT);

        //When stretching the image we need to pixelate (can change)

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filtering);
        //When Shrinking image , we also need to pixelate ()
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filtering);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        //stbi_set_flip_vertically_on_load(true);
        File file = new File(filepath);
        ByteBuffer image;
        if(file.exists()) {
            image = stbi_load(filepath, width, height, channels, 0);
            this.width = width.get(0);
            this.height = height.get(0);
            glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGBA , width.get(0) , height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE , image);

            stbi_image_free(image);
        }else
        {
            image = stbi_load("assets/null.png", width, height, channels, 0);
            this.width = width.get(0);
            this.height = height.get(0);
            glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGBA , width.get(0) , height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE , image);

            stbi_image_free(image);
        }

        return this;

    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D , texID);
    }
    public void UnBind(){
        glBindTexture(GL_TEXTURE_2D , 0);
    }
    public int getWidth(){ return this.width;}
    public int getHeight() { return this.height;}
    public int getTexID() {return this.texID;}


    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(!(o instanceof Texture)) return false;
        Texture otex = (Texture) o;
        return otex.getWidth() == this.width && otex.getHeight() == this.height && otex.getTexID() == this.texID
                && otex.getFilePath().equals(this.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, texID, width, height);
    }

    public String getFilePath() {
        return filePath;
    }
}
