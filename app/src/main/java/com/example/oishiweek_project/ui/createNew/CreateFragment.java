package com.example.oishiweek_project.ui.createNew;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.TimedText;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.oishiweek_project.Ingrediente;
import com.example.oishiweek_project.MainActivity;
import com.example.oishiweek_project.MyAdapterListViewHome;
import com.example.oishiweek_project.MyAdapterListViewIngredienti;
import com.example.oishiweek_project.MyAdapterListViewStep;
import com.example.oishiweek_project.MyAdapterListViewStrumenti;
import com.example.oishiweek_project.R;
import com.example.oishiweek_project.Ricetta;
import com.example.oishiweek_project.RicetteHandler;
import com.example.oishiweek_project.Step;
import com.example.oishiweek_project.databinding.FragmentCreateBinding;

import java.util.ArrayList;

public class CreateFragment extends Fragment {

    private FragmentCreateBinding binding;
    private final int SELECT_IMAGE_CODE = 1;
    private Uri uriImageSelected = null;
    private ArrayList<Ingrediente> elencoIngredienti;
    private ArrayList<String> elencoStrumenti;
    private ArrayList<Step> elencoStep;

    private String bottonePremutoPerActivityResult = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentCreateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        elencoIngredienti = new ArrayList<>();
        elencoStrumenti = new ArrayList<>();
        elencoStep = new ArrayList<>();

        binding.btnSelezionaImmagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //vengono controllati i permessi di accesso alla galleria (EXTERNAL_MEMORY)
                try {
                    if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_IMAGE_CODE);
                    } else {
                        bottonePremutoPerActivityResult = "RICETTA";
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, SELECT_IMAGE_CODE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        binding.btnAggiungiIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Aggiungi ingrediente");

                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText nomeIngrediente = new EditText(getContext());
                nomeIngrediente.setHint("nome ingrediente");
                layout.addView(nomeIngrediente);

                final EditText quantita = new EditText(getContext());
                quantita.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                quantita.setHint("quantità");
                layout.addView(quantita);

                final Spinner udm = new Spinner(getContext());
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.udm_array, android.R.layout.simple_spinner_item);
                udm.setAdapter(adapter);
                layout.addView(udm);

                alert.setView(layout);

                alert.setPositiveButton("Aggiungi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if(nomeIngrediente.getText().toString().isEmpty() || quantita.getText().toString().isEmpty()) {

                            Toast.makeText(getContext(), "tutti i campi sono obbligatori", Toast.LENGTH_SHORT).show();

                        }

                        else {
                            Ingrediente nuovoIngrediente = new Ingrediente(nomeIngrediente.getText().toString(), Float.parseFloat(quantita.getText().toString()), udm.getSelectedItem().toString());
                            elencoIngredienti.add(nuovoIngrediente);

                            MyAdapterListViewIngredienti adapter = new MyAdapterListViewIngredienti(getContext(), elencoIngredienti, binding);
                            binding.listViewCreateIngredienti.setAdapter(adapter);

                            dialog.dismiss();
                        }

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });

                alert.show();

            }
        });

        MyAdapterListViewIngredienti adapterIngredienti = new MyAdapterListViewIngredienti(getContext(), elencoIngredienti, binding);
        binding.listViewCreateIngredienti.setAdapter(adapterIngredienti);

        binding.btnAggiungiStrumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Aggiungi strumento");

                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText nomeStrumento = new EditText(getContext());
                nomeStrumento.setHint("nome stumento");
                layout.addView(nomeStrumento);

                alert.setView(layout);

                alert.setPositiveButton("Aggiungi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if(nomeStrumento.getText().toString().isEmpty()) {

                            Toast.makeText(getContext(), "tutti i campi sono obbligatori", Toast.LENGTH_SHORT).show();

                        }

                        else {
                            elencoStrumenti.add(nomeStrumento.getText().toString());

                            MyAdapterListViewStrumenti adapter = new MyAdapterListViewStrumenti(getContext(), elencoStrumenti, binding);
                            binding.listViewCreateStrumenti.setAdapter(adapter);

                            dialog.dismiss();
                        }

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });

                alert.show();

            }
        });

        MyAdapterListViewStrumenti adapterStrumenti = new MyAdapterListViewStrumenti(getContext(), elencoStrumenti, binding);
        binding.listViewCreateStrumenti.setAdapter(adapterStrumenti);

        binding.btnAggiungiStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Aggiungi step");

                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                EditText titoloStep = new EditText(getContext());
                titoloStep.setHint("titolo step");
                layout.addView(titoloStep);

                EditText descrizioneStep = new EditText(getContext());
                descrizioneStep.setHint("descrizione step");
                layout.addView(descrizioneStep);

                EditText tempoStep = new EditText(getContext());
                tempoStep.setInputType(InputType.TYPE_CLASS_NUMBER);
                tempoStep.setHint("tempo in secondi");
                layout.addView(tempoStep);

                alert.setView(layout);

                alert.setPositiveButton("Aggiungi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if(titoloStep.getText().toString().isEmpty() || descrizioneStep.getText().toString().isEmpty() || tempoStep.getText().toString().isEmpty()) {

                            Toast.makeText(getContext(), "tutti i campi sono obbligatori", Toast.LENGTH_SHORT).show();

                        }

                        else {

                            Step nuovoStep = new Step(titoloStep.getText().toString(), descrizioneStep.getText().toString(), Integer.parseInt(tempoStep.getText().toString()));
                            elencoStep.add(nuovoStep);

                            MyAdapterListViewStep adapter = new MyAdapterListViewStep(getContext(), elencoStep, binding);
                            binding.listViewCreateStep.setAdapter(adapter);

                            dialog.dismiss();

                        }

                    }
                });

                alert.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });

                alert.show();

            }
        });

        MyAdapterListViewStep adapterStep = new MyAdapterListViewStep(getContext(), elencoStep, binding);
        binding.listViewCreateStep.setAdapter(adapterStep);

        binding.btnCreaRicetta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String statoFields = CheckFields();

                if(statoFields.equals("OK")) {

                    Ricetta nuovaRicetta = new Ricetta(binding.txtNome.getText().toString(), binding.txtTempo.getText().toString()+" min", Integer.parseInt(binding.txtKCal.getText().toString()), uriImageSelected, elencoIngredienti, elencoStrumenti, elencoStep);

                    RicetteHandler ricetteHandler = new RicetteHandler(getContext());
                    ricetteHandler.AggiungiRicetta(nuovaRicetta);

                    //se la ricetta viene inserita
                    Toast.makeText(getContext(), "Ricetta aggiunta alla lista", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), statoFields, Toast.LENGTH_SHORT).show();
                }

            }

        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //se l'activity che risponde è quella che ritorna l'immagine selezionata dalla galleria
        if(requestCode == SELECT_IMAGE_CODE && data != null) {

            if (bottonePremutoPerActivityResult.equals("RICETTA")) {

                uriImageSelected = data.getData();
                binding.img.setImageURI(uriImageSelected);

            }

        }

    }

    private String CheckFields() {
        if(binding.txtNome.getText().toString().isEmpty()) {
            return "dare un nome alla ricetta";
        }
        if(binding.txtTempo.getText().toString().isEmpty()) {
            return "specificare il tempo di preparazione";
        }
        if(binding.txtKCal.getText().toString().isEmpty()) {
            return "specificare le kCal totali";
        }
        if(uriImageSelected == null) {
            return "nessun immagine di copertina è stata selezionata";
        }
        if(elencoIngredienti.isEmpty()) {
            return "nessun ingrediente è stato aggiunto";
        }
        if(elencoStrumenti.isEmpty()) {
            return "nessuno strumento è stato aggiunto";
        }
        if(elencoStep.isEmpty()) {
            return "nessuno step per la preparazione della ricetta è stato specificato";
        }

        return "OK";

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case SELECT_IMAGE_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, SELECT_IMAGE_CODE);
                } else {
                    Toast.makeText(getContext(), "Accesso non consentito", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}