package nyc.c4q.enough.news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.enough.R;
import nyc.c4q.enough.controller.NYTadapter;
import nyc.c4q.enough.model.NYTResults;
import nyc.c4q.enough.model.Results;
import nyc.c4q.enough.network.NYTAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static nyc.c4q.enough.NewsActivity.apiCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthNewsFragment extends Fragment {
    List<Results> healthResultsList = new ArrayList<>();
    RecyclerView recyclerView;

    public HealthNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_health, container, false);

        recyclerView = view.findViewById(R.id.health_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getHealthData();
        return view;
    }

    public void getHealthData(){
        Call<NYTResults> nytResultsCall = apiCallback.getNYThealth(NYTAPI.NYT_Top_APIKey);
        nytResultsCall.enqueue(new Callback<NYTResults>() {
            @Override
            public void onResponse(Call<NYTResults> call, Response<NYTResults> response) {
                Log.d("response", "yay! health");
                healthResultsList = response.body().getResults();
                NYTadapter nyTadapter = new NYTadapter(healthResultsList);
                recyclerView.setAdapter(nyTadapter);

            }

            @Override
            public void onFailure(Call<NYTResults> call, Throwable t) {

            }
        });
    }

}
