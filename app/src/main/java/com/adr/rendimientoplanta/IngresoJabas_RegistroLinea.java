package com.adr.rendimientoplanta;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Linea;
import com.adr.rendimientoplanta.DATA.T_LineaRegistro;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.util.Calendar;

public class IngresoJabas_RegistroLinea extends AppCompatActivity {

    //SMP: Declaración de variables TextView
    private TextView lblSucursal;
    private TextView lblCultivo;
    private TextView lblFecha;
    private TextView lblEmpresa;
    private TextView lblLinea;

    //SMP: Declaración variables EditText
    private EditText displayTime;
    private EditText edtHoraIni;
    private EditText edtHoraFin;
    private EditText edtHoraIniPar;
    private EditText edtHoraFinPar;

    //SMP: Declaración variables ImageButton
    private ImageButton imbRegresar;
    private ImageButton imbHoraIni;
    private ImageButton imbHoraFin;
    private ImageButton imbHoraIniPar;
    private ImageButton imbHoraFinPar;

    //SMP: Declaración de variables Button
    private Button btnAgregarJabas;

    //SMP: Declaración de variables estaticas para la elección de dialog
    static final int DATE_ID=0;
    static final int TIME_DIALOG_ID = 1;

    //SMP: Declaración de variable heredando funciones globales
    private Funciones fnc;

    //SMP: Declaración variables para Dialog Time
    private int pHour;
    private int pMinute;

    //SMP: Declaración Base de datos local
    LocalBD LBD;
    SQLiteDatabase LocBD;

    //Inicio Procedimiento OnCreate---------------------------------------------------------------->
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_jabas_registro_linea);
        //Codigo a ejecutar al crear la actividad

        //SMP: Inicialización Base d datos local
        LBD = new LocalBD(this) ;
        LocBD = LBD.getWritableDatabase();

        //SMP: Asignación de variables EditText a Layout
        edtHoraIni = (EditText) findViewById(R.id.edtHoraIni);
        edtHoraFin = (EditText) findViewById(R.id.edtHoraFin);
        edtHoraIniPar = (EditText) findViewById(R.id.edtHoraIniPar);
        edtHoraFinPar = (EditText) findViewById(R.id.edtHoraFinPar);

        //SMP: Asignación de variables TextView a Layout
        lblCultivo = (TextView) findViewById(R.id.lblCultivo);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblLinea = (TextView) findViewById(R.id.lblLinea);

        //SMP: Asignación de las variables ImageButton a layout
        imbHoraIni = (ImageButton) findViewById(R.id.imbHoraIni);
        imbHoraFin = (ImageButton) findViewById(R.id.imbHoraFin);
        imbHoraIniPar = (ImageButton) findViewById(R.id.imbHoraIniPar);
        imbHoraFinPar = (ImageButton) findViewById(R.id.imbHoraFinPar);
        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);

        //SMP: Asignación de variables Button a Layout
        btnAgregarJabas = (Button) findViewById(R.id.btnAgregarJabas);

        //SMP: Creación nueva variable Heredando la clase Funciones
        fnc = new Funciones();

        //SMP: Actualizar por defecto la hora
        final Calendar C = Calendar.getInstance();
        pHour = C.get(Calendar.HOUR_OF_DAY);
        pMinute = C.get(Calendar.MINUTE);

        //SMP: Asignación de texto a variables TextView en base a parametros establecidos
        lblFecha.setText(Variables.FechaStr);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblCultivo.setText(Variables.Cul_Descripcion);
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblLinea.setText(Variables.Lin_Descripcion);

        //SMP: Validación registro nuevo
        Cursor CurLineaRegistro = LocBD.rawQuery(T_LineaRegistro.LineaRegistro_SeleccionarLinea(Variables.Lin_Id,Variables.FechaStr),null);
        CurLineaRegistro.moveToFirst();
        if (CurLineaRegistro.getCount()!=0) {
            Toast.makeText(this, "Registro existente", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Registro nuevo",Toast.LENGTH_SHORT).show();
        }

        //SMP: Asignación de evento y acciones a ejecutar
        btnAgregarJabas.setOnClickListener(new View.OnClickListener()
             {
                 @Override
                 public void onClick (View v){
                     Intent ActividadNueva = new Intent(IngresoJabas_RegistroLinea.this,
                             IngresoJabas_RegistroJabas.class);
                     startActivity(ActividadNueva);
                 }
             }
        );
        imbRegresar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent NuevaActividad = new Intent(IngresoJabas_RegistroLinea.this,
                        IngresoJabas_Grilla.class);
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
//Fin procedimiento OnCreate ---------------------------------------------------------------------->

    //SMP: Switch elección tipo dialog
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
    //SMP: Evento de TimePickerDialog
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
