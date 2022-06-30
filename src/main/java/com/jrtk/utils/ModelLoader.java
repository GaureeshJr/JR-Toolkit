package com.jrtk.utils;

import com.Sandbox.Utils;
import com.jrtk.render.Mesh;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIColor3D;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.assimp.Assimp.aiImportFile;
import static org.lwjgl.assimp.Assimp.aiProcess_Triangulate;

public class ModelLoader {


    public Mesh[] load(String resoucePath)
    {
        AIScene scene = aiImportFile(resoucePath, aiProcess_Triangulate);
        if(scene == null)
        {
            System.out.println("ErrorLoadingModel");
        }

        int numMeshes = scene.mNumMeshes();
        PointerBuffer aiMeshes = scene.mMeshes();

        Mesh[] meshes = new Mesh[numMeshes];

        for(int i = 0; i < aiMeshes.limit(); i++)
        {
            AIMesh mesh = AIMesh.create(aiMeshes.get(i));

            List<Float> vertices = new ArrayList<>();

            AIVector3D.Buffer Positions = mesh.mVertices();
            AIVector3D.Buffer Normals = mesh.mNormals();
            AIVector3D.Buffer TexCoords = mesh.mTextureCoords(0);


            while(Positions.remaining() > 0)
            {
                AIVector3D aiVertex = Positions.get();
                vertices.add(aiVertex.x());
                vertices.add(aiVertex.y());
                vertices.add(aiVertex.z());

                AIVector3D aiNormals = Normals.get();
                vertices.add(aiNormals.x());
                vertices.add(aiNormals.y());
                vertices.add(aiNormals.z());

                AIVector3D aiTexCoords = TexCoords.get();
                vertices.add(aiTexCoords.x());
                vertices.add(aiTexCoords.y());

            }

            meshes[i] = new Mesh(Utils.ListToArray(vertices), 8);
        }

        return meshes;
    }
}
