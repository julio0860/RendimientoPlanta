package com.adr.rendimientoplanta;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.util.Calendar;

public class IngresoJabas_RegistroLinea extends AppCompatActivity {

    private TextView lblSucursal;
    private TextView lblCultivo;
    private TextView lblFecha;
    private TextView lblEmpresa;

    private TextView lblLinea;

    private Button btnAgregarJabas;

    static final int DATE_ID=0;
    static final int TIME_DIALOG_ID = 1;

    private Funciones fnc;
    //ENTEROS PARA HORA
    private int pHour;
    private int pMinute;


    //EDIT TEXT
    private EditText displayTime;
    private EditText edtHoraIni;
    private EditText edtHoraFin;
    private EditText edtHoraIniPar;
    private EditText edtHoraFinPar;

    //Image Buttons
    private ImageButton imbRegresar;

    private ImageButton imbHoraIni;
    private ImageButton imbHoraFin;
    private ImageButton imbHoraIniPar;
    private ImageButton imbHoraFinPar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_jabas_registro_linea);

        //AGISNACION LAYOUT A EDIT TEXT

        edtHoraIni = (EditText) findViewById(R.id.edtHoraIni);
        edtHoraFin = (EditText) findViewById(R.id.edtHoraFin);
        edtHoraIniPar = (EditText) findViewById(R.id.edtHoraIniPar);
        edtHoraFinPar = (EditText) findViewById(R.id.edtHoraFinPar);


        lblCultivo = (TextView) findViewById(R.id.lblCultivo);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblLinea = (TextView) findViewById(R.id.lblLinea);

        btnAgregarJabas = (Button) findViewById(R.id.btnAgregarJabas);

        fnc = new Funciones();
        //Actualizar por defecto la hora

        final Calendar C = Calendar.getInstance();
        pHour = C.get(Calendar.HOUR_OF_DAY);
        pMinute = C.get(Calendar.MINUTE);

        lblFecha.setText(Variables.FechaStr);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblCultivo.setText(Variables.Cul_Descripcion);
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblLinea.setText(Variables.Lin_Descripcion);

        //ASIGNACION DE CONTROLES LAYOUT A VARIABLES
        imbHoraIni = (ImageButton) findViewById(R.id.imbHoraIni);
        imbHoraFin = (ImageButton) findViewById(R.id.imbHoraFin);
        imbHoraIniPar = (ImageButton) findViewById(R.id.imbHoraIniPar);
        imbHoraFinPar = (ImageButton) findViewById(R.id.imbHoraFinPar);

        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);

        btnAgregarJabas.setOnClickListener(new View.OnClickListener()
             {
                 @Override
                 public void onClick (View v){
                     Intent ActividadNueva = new Intent(IngresoJabas_RegistroLinea.this, IngresoJabas_RegistroJabas.class);
                     startActivity(ActividadNueva);
                 }
             }
        );
        imbRegresar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent NuevaActividad = new Intent(IngresoJabas_RegistroLinea.this,IngresoJabas_Grilla.class);
                startActivity(NuevaActividad);
            }
        });
        imbHoraIni.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                displayTime = edtHoraIni;
                showDialog(TIME_DIALOG_ID);
            }
        });
        imbHoraFin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                displayTime = edtHoraFin;
                showDialog(TIME_DIALOG_ID);
            }
        });
        imbHoraIniPar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                displayTime = edtHoraIniPar;
                showDialog(TIME_DIALOG_ID);
            }
        });
        imbHoraFinPar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                displayTime = edtHoraFinPar;
                showDialog(TIME_DIALOG_ID);
            }
        });
    }

    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case DATE_ID:
                //return new DatePickerDialog(this,mDateSetListener,sAño,sMes,sDia);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,mTimeSetListener, pHour, pMinute,false);
        }
        return null;
    }
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            pHour = hourOfDay;
            pMinute = minute;
            //updateDisplay(displayTime);
            fnc.EstablecerHoraEdt(displayTime,pHour,pMinute);
            //displayToast();
        }
        };

}
