package com.example.albertsnow.myapplication.objects;

import com.example.albertsnow.myapplication.data.VertexArray;
import com.example.albertsnow.myapplication.programs.ColorShaderProgram;
import com.example.albertsnow.myapplication.util.Geometry;

import java.util.List;

/**
 * Created by albertsnow on 6/29/17.
 */

public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius;
    public final float height;

    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawCommands;

    public Mallet(float radius, float height, int numPointsAroundMallet) {
        ObjectBuilder.GeneratedData generatedData = ObjectBuilder.createMallet(
                new Geometry.Point(0f, 0f, 0f), radius, height, numPointsAroundMallet
        );

        this.radius = radius;
        this.height = height;

        vertexArray = new VertexArray(generatedData.vertexData);
        drawCommands = generatedData.drawList;
    }

    public void bindData(ColorShaderProgram colorShaderProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                colorShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                0
        );
    }

    public void draw() {
        for (ObjectBuilder.DrawCommand drawCommand: drawCommands) {
            drawCommand.draw();
        }
    }

}
