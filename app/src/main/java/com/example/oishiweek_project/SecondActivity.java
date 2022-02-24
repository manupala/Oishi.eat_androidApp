package com.example.oishiweek_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.oishiweek_project.ui.step.StepFragment;

public class SecondActivity extends AppCompatActivity {

    private Ricetta ricettaRicevuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //viene bloccato il blocco schermo automatico
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (savedInstanceState == null) {
            StepFragment stepFragment = new StepFragment(0);   //si parte dallo step 0
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, stepFragment)
                    .commitNow();
        }
    }
}