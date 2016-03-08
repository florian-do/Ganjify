package fr.do_f.ganjify.home.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.do_f.ganjify.R;
import fr.do_f.ganjify.api.RestClient;
import fr.do_f.ganjify.api.json.LoginResponse;
import fr.do_f.ganjify.api.json.StatsResponse;
import fr.do_f.ganjify.home.adapter.FeedAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TOKEN = "fr.do_f.ganjify.token";
    private static final String PACKAGE = "fr.do_f.ganjify";
    private static final String DEBUG_TAG = "FeedFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String token;

    @Bind(R.id.rvFeed)
    RecyclerView        rvFeed;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout  swipe;

    private FeedAdapter             feedAdapter = null;
    private List<StatsResponse>     response;

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);

        SharedPreferences prefs = getActivity().getSharedPreferences(
                PACKAGE, Context.MODE_PRIVATE);
        token = prefs.getString(TOKEN, "null");
        swipe.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Call<List<StatsResponse>> call = RestClient.get(token).getStats();
        call.enqueue(new Callback<List<StatsResponse>>() {
            @Override
            public void onResponse(Call<List<StatsResponse>> call, Response<List<StatsResponse>> response) {
                    setupFeed(response.body());
            }

            @Override
            public void onFailure(Call<List<StatsResponse>> call, Throwable t) {

            }
        });

    }

    public void setupFeed(List<StatsResponse> _response)
    {
        response = _response;
        LinearLayoutManager lManager = new LinearLayoutManager(getActivity());
        rvFeed.setHasFixedSize(true);
        rvFeed.setLayoutManager(lManager);
        feedAdapter = new FeedAdapter(response);
        rvFeed.setAdapter(feedAdapter);
    }

    public void updateFeed(List<StatsResponse> _response)
    {
        response = _response;
        feedAdapter.refreshAdapter(_response);
    }

    @Override
    public void onRefresh()
    {
        Call<List<StatsResponse>> call = RestClient.get(token).getStats();
        call.enqueue(new Callback<List<StatsResponse>>() {
            @Override
            public void onResponse(Call<List<StatsResponse>> call, Response<List<StatsResponse>> response) {
                updateFeed(response.body());
                Log.d(DEBUG_TAG, "onResponse");
                swipe.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<StatsResponse>> call, Throwable t) {
                Log.d(DEBUG_TAG, "onFailure");
                swipe.setRefreshing(false);
            }
        });
    }
}
