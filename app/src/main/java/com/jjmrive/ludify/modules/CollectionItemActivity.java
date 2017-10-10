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
import com.jjmrive.ludify.visit.CollectionItem;
import com.squareup.picasso.Picasso;

public class CollectionItemActivity extends Activity {

    private int visitIndex;
    private int collectionIndex;
    private CollectionItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_item);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        visitIndex = (int) getIntent().getSerializableExtra("VISIT_INDEX");
        collectionIndex = (int) getIntent().getSerializableExtra("COLLECTION_INDEX");
        item = (CollectionItem) getIntent().getSerializableExtra("COLLECTION_ITEM");

        ImageView viewImageCollectionItem = (ImageView) findViewById(R.id.imageCollectionItem);
        Picasso.with(getApplicationContext()).load(item.getUrlPhoto()).into(viewImageCollectionItem);
        TextView viewCollection = (TextView) findViewById(R.id.collection);
        viewCollection.setText(DataHolder.getVisitsList().get(visitIndex).getCollections().get(collectionIndex).getName());
        Button b1 = (Button) findViewById(R.id.okButton);
        int itemsToComplete = DataHolder.getVisitsList().get(visitIndex).getCollections().get(collectionIndex).getTotal() - DataHolder.getVisitsList().get(visitIndex).getCollections().get(collectionIndex).getItemNumber();
        b1.setText("You need " + Integer.toString(itemsToComplete) + " more!");
        TextView viewPoints = (TextView) findViewById(R.id.points);
        viewPoints.setText(Integer.toString(item.getPrize()) + " pts");
        TextView viewItemTxt = (TextView) findViewById(R.id.itemTxt);
        viewItemTxt.setText(item.getItemTxt());
        TextView viewPosition = (TextView) findViewById(R.id.position);
        viewPosition.setText("Item number " + Integer.toString(item.getPosition()));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("COLLECTION_ITEM_RESULT", "GREAT! +" + Integer.toString(item.getPrize()) + " pts");
                startActivity(intent);
                finish();
            }
        });
    }
}
