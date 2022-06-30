package com.jrtk.render;
import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader
{
    private int shaderProgramID;
    private boolean beingUsed;

    private String vertexSource , fragmentSource;
    private String filepath;

    public Shader(String filepath) {
        this.filepath = filepath;
        this.compile();
    }

    public void compile(){

        try {
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            // Find the first pattern after #type 'pattern'
            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\r\n", index);
            String firstPattern = source.substring(index, eol).trim();

            // Find the second pattern after #type 'pattern'
            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index, eol).trim();

            if (firstPattern.equals("vertex")) {
                vertexSource = splitString[1];
            } else if (firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            } else {
                throw new IOException("Unexpected token '" + firstPattern + "'");
            }

            if (secondPattern.equals("vertex")) {
                vertexSource = splitString[2];
            } else if (secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];
            } else {
                throw new IOException("Unexpected token '" + secondPattern + "'");
            }
        } catch(IOException e) {
            e.printStackTrace();
            assert false : "Error: Could not open file for shader: '" + filepath + "'";
        }


        int vertexID, fragmentID;
        // First load and compile the vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        // Pass the shader source to the GPU
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);

        // Check for errors in compilation
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" +filepath+ "'\n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        // First load and compile the vertex shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        // Pass the shader source to the GPU
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);

        // Check for errors in compilation
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '"+filepath+"'\n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        // Link shaders and check for errors
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '"+filepath+"'\n\tLinking of shaders failed.");
            System.out.println(glGetProgramInfoLog(shaderProgramID, len));
            assert false : "";
        }

    }
    public void use(){
        // Bind shader program
        if(!beingUsed){
            glUseProgram(shaderProgramID);
            beingUsed = true;
        }
    }
    public void detach(){
        glUseProgram(0);
        beingUsed = false;
    }
    public void UploadMat4f(String varName, Matrix4f mat4){
        int varlocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varlocation, false , matBuffer);
        detach();
    }

    public void UploadMat3f(String varName, Matrix4f mat3){
        int varlocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
        mat3.get(matBuffer);
        glUniformMatrix3fv(varlocation, false , matBuffer);
        detach();
    }

    public void UploadVec4f(String varname , Vector4f vec)
    {
        int varLocation = glGetUniformLocation(shaderProgramID, varname);
        use();
        glUniform4f(varLocation, vec.x , vec.y , vec.z , vec.w);
        detach();
    }

    public void UploadVec3f(String varname , Vector3f vec)
    {
        int varLocation = glGetUniformLocation(shaderProgramID, varname);
        use();
        glUniform3f(varLocation, vec.x , vec.y , vec.z);
        detach();
    }
    public void UploadVec2f(String varname , Vector2f vec)
    {
        int varLocation = glGetUniformLocation(shaderProgramID, varname);
        use();
        glUniform2f(varLocation, vec.x , vec.y);
        detach();
    }

    public void UploadFloat(String varName , float value){
        int varLocation = glGetUniformLocation(shaderProgramID , varName);
        use();
        glUniform1f(varLocation , value);
        detach();
    }
    public void UploadInt(String varName , int value){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1i(varLocation , value);
        detach();
    }

    public void UploadBoolean(String varName, boolean bool){
        if(bool == false){
            this.UploadInt(varName, 0);
        }else{
            this.UploadInt(varName, 1);
        }
    }

    public void UploadTexture2d(String varName , int slot){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1i(varLocation , slot);
        detach();
    }

    public void UploadIntArray(String varName, int[] arrays)
    {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1iv(varLocation , arrays);
        detach();
    }

}
