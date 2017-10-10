package com.jjmrive.ludify.modules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjmrive.ludify.DataHolder;
import com.jjmrive.ludify.R;
import com.jjmrive.ludify.VisitsActivity;
import com.jjmrive.ludify.visit.Question;

public class QuestionActivity extends Activity {

    private int visitIndex;
    private int questionIndex;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        visitIndex = (int) getIntent().getSerializableExtra("VISIT_INDEX");
        question = (Question) getIntent().getSerializableExtra("QUESTION");
        questionIndex = DataHolder.getVisitsList().get(visitIndex).getQuestions().indexOf(question);

        TextView viewQuestion = (TextView) findViewById(R.id.question);
        viewQuestion.setText(question.getQuestion());
        TextView viewPoints = (TextView) findViewById(R.id.points);
        viewPoints.setText(Integer.toString(question.getPrize()) + " pts");
        Button b1 = (Button) findViewById(R.id.ans1);
        b1.setText(question.getAnswers().get(0));
        Button b2 = (Button) findViewById(R.id.ans2);
        b2.setText(question.getAnswers().get(1));
        Button b3 = (Button) findViewById(R.id.ans3);
        b3.setText(question.getAnswers().get(2));
        Button b4 = (Button) findViewById(R.id.ans4);
        b4.setText(question.getAnswers().get(3));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question.getCorrect() == 1) {
                    DataHolder.getVisitsList().get(visitIndex).getQuestions().get(questionIndex).setResult(true);
                    DataHolder.saveVisitsList(getApplicationContext());
                    Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("QUESTION_RESULT", "Correct! +" + Integer.toString(question.getPrize()) + " pts");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("QUESTION_RESULT", "Incorrect!");
                    startActivity(intent);
                    finish();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question.getCorrect() == 2) {
                    DataHolder.getVisitsList().get(visitIndex).getQuestions().get(questionIndex).setResult(true);
                    DataHolder.saveVisitsList(getApplicationContext());
                    Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("QUESTION_RESULT", "Correct! " + Integer.toString(question.getPrize()) + " pts");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("QUESTION_RESULT", "Incorrect!");
                    startActivity(intent);
                    finish();
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question.getCorrect() == 3) {
                    DataHolder.getVisitsList().get(visitIndex).getQuestions().get(questionIndex).setResult(true);
                    DataHolder.saveVisitsList(getApplicationContext());
                    Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("QUESTION_RESULT", "Correct! " + Integer.toString(question.getPrize()) + " pts");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("QUESTION_RESULT", "Incorrect!");
                    startActivity(intent);
                    finish();
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question.getCorrect() == 4) {
                    DataHolder.getVisitsList().get(visitIndex).getQuestions().get(questionIndex).setResult(true);
                    DataHolder.saveVisitsList(getApplicationContext());
                    Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("QUESTION_RESULT", "Correct! " + Integer.toString(question.getPrize()) + " pts");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("QUESTION_RESULT", "Incorrect!");
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
