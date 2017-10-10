package com.jjmrive.ludify.modules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjmrive.ludify.DataHolder;
import com.jjmrive.ludify.R;
import com.jjmrive.ludify.VisitsActivity;
import com.jjmrive.ludify.visit.Fact;
import com.squareup.picasso.Picasso;

public class FactActivity extends Activity {

    private int visitIndex;
    private int factIndex;
    private Fact fact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        visitIndex = (int) getIntent().getSerializableExtra("VISIT_INDEX");
        fact = (Fact) getIntent().getSerializableExtra("FACT");
        factIndex = DataHolder.getVisitsList().get(visitIndex).getFacts().indexOf(fact);

        ImageView viewImageFact = (ImageView) findViewById(R.id.imageFact);
        Picasso.with(getApplicationContext()).load(fact.getUrlPhoto()).into(viewImageFact);
        TextView viewFact = (TextView) findViewById(R.id.fact);
        viewFact.setText(fact.getFact());
        Button b1 = (Button) findViewById(R.id.okButton);
        b1.setText(fact.getOk());
        TextView viewPoints = (TextView) findViewById(R.id.points);
        viewPoints.setText(Integer.toString(fact.getPrize()) + " pts");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("FACT_RESULT", "GREAT! +" + Integer.toString(fact.getPrize()) + " pts");
                startActivity(intent);
                finish();
            }
        });

    }
}
