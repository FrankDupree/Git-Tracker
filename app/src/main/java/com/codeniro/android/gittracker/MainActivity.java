package com.codeniro.android.gittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private MaterialBetterSpinner materialDesignSpinner;
    private MaterialBetterSpinner materialDesignSpinner2;


    String[] LANGS = {"Java", "Php", "Ruby", "Javascript"};
    String[] LOCATION = {"Lagos", "Texas", "Cairo", "Moscow"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LANGS);
        materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.android_material_design_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LOCATION);
         materialDesignSpinner2 = (MaterialBetterSpinner)
                findViewById(R.id.android_material_design_spinner2);
        materialDesignSpinner2.setAdapter(arrayAdapter2);


        button = (Button) findViewById(R.id.btn_search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lang = materialDesignSpinner.getText().toString();
                String loca = materialDesignSpinner2.getText().toString();
                if(lang.isEmpty() || loca.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please fill both fields!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(MainActivity.this, StatsActivity.class);
                    i.putExtra("lang", lang);
                    i.putExtra("loca", loca);
                    startActivity(i);
                }

            }
        });



    }




}
