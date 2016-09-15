package com.adr.rendimientoplanta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RegistroRendimiento extends AppCompatActivity {

    ArrayAdapter<String> adspnPresentacion;
    private Spinner spnPresentacion;

    private TextView lblDNI;
    private TextView lblNombres;
    final String[] Presentaciones = new String[]{
            "PUNNET 500 GRS"
            ,"CLAMSHELL 2LBS"
            ,"CLAMSHELL 3LBS"
            ,"CLAMSHELL 4LBS"
            ,"CLAMSHELL 5LBS"
            ,"BOLSA 1.5 LBS"
            ,"BOLSA 2.0 LBS"
            ,"PUNNET 250 GRS"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_rendimiento);

        spnPresentacion = (Spinner) findViewById(R.id.spnPresentacion);
        lblDNI = (TextView) findViewById(R.id.lblDNI);
        lblNombres = (TextView) findViewById(R.id.lblNombres);

        lblDNI.setText(getIntent().getStringExtra("ID"));
        lblNombres.setText(getIntent().getStringExtra("NOMBRES"));

        adspnPresentacion = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Presentaciones);
        spnPresentacion.setAdapter(adspnPresentacion);

    }
}
