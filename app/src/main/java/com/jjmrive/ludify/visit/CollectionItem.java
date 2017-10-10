package com.jjmrive.ludify.visit;

import java.io.Serializable;


public class CollectionItem implements Serializable {

    private int idItem;
    private int idCollection;
    private String itemTxt;
    private String urlPhoto;
    private int position;
    private int prize;

    public CollectionItem(int idItem, int idCollection, String itemTxt, String urlPhoto, int position, int prize){
        this.idItem = idItem;
        this.idCollection = idCollection;
        this.itemTxt = itemTxt;
        this.urlPhoto = urlPhoto;
        this.position = position;
        this.prize = prize;
    }

    public int getIdItem(){
        return idItem;
    }

    public int getIdCollection(){
        return idCollection;
    }

    public String getItemTxt(){
        return itemTxt;
    }

    public String getUrlPhoto(){
        return urlPhoto;
    }

    public int getPosition(){
        return position;
    }

    public int getPrize(){
        return prize;
    }

    @Override
    public boolean equals(Object o){
        boolean eq = false;
        CollectionItem item = (CollectionItem) o;
        if (item.idItem == this.idItem){
            eq = true;
        }
        return eq;
    }

}
