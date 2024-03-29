package com.LukeHackett;

import processing.core.PApplet;

import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.Set;

public class GraphScreen {
    /*
    Written by LH - GraphScreen object to hold graphs and flick between them
     */

    private PApplet canvas;
    private float xPos;
    private float yPos;
    private float xSize;
    private float ySize;
    private Animation loadingGraphsAnimation;

    private int activeIndex;

    private LinkedHashMap<Graph, Boolean> graphs;

    GraphScreen(PApplet canvas, float xPos, float yPos, float xSize, float ySize) {
        this.canvas = canvas;
        this.xPos = xPos;
        this.yPos = yPos;

        this.xSize = xSize;
        this.ySize = ySize;

        this.loadingGraphsAnimation = new Animation("frames",8,this.canvas);
        graphs = new LinkedHashMap<Graph, Boolean>();

        activeIndex = 0;
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
            for (Graph g : graphSet) {
                graphs.put(g, false);
                tempIndex++;
            }
            graphs.put(graph, true);
            activeIndex = tempIndex;
        } else {
            graphs.put(graph, false);
        }
    }

    public void setActive(String graphName) {
        Set<Graph> graphSet = graphs.keySet();
        boolean activated = false;
        int tempIndex = 0;
        for (Graph g : graphSet) {
            if (graphName.equals(g.getName())) {
                graphs.put(g, true);
                activated = true;
                activeIndex = tempIndex;
            } else {
                graphs.put(g, false);
                tempIndex++;
            }
        }
        if (!activated) System.out.println("Could not find graph to activate");
    }

    public void setActive(int index) {
        Set<Graph> graphSet = graphs.keySet();
        boolean activated = false;
        int tempIndex = 0;
        for (Graph g : graphSet) {
            if (tempIndex != index) {
                graphs.put(g, false);
            } else {
                activeIndex = tempIndex;
                graphs.put(g, true);
            }
            tempIndex++;
        }
    }

    public void setActiveSelect(String type){
        if(type.equals("stars") && Main.starLoaded){
            int tempIndex = 0;
            Set<Graph> graphSet = graphs.keySet();
            for (Graph g : graphSet) {
                if(g.type() == Main.STARS_CHART){
                    graphs.put(g, true);
                    activeIndex = tempIndex;
                }
                else{
                    graphs.put(g, false);
                }
                tempIndex++;
            }
        }
        else if(type.equals("checkIn") && Main.checkinLoaded){
            int tempIndex = 0;
            Set<Graph> graphSet = graphs.keySet();
            for (Graph g : graphSet) {
                if(g.type() == Main.CHECKIN_CHART){
                    graphs.put(g, true);
                    activeIndex = tempIndex;
                }
                else{
                    graphs.put(g, false);
                }
                tempIndex++;
            }
        }
        else if(type.equals("openingTimes") && Main.hoursLoaded){
            int tempIndex = 0;
            Set<Graph> graphSet = graphs.keySet();
            for (Graph g : graphSet) {
                if(g.type() == Main.HOURS_CHART){
                    graphs.put(g, true);
                    activeIndex = tempIndex;
                }
                else{
                    graphs.put(g, false);
                }
                tempIndex++;
            }
        }
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public void draw(){
        canvas.textFont(Main.graphFontSmall);
        canvas.fill(255, 20);
        canvas.rect(xPos, yPos, xSize, ySize + 35);

        if (graphs.isEmpty()) {
            canvas.textFont(Main.graphFont);
            canvas.fill(255);
            loadingGraphsAnimation.displayAnimation((int)(this.xPos + xSize/2 -32), (int)(this.yPos + (ySize/2 - 15)));
            //canvas.text("loading...", (xPos + xSize / 2) - canvas.textWidth("loading...") / 2, yPos + ySize / 2);
        } else {
            Set<Graph> graphSet = graphs.keySet();
            int tempIndex = 0;
            boolean drawn = false;
            try {
                for (Graph g : graphSet) {
                    if (graphs.size() > 1) {
                        if (graphs.get(g) && tempIndex == activeIndex) {
                            g.draw(xPos, yPos, xSize, ySize);
                            drawn = true;
                        } else if (graphs.get(g)) {
                            graphs.put(g, false);
                        }
                    } else {
                        g.draw(xPos, yPos, xSize, ySize);
                        drawn = true;
                        setActive(g.getName());
                    }
                    tempIndex++;
                }
            } catch (ConcurrentModificationException e){
                System.err.println("ConcurrentModificationException in graphScreen");
            } catch (NullPointerException e){
                System.err.println("Null pointer in graphScreen");
            }
            if(!drawn){
                for(Graph g : graphSet){
                    setActive(g.getName());
                    g.draw(xPos, yPos, xSize, ySize);
                    break;
                }
            }
        }
    }
}
