package com.jjmrive.ludify.visit;


import java.io.Serializable;

public class Prize implements Serializable{

    private String name;
    private int pts;

    public Prize (String name, int pts){
        this.name = name;
        this.pts = pts;
    }

    public String getName(){
        return name;
    }

    public int getPts(){
        return pts;
    }
}
