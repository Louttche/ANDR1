package com.example.andr1_group_8;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {

    private Context context;
    private View view;
    private HomeActivity homeActivity_Reference;

    public homeFragment() {
        // Required empty public constructor
        super(R.layout.fragment_home);
    }

    public static homeFragment newInstance() {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        view.setVisibility(View.GONE);

        Log.d("home", "onStop: home");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        // Set this as home activity's main fragment
        homeActivity_Reference = (HomeActivity) this.getActivity();
        homeActivity_Reference.mainFragment = this;

        WebView webView = (WebView) view.findViewById(R.id.wv_dashboard);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://fontys.edu");

        return view;
    }
}