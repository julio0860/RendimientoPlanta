package com.adr.rendimientoplanta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    //DECLARACION DE VARIABLES BUTTON
    private Button btnRegistrar;
    private Button btnCambiarPresentacion;
    private ImageButton imbRegresar;

    //DECLARACION DEL RADIOGROUP
    private RadioGroup rbgGrupoTipo;
    private RadioButton rbnEntrega;
    private RadioButton rbnDevolucion;

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

        //ASIGNAR VARIABLES A BUTTON EN LAYOUT
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnCambiarPresentacion = (Button)findViewById(R.id.btnCambiarPresentacion);

        imbRegresar = (ImageButton)findViewById(R.id.imbRegresar);
        //ASIGNACION DE VARIABLES A EDITTEXT DE LAYOUT
        edtCantidad = (EditText) findViewById(R.id.edtCantidad);
        edtPeso = (EditText) findViewById(R.id.edtPeso);

        //ASIGNACION DE RADIOGROUP Y RADIOBUTTON A LAYOUT
        rbgGrupoTipo = (RadioGroup) findViewById(R.id.rbgGrupoTipo);
        rbnEntrega = (RadioButton)findViewById(R.id.rbnEntrega);
        rbnDevolucion=(RadioButton)findViewById(R.id.rbnDevolucion);

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
        edtPeso.setText(String.valueOf((int)(Variables.PreEnv_PesoTorre*1000)));
        edtCantidad.setText(String.valueOf(Variables.PreEnv_CantidadTorre));

        edtPeso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtCantidad.hasFocus()==true)
                {
                    if(s.length() != 0)
                        edtPeso.setText(String.valueOf(CalcularPeso(Integer.parseInt(edtCantidad.getText().toString()))));
                    else
                        edtPeso.setText("0");
                }
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
            }
        );
        rbgGrupoTipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rbnEntrega){
                    btnRegistrar.setText("AGREGAR ENTREGA");
                    edtCantidad.requestFocus();
                    edtCantidad.setText("");

                }else if (checkedId == R.id.rbnDevolucion){
                    btnRegistrar.setText("AGREGAR DEVOLUCION");
                    edtPeso.requestFocus();
                    edtPeso.setText("");
                }
            }
        });
        btnCambiarPresentacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent NuevaActividad = new Intent(RendArmado_Kardex.this,RendArmado_Registro.class);
                    startActivity(NuevaActividad);
                }
            }
        );
        imbRegresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent NuevaActividad = new Intent(RendArmado_Kardex.this,RendArmado_Lista.class);
                    startActivity(NuevaActividad);
                }
            }
        );
    }

    private int CalcularCantidad(int Peso) {
        return (int) ((Peso * Variables.PreEnv_CantidadTorre) / (Variables.PreEnv_PesoTorre * 1000));
    }

    private int CalcularPeso(int Cantidad) {
        return (int) ((Cantidad * (Variables.PreEnv_PesoTorre * 1000)) / Variables.PreEnv_CantidadTorre);
    }

}
