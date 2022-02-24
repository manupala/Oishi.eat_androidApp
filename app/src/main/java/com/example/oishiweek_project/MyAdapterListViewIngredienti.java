package com.example.oishiweek_project;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.oishiweek_project.databinding.FragmentCreateBinding;
import com.example.oishiweek_project.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class MyAdapterListViewIngredienti extends ArrayAdapter<Ingrediente> {

    private Context context;
    private MyAdapterListViewIngredienti adapter;
    private FragmentCreateBinding binding;
    private ArrayList<Ingrediente> elencoIngredienti;

    public MyAdapterListViewIngredienti (Context c, ArrayList<Ingrediente> elencoIngredienti, FragmentCreateBinding binding) {
        super(c, R.layout.fragment_create_ingredient, R.id.textTitle, elencoIngredienti.toArray(new Ingrediente[0]));
        this.context = c;
        this.adapter = this;
        this.binding = binding;
        this.elencoIngredienti = elencoIngredienti;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.fragment_create_ingredient, parent, false);

        TextView nomeIngrediente = row.findViewById(R.id.nomeIngrediente);
        TextView quantitaEudm = row.findViewById(R.id.quantitaEunita);

        Button myButton = row.findViewById(R.id.btnEliminaIngrediente);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Ingrediente ingrediente = new Ingrediente();
                ingrediente.setNome(nomeIngrediente.getText().toString());

                //rimozione della listView
                adapter.elencoIngredienti.remove(position);
                MyAdapterListViewIngredienti adapter = new MyAdapterListViewIngredienti(getContext(), elencoIngredienti, binding);
                binding.listViewCreateIngredienti.setAdapter(adapter);

            }

        });

        nomeIngrediente.setText(elencoIngredienti.get(position).getNome());
        quantitaEudm.setText(String.valueOf(elencoIngredienti.get(position).getQuantita()) + "\t" + elencoIngredienti.get(position).getUnitaDiMisura());

        return row;
    }
}
