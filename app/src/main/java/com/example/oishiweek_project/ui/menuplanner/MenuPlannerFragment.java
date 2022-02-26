package com.example.oishiweek_project.ui.menuplanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.oishiweek_project.R;
import com.example.oishiweek_project.databinding.FragmentWeekmenuBinding;

public class MenuPlannerFragment extends Fragment {

    private FragmentWeekmenuBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWeekmenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.giorni, android.R.layout.simple_spinner_item);
        binding.spinnerGiorni.setAdapter(adapter);


        binding.spinnerGiorni.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //richiamare il metodo per aggiornare la listView
                Toast.makeText(getContext(), binding.spinnerGiorni.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        binding.switchPranzoCena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //richiamare il metodo per aggiornare la listView
                if (binding.switchPranzoCena.isChecked()) {
                    Toast.makeText(getContext(), "Cena", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Pranzo", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //bisognerà creare una classe adapter per editare la listView.
        //Nell'adapter usare le seguenti istruzioni:
        // - giornoSettimanaSelezionato = binding.spinnerGiorni.getSelectedItemPosition();
        // - binding.switchPranzoCena.getSwitchPadding();
        //In base a cosa è stato selezionato si andrà a cercare le ricette che l'utente ha
        //messo in quel pranzo/cena di quel determinato giorno.

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}