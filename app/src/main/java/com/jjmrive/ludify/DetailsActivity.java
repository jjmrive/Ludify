package com.jjmrive.ludify;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjmrive.ludify.visit.Visit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    Visit visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        visit = (Visit) i.getSerializableExtra("Visita");

        final CollapsingToolbarLayout collapser =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapser.setTitle(" ");

        TextView text = (TextView) findViewById(R.id.title);
        text.setText(visit.getName());
        text = (TextView) findViewById(R.id.date);
        text.setText(visit.getDate());
        ImageView backPhoto = (ImageView) findViewById(R.id.backPhoto);
        Picasso.with(getApplicationContext()).load(visit.getUrlPhoto()).into(backPhoto);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapser.setTitle(visit.getName());
                    isShow = true;
                } else if (isShow) {
                    collapser.setTitle(" ");
                    isShow = false;
                }
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SectionsPagerAdapter sectionsAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager(), visit);
        viewPager.setAdapter(sectionsAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete_menu) {

            ArrayList<Visit> visitsList = (ArrayList<Visit>) DataHolder.getVisitsList();

            for (int i = 0; i < visitsList.size(); i++){
                if (visitsList.get(i).getDateRaw().equals(visit.getDateRaw())){
                    visitsList.remove(i);
                }
            }

            DataHolder.saveVisitsList(this.getApplicationContext());

            Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
            intent.putExtra("DELETE", "Visit deleted");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
