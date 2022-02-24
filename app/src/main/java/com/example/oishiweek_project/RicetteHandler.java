package com.example.oishiweek_project;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RicetteHandler {

    private MySQLiteHelper db;
    private List<Ricetta> ricette;
    private Context context;

    public RicetteHandler(Context c) {
        this.context = c;
        this.db = new MySQLiteHelper(c);
        this.ricette = this.db.getRicette();    //vengono caricate in memoria centrale le ricette memorizzate nel DB
        this.UpLoad();
    }

    public void AggiungiRicetta(Ricetta nuovaRicetta) {

        int index = this.TrovaIndiceRicetta(nuovaRicetta.getNome());

        if(index == -1) {
            db.addRicetta(nuovaRicetta);
            ricette.add(nuovaRicetta);
        }

    }

    public Ricetta LeggiRicetta(String nomeRicetta) {

        int index = this.TrovaIndiceRicetta(nomeRicetta);

        if(index < ricette.size() && index > -1) {
            return ricette.get(index);
        }

        return null;

    }

    public Ricetta LeggiRicetta(int pos) {

        if(pos < ricette.size() && pos > -1) {
            return ricette.get(pos);
        }

        return null;

    }

    public List<Ricetta> ListaRicette() {
        return ricette;
    }

    public void ModificaRicetta(Ricetta nuovaRicetta) {

        int index = this.TrovaIndiceRicetta(nuovaRicetta.getNome());

        if(index != -1) {
            db.updateRicetta(nuovaRicetta);
            this.ricette.set(index, nuovaRicetta);
        }

    }

    public void EliminaRicetta(Ricetta ricettaDaEliminare) {

        int index = TrovaIndiceRicetta(ricettaDaEliminare.getNome());

        if(index != -1) {
            db.deleteRicetta(ricettaDaEliminare);
            ricette.remove(index);
        }

    }

    private int TrovaIndiceRicetta(String nomeRicetta) {

        int i = 0;
        for (Ricetta ricetta : ricette) {
            if(ricetta.getNome().equals(nomeRicetta)) {
                return i;
            }
            i++;
        }

        return -1;
    }

    public int len() {
        return ricette.size();
    }

    public void UpLoad() {

        /*
         *      PASTA AL RAGU
         */
        Ricetta pastaRagu = new Ricetta();
        pastaRagu.setNome("Tagliatelle al ragù (x4)");
        pastaRagu.setkCal(135);
        pastaRagu.setTempo("105 min");
        Resources resources = this.context.getResources();
        pastaRagu.setImmagine(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.pasta_ragu) + '/' + resources.getResourceTypeName(R.drawable.pasta_ragu) + '/' + resources.getResourceEntryName(R.drawable.pasta_ragu) ));

        //ingredienti
        ArrayList<Ingrediente> ingredienti = new ArrayList<>();
        Ingrediente ingrediente = new Ingrediente();

        ingrediente.setNome("carne macinata");
        ingrediente.setQuantita(500);
        ingrediente.setUnitaDiMisura("grammi");

        ingredienti.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNome("olio d'oliva");
        ingrediente.setQuantita(0);
        ingrediente.setUnitaDiMisura("q.b.");

        ingredienti.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNome("cipolla");
        ingrediente.setQuantita(1);
        ingrediente.setUnitaDiMisura("unità");

        ingredienti.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNome("carota");
        ingrediente.setQuantita(1);
        ingrediente.setUnitaDiMisura("unità");

        ingredienti.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNome("sedano");
        ingrediente.setQuantita(0.25F);
        ingrediente.setUnitaDiMisura("unità");

        ingredienti.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNome("piselli");
        ingrediente.setQuantita(100);
        ingrediente.setUnitaDiMisura("grammi");

        ingredienti.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNome("passata di pomodoro");
        ingrediente.setQuantita(300);
        ingrediente.setUnitaDiMisura("millilitri");

        ingredienti.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNome("vino rosso");
        ingrediente.setQuantita(1);
        ingrediente.setUnitaDiMisura("bicchieri");

        ingredienti.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNome("sale");
        ingrediente.setQuantita(0);
        ingrediente.setUnitaDiMisura("q.b.");

        ingredienti.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNome("pepe nero");
        ingrediente.setQuantita(0);
        ingrediente.setUnitaDiMisura("q.b.");

        ingredienti.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNome("parmigiano reggiano");
        ingrediente.setQuantita(0);
        ingrediente.setUnitaDiMisura("q.b");

        ingredienti.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNome("latte");
        ingrediente.setQuantita(50);
        ingrediente.setUnitaDiMisura("millilitri");

        ingredienti.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNome("tagliatelle");
        ingrediente.setQuantita(400);
        ingrediente.setUnitaDiMisura("grammi");

        ingredienti.add(ingrediente);

        pastaRagu.setIngredienti(ingredienti);



        //strumenti
        ArrayList<String> strumenti = new ArrayList<>();
        strumenti.add("tagliere");
        strumenti.add("coltello");
        strumenti.add("cucchiaio");
        strumenti.add("frullatore");
        strumenti.add("mestolo");
        strumenti.add("casseruola dal fondo largo");

        pastaRagu.setStrumenti(strumenti);


        //step
        ArrayList<Step> steps = new ArrayList<>();
        Step step = new Step();

        step.setTitolo("Taglio cipolla");
        step.setDescrizione("Pulite la cipolla e affettatela finemente");
        step.setTempo(0);

        steps.add(step);

        step = new Step();
        step.setTitolo("Cottura cipolla");
        step.setDescrizione("" +
                "Prendete una casseruola dal fondo largo, versatevi un paio di cucchiai di olio d’oliva" +
                " ed aggiungete il trito di cipolla. Lasciate appassire la cipolla a fiamma dolce ed " +
                "intanto pulite la carota e la costa di sedano e tritate anch’essi");
        step.setTempo(0);

        steps.add(step);

        step = new Step();
        step.setTitolo("Cottura carota e sedano");
        step.setDescrizione("Quando la cipolla sarà appassita, aggiungete il trito di carota e sedano e fateli " +
                "rosolare insieme alla cipolla");
        step.setTempo(0);

        steps.add(step);

        step = new Step();
        step.setTitolo("Cottura carne");
        step.setDescrizione("Aggiungete la carne macinata di manzo e di suino e rosolate anch’essa, sbriciolandola " +
                "ben bene con un cucchiaio di legno");
        step.setTempo(0);

        steps.add(step);

        step = new Step();
        step.setTitolo("Sfumare con vino rosso");
        step.setDescrizione("Non appena la carne sarà ben sbriciolata e rosolata aggiungete 1 bicchiere di vino rosso" +
                " e lasciate sfumare l’alcol");
        step.setTempo(0);

        steps.add(step);

        step = new Step();
        step.setTitolo("Cottura piselli e passata di pomodoro");
        step.setDescrizione("Aggiungete i piselli, la passata di pomodoro e 2-3 mestoli di acqua calda");
        step.setTempo(0);

        steps.add(step);

        step = new Step();
        step.setTitolo("Cottura lunga");
        step.setDescrizione("Salate, coprite con un coperchio e lasciate cuocere a fiamma bassa per 1 ora e mezza circa. " +
                "Se il ragù dovesse asciugarsi troppo, aggiungete ancora un mestolo di acqua calda");
        step.setTempo(5);

        steps.add(step);

        step = new Step();
        step.setTitolo("Tocco finale");
        step.setDescrizione("A cottura ultimata, aggiungete un paio di cucchiai di latte intero" +
                " e lasciar cuocere ancora un paio di minuti. Il latte conferisce un po’ di cremosità al " +
                "ragù. A cottura ultimata aggiustate di sale e pepe nero");
        step.setTempo(2);

        steps.add(step);

        step = new Step();
        step.setTitolo("Mantecare");
        step.setDescrizione("Cuocete la pasta, in questo caso delle tagliatelle, e, quando mancheranno 2 minuti alla fine della cottura, " +
                "trasferitele nella casseruola con il ragù e mantecate fino ad ultimarne la cottura");
        step.setTempo(2);

        steps.add(step);

        step = new Step();
        step.setTitolo("Servire sul piatto");
        step.setDescrizione("A cottura ultimata, togliete la pasta al ragù dal fuoco, aggiungete un paio di cucchiai di parmigiano grattugiato e mescolate. " +
                "Servite le vostre tagliatelle al ragù");
        step.setTempo(0);

        steps.add(step);

        pastaRagu.setPreparazione(steps);


        AggiungiRicetta(pastaRagu);

    }


}
