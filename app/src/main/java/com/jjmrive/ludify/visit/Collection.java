package com.jjmrive.ludify.visit;

import java.io.Serializable;
import java.util.ArrayList;


public class Collection implements Serializable {

    private int idCollection;
    private String name;
    private ArrayList<CollectionItem> items;
    private String urlPhoto;
    private int total;
    private int prize;
    private boolean result;

    public Collection(int idCollection, String name, String urlPhoto, int total, int prize){
        this.idCollection = idCollection;
        this.name = name;
        this.items = new ArrayList<>();
        this.urlPhoto = urlPhoto;
        this.total = total;
        this.prize = prize;
        this.result = false;
    }

    public void addItem(CollectionItem item){
        items.add(item);
    }

    public int getIdCollection(){
        return idCollection;
    }

    public String getName(){
        return name;
    }

    public String getUrlPhoto(){
        return urlPhoto;
    }

    public int getTotal(){
        return total;
    }

    public ArrayList<CollectionItem> getItems(){
        return items;
    }

    public int getItemNumber(){
        return items.size();
    }

    public int getPrize(){
        return prize;
    }

    public boolean getResult(){
        if (items.size() == total){
            result = true;
        }
        return result;
    }

    @Override
    public boolean equals(Object o){
        boolean eq = false;
        Collection col = (Collection) o;
        if (col.idCollection == this.idCollection){
            eq = true;
        }
        return eq;
    }


}
