package com.Sandbox;

import java.util.List;

public class Utils {
    public static final float[] vertices =
            {
                    -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                    0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                    0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                    0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                    -0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                    -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,

                    -0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,
                    0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,
                    0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,
                    0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,
                    -0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,
                    -0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,

                    -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
                    -0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
                    -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
                    -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
                    -0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
                    -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,

                    0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
                    0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
                    0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
                    0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
                    0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
                    0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,

                    -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
                    0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
                    0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
                    0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
                    -0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
                    -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,

                    -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
                    0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
                    0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
                    0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
                    -0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
                    -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f
            };

    public static float[] ListToArray(List<Float> list){
        float[] arr = new float[list.size()];

        for(int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }

        return arr;
    };

}