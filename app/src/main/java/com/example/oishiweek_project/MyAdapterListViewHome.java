package com.example.oishiweek_project;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.oishiweek_project.databinding.FragmentHomeBinding;
import com.example.oishiweek_project.ui.home.HomeFragment;
import com.example.oishiweek_project.ui.step.StepFragment;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class MyAdapterListViewHome extends ArrayAdapter<Ricetta> {

    private Context context;
    private Ricetta[] elencoRicette;
    private MyAdapterListViewHome adapter;
    private FragmentHomeBinding binding;
    private RicetteHandler ricetteHandler;
    private FragmentActivity fragmentActivity;

    public MyAdapterListViewHome (Context c, Ricetta[] elencoRicette, FragmentHomeBinding binding, RicetteHandler ricetteHandler, FragmentActivity fragmentActivity) {
        super(c, R.layout.fragment_home_row, R.id.textTitle, elencoRicette);
        this.context = c;
        this.elencoRicette = elencoRicette;
        this.adapter = this;
        this.binding = binding;
        this.ricetteHandler = ricetteHandler;
        this.fragmentActivity = fragmentActivity;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.fragment_home_row, parent, false);
        ImageView images = row.findViewById(R.id.image);
        TextView myTitle = row.findViewById(R.id.textTitle);
        TextView myDescription = row.findViewById(R.id.textSubtitle);

        Button btnRicetta = row.findViewById(R.id.btnRow);
        btnRicetta.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_ricetta_riepilogo, null);

                TextView nome = (TextView) popupView.findViewById(R.id.activityPreparazione_nome);
                TextView tempo = (TextView) popupView.findViewById(R.id.activityPreparazione_tempo);
                TextView kcal = (TextView) popupView.findViewById(R.id.activityPreparazione_kcal);
                ImageView immagine = (ImageView) popupView.findViewById(R.id.activityPreparazione_img);
                ListView ingredienti = (ListView) popupView.findViewById(R.id.activityPreparazione_ingredienti);
                ListView strumenti = (ListView) popupView.findViewById(R.id.activityPreparazione_Strumenti);
                ListView step = (ListView) popupView.findViewById(R.id.activityPreparazione_Step);

                //ora prelevare dati dalla ricetta corrente e settare i vari campi appena definiti
                Ricetta r = ricetteHandler.LeggiRicetta(position);
                nome.setText(r.getNome());
                tempo.setText(r.getTempo());
                kcal.setText(String.valueOf(r.getkCal())+" kCal");
                if(position==0) immagine.setImageResource(R.drawable.pasta_ragu);
                else immagine.setImageURI(r.getImmagine());
                strumenti.setAdapter(new ArrayAdapter<String>(adapter.context, android.R.layout.simple_list_item_1, r.getStrumenti()));
                //per i 2 (Strumenti è già String) elenchi creo delle stringhe 'banali' per poter usare un ArrayAdapter<String>
                ArrayList<String> array = new ArrayList<>();
                for(Ingrediente i : r.getIngredienti()) {
                    array.add(i.getNome() + ": " + i.getQuantita() + i.getUnitaDiMisura());
                }
                ingredienti.setAdapter(new ArrayAdapter<String>(adapter.context, android.R.layout.simple_list_item_1, array));
                array = new ArrayList<>();
                for(Step s : r.getPreparazione()) {
                    array.add(s.getTitolo() + "['" + s.getTempo() + "']:\n" +
                              s.getDescrizione());
                }
                step.setAdapter(new ArrayAdapter<String>(adapter.context, android.R.layout.simple_list_item_1, array));

                //bottone per iniziare a cucinare stepByStep
                Button btnInizia = (Button) popupView.findViewById(R.id.activityPreparazione_btnInizia);
                btnInizia.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                         Intent stepActivity = new Intent(fragmentActivity, SecondActivity.class);
                         //stepActivity.putExtra("nomeRicetta", r.getNome());    //gli passo la ricetta selezionata
                         //Toast.makeText(context, "questo è un toast", Toast.LENGTH_LONG).show();
                         StepFragment.ricettaCorrente = r;

                         fragmentActivity.startActivity(stepActivity);

                     }
                });

                // create the popup window
                int width = 1000;
                int height = 1400;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

        Button myButton = row.findViewById(R.id.btnElimina);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Ricetta ricetta = new Ricetta();
                ricetta.setNome(myTitle.getText().toString());

                AlertDialog.Builder builder = new AlertDialog.Builder(adapter.context);
                builder.setTitle("Conferma eliminazione");
                builder.setMessage("Sei sicuro di voler eliminare la ricetta dalla lista?");
                builder.setNegativeButton("NO", new
                        DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                            });
                builder.setPositiveButton("SI", new
                        DialogInterface.OnClickListener() {
                            public void onClick(
                                DialogInterface dialog, int id) {

                                    //rimozione della ricetta dal DB e aggiornamento della listView
                                    adapter.ricetteHandler.EliminaRicetta(ricetta);
                                    adapter = new MyAdapterListViewHome(adapter.context, adapter.ricetteHandler.ListaRicette().toArray(new Ricetta[0]), adapter.binding, adapter.ricetteHandler, fragmentActivity);
                                    binding.listViewHome.setAdapter(adapter);

                                }
                            });
                AlertDialog alert = builder.create();
                alert.show();

            }

        });

        // now set our resources on views
        //Picasso.with(getContext()).load(elencoRicette[position].getImmagine()).into(images);
        myTitle.setText(elencoRicette[position].getNome());
        myDescription.setText(elencoRicette[position].getTempo());
        if (ActivityCompat.checkSelfPermission(this.context.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if(position==0) images.setImageResource(R.drawable.pasta_ragu);
            else images.setImageURI(elencoRicette[position].getImmagine());
        } else {
            Toast.makeText(this.context, "Impossibile caricare le immagini. L'app non dispone dei permessi per accedere alla memoria esterna", Toast.LENGTH_LONG).show();
        }

        return row;
    }
}
