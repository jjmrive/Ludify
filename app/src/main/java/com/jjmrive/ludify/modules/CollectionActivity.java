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
import com.jjmrive.ludify.visit.Collection;
import com.squareup.picasso.Picasso;

public class CollectionActivity extends Activity {

    private int visitIndex;
    private int collectionIndex;
    private Collection collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        visitIndex = (int) getIntent().getSerializableExtra("VISIT_INDEX");
        collection = (Collection) getIntent().getSerializableExtra("COLLECTION");
        collectionIndex = DataHolder.getVisitsList().get(visitIndex).getCollections().indexOf(collection);

        ImageView viewImageCollection = (ImageView) findViewById(R.id.imageCollection);
        Picasso.with(getApplicationContext()).load(collection.getUrlPhoto()).into(viewImageCollection);
        TextView viewCollection = (TextView) findViewById(R.id.collection);
        viewCollection.setText(collection.getName());
        Button b1 = (Button) findViewById(R.id.okButton);
        b1.setText(getResources().getText(R.string.collection_ready));
        TextView viewPoints = (TextView) findViewById(R.id.points);
        viewPoints.setText(Integer.toString(collection.getPrize()) + " pts");
        TextView viewItems = (TextView) findViewById(R.id.items);
        viewItems.setText("Find the " + Integer.toString(collection.getTotal()) + " items of this collection");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("COLLECTION_RESULT", "Let's go!");
                startActivity(intent);
                finish();
            }
        });
    }
}
