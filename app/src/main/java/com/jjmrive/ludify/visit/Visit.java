package com.jjmrive.ludify.visit;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Visit implements Serializable{
    private int idVisit;
    private String name;
    private String date;
    private Date dateRaw;
    private String urlPhoto;
    private boolean ended;

    private ArrayList<Question> questions;
    private ArrayList<Fact> facts;
    private ArrayList<Collection> collections;
    private ArrayList<Prize> prizes;


    public Visit(int idVisit, String name, Date date, String urlPhoto){
        this.idVisit = idVisit;
        this.name = name;
        dateRaw = date;
        this.date = new SimpleDateFormat("EEEE, dd MMMM, yyyy").format(date);
        this.urlPhoto = urlPhoto;
        ended = false;

        questions = new ArrayList<>();
        facts = new ArrayList<>();
        collections = new ArrayList<>();
        prizes = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDate(){
        return date;
    }

    public Date getDateRaw(){
        return dateRaw;
    }

    public void setDate(Date date){
        this.date = new SimpleDateFormat("EEEE, dd MMMM, yyyy").format(date);
    }

    public String getUrlPhoto(){
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto){
        this.urlPhoto = urlPhoto;
    }

    public int getScore(){
        return getScoreQuestions() + getScoreFacts() + getScoreCollections();
    }

    public boolean getStatus(){
        return ended;
    }

    public void setStatus(boolean status){
        this.ended = status;
    }


    public ArrayList<Prize> getPrizes(){
        return prizes;
    }

    public void addPrizes(Prize prize){
        this.prizes.add(prize);
    }

    public void addPrizes(ArrayList<Prize> prizesList){
        this.prizes = prizesList;
    }


    public void addQuestion(Question q){
        questions.add(q);
    }

    public boolean containsQuestion(Question q){
        return questions.contains(q);
    }

    public ArrayList<Question> getQuestions(){
        return questions;
    }

    public ArrayList<Fact> getFacts(){
        return facts;
    }

    public ArrayList<Collection> getCollections(){
        return collections;
    }

    public void addFact(Fact f){
        facts.add(f);
    }

    public boolean containsFact(Fact f){
        return facts.contains(f);
    }

    public void addCollection(Collection c){
        collections.add(c);
    }

    public boolean containsCollection(Collection c){
        return collections.contains(c);
    }

    public void addCollectionItem(CollectionItem item){

        Collection cll = new Collection(item.getIdCollection(), null, null, 0, 0);

        int indx = collections.indexOf(cll);

        collections.get(indx).addItem(item);

    }

    public boolean containsCollectionItem(CollectionItem item){
        boolean cnt = false;
        Collection cll = new Collection(item.getIdCollection(), null, null, 0, 0);

        int indx = collections.indexOf(cll);

        if (indx != -1){
            cnt = collections.get(indx).getItems().contains(item);
        }

        return cnt;
    }

    @Override
    public boolean equals(Object o){
        boolean eq = false;
        Visit vs = (Visit) o;
        if ((vs.idVisit == this.idVisit) && (this.ended == vs.ended)){
            eq = true;
        }
        return eq;
    }

    public int getQuestionsCorrect(){
        int questionsCorrect = 0;
        for (int i = 0; i < questions.size(); i++){
            if (questions.get(i).getResult()){
                questionsCorrect++;
            }
        }
        return questionsCorrect;
    }

    public int getQuestionsTotal(){
        return questions.size();
    }

    public int getFactsTotal(){
        return facts.size();
    }

    public Collection getCollection(int i){
        return collections.get(i);
    }

    public int getCollectionsTotal(){
        return collections.size();
    }

    public int getScoreQuestions(){
        int v = 0;
        for (int i = 0; i < questions.size(); i++){
            if (questions.get(i).getResult()) {
                v += questions.get(i).getPrize();
            }
        }
        return v;
    }

    public int getScoreFacts(){
        int v = 0;
        for (int i = 0; i < facts.size(); i++){
            v += facts.get(i).getPrize();
        }
        return v;
    }

    public int getScoreCollections(){
        int v = 0;
        for (int i = 0; i < collections.size(); i++){
            for (int j = 0; j < collections.get(i).getItemNumber(); j++){
                ArrayList<CollectionItem> item = collections.get(i).getItems();
                v += item.get(j).getPrize();
            }
            if (collections.get(i).getResult()){
                v += collections.get(i).getPrize();
            }
        }
        return v;
    }


}
