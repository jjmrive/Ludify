package com.jjmrive.ludify.visit;

import java.io.Serializable;


public class Fact implements Serializable {

    private int idFact;
    private String fact;
    private String urlPhoto;
    private String ok;
    private int prize;

    public Fact(int idFact, String fact, String urlPhoto, String ok, int prize){
        this.idFact = idFact;
        this.fact = fact;
        this.urlPhoto = urlPhoto;
        this.ok = ok;
        this.prize = prize;
    }

    public int getPrize(){
        return prize;
    }

    public int getId(){
        return idFact;
    }

    public String getFact(){
        return fact;
    }

    public String getUrlPhoto(){
        return urlPhoto;
    }

    public String getOk(){
        return ok;
    }

    @Override
    public boolean equals(Object o){
        boolean eq = false;
        Fact fc = (Fact) o;
        if (fc.idFact == this.idFact){
            eq = true;
        }
        return eq;
    }
}
