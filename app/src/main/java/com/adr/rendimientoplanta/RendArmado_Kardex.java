package com.adr.rendimientoplanta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.adr.rendimientoplanta.LIBRERIA.Variables;

public class RendArmado_Kardex extends AppCompatActivity {

    //DECLARACIÓN DE VARIABLES TEXTVIEW
    private TextView lblEmpresa;
    private TextView lblSucursal;
    private TextView lblLinea;
    private TextView lblLado;
    private TextView lblFecha;
    private TextView lblMesa;
    private TextView lblDNI;
    private TextView lblNombres;
    private TextView lblPresentacion;

    //DECLARACIÓN DE VARIABLES TEXTVIEW
    private EditText edtCantidad;
    private EditText edtPeso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rend_armado_kardex);

        //ASIGNACION DE VARIABLES A TEXTVIEW DE LAYOUT
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblLinea = (TextView) findViewById(R.id.lblLinea);
        lblLado = (TextView) findViewById(R.id.lblLado);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblMesa = (TextView) findViewById(R.id.lblMesa);
        lblDNI = (TextView) findViewById(R.id.lblDNI);
        lblNombres = (TextView) findViewById(R.id.lblNombres);
        lblPresentacion = (TextView) findViewById(R.id.lblPresentacion);

        //ASIGNACION DE VARIABLES A EDITTEXT DE LAYOUT
        edtCantidad = (EditText) findViewById(R.id.edtCantidad);
        edtPeso = (EditText) findViewById(R.id.edtPeso);

        //ASIGNACIÓN DE PARAMETROS A LA ACTIVIDAD
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblLinea.setText(Variables.Lin_Descripcion);
        lblLado.setText(Variables.Lin_Lado);
        lblFecha.setText(Variables.FechaStr);
        lblMesa.setText(String.valueOf(Variables.Per_Ubicacion));
        lblDNI.setText(Variables.Per_Dni);
        lblNombres.setText(Variables.Per_Nombres);
        lblPresentacion.setText(Variables.PreEnv_DescripcionCor);

        //ASIGNACION DE EDITTEXT A ACTIVITY
        edtPeso.setText(String.valueOf(Variables.PreEnv_PesoTorre*1000));
        edtCantidad.setText(String.valueOf(Variables.PreEnv_CantidadTorre));

        edtPeso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (edtPeso.hasFocus()==true)
                {
                    if(s.length() != 0)
                        edtCantidad.setText(String.valueOf(CalcularCantidad(Integer.parseInt(edtPeso.getText().toString()))));
                    else
                        edtCantidad.setText("0");

                }
            }
        });

        edtCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if(edtCantidad.hasFocus()==true)
                {
                    if(s.length() != 0)
                        edtPeso.setText(String.valueOf(CalcularPeso(Integer.parseInt(edtCantidad.getText().toString()))));
                    else
                        edtPeso.setText("0");
                }
            }

        });

    }

    private int CalcularCantidad(int Peso) {
        return (int) ((Peso * Variables.PreEnv_CantidadTorre) / (Variables.PreEnv_PesoTorre * 1000));
    }

    private int CalcularPeso(int Cantidad) {
        return (int) ((Cantidad * (Variables.PreEnv_PesoTorre * 1000)) / Variables.PreEnv_CantidadTorre);
    }

}
