package com.example.oishiweek_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.oishiweek_project.databinding.FragmentCreateBinding;

import java.util.ArrayList;

public class MyAdapterListViewStep extends ArrayAdapter<Step> {

    private Context context;
    private MyAdapterListViewStep adapter;
    private FragmentCreateBinding binding;
    private ArrayList<Step> elencoStep;

    public MyAdapterListViewStep(Context c, ArrayList<Step> elencoStep, FragmentCreateBinding binding) {

        //uso quello degli strumenti perchè il layout è uguale (una semplice stringa + bottone per eliminazione)
        super(c, R.layout.fragment_create_tool, R.id.textTitle, elencoStep.toArray(new Step[0]));
        this.context = c;
        this.adapter = this;
        this.binding = binding;
        this.elencoStep = elencoStep;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //uso quello degli strumenti perchè il layout è uguale (una semplice stringa + bottone per eliminazione)
        View row = layoutInflater.inflate(R.layout.fragment_create_tool, parent, false);

        TextView titoloStep = row.findViewById(R.id.nomeStrumento);

        Button myButton = row.findViewById(R.id.btnEliminaIStrumento);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String titolo = titoloStep.getText().toString();

                //rimozione della listView
                adapter.elencoStep.remove(position);
                MyAdapterListViewStep adapter = new MyAdapterListViewStep(getContext(), elencoStep, binding);
                binding.listViewCreateStep.setAdapter(adapter);

            }

        });

        titoloStep.setText(elencoStep.get(position).getTitolo());

        return row;
    }
}
