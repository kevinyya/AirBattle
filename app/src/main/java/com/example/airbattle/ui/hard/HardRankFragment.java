package com.example.airbattle.ui.hard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airbattle.PlayerAdapter;
import com.example.airbattle.PlayerDatabase.PlayerDao;
import com.example.airbattle.PlayerDatabase.PlayerData;
import com.example.airbattle.PlayerDatabase.PlayerDatabase;
import com.example.airbattle.databinding.FragmentHardRankBinding;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HardRankFragment extends Fragment {

    private FragmentHardRankBinding binding;
    private PlayerDao playerDao;
    private List<PlayerData> playerList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HardRankViewModel hardRankViewModel =
                new ViewModelProvider(this).get(HardRankViewModel.class);

        binding = FragmentHardRankBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get PlayerDao
        playerDao = PlayerDatabase.getInstance(getContext()).playerDao();

        // Return in Toolbar
        Toolbar toolbar = binding.hardRankTB;

        // Draw Normal Ranking Table
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Get Player List
                playerList = playerDao.getPlayersSortByHard();

                // Display Normal Rank Table by RecyclerView
                RecyclerView rankRV = binding.hardRankRV;
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                rankRV.setLayoutManager(layoutManager);
                PlayerAdapter adapter = new PlayerAdapter(playerList, true);
                rankRV.setAdapter(adapter);
            }
        }).start();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}