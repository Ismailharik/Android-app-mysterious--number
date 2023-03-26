package com.example.appfrom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewScore;
    private Button buttonOk;
    private EditText editText;
    private TextView textViewIndication;
    private TextView textViewTentative;
    private ListView listViewHistoric;

    private ProgressBar progressBar;

    private int secret;
    private int counter;
    private int score=0;
    private int maxTentative=6;
    private List<String> listHistoric = new ArrayList<>();
    private ArrayAdapter<String> model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewScore = findViewById(R.id.scoreNumberTextView);
        editText = findViewById(R.id.editTextNumber);
        buttonOk = findViewById(R.id.buttonOK);

        progressBar = findViewById(R.id.progressBar);
        textViewTentative = findViewById(R.id.progressNumber);

        textViewIndication = findViewById(R.id.textViewIndication);

        listViewHistoric = findViewById(R.id.listViewHistoric);
        model = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listHistoric);
        listViewHistoric.setAdapter(model);


        this.initialize();
        buttonOk.setOnClickListener(view -> {
            int number = Integer.parseInt(editText.getText().toString());
            if (counter>5){
                this.rejouer();
            }else{
                if(number<secret){
                    textViewIndication.setText("entrer une valeur superieur");
                }else if ( number>secret){
                    textViewIndication.setText("entrer une valeur inferieur");
                }else{
                    textViewIndication.setText("BRAVO");
                    textViewScore.setText(String.valueOf(++score));
                    rejouer();
                }
                counter++;
                textViewTentative.setText(String.valueOf(counter));
                progressBar.setProgress(counter);

                //handle historic
                listHistoric.add(counter +" => "+number);
                model.notifyDataSetChanged();   //each time we change the array list we should inform the view that data has been changed

            }

        });


    }
    private void initialize(){

        secret = 1+ (int)(Math.random()*100);
        counter=0;
        textViewTentative.setText(String.valueOf(counter));
        progressBar.setProgress(counter);
        progressBar.setMax(maxTentative);
        textViewIndication.setText("entrer un nombre");
        listHistoric.clear();model.notifyDataSetChanged();

    }
    private void rejouer(){
        Log.i("MyLog","Rejouer...");
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Rejouer?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initialize();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Quitter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialog.show();
//        secret = 1+(int)Math.random()*100;
//        counter = 1;
    }
}