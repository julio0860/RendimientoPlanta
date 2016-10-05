package com.adr.rendimientoplanta;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
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
import com.adr.rendimientoplanta.DATA.T_LineaIngreso;
import com.adr.rendimientoplanta.DATA.T_LineaParadas;
import com.adr.rendimientoplanta.DATA.T_LineaRegistro;
import com.adr.rendimientoplanta.DATA.T_MotivoParada;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class IngresoJabas_RegistroLinea extends AppCompatActivity {

    //SMP: Declaración varialbes para insercion
    private int RegLin_Id;
    private int MotPar_Id;
    private int Est_Id;

    private String MotPar_Descripcion;
    private String HoraParIni;
    private String HoraParFin;
    private double tEfectivoPar;

    //SMP: Para auditoria
    private String HoraRegistro;

    //SMP: Resumen registro
    private int NumParadas=0;
    private double SumParadas=0;
    private double TotalTiempo=0;
    private double TiempoEfectivo=0;
    private double CantidadEquivalente=0;
    private double CantidadPorHora=0;

    //SMP: String Hora Vacia
    private String HoraNula="--";
    private int NumIngresos =0;

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
    private TextView lblTiempoParadas;
    private TextView lblHoraEfectiva;
    private TextView lblCantidad;
    private TextView lblCantidadPorHora;
    private TextView lblTiempoTotal;



    private TextView lblNumParadas;

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

    @Override
    public void onBackPressed()
    {
        // Your Code Here. Leave empty if you want nothing to happen on back press.
    }
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

        lblHoraEfectiva = (TextView) findViewById(R.id.lblHoraEfectiva);
        lblCantidad = (TextView) findViewById(R.id.lblCantidad);
        lblCantidadPorHora = (TextView) findViewById(R.id.lblCantidadPorHora);
        lblNumParadas = (TextView) findViewById(R.id.lblNumParadas);
        lblTiempoParadas = (TextView)findViewById(R.id.lblTiempoParadas);
        lblTiempoTotal = (TextView)findViewById(R.id.lblTiempoTotal);

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
        btnKardex = (Button) findViewById(R.id.btnCambiarPresentacion);
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
            HoraIni=CurLineaRegistro.getString(CurLineaRegistro.getColumnIndex(T_LineaRegistro.LinRegHoraIni));
            HoraFin=CurLineaRegistro.getString(CurLineaRegistro.getColumnIndex(T_LineaRegistro.LinRegHoraFin));
            Est_Id=CurLineaRegistro.getInt(CurLineaRegistro.getColumnIndex(T_LineaRegistro.EstId));
            NumIngresos = CurLineaRegistro.getInt(CurLineaRegistro.getColumnIndex(T_LineaRegistro.LinRegNumIngresos));

            edtHoraIni.setText(HoraIni);
            edtHoraFin.setText(HoraFin);
            //RecuperarNumeroParadas();
            lblNumParadas.setText(String.valueOf(CurLineaRegistro.getInt(CurLineaRegistro.getColumnIndex(T_LineaRegistro.LinRegNumParadas))));
            lblTiempoParadas.setText(String.valueOf(CurLineaRegistro.getDouble(CurLineaRegistro.getColumnIndex(T_LineaRegistro.LinRegParadas))));
            lblHoraEfectiva.setText(String.valueOf(CurLineaRegistro.getDouble(CurLineaRegistro.getColumnIndex(T_LineaRegistro.LinRegHoraEfectiva))));
            lblCantidad.setText(String.valueOf(CurLineaRegistro.getDouble(CurLineaRegistro.getColumnIndex(T_LineaRegistro.LinRegCantidad))));
            lblCantidadPorHora.setText(String.valueOf(CurLineaRegistro.getDouble(CurLineaRegistro.getColumnIndex(T_LineaRegistro.LinRegCantidadPorHora))));
            RegLin_Id = Integer.parseInt(CurLineaRegistro.getString(0)) ;
            edtHoraIniPar.setText(HoraNula);
            edtHoraFinPar.setText(HoraNula);
            ActualizarResumen();

            if (Est_Id==1)
            {
                Toast.makeText(this,"Registro existente Id: "+RegLin_Id, Toast.LENGTH_SHORT).show();
                BloquearBotones(true);

            }
            else if(Est_Id==2)
            {
                BloquearBotones(false);
                btnKardex.setEnabled(true);
                imbHoraIni.setEnabled(false);
                btnIniciar.setEnabled(false);
            }

        }
        else {
            Toast.makeText(this,"Registro nuevo",Toast.LENGTH_SHORT).show();
            BloquearBotones(false);
            edtHoraIni.setText(HoraNula);
            edtHoraFin.setText(HoraNula);
            edtHoraIniPar.setText(HoraNula);
            edtHoraFinPar.setText(HoraNula);
        }

        //SMP: Poblar Spinner motivo paradas
        Cursor MotivoParadas = LocBD.rawQuery(T_MotivoParada.MotivoParada_SeleccionarParadas(),null);
        spnMotivoParadas.setAdapter(fnc.AdaptadorSpinnerSimpleLarge(this,MotivoParadas,T_MotivoParada.MotDescripcion));

        //SMP: Asignación de evento y acciones a ejecutar
        btnAgregarJabas.setOnClickListener(new View.OnClickListener()
             {
                 @Override
                 public void onClick (View v){
                     Intent ActividadNueva = new Intent(IngresoJabas_RegistroLinea.this,
                             IngresoJabas_RegistroJabas.class);
                     ActividadNueva.putExtra("RegLin_Id",RegLin_Id);
                     ActividadNueva.putExtra("LinReg_HoraIni",HoraIni);
                     startActivity(ActividadNueva);
                 }
             }
        );
        btnGrabarParada.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v){
            HoraParIni =edtHoraIniPar.getText().toString();
                    HoraParFin =edtHoraFinPar.getText().toString();
                    if (HoraParIni.equals("--") ||  HoraParFin.equals("--"))
                    {
                        Toast.makeText(IngresoJabas_RegistroLinea.this,"Revisar la hora",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        tEfectivoPar = fnc.HoraEfectivaEntreHorasValidar(HoraParIni,HoraParFin);
                        if (tEfectivoPar>0)
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
                                            Cursor CurMotPar = (Cursor) spnMotivoParadas.getAdapter().getItem(spnMotivoParadas.getSelectedItemPosition());
                                            MotPar_Id= CurMotPar.getInt(CurMotPar.getColumnIndex(BaseColumns._ID));
                                            MotPar_Descripcion= CurMotPar.getString(CurMotPar.getColumnIndex(T_MotivoParada.MotDescripcion));
                                            try {
                                                LocBD.execSQL(T_LineaParadas.LineaParadas_Insertar(RegLin_Id,MotPar_Id,HoraParIni,HoraParFin,tEfectivoPar,0,fnc.HoraSistema(),MotPar_Descripcion,2,0));
                                                Toast.makeText(IngresoJabas_RegistroLinea.this,"Parada registrada correctamente",Toast.LENGTH_LONG).show();
                                                ActualizarResumen();
                                                edtHoraIniPar.setText(HoraNula);
                                                edtHoraFinPar.setText(HoraNula);
                                            }catch (SQLException e)
                                            {
                                                Toast.makeText(IngresoJabas_RegistroLinea.this,e.toString(),Toast.LENGTH_SHORT).show();
                                            }
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
            }
        );
        btnIniciar.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v){
            HoraIni=edtHoraIni.getText().toString();
            HoraFin=edtHoraFin.getText().toString();
            if (HoraIni.equals("--"))
            {
                Toast.makeText(IngresoJabas_RegistroLinea.this,"Revisar la hora",Toast.LENGTH_SHORT).show();
            }else {
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
                            Est_Id=1;
                            LocBD.execSQL(T_LineaRegistro.LineaRegistro_Insertar(Variables.Lin_Id,
                                    Variables.FechaStr,HoraIni,Variables.MAC,fnc.HoraSistema(),Est_Id,Variables.Usu_Id,Variables.Suc_Id,Variables.Cul_Id,HoraFin));
                            BloquearBotones(true);
                            Cursor Registro = LocBD.rawQuery(T_LineaRegistro.LineaRegistro_SeleccionarLinea(Variables.Lin_Id,Variables.FechaStr),null);
                            Registro.moveToFirst();
                            RegLin_Id=Registro.getInt(0);
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
            }
        );
        btnTerminar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v) {
            final double tEfectivo;
                NumIngresos = NumeroIngresos(RegLin_Id);
            if (edtHoraFin.getText().toString().equals(HoraNula))
            {
                Toast.makeText(IngresoJabas_RegistroLinea.this, "Error al asignar horas, verificar!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                tEfectivo = fnc.HoraEfectivaEntreHorasValidar(edtHoraIni.getText().toString(),
                        edtHoraFin.getText().toString());

                if (tEfectivo > 0) {
                    TerminarLinea();
                }
                else if (tEfectivo<0&&Variables.LinReg_NumIngresos>= NumIngresos)
                {
                    TerminarLinea();
                }
                else
                {
                    Toast.makeText(IngresoJabas_RegistroLinea.this, "Error al asignar horas, verificar!", Toast.LENGTH_LONG).show();
                }
            }
            }
        }
        );
        btnKardex.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v){
            Intent NuevaActividad = new Intent(IngresoJabas_RegistroLinea.this,IngresoJabas_Kardex.class);
            NuevaActividad.putExtra("RegLin_Id",RegLin_Id);
            startActivity(NuevaActividad);
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
        spnMotivoParadas.setEnabled(Estado);
    }

    private void TerminarLinea()
    {
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
                    Est_Id=2;
                    LocBD.execSQL(T_LineaRegistro.LineaRegistro_ActualizarTermino(RegLin_Id,HoraFin,Est_Id));
                    Toast.makeText(IngresoJabas_RegistroLinea.this, "Linea Terminada, hora: " + HoraFin, Toast.LENGTH_SHORT).show();

                    //ACTUALIZACIÓN HORA FIN INGRESOS
                    int Mix;
                    int LinIng_Id;
                    String HoraInicial;
                    double tEfectivo;
                    Cursor curIngresos= LocBD.rawQuery(T_LineaIngreso.LineaIngreso_SeleccionarIdCabecera(RegLin_Id),null);

                    curIngresos.moveToLast();
                    Mix = curIngresos.getInt(curIngresos.getColumnIndex(T_LineaIngreso.LinIngMix));
                    //OBTENER HORA DE REGISTRO
                    LinIng_Id = curIngresos.getInt(curIngresos.getColumnIndex(BaseColumns._ID));
                    HoraInicial = curIngresos.getString(curIngresos.getColumnIndex(T_LineaIngreso.LinIngHoraIni));
                    tEfectivo= fnc.HoraEfectivaEntreHoras24(HoraInicial,HoraFin);
                    if (Mix==0)
                    {
                        //Actualiza el ultimo registro
                        LocBD.execSQL(T_LineaIngreso.LineaIngreso_ActualizarHora(LinIng_Id,tEfectivo,HoraFin,0));
                    }else if (Mix==1)
                    {
                        LocBD.execSQL(T_LineaIngreso.LineaIngreso_ActualizarHora(LinIng_Id,tEfectivo,HoraFin,0));
                        //Obtiene el penultimo registro y Actualiza
                        curIngresos.moveToPrevious();
                        LinIng_Id = curIngresos.getInt(curIngresos.getColumnIndex(BaseColumns._ID));
                        HoraInicial = curIngresos.getString(curIngresos.getColumnIndex(T_LineaIngreso.LinIngHoraIni));
                        tEfectivo= fnc.HoraEfectivaEntreHoras24(HoraInicial,HoraFin);
                        LocBD.execSQL(T_LineaIngreso.LineaIngreso_ActualizarHora(LinIng_Id,tEfectivo,HoraFin,0));
                    }

                    BloquearBotones(false);
                    btnKardex.setEnabled(true);
                    imbHoraIni.setEnabled(false);
                    btnIniciar.setEnabled(false);
                    ActualizarResumen();
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
    }
    private void ActualizarResumen()
    {
        NumParadas=NumeroParadas(RegLin_Id);
        SumParadas=TiempoParadas(RegLin_Id);
        NumIngresos = NumeroIngresos(RegLin_Id);

        if(NumIngresos>0)
        {
            TotalTiempo = HoraTotal(RegLin_Id,Est_Id);
            TiempoEfectivo = TotalTiempo-SumParadas;
            CantidadEquivalente = CantidadEquivalente(RegLin_Id);
            CantidadPorHora = fnc.RedondeoDecimal((CantidadEquivalente/TiempoEfectivo),2,BigDecimal.ROUND_HALF_UP);

        }
        LocBD.execSQL(T_LineaRegistro.LineaRegistro_ActualizarResumen(RegLin_Id,NumParadas,SumParadas,TotalTiempo,TiempoEfectivo,
                CantidadEquivalente,CantidadPorHora, NumIngresos));

        lblNumParadas.setText(String.valueOf(NumParadas));
        lblTiempoParadas.setText(String.valueOf(SumParadas));
        lblTiempoTotal.setText(String.valueOf(TotalTiempo));
        lblHoraEfectiva.setText(String.valueOf(TiempoEfectivo));
        lblCantidad.setText(String.valueOf(CantidadEquivalente));
        lblCantidadPorHora.setText(String.valueOf(CantidadPorHora));
    }

    private double HoraTotal(int LinReg_Id,int EstId)
    {
        double hTotal=0;
        if (EstId==1)
        {
            hTotal= fnc.HoraEfectivaEntreHoras24(HoraIni,HoraIngresoJaba(LinReg_Id));
        }
        else if (EstId==2)
        {
            hTotal= fnc.HoraEfectivaEntreHoras24(HoraIni,HoraFin);
        }
        return hTotal;
    }

    private String HoraIngresoJaba(int LinReg_Id)
    {
        Cursor curIngresoHora= LocBD.rawQuery(T_LineaIngreso.LineaIngreso_SeleccionarIdCabecera(LinReg_Id),null);
        curIngresoHora.moveToLast();
        return curIngresoHora.getString(curIngresoHora.getColumnIndex(T_LineaIngreso.LinIngHoraIni));
    }

    private int NumeroIngresos(int LinReg_Id)
    {
        Cursor curCantidadIngreso= LocBD.rawQuery(T_LineaIngreso.CantidadPorId(LinReg_Id),null);
        curCantidadIngreso.moveToFirst();
        return curCantidadIngreso.getInt(0);
    }
    private double CantidadEquivalente(int LinReg_Id)
    {
        Cursor curCantEquiv = LocBD.rawQuery(T_LineaIngreso.EquivalenteResumen(LinReg_Id),null);
        curCantEquiv.moveToFirst();
        return curCantEquiv.getDouble(0);
    }
    private double TiempoParadas(int LinReg_Id)
    {
        Cursor curParadas = LocBD.rawQuery(T_LineaParadas.ResumenPorId(LinReg_Id),null);
        curParadas.moveToFirst();
        return fnc.RedondeoDecimal(curParadas.getDouble(1),2, BigDecimal.ROUND_HALF_UP);
    }
    private int NumeroParadas(int LinReg_Id)
    {
        Cursor curParadas = LocBD.rawQuery(T_LineaParadas.ResumenPorId(LinReg_Id),null);
        curParadas.moveToFirst();
        return curParadas.getInt(0);
    }


}
