package com.jjmrive.ludify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjmrive.ludify.visit.Visit;

public class ScoreFragment extends Fragment {

    private Visit visit;

    private TextView scoreScore;
    private ImageView scoreStatusIcon;
    private TextView scoreStatus;
    private TextView scoreQuestions;
    private TextView scoreQuestionsPts;
    private TextView scoreFacts;
    private TextView scoreFactsPts;
    private TextView scoreCollections;
    private TextView scoreCollectionsPts;


    public static ScoreFragment newInstance(Visit visit) {
        ScoreFragment fragment = new ScoreFragment();
        fragment.visit = visit;
        return fragment;
    }

    public ScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_score, container, false);

        scoreScore = (TextView) inf.findViewById(R.id.scoreScore);
        scoreStatusIcon = (ImageView) inf.findViewById(R.id.scoreStatusIcon);
        scoreStatus = (TextView) inf.findViewById(R.id.scoreStatus);
        scoreQuestions = (TextView) inf.findViewById(R.id.scoreQuestions);
        scoreQuestionsPts = (TextView) inf.findViewById(R.id.scoreQuestionsPts);
        scoreFacts = (TextView) inf.findViewById(R.id.scoreFacts);
        scoreFactsPts = (TextView) inf.findViewById(R.id.scoreFactsPts);
        scoreCollections = (TextView) inf.findViewById(R.id.scoreCollections);
        scoreCollectionsPts = (TextView) inf.findViewById(R.id.scoreCollectionsPts);

        updateLayout();
        return inf;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        if (isVisibleToUser){
            updateLayout();
        }
    }

    public void updateLayout() {
        if (visit != null && scoreScore != null && scoreStatusIcon != null && scoreStatus != null
                && scoreQuestions != null && scoreQuestionsPts != null && scoreFacts != null
                && scoreFactsPts != null && scoreCollections != null && scoreCollectionsPts != null) {

            scoreScore.setText(visit.getScore() +" "+getResources().getText(R.string.points));

            if (visit.getStatus()){
                scoreStatusIcon.setImageResource(R.drawable.ic_done_black_24dp);
                scoreStatus.setText(getResources().getText(R.string.ended_status));
            } else {
                scoreStatusIcon.setImageResource(R.drawable.ic_timelapse_black_24dp);
                scoreStatus.setText(getResources().getText(R.string.pending_status));
            }

            scoreQuestions.setText(getResources().getText(R.string.questions) +" "+String.valueOf(visit.getQuestionsCorrect()) +"/"+String.valueOf(visit.getQuestionsTotal()));
            scoreQuestionsPts.setText(String.valueOf(visit.getScoreQuestions())+ " " + getResources().getText(R.string.points));

            scoreFacts.setText(getResources().getText(R.string.facts) +" "+String.valueOf(visit.getFactsTotal()));
            scoreFactsPts.setText(String.valueOf(visit.getScoreFacts())+ " " + getResources().getText(R.string.points));



            if (visit.getCollectionsTotal() == 0){
                scoreCollections.setText(getResources().getText(R.string.collections) +" "+String.valueOf(visit.getCollectionsTotal()));
            } else {
                StringBuilder detailCollections = new StringBuilder();
                detailCollections.append(getResources().getText(R.string.collections) +" "+String.valueOf(visit.getCollectionsTotal())+"\n\n");
                for (int i = 0; i < visit.getCollectionsTotal(); i++){
                    detailCollections.append(visit.getCollection(i).getName()+": "+visit.getCollection(i).getItemNumber()+"/"+visit.getCollection(i).getTotal()+"\n");
                }
                scoreCollections.setText(detailCollections);
            }
            scoreCollectionsPts.setText(String.valueOf(visit.getScoreCollections())+ " " + getResources().getText(R.string.points));

        }
    }


}