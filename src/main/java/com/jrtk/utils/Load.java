package com.jrtk.utils;

import com.jrtk.render.Mesh;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Load {

    private static ModelLoader mLoader = new ModelLoader();

    private static Map<String, Mesh[]> MESHES = new HashMap<>();
    private static Map<String, Mesh> SHADERS = new HashMap<>();

    public static Mesh[] Meshes(String path)
    {
        File f = new File(path);

        if(MESHES.containsKey(f.getAbsolutePath()))
        {
            return MESHES.get(f.getAbsolutePath());
        }
        else
        {
            Mesh[] arr = mLoader.load(path);
            MESHES.put(f.getAbsolutePath(), arr);
            return arr;
        }
    }


}
