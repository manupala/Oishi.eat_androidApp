package com.example.oishiweek_project.ui.step;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.format.Time;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.oishiweek_project.MyAdapterListViewHome;
import com.example.oishiweek_project.R;
import com.example.oishiweek_project.Ricetta;
import com.example.oishiweek_project.RicetteHandler;
import com.example.oishiweek_project.SecondActivity;
import com.example.oishiweek_project.Step;
import com.example.oishiweek_project.databinding.FragmentHomeBinding;
import com.example.oishiweek_project.databinding.StepFragmentBinding;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class StepFragment extends Fragment {

    private StepFragmentBinding binding;
    public static Ricetta ricettaCorrente;
    private int numeroStep;
    CountDownTimer timer;

    int[] arrayIcons = {R.drawable.step_icon_1, R.drawable.step_icon_2, R.drawable.step_icon_3, R.drawable.step_icon_4,
            R.drawable.step_icon_5, R.drawable.step_icon_6, R.drawable.step_icon_7, R.drawable.step_icon_8,
            R.drawable.step_icon_9, R.drawable.step_icon_10};

    private static int imgChooser = 0;

    public StepFragment(int numeroStep) {

        this.numeroStep = numeroStep;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = StepFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Step stepCorrente = ricettaCorrente.getPreparazione().get(numeroStep);

        binding.StepTitolo.setText(stepCorrente.getTitolo());
        binding.StepImmagine.setImageResource(arrayIcons[imgChooser]);
        imgChooser = (imgChooser+1)%10; //così da garantire una scelta ciclica
        binding.StepTxtTimer.setText("-- : --");
        binding.StepDescrizione.setText(stepCorrente.getDescrizione());
        binding.StepDescrizione.setMovementMethod(new ScrollingMovementMethod());   //per rendere scrollable il testo
        //se il tempo è pari a 0, significa che non ci sono timer da impostare
        if(stepCorrente.getTempo() == 0) {
            binding.btnNextStep.setEnabled(true);
            binding.StepBtnTimer.setVisibility(View.INVISIBLE);
            binding.StepTxtTimer.setVisibility(View.INVISIBLE);
        }
        else {
            binding.StepBtnTimer.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        NotificationChannel channel = new NotificationChannel("MyNotification", "MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationManager manager = getContext().getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel);
                    }

                    long durata = stepCorrente.getTempo() * 1000;

                    timer = new CountDownTimer(durata, 1000) {
                        @Override
                        public void onTick(long l) {

                            //vedere come considerare anche le ore

                            //conversione dei millisecondi in minuti e secondi
                            String sDurata = String.format(Locale.ITALY, "%02d : %02d : %02d",
                                    TimeUnit.MILLISECONDS.toHours(l) % 60,
                                    TimeUnit.MILLISECONDS.toMinutes(l) % 60,
                                    TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds((TimeUnit.MILLISECONDS.toMinutes(l))));

                            binding.StepTxtTimer.setText(sDurata);

                        }

                        @Override
                        public void onFinish() {

                            String message = "È ora possibile proseguire con il prossimo step";
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "MyNotification");
                            builder.setContentTitle("Timer scaduto");
                            builder.setContentText(message);
                            builder.setSmallIcon(R.mipmap.oishiweek_logo);
                            builder.setAutoCancel(true);
                            /*Intent intent = new Intent(getContext(), NotificationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("message",message);
                            PendingIntent pendingIntent=PendingIntent.getActivity(getContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);*/


                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                            managerCompat.notify(1, builder.build());

                            binding.btnNextStep.setEnabled(true);

                        }
                    };

                    timer.start();

                }
            });
        }

        Button btnNextStep = binding.btnNextStep;
        binding.btnNextStep.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //se nella ricetta ci sono ancora step, andare al prossimo
                if(numeroStep+1 < ricettaCorrente.getPreparazione().size()) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new StepFragment(numeroStep+1))
                            .commitNow();
                }

                //altrimenti tornare alla home
                else {
                    getActivity().finish();
                }
            }

        });

        //fare l'onClick su un button a tutta pagina per andare allo Step successivo

        return root;

        //return inflater.inflate(R.layout.step_fragment, container, false);

    }

    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StepViewModel.class);
        // TODO: Use the ViewModel
    }*/

}