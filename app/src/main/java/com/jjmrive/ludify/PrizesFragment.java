package com.jjmrive.ludify;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjmrive.ludify.visit.Visit;

public class PrizesFragment extends Fragment {

    private Visit visit;

    public static PrizesFragment newInstance(Visit visit) {
        PrizesFragment fragment = new PrizesFragment();
        fragment.visit = visit;
        return fragment;
    }

    public PrizesFragment() {
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
        View inf = inflater.inflate(R.layout.fragment_prizes, container, false);

        Context context = inf.getContext();
        RecyclerView recyclerView = (RecyclerView) inf.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new PrizesFragmentAdapter(visit));

        return inf;
    }

}