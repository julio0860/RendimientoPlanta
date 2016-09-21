package com.adr.rendimientoplanta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.adr.rendimientoplanta.LIBRERIA.Variables;

public class RegistroOperario extends AppCompatActivity {
    private TextView lblEmpresa;
    private TextView lblSucursal;
    private TextView lblProceso;
    private TextView lblSubproceso;
    private TextView lblLinea;
    private TextView lblLado;
    private EditText edtFecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_operario);

        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblProceso = (TextView) findViewById(R.id.lblProceso);
        lblSubproceso = (TextView) findViewById(R.id.lblSubproceso);
        lblLinea = (TextView) findViewById(R.id.lblLinea);
        lblLado = (TextView) findViewById(R.id.lblLado);
        edtFecha=(EditText) findViewById(R.id.edtFecha);

        //ASIGNACIÓN DE PARAMETROS A LA ACTIVIDAD
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblProceso.setText(Variables.Pro_Descripcion);
        lblSubproceso.setText(Variables.Sub_Descripcion);
        lblLinea.setText(Variables.Lin_Descripcion);
        lblLado.setText("LADO: "+Variables.Lin_Lado);
        edtFecha.setText(Variables.FechaStr);
    }
}
