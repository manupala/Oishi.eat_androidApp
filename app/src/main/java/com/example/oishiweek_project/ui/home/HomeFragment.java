package com.example.oishiweek_project.ui.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.oishiweek_project.Ingrediente;
import com.example.oishiweek_project.MyAdapterListViewHome;
import com.example.oishiweek_project.R;
import com.example.oishiweek_project.Ricetta;
import com.example.oishiweek_project.RicetteHandler;
import com.example.oishiweek_project.Step;
import com.example.oishiweek_project.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RicetteHandler ricetteHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RicetteHandler ricetteHandler = new RicetteHandler(getContext());

        MyAdapterListViewHome adapter = new MyAdapterListViewHome(getContext(), ricetteHandler.ListaRicette().toArray(new Ricetta[0]), binding, ricetteHandler, getActivity());
        binding.listViewHome.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}