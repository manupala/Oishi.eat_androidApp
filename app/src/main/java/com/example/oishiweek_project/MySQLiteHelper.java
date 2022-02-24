package com.example.oishiweek_project;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.oishiweek_project.ui.home.HomeFragment;

public class MySQLiteHelper extends SQLiteOpenHelper {//usiamo la classe OpenHelper pewr l'utilizzo dei DB

    private Context context;

    private static final int DATABASE_VERSION = 1;
    // DB name
    private static final String DATABASE_NAME = "Ricette_DB";

    // Table names
    private static final String TABELLA_RICETTE = "tRicette";
    private static final String TABELLA_INGREDIENTI = "tIngredienti";
    private static final String TABELLA_SERVE = "tServe";   //associazioni tra tRicette e tIngredienti
    private static final String TABELLA_STRUMENTI = "tStrumenti";
    private static final String TABELLA_UTILI = "tUtili";   //associazioni tra tRicette e tStrumenti
    private static final String TABELLA_STEP = "tStep";


    // tRicette Columns names
    private static final String KEY_NOME_tRICETTE = "nome_tRicette";
    private static final String KEY_TEMPO_tRICETTE = "tempo_tRicette";
    private static final String KEY_KCAL_tRICETTE = "kCal_tRicette";
    private static final String KEY_IMMAGINE_tRICETTE = "immagine_tRicette";

    // tIngredienti Columns names
    private static final String KEY_NOME_tINGREDIENTI = "nome_tIngredienti";

    // tServe Columns names
    private static final String KEY_RICETTA_tSERVE = "ricetta_tServe";
    private static final String KEY_INGREDIENTE_tSERVE = "ingrediente_tServe";
    private static final String KEY_QUANTITA_tSERVE = "quantita_tServe";
    private static final String KEY_UDM_tSERVE = "udm_tServe";

    // tStrumenti Columns names
    private static final String KEY_NOME_tSTRUMENTI = "nome_tStrumenti";

    // tUtili Columns names
    private static final String KEY_STRUMENTO_tUTILI = "strumento_tUtili";
    private static final String KEY_RICETTA_tUTILI = "ricetta_tUtili";

    // tStep Columns names
    private static final String KEY_TITOLO_tSTEP = "titolo_tStep";
    private static final String KEY_RICETTA_tSTEP = "ricetta_tStep";
    private static final String KEY_DESCRIZIONE_tSTEP = "descrizione_tStep";
    private static final String KEY_TEMPO_tSTEP = "tempo_tStep";

    //definizione degli schemi delle tabelle
    private static final String[] TABELLA_RICETTE_COLUMNS = {KEY_NOME_tRICETTE, KEY_TEMPO_tRICETTE, KEY_KCAL_tRICETTE, KEY_IMMAGINE_tRICETTE};
    private static final String[] TABELLA_INGREDIENTI_COLUMNS = {KEY_NOME_tINGREDIENTI};
    private static final String[] TABELLA_SERVE_COLUMNS = {KEY_RICETTA_tSERVE, KEY_INGREDIENTE_tSERVE, KEY_QUANTITA_tSERVE, KEY_UDM_tSERVE};
    private static final String[] TABELLA_STRUMENTI_COLUMNS = {KEY_NOME_tSTRUMENTI};
    private static final String[] TABELLA_UTILI_COLUMNS = {KEY_STRUMENTO_tUTILI, KEY_RICETTA_tUTILI};
    private static final String[] TABELLA_STEP_COLUMNS = {KEY_TITOLO_tSTEP, KEY_RICETTA_tSTEP, KEY_DESCRIZIONE_tSTEP, KEY_TEMPO_tSTEP};

 
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {//creazione del DB con la Tabella attraverso comando sqlite

        String CREATE_TABELLA_RICETTE = "CREATE TABLE " + TABELLA_RICETTE + " ( " +
                KEY_NOME_tRICETTE + " TEXT NOT NULL PRIMARY KEY, " +
                KEY_TEMPO_tRICETTE + " TEXT, " +
                KEY_KCAL_tRICETTE + " INTEGER, " +
                KEY_IMMAGINE_tRICETTE + " TEXT " +
                ")";

        String CREATE_TABELLA_INGREDIENTI = "CREATE TABLE " + TABELLA_INGREDIENTI + " ( " +
                KEY_NOME_tINGREDIENTI + " TEXT NOT NULL PRIMARY KEY " +
                ")";

        String CREATE_TABELLA_SERVE = "CREATE TABLE " + TABELLA_SERVE + " ( " +
                KEY_INGREDIENTE_tSERVE + " TEXT NOT NULL, " +
                KEY_RICETTA_tSERVE + " TEXT NOT NULL, " +
                KEY_QUANTITA_tSERVE + " REAL, " +
                KEY_UDM_tSERVE + " TEXT, " +
                "FOREIGN KEY (" + KEY_INGREDIENTE_tSERVE + ") REFERENCES " + TABELLA_INGREDIENTI + "(" + KEY_NOME_tINGREDIENTI + "), " +
                "FOREIGN KEY (" + KEY_RICETTA_tSERVE + ") REFERENCES " + TABELLA_RICETTE + "(" + KEY_NOME_tRICETTE + "), " +
                "PRIMARY KEY (" + KEY_INGREDIENTE_tSERVE + ", " + KEY_RICETTA_tSERVE + ") " +
                ")";

        String CREATE_TABELLA_STRUMENTI = "CREATE TABLE " + TABELLA_STRUMENTI + " ( " +
                KEY_NOME_tSTRUMENTI + " TEXT NOT NULL PRIMARY KEY " +
                ")";

        String CREATE_TABELLA_UTILI = "CREATE TABLE " + TABELLA_UTILI + " ( " +
                KEY_STRUMENTO_tUTILI + " TEXT NOT NULL, " +
                KEY_RICETTA_tUTILI + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + KEY_STRUMENTO_tUTILI + ") REFERENCES " + TABELLA_STRUMENTI + "(" + KEY_NOME_tSTRUMENTI + "), " +
                "FOREIGN KEY (" + KEY_RICETTA_tUTILI + ") REFERENCES " + TABELLA_RICETTE + "(" + KEY_NOME_tRICETTE + "), " +
                "PRIMARY KEY (" + KEY_STRUMENTO_tUTILI + ", " + KEY_RICETTA_tUTILI + ") " +
                ")";

        String CREATE_TABELLA_STEP = "CREATE TABLE " + TABELLA_STEP + " ( " +
                KEY_TITOLO_tSTEP + " TEXT NOT NULL, " +
                KEY_RICETTA_tSTEP + " TEXT NOT NULL, " +
                KEY_DESCRIZIONE_tSTEP + " TEXT, " +
                KEY_TEMPO_tSTEP + " INTEGER, " +
                "FOREIGN KEY (" + KEY_RICETTA_tSTEP + ") REFERENCES " + TABELLA_RICETTE + "(" + KEY_NOME_tRICETTE + "), " +
                "PRIMARY KEY (" + KEY_TITOLO_tSTEP + ", " + KEY_RICETTA_tSTEP + ") " +
                ")";

 
        // create ricette table
        db.execSQL(CREATE_TABELLA_RICETTE); //esecuzione della query
        db.execSQL(CREATE_TABELLA_INGREDIENTI);
        db.execSQL(CREATE_TABELLA_SERVE);
        db.execSQL(CREATE_TABELLA_STRUMENTI);
        db.execSQL(CREATE_TABELLA_UTILI);
        db.execSQL(CREATE_TABELLA_STEP);

    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//quando si aggiorna il DB aggiornando ogni volta la tabella dopo una modifica
        // Drop older ricette table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABELLA_RICETTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABELLA_INGREDIENTI);
        db.execSQL("DROP TABLE IF EXISTS " + TABELLA_SERVE);
        db.execSQL("DROP TABLE IF EXISTS " + TABELLA_STRUMENTI);
        db.execSQL("DROP TABLE IF EXISTS " + TABELLA_UTILI);
        db.execSQL("DROP TABLE IF EXISTS " + TABELLA_STEP);
 
        // create fresh ricette table
        this.onCreate(db);
    }
    
    public void addRicetta(Ricetta nuovaRicetta){ //metodo per aggiungere un record

    	//for logging
    	//Log.d("addRicetta", nuovaRicetta.toString());

    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();

    	/*
    	 2. create ContentValues to add key "column"/value and insert into DB
    	 */

        ContentValues values_tRicette = new ContentValues();
        values_tRicette.put(KEY_NOME_tRICETTE, nuovaRicetta.getNome()); // get nome
        values_tRicette.put(KEY_TEMPO_tRICETTE, nuovaRicetta.getTempo()); // get tempo
        values_tRicette.put(KEY_KCAL_tRICETTE, nuovaRicetta.getkCal()); // get kcal
        values_tRicette.put(KEY_IMMAGINE_tRICETTE, nuovaRicetta.getImmagine().toString()); // get immagine

        //insert
        db.insert(TABELLA_RICETTE, // table
                null, //nullColumnHack
                values_tRicette); // key/value -> keys = column names/ values = column values

        ContentValues values_tIngredienti;
        ContentValues values_tServe;
        for (Ingrediente i : nuovaRicetta.getIngredienti()) {
            //tIngredienti
            values_tIngredienti = new ContentValues();
            values_tIngredienti.put(KEY_NOME_tINGREDIENTI, i.getNome());

            //tServe
            values_tServe = new ContentValues();
            values_tServe.put(KEY_RICETTA_tSERVE, nuovaRicetta.getNome());
            values_tServe.put(KEY_INGREDIENTE_tSERVE, i.getNome());
            values_tServe.put(KEY_QUANTITA_tSERVE, i.getQuantita());
            values_tServe.put(KEY_UDM_tSERVE, i.getUnitaDiMisura());

            db.insert(TABELLA_INGREDIENTI,
                    null,
                    values_tIngredienti);

            db.insert(TABELLA_SERVE,
                    null,
                    values_tServe);

        }

        ContentValues values_tStrumenti;
        ContentValues values_tUtili;
        for (String s : nuovaRicetta.getStrumenti()) {
            //tStrumenti
            values_tStrumenti = new ContentValues();
            values_tStrumenti.put(KEY_NOME_tSTRUMENTI, s);

            //tUtili
            values_tUtili = new ContentValues();
            values_tUtili.put(KEY_RICETTA_tUTILI, nuovaRicetta.getNome());
            values_tUtili.put(KEY_STRUMENTO_tUTILI, s);

            db.insert(TABELLA_STRUMENTI,
                    null,
                    values_tStrumenti);

            db.insert(TABELLA_UTILI,
                    null,
                    values_tUtili);

        }

        ContentValues values_tStep;
        for (Step s : nuovaRicetta.getPreparazione()) {
            values_tStep = new ContentValues();
            values_tStep.put(KEY_TITOLO_tSTEP, s.getTitolo());
            values_tStep.put(KEY_RICETTA_tSTEP, nuovaRicetta.getNome());
            values_tStep.put(KEY_DESCRIZIONE_tSTEP, s.getDescrizione());
            values_tStep.put(KEY_TEMPO_tSTEP, s.getTempo());

            db.insert(TABELLA_STEP,
                    null,
                    values_tStep);
        }

    	// 4. close
    	db.close();

    }
    
    public Ricetta getRicetta(String nomeRicetta){//leggere record
    	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c;

        String tempoRicetta = "";
        int KCalRicetta = 1000;
        Uri immagineRicetta = null;
        ArrayList<Ingrediente> elencoIngredienti = new ArrayList<>();
        Ingrediente ingrediente = new Ingrediente();
        ArrayList<String> elencoStrumenti = new ArrayList<>();
        ArrayList<Step> stepPreparazione = new ArrayList<>();
        Step step = new Step();

        //query per la ricetta
        c = db.rawQuery("SELECT * " +
                           "FROM " + TABELLA_RICETTE +
                           " WHERE " + KEY_NOME_tRICETTE + " = '" + nomeRicetta + "'",
                null);

        if (c.moveToFirst()){
            //String nomeRicetta = c.getString(0); : il nome della ricetta uso quello passato per parametro
            tempoRicetta = c.getString(1);
            KCalRicetta = c.getInt(2);
            immagineRicetta = Uri.parse(c.getString(3));
        }

        else    //se la query non ha prodotto alcun risultato
            return null;


        //query per ingredienti
        c = db.rawQuery("SELECT " + KEY_INGREDIENTE_tSERVE + ", " + KEY_QUANTITA_tSERVE + ", " + KEY_UDM_tSERVE +
                        " FROM " + TABELLA_SERVE +
                        " WHERE " + KEY_RICETTA_tSERVE + " = '" + nomeRicetta + "'",
                null);


        if (c.moveToFirst()){
            do {
                String nomeIngrediente = c.getString(0);
                Float quantita = c.getFloat(1);
                String udm = c.getString(2);
                ingrediente = new Ingrediente(nomeIngrediente, quantita, udm);
                elencoIngredienti.add(ingrediente);
            } while(c.moveToNext());
        }

        //query per strumenti
        c = db.rawQuery("SELECT " + KEY_STRUMENTO_tUTILI +
                        " FROM " + TABELLA_UTILI +
                        " WHERE " + KEY_RICETTA_tUTILI + " = '" + nomeRicetta + "'",
                null);


        if (c.moveToFirst()){
            do {
                String nomeStrumento = c.getString(0);
                elencoStrumenti.add(nomeStrumento);
            } while(c.moveToNext());
        }

        //query per gli step
        c = db.rawQuery("SELECT " + KEY_TITOLO_tSTEP + ", " + KEY_DESCRIZIONE_tSTEP + ", " + KEY_TEMPO_tSTEP +
                        " FROM " + TABELLA_STEP +
                        " WHERE " + KEY_RICETTA_tSTEP + " = '" + nomeRicetta + "'",
                null);


        if (c.moveToFirst()){
            do {
                String titoloStep = c.getString(0);
                String descrizioneStep = c.getString(1);
                int tempoStep = Integer.parseInt(c.getString(2));
                step = new Step(titoloStep, descrizioneStep, tempoStep);
                stepPreparazione.add(step);
            } while(c.moveToNext());
        }
     
        // 4. build Ricetta object
        Ricetta ricetta = new Ricetta();
        ricetta.setNome(nomeRicetta);
        ricetta.setTempo(tempoRicetta);
        ricetta.setkCal(KCalRicetta);
        ricetta.setImmagine(immagineRicetta);
        ricetta.setIngredienti(elencoIngredienti);
        ricetta.setStrumenti(elencoStrumenti);
        ricetta.setPreparazione(stepPreparazione);

        //c.close();
        db.close();

        // 5. return book
        return ricetta;
    }
    
    public List<Ricetta> getRicette() {//legge tutti i record e li inserisce in una lista
        List<Ricetta> ricette = new LinkedList<Ricetta>();
  
        // 1. build the query
        String query = "SELECT * FROM " + TABELLA_RICETTE;
  
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build book and add it to list
        Ricetta ricetta = null;
        if (cursor.moveToFirst()) {
            do {
                ricetta = getRicetta(cursor.getString(0));  //colonna corrispondente al nome della ricetta
                // Add ricetta to ricette
                ricette.add(ricetta);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ricette;
    }
    
    public void updateRicetta(Ricetta nuovaRicetta) {//modifica record
    	 
        deleteRicetta(nuovaRicetta);    //viene eliminata la ricetta con lo stesso nome
        addRicetta(nuovaRicetta);   //e dunque ricreata con la versione aggiornata
     
    }
    
    public void deleteRicetta(Ricetta ricetta) {//cancella record
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABELLA_RICETTE, //table name
                KEY_NOME_tRICETTE + " = ?",  // selections
                new String[] { ricetta.getNome() });

        db.delete(TABELLA_SERVE, //table name
                KEY_RICETTA_tSERVE + " = ?",  // selections
                new String[] { ricetta.getNome() });

        db.delete(TABELLA_UTILI, //table name
                KEY_RICETTA_tUTILI + " = ?",  // selections
                new String[] { ricetta.getNome() });

        db.delete(TABELLA_STEP, //table name
                KEY_RICETTA_tSTEP + " = ?",  // selections
                new String[] { ricetta.getNome() });
 
        // 3. close
        db.close();
 
    }
 
}
