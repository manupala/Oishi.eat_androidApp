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

public class MyAdapterListViewStrumenti extends ArrayAdapter<String> {

    private Context context;
    private MyAdapterListViewStrumenti adapter;
    private FragmentCreateBinding binding;
    private ArrayList<String> elencoStrumenti;

    public MyAdapterListViewStrumenti(Context c, ArrayList<String> elencoStrumenti, FragmentCreateBinding binding) {
        super(c, R.layout.fragment_create_tool, R.id.textTitle, elencoStrumenti.toArray(new String[0]));
        this.context = c;
        this.adapter = this;
        this.binding = binding;
        this.elencoStrumenti = elencoStrumenti;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.fragment_create_tool, parent, false);

        TextView nomeStrumento = row.findViewById(R.id.nomeStrumento);

        Button myButton = row.findViewById(R.id.btnEliminaIStrumento);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String strumento = nomeStrumento.getText().toString();

                //rimozione della listView
                adapter.elencoStrumenti.remove(position);
                MyAdapterListViewStrumenti adapter = new MyAdapterListViewStrumenti(getContext(), elencoStrumenti, binding);
                binding.listViewCreateStrumenti.setAdapter(adapter);

            }

        });

        nomeStrumento.setText(elencoStrumenti.get(position));

        return row;
    }
}
