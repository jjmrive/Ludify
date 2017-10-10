package com.jjmrive.ludify;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.jjmrive.ludify.barcode.BarcodeCaptureActivity;
import com.jjmrive.ludify.visit.Visit;

import java.util.List;

public class VisitsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VisitsAdapter adapter;
    private List<Visit> visitsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visits);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Visits list");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        visitsList = DataHolder.getVisitsList();

        adapter = new VisitsAdapter(this, visitsList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Visit visit = visitsList.get(position);
                Intent i = new Intent(getApplicationContext(), DetailsActivity.class);
                i.putExtra("Visita", visit);
                
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                        startActivity(intent);
                    }
                }
        );


        if (getIntent().getSerializableExtra("SCAN_RESULT") != null){
            String result = (String) getIntent().getSerializableExtra("SCAN_RESULT");
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
        if (getIntent().getSerializableExtra("DELETE") != null){
            String result = (String) getIntent().getSerializableExtra("DELETE");
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
        if (getIntent().getSerializableExtra("QUESTION_RESULT") != null){
            String result = (String) getIntent().getSerializableExtra("QUESTION_RESULT");
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
        if (getIntent().getSerializableExtra("FACT_RESULT") != null){
            String result = (String) getIntent().getSerializableExtra("FACT_RESULT");
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
        if (getIntent().getSerializableExtra("COLLECTION_RESULT") != null){
            String result = (String) getIntent().getSerializableExtra("COLLECTION_RESULT");
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
        if (getIntent().getSerializableExtra("COLLECTION_ITEM_RESULT") != null){
            String result = (String) getIntent().getSerializableExtra("COLLECTION_ITEM_RESULT");
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }

    }

}
