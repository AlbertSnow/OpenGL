package com.example.albertsnow.myapplication.objects;

import com.example.albertsnow.myapplication.data.VertexArray;
import com.example.albertsnow.myapplication.programs.ColorShaderProgram;
import com.example.albertsnow.myapplication.util.Geometry;

import java.util.List;

/**
 * Created by albertsnow on 6/30/17.
 */

public class Puck {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public  final float radius, height;

    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawCommands;

    public Puck (float radius, float height, int numPointsAroundPuck) {
        ObjectBuilder.GeneratedData generatedData = ObjectBuilder.createPucky(
                new Geometry.Cylinder(new Geometry.Point(0f, 0f, 0f), radius, height),
                numPointsAroundPuck);
        this.radius = radius;
        this.height = height;

        vertexArray = new VertexArray(generatedData.vertexData);
        drawCommands = generatedData.drawList;
    }

    public void bindData (ColorShaderProgram colorShaderProgram) {
        vertexArray.setVertexAttribPointer(0,
                colorShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }

    public void draw() {
        for (ObjectBuilder.DrawCommand drawCommand : drawCommands) {
            drawCommand.draw();
        }
    }

}
