package com.android.app.view.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.app.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

//        String token = new PreferenceUtils2(getContext()).getToken();
//        Call<List<BaseUser>> t = new RetrofitInitializer().users().list("asdg");
//        t.enqueue(new Callback<List<BaseUser>>() {
//            @Override
//            public void onResponse(Call<List<BaseUser>> call, Response<List<BaseUser>> response) {
//                Log.d("Cassio", response.errorBody().toString());
//            }
//
//            @Override
//            public void onFailure(Call<List<BaseUser>> call, Throwable t) {
//                Log.e("cassio", t.toString());
//            }
//        });

    }
}