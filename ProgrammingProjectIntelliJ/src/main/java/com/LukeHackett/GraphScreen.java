package com.LukeHackett;

import processing.core.PApplet;

import java.util.LinkedHashMap;
import java.util.Set;

public class GraphScreen {
    private PApplet canvas;
    private float xPos;
    private float yPos;
    private float xSize;
    private float ySize;

    private int activeIndex;

    private LinkedHashMap<Graph, Boolean> graphs;

    GraphScreen(PApplet canvas, float xPos, float yPos, float xSize, float ySize) {
        this.canvas = canvas;
        this.xPos = xPos;
        this.yPos = yPos;

        this.xSize = xSize;
        this.ySize = ySize;

        graphs = new LinkedHashMap<Graph, Boolean>();
    }

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    public LinkedHashMap<Graph, Boolean> getGraphs() {
        return graphs;
    }

    public void setGraphs(LinkedHashMap<Graph, Boolean> graphs) {
        this.graphs = graphs;
    }

    public void addGraph(Graph graph, boolean active) {
        int tempIndex = 0;
        if (active) {
            Set<Graph> graphSet = graphs.keySet();
            for(Graph g : graphSet){
                graphs.put(g, false);
                tempIndex++;
            }
            graphs.put(graph, true);
            activeIndex = tempIndex;
        }
        else{
            graphs.put(graph, false);
        }
    }

    public void setActive(String graphName){
        Set<Graph> graphSet = graphs.keySet();
        boolean activated = false;
        int tempIndex = 0;
        for(Graph g : graphSet){
            if(graphName.equals(g.getName())){
                graphs.put(g, true);
                activated = true;
                activeIndex = tempIndex;
            }
            graphs.put(g, false);
            tempIndex++;
        }
        if(!activated) System.out.println("Could not find graph to activate");
    }

    public void setActive(int index){
        Set<Graph> graphSet = graphs.keySet();
        boolean activated = false;
        int tempIndex = 0;
        for(Graph g : graphSet) {
            if(tempIndex != index){
                graphs.put(g, false);
            }
            else{
                activeIndex = tempIndex;
                graphs.put(g, true);
            }
            tempIndex++;
        }
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public void draw() {
        canvas.fill(255);
        canvas.rect(xPos, yPos, xSize, ySize+30);
        Set<Graph> graphSet = graphs.keySet();
        for(Graph g : graphSet){
            if(graphs.get(g)){
                g.draw(xPos, yPos, xSize, ySize);
            }
        }
    }
}