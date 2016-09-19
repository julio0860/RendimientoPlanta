package com.adr.rendimientoplanta;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_LineaRegistro;
import com.adr.rendimientoplanta.DATA.T_MotivoParada;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import net.sourceforge.jtds.jdbc.DateTime;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IngresoJabas_RegistroLinea extends AppCompatActivity {

    //SMP: Variables para insertar
    private String HoraIni="00:00:00";
    private String HoraFin="00:00:00";

    //SMP: Diferencia de horas
    SimpleDateFormat FormatoHora = new SimpleDateFormat("HH:mm:ss",java.util.Locale.getDefault());

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
    private Button btnIniciar;
    private Button btnKardex;
    private Button btnTerminar;
    private Button btnGrabarParada;

    //SMP: Declaracion de variable Spinner
    private Spinner spnMotivoParadas;

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
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnTerminar = (Button) findViewById(R.id.btnTerminar);
        btnKardex = (Button) findViewById(R.id.btnKardex);
        btnGrabarParada = (Button) findViewById(R.id.btnGrabarParada);

        //SMP: Asignar de varible Spinner a Layout
        spnMotivoParadas = (Spinner) findViewById(R.id.spnMotivoParadas);
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

        //SMP: Validación registro nuevo o existente
        if (CurLineaRegistro.getCount()!=0) {
            Toast.makeText(this,"Registro existente Id: "+CurLineaRegistro.getString(0), Toast.LENGTH_SHORT).show();
            BloquearBotones(true);
            edtHoraIni.setText(CurLineaRegistro.getString(CurLineaRegistro.getColumnIndex(T_LineaRegistro.LinRegHoraIni)));
        }
        else {
            Toast.makeText(this,"Registro nuevo",Toast.LENGTH_SHORT).show();
            BloquearBotones(false);
        }

        //SMP: Poblar Spinner motivo paradas
        Cursor Empresa = LocBD.rawQuery(T_MotivoParada.MotivoParada_SeleccionarTodos(),null);
        spnMotivoParadas.setAdapter(fnc.AdaptadorSpinnerSimpleLarge(this,Empresa,T_MotivoParada.MotParDescripcion));

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
        btnGrabarParada.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v){
                    final double tEfectivo;
                    tEfectivo = fnc.HoraEfectivaEntreHoras(edtHoraIniPar.getText().toString(),
                            edtHoraFinPar.getText().toString());

                    if (tEfectivo>0)
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IngresoJabas_RegistroLinea.this);
                        String alert_title = "Registrar parada";
                        String alert_description = "¿Estas seguro que quiere insertar la siguiente parada?";
                        alertDialogBuilder.setTitle(alert_title);
                        // set dialog message
                        alertDialogBuilder
                                .setMessage(alert_description)
                                .setCancelable(false)
                                .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                                    // Lo que sucede si se pulsa yes
                                    public void onClick(DialogInterface dialog,int id) {
                                        // Código propio del método calculo de diferencia de horas
                                        Toast.makeText(IngresoJabas_RegistroLinea.this,"Registro insertado "
                                                +tEfectivo,Toast.LENGTH_LONG).show();
                                    }
                                })
                                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // Si se pulsa no no hace nada
                                        Toast.makeText(IngresoJabas_RegistroLinea.this,"Operación cancelada",Toast.LENGTH_LONG).show();
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
                    }else
                    {
                        Toast.makeText(IngresoJabas_RegistroLinea.this,"Error al asignar horas, verificar!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        );
        btnIniciar.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v){

                    HoraIni=edtHoraIni.getText().toString();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IngresoJabas_RegistroLinea.this);
                    String alert_title = "Iniciar Linea";
                    String alert_description = "¿Estas seguro que quiere iniciar la Linea a las "+HoraIni+"?";
                    alertDialogBuilder.setTitle(alert_title);
                    // set dialog message
                    alertDialogBuilder
                            .setMessage(alert_description)
                            .setCancelable(false)
                            .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                                // Lo que sucede si se pulsa yes
                                public void onClick(DialogInterface dialog,int id) {
                                    // Código propio del método borrado para ejemplo
                                    try {
                                        LocBD.execSQL(T_LineaRegistro.LineaRegistro_Insertar(Variables.Lin_Id,
                                                Variables.FechaStr,HoraIni,Variables.MAC,Variables.FechaStr,1,Variables.Usu_Id));
                                        BloquearBotones(true);
                                        Cursor Registro = LocBD.rawQuery(T_LineaRegistro.LineaRegistro_SeleccionarLinea(Variables.Lin_Id,Variables.FechaStr),null);
                                        Registro.moveToFirst();
                                        Toast.makeText(IngresoJabas_RegistroLinea.this,"Linea Iniciada, Id: "+Registro.getString(0),Toast.LENGTH_SHORT).show();
                                    }catch (SQLException e)
                                    {
                                        Toast.makeText(IngresoJabas_RegistroLinea.this,e.toString(),Toast.LENGTH_SHORT).show();
                                    }
                                }

                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // Si se pulsa no no hace nada
                                    Toast.makeText(IngresoJabas_RegistroLinea.this,"Operación cancelada",Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }
            }
        );
        btnTerminar.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v) {
                    final double tEfectivo;
                    tEfectivo = fnc.HoraEfectivaEntreHoras(edtHoraIni.getText().toString(),
                            edtHoraFin.getText().toString());

                    if (tEfectivo > 0) {
                        HoraFin = edtHoraFin.getText().toString();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IngresoJabas_RegistroLinea.this);
                        String alert_title = "Terminar Linea";
                        String alert_description = "¿Estas seguro que quiere terminar la Linea a las " + HoraFin + "?";
                        alertDialogBuilder.setTitle(alert_title);
                        // set dialog message
                        alertDialogBuilder
                                .setMessage(alert_description)
                                .setCancelable(false)
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    // Lo que sucede si se pulsa yes
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Código propio del método borrado para ejemplo
                                        try {
                                            BloquearBotones(true);
                                            Toast.makeText(IngresoJabas_RegistroLinea.this, "Linea Terminada, hora: " + HoraFin, Toast.LENGTH_SHORT).show();
                                        } catch (SQLException e) {
                                            Toast.makeText(IngresoJabas_RegistroLinea.this, e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Si se pulsa no no hace nada
                                        Toast.makeText(IngresoJabas_RegistroLinea.this, "Operación cancelada", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
                    } else {
                        Toast.makeText(IngresoJabas_RegistroLinea.this, "Error al asignar horas, verificar!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        );

        btnKardex.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v){

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
    private void BloquearBotones(boolean Estado)
    {
        btnIniciar.setEnabled(!Estado);
        imbHoraIni.setEnabled(!Estado);
        imbHoraFin.setEnabled(Estado);
        imbHoraIniPar.setEnabled(Estado);
        imbHoraFinPar.setEnabled(Estado);
        btnTerminar.setEnabled(Estado);
        btnKardex.setEnabled(Estado);
        btnAgregarJabas.setEnabled(Estado);
        btnGrabarParada.setEnabled(Estado);
    }

}
