package com.jrtk.render;

import com.jrtk.client.Window;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f position;
    private Vector3f rotation;

    private float aspectRatio;

    private float FOV;
    private Matrix4f cameraMatrix;


    public Vector3f cameraFront;


    public Camera(float aspectRatio){
        this.position = new Vector3f(0, 1, 1);
        this.rotation = new Vector3f(0, 0, 0);
        this.cameraMatrix = new Matrix4f().identity();
        this.aspectRatio = aspectRatio;
        this.cameraFront = new Vector3f(0, 0, -1);
    }

    public Matrix4f CalculateMatrix(){
        cameraMatrix.identity();
        calculateProjection(cameraMatrix);
        calculateView(cameraMatrix);
        return cameraMatrix;
    }

    private void calculateProjection(Matrix4f mat)
    {
        mat.perspective(FOV,aspectRatio , 0.01f, 10000f);
    }



    private void calculateView(Matrix4f mat)
    {
        cameraFront.x = Math.cos(Math.toRadians(rotation.y)) * Math.cos(Math.toRadians(rotation.x));
        cameraFront.y = Math.sin(Math.toRadians(rotation.x));
        cameraFront.z = Math.sin(Math.toRadians(rotation.y)) * Math.cos(Math.toRadians(rotation.x)) ;

        mat.lookAt(position, new Vector3f(position.x + cameraFront.x, position.y + cameraFront.y, position.z  + cameraFront.z), new Vector3f(0, 1, 0));
    }





    //--------------------------------- Getters and Setters----------------------------------------------
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getFOV() {
        return FOV;
    }

    public void setFOV(float FOV) {
        this.FOV = (float) Math.toRadians(FOV);
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    //------------------------ helper functions -------------------------------------------------
    public void addPosition(float x, float y, float z)
    {
        this.position.x += x;
        this.position.y += y;
        this.position.z += z;
    }
}
