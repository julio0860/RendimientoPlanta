package com.adr.rendimientoplanta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Consumidor;
import com.adr.rendimientoplanta.DATA.T_LineaIngreso;
import com.adr.rendimientoplanta.DATA.T_LineaParadas;
import com.adr.rendimientoplanta.DATA.T_LineaRegistro;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.math.BigDecimal;
import java.util.Calendar;

public class IngresoJabas_RegistroJabas extends AppCompatActivity {

    private Funciones fnc;
//
private String HoraIniLinea;

    //SMP: Variables para recuperar datos a insertar:
    private int EsMix;
    private String HoraIni;
    private int Con_Id;
    private String Con_DescripcionCor;
    private int Con_IdMix;
    private String Con_DescripcionCorMix;
    private int LinIng_Cantidad;
    private int LinIng_CantidadMix;

    private int CantidadLotes;

    private int MatPriOriId;
    private double MatPriOriFactor;
    private String MatPriOriDescripcion;
    private int MatPriOriIdMix;
    private double MatPriOriFactorMix;
    private String MatPriOriDescripcionMix;

    private double LinIngEquivalente;
    private double LinIngEquivalenteMix;

    private String HoraNula="--";

    //SMP:
    private EditText displayTime;

    //SMP: Variable obtenida de actividad anterior:
    private int RegLin_Id;

    //SMP: Declaración de variables estaticas para la elección de dialog
    static final int DATE_ID=0;
    static final int TIME_DIALOG_ID = 1;
    private int CantidadRegistros=0;


    //SMP: Variables tipo TextView
    private TextView lblSucursal;
    private TextView lblCultivo;
    private TextView lblFecha;
    private TextView lblEmpresa;
    private TextView lblLinea;

    private TextView lblNumIngresos;

    //SMP: Variables tipo ImageButton
    private ImageButton imbRegresar;
    private ImageButton imbHoraIni;

    //SMP: Variables tipo Button
    private Button btnAgregar;

    //SMP: Variables tipo CheckBox
    private CheckBox cbxMix;

    //SMP: Variables tipo EditText
    private EditText edtCantidadMix;
    private EditText edtHoraIni;
    private EditText edtCantidad;

    //SMP: Variables tipo Spinner
    private Spinner spnConsumidorMix;
    private Spinner spnConsumidor;

    //SMP: Variables tipo RadioGroup
    private RadioGroup rbnGrupoMix;

    //SMP: Variables tipo RadioButton
    private RadioButton rbnCampo;
    private RadioButton rbnReProceso;
    private RadioButton rbnCampoMix;
    private RadioButton rbnReProcesoMix;

    //SMP: Declaración variables para Dialog Time
    private int pHour;
    private int pMinute;

    //SMP: Variable tipo Base d datos
    LocalBD LBD;
    SQLiteDatabase LocBD;

    @Override
    public void onBackPressed()
    {
        // Your Code Here. Leave empty if you want nothing to happen on back press.
        Intent NuevaActividad = new Intent(IngresoJabas_RegistroJabas.this,IngresoJabas_RegistroLinea.class);
        startActivity(NuevaActividad);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_jabas_registro_jabas);

        //SMP: Iniciación base de datos local
        LBD = new LocalBD(IngresoJabas_RegistroJabas.this) ;
        LocBD = LBD.getWritableDatabase();

        //SMP: Asignación clase funciones
        fnc = new Funciones();

        //SMP: Obtención de Registro Linea Actividad anterior
        RegLin_Id = getIntent().getIntExtra("RegLin_Id",0);
        HoraIniLinea =getIntent().getStringExtra("LinReg_HoraIni");

        //SMP: Asignación de variables TextView a Layout
        lblCultivo = (TextView) findViewById(R.id.lblCultivo);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblLinea = (TextView) findViewById(R.id.lblLinea);
        lblNumIngresos = (TextView) findViewById(R.id.lblNumIngresos);

        lblFecha.setText(Variables.FechaStr);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblCultivo.setText(Variables.Cul_Descripcion);
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblLinea.setText(Variables.Lin_Descripcion);

        //SMP: Asignación de variables Button a Layout
        btnAgregar = (Button) findViewById(R.id.btnAgregar);

        //SMP: Asignación de variables ImageButton a Layout
        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);
        imbHoraIni = (ImageButton) findViewById(R.id.imbHoraIni);

        //SMP: Asignación de variables CheckBox a Layout
        cbxMix = (CheckBox) findViewById(R.id.cbxMix);

        //SMP: Asignación de variables EditText a Layout
        edtCantidad = (EditText) findViewById(R.id.edtCantidad);
        edtCantidadMix = (EditText) findViewById(R.id.edtCantidadMix);
        edtHoraIni=(EditText)findViewById(R.id.edtHoraIni);

        //SMP: Asignación Hora nula a editText
        edtHoraIni.setText(HoraNula);

        //SMP: Asignación de variables Spinner a Layout
        spnConsumidorMix = (Spinner) findViewById(R.id.spnConsumidorMix);
        spnConsumidor = (Spinner) findViewById(R.id.spnConsumidor);

        //SMP: Asignación de variables RadioGroup a Layout
        rbnGrupoMix = (RadioGroup) findViewById(R.id.rbnGrupoMix);

        //SMP: Asignación de varialbes RadioButton a Layout
        rbnCampo = (RadioButton) findViewById(R.id.rbnCampo);
        rbnReProceso = (RadioButton) findViewById(R.id.rbnReProceso);
        rbnCampoMix = (RadioButton)findViewById(R.id.rbnCampoMix);
        rbnReProcesoMix = (RadioButton)findViewById(R.id.rbnReProcesoMix);

        //SMP: Actualizar por defecto la hora
        final Calendar C = Calendar.getInstance();
        pHour = C.get(Calendar.HOUR_OF_DAY);
        pMinute = C.get(Calendar.MINUTE);

        //Asignación de carga inicial de Spinners
        Cursor Consumidor = LocBD.rawQuery(T_Consumidor._SELECT_CON(1,2, Variables.Cul_Id, Variables.Emp_Id),null);
        spnConsumidor.setAdapter(fnc.AdaptadorSpinnerSimpleLarge(this,Consumidor, T_Consumidor.CONDESCRIPCIONCOR));
        CantidadLotes=Consumidor.getCount()-1;


        Cursor ConsumidorMix = LocBD.rawQuery(T_Consumidor._SELECT_CON(1,2, Variables.Cul_Id, Variables.Emp_Id),null);
        spnConsumidorMix.setAdapter(fnc.AdaptadorSpinnerSimpleLarge(this,ConsumidorMix, T_Consumidor.CONDESCRIPCIONCOR));
        spnConsumidorMix.setSelection(CantidadLotes);
        //spnConsumidorMix.setSelection(Consumidor.getCount());
        //Asignación valores iniciales lblNumeroDeIngresos:
        CantidadRegistros=CantidadReg(RegLin_Id);
        lblNumIngresos.setText(String.valueOf(CantidadRegistros));

        //Asignación de Eventos y Acciones a los componentes
        btnAgregar.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v){
            boolean ValidacionHora = false;
            boolean Validacion;
            boolean ValidacionMix;
            double DiferenciaHora =0;
            String TextCantidad;
            String TextCantidadMix;

            TextCantidad =edtCantidad.getText().toString();
            TextCantidadMix = edtCantidadMix.getText().toString();
            HoraIni=edtHoraIni.getText().toString();

            if (HoraIni.equals(HoraNula))
            {
                ValidacionHora=false;
            }else
            {
                DiferenciaHora=fnc.HoraEfectivaEntreHorasValidar(HoraIniLinea,HoraIni);
                if (DiferenciaHora>=0)
                {
                    ValidacionHora = true;
                }else if(DiferenciaHora<0&&CantidadRegistros>=Variables.LinReg_RegistrosMinimos)
                {
                    ValidacionHora = true;
                }else
                {
                    ValidacionHora=false;
                }
            }
            if(TextCantidad.length () == 0 || HoraIni.equals("--"))
            {
                Validacion = false;
            }else {

                Validacion = true;
                LinIng_Cantidad =  Integer.parseInt(TextCantidad);
            }
            if(TextCantidadMix.length()==0 || HoraIni.equals("--"))
            {
                ValidacionMix = false;
            }else {
                DiferenciaHora=fnc.HoraEfectivaEntreHorasValidar(HoraIniLinea,HoraIni);
                ValidacionMix = true;
                LinIng_CantidadMix = Integer.parseInt(TextCantidadMix);
            }

            if (rbnCampo.isChecked()==true)
            {
                MatPriOriId = 1;
                MatPriOriFactor = 1;
                MatPriOriDescripcion ="CAMPO";
            }else {
                MatPriOriId = 2;
                MatPriOriFactor = 0.5;
                MatPriOriDescripcion ="RE-PROCESO";
            }
            if (rbnCampoMix.isChecked()==true)
            {
                MatPriOriIdMix = 1;
                MatPriOriFactorMix = 1;
                MatPriOriDescripcionMix ="CAMPO";
            }else {
                MatPriOriIdMix = 2;
                MatPriOriFactorMix = 0.5;
                MatPriOriDescripcionMix ="RE-PROCESO";
            }

            if(ValidacionHora==false)
            {
                Toast.makeText(IngresoJabas_RegistroJabas.this,"VERIFIQUE LA HORA DE INCIO",Toast.LENGTH_LONG).show();
                edtHoraIni.setText(HoraNula);
            }
            else
            {
                Cursor CurConsumidor = (Cursor) spnConsumidor.getAdapter().getItem(spnConsumidor.getSelectedItemPosition());
                Con_Id= CurConsumidor.getInt(CurConsumidor.getColumnIndex(BaseColumns._ID));
                Con_DescripcionCor= CurConsumidor.getString(CurConsumidor.getColumnIndex(T_LineaIngreso.ConDescripcionCor));

                Cursor CurConsumidorMix = (Cursor) spnConsumidorMix.getAdapter().getItem(spnConsumidorMix.getSelectedItemPosition());
                Con_IdMix= CurConsumidorMix.getInt(CurConsumidorMix.getColumnIndex(BaseColumns._ID));
                Con_DescripcionCorMix= CurConsumidorMix.getString(CurConsumidorMix.getColumnIndex(T_LineaIngreso.ConDescripcionCor));


                if (EsMix==0)
                {
                    if(Validacion == false) {
                        Toast.makeText(IngresoJabas_RegistroJabas.this,"Verifique los datos",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IngresoJabas_RegistroJabas.this);
                        String alert_title = "Registrar Ingreso";
                        String alert_description = "¿Estas seguro que quiere insertar el siguiente registro | Hora: "+HoraIni+ " | Jabas: "
                                +String.valueOf(LinIng_Cantidad) +" | Lote: "+Con_DescripcionCor+ " | Tipo: "+MatPriOriDescripcion+" ?";
                        alertDialogBuilder.setTitle(alert_title);
                        // set dialog message
                        alertDialogBuilder
                            .setMessage(alert_description)
                            .setCancelable(false)
                            .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                                // Lo que sucede si se pulsa yes
                                public void onClick(DialogInterface dialog,int id) {
                                    // Código propio del método calculo de diferencia de horas
                                    //try {
                                    double Equivalente = LinIng_Cantidad * MatPriOriFactor;
                                    ActualizarHoraFin(HoraIni);
                                    LocBD.execSQL(T_LineaIngreso.LineaIngreso_Insertar(
                                            RegLin_Id,Con_Id,Con_DescripcionCor,
                                            LinIng_Cantidad,MatPriOriId,MatPriOriDescripcion,
                                            MatPriOriFactor,Equivalente,HoraIni,HoraNula,0,
                                            EsMix,fnc.HoraSistema(),-1,2,0));
                                    Toast.makeText(IngresoJabas_RegistroJabas.this,"Ingreso registrado correctamente",Toast.LENGTH_LONG).show();
                                    edtHoraIni.setText(HoraNula);
                                    CantidadRegistros=CantidadReg(RegLin_Id);
                                    lblNumIngresos.setText(String.valueOf(CantidadRegistros));
                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // Si se pulsa no no hace nada
                                    Toast.makeText(IngresoJabas_RegistroJabas.this,"Operación cancelada",Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
                    }
                }
                else
                {
                    //SMP: Validación para el ingreso de jabas mix
                    if(Validacion ==false && ValidacionMix == false) {
                        Toast.makeText(IngresoJabas_RegistroJabas.this,"Verifique los datos",Toast.LENGTH_SHORT).show();
                    }else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IngresoJabas_RegistroJabas.this);
                        String alert_title = "Registrar Ingreso";
                        String alert_description = "¿Estas seguro que quiere insertar el siguiente registro | Hora: "+HoraIni+ "| Jabas: "
                                +TextCantidad +" | Lote: "+Con_DescripcionCor+ " | Tipo: "+MatPriOriDescripcion+" | JabasMix: "+TextCantidadMix+" | LoteMix: "
                                +Con_DescripcionCorMix+ "| Tipo: "+MatPriOriDescripcionMix+" ?";
                        alertDialogBuilder.setTitle(alert_title);
                        // set dialog message
                        alertDialogBuilder
                                .setMessage(alert_description)
                                .setCancelable(false)
                                .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                                    // Lo que sucede si se pulsa yes
                                    public void onClick(DialogInterface dialog,int id) {
                                        // Código propio del método calculo de diferencia de horas
                                        try {
                                            double Equivalente = LinIng_Cantidad * MatPriOriFactor;
                                            double EquivalenteMix = LinIng_CantidadMix * MatPriOriFactorMix;
                                            ActualizarHoraFin(HoraIni);
                                            LocBD.execSQL(T_LineaIngreso.LineaIngreso_Insertar(
                                                    RegLin_Id,Con_Id,Con_DescripcionCor,
                                                    LinIng_Cantidad,MatPriOriId,MatPriOriDescripcion,
                                                    MatPriOriFactor,Equivalente,HoraIni,HoraNula,0,
                                                    EsMix,fnc.HoraSistema(),-1,2,0));

                                            LocBD.execSQL(T_LineaIngreso.LineaIngreso_Insertar(
                                                    RegLin_Id,Con_IdMix,Con_DescripcionCorMix,
                                                    LinIng_CantidadMix,MatPriOriIdMix,MatPriOriDescripcionMix,
                                                    MatPriOriFactorMix,EquivalenteMix,HoraIni,HoraNula,0,
                                                    EsMix,fnc.HoraSistema(),-1,2,0));

                                            Toast.makeText(IngresoJabas_RegistroJabas.this,"Ingreso registrado correctamente",Toast.LENGTH_LONG).show();
                                            edtHoraIni.setText(HoraNula);
                                            CantidadRegistros=CantidadReg(RegLin_Id);
                                            lblNumIngresos.setText(String.valueOf(CantidadRegistros));
                                        }catch (SQLException e)
                                        {
                                            Toast.makeText(IngresoJabas_RegistroJabas.this,e.toString(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // Si se pulsa no no hace nada
                                        Toast.makeText(IngresoJabas_RegistroJabas.this,"Operación cancelada",Toast.LENGTH_LONG).show();
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
                    }
                }
            }}
            }
        );
        spnConsumidor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int Posicion1;
                int Posicion2;
                Posicion1 =spnConsumidor.getSelectedItemPosition();
                Posicion2 =spnConsumidorMix.getSelectedItemPosition();

                if (Posicion1==Posicion2)
                {

                    if (Posicion2!=CantidadLotes) {
                        spnConsumidor.setSelection(CantidadLotes);
                    }else
                    {
                        spnConsumidor.setSelection(0);
                    }
                    if(cbxMix.isChecked())
                    {
                        Toast.makeText(IngresoJabas_RegistroJabas.this,"NO SE PUEDE REPETIR EL LOTE",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //TODO Auto-generated method stub
            }
        });
        spnConsumidorMix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int Posicion1;
                int Posicion2;
                Posicion1 =spnConsumidorMix.getSelectedItemPosition();
                Posicion2 =spnConsumidor.getSelectedItemPosition();
                if (Posicion1==Posicion2)
                {
                    Toast.makeText(IngresoJabas_RegistroJabas.this,"NO SE PUEDE REPETIR EL LOTE",Toast.LENGTH_LONG).show();
                    if (Posicion2!=CantidadLotes) {
                        spnConsumidorMix.setSelection(CantidadLotes);
                    }else
                    {
                        spnConsumidorMix.setSelection(0);
                    }
                    if(cbxMix.isChecked())
                    {
                        Toast.makeText(IngresoJabas_RegistroJabas.this,"NO SE PUEDE REPETIR EL LOTE",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //TODO Auto-generated method stub
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
        imbRegresar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                //TextView HORA inicio
                //lblHoraInicio.setText(Formato.format(new Date(System.currentTimeMillis())));
                Intent NuevaActividad = new Intent(IngresoJabas_RegistroJabas.this,IngresoJabas_RegistroLinea.class);
                startActivity(NuevaActividad);
            }
        });
        cbxMix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                VisibilidadConsumidor(isChecked);
                if (isChecked==true)
                {
                    EsMix=1;
                }else{
                    EsMix=0;
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
                        edtCantidadMix.setText(String.valueOf(edtCantidad.getText().toString()));
                    else
                        edtCantidadMix.setText("0");
                }
            }
        });
    }
    private void VisibilidadConsumidor(Boolean Est)
    {
        if (Est==true) {
            spnConsumidorMix.setVisibility(View.VISIBLE);
            edtCantidadMix.setVisibility(View.VISIBLE);
            rbnGrupoMix.setVisibility(View.VISIBLE);
        }else
        {
            spnConsumidorMix.setVisibility(View.INVISIBLE);
            edtCantidadMix.setVisibility(View.INVISIBLE);
            rbnGrupoMix.setVisibility(View.INVISIBLE);

        }
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
    private int CantidadReg(int LinReg_Id)
    {
        Cursor curCantidadIngreso= LocBD.rawQuery(T_LineaIngreso.CantidadPorId(LinReg_Id),null);
        curCantidadIngreso.moveToFirst();
        return curCantidadIngreso.getInt(0);
    }
    private double CantidadEquiv(int LinReg_Id)
    {
        Cursor curCantEquiv = LocBD.rawQuery(T_LineaIngreso.EquivalentePorId(LinReg_Id),null);
        curCantEquiv.moveToFirst();
        return curCantEquiv.getDouble(0);
    }
    private double Paradas(int LinReg_Id)
    {
        Cursor curParadas = LocBD.rawQuery(T_LineaParadas.ResumenPorId(LinReg_Id),null);
        curParadas.moveToFirst();
        return curParadas.getInt(1);
    }

    private void ActualizarHoraFin(String HoraFin)
    {

        int Cantidad;
        int CantidadMix;
        int Mix;
        int LinIng_Id;
        String HoraInicial;
        double tEfectivo;

        //Variables para actualizar tabla principal
        double CantEquiv;
        double HoraEf;
        double HoraTotal;
        double paradas;
        double CantHora;


        Cursor curIngresos= LocBD.rawQuery(T_LineaIngreso.LineaIngreso_SeleccionarIdCabecera(RegLin_Id),null);
        Cantidad = CantidadReg(RegLin_Id);
        //Si hay mas de un registro actualiza con la hora del nuevo registro insertado
        if (Cantidad>0)
        {
            //PARA ACTUALIZAR TABLA PRINCIPAL UBICACIÓN TEMPORAL
            CantEquiv=CantidadEquiv(RegLin_Id);
            HoraTotal = fnc.HoraEfectivaEntreHoras24(HoraIniLinea,HoraIni);
            paradas =Paradas(RegLin_Id);
            HoraEf = HoraTotal-paradas;
            CantHora = fnc.RedondeoDecimal((CantEquiv/HoraEf),2, BigDecimal.ROUND_HALF_UP);
            CantidadRegistros =CantidadReg(RegLin_Id);
            if (cbxMix.isChecked())
            {
                CantidadRegistros=CantidadRegistros+2;
            }
            else
            {
                CantidadRegistros=CantidadRegistros+1;
            }
            LocBD.execSQL(T_LineaRegistro.LineaRegistro_ActualizarIngreso(RegLin_Id,CantEquiv,HoraEf,CantHora,HoraTotal,CantidadRegistros));
            //Fin actualizar tabla principal
            //---------------------------------------------------------------------

            curIngresos.moveToLast();

            Mix = curIngresos.getInt(curIngresos.getColumnIndex(T_LineaIngreso.LinIngMix));
            if (Mix==0)
            {
                //Actualiza el ultimo registro
                LinIng_Id = curIngresos.getInt(curIngresos.getColumnIndex(BaseColumns._ID));
                HoraInicial = curIngresos.getString(curIngresos.getColumnIndex(T_LineaIngreso.LinIngHoraIni));
                tEfectivo= fnc.HoraEfectivaEntreHoras24(HoraInicial,HoraFin);
                LocBD.execSQL(T_LineaIngreso.LineaIngreso_ActualizarHora(LinIng_Id,tEfectivo,HoraFin,0));
            }else if (Mix==1)
            {
                //SMP: Si es Mix
                //Obtiene el penultimo registro y Actualiza
                //CantidadMix = Cantidad-2;
                //curIngresos.move(CantidadMix);
                curIngresos.moveToLast();
                LinIng_Id = curIngresos.getInt(curIngresos.getColumnIndex(BaseColumns._ID));
                HoraInicial = curIngresos.getString(curIngresos.getColumnIndex(T_LineaIngreso.LinIngHoraIni));
                tEfectivo= fnc.HoraEfectivaEntreHoras24(HoraInicial,HoraFin);
                LocBD.execSQL(T_LineaIngreso.LineaIngreso_ActualizarHora(LinIng_Id,tEfectivo,HoraFin,0));

                //Obtiene el ultimo registro y Actualiza
                curIngresos.moveToPrevious();
                LinIng_Id = curIngresos.getInt(curIngresos.getColumnIndex(BaseColumns._ID));
                HoraInicial = curIngresos.getString(curIngresos.getColumnIndex(T_LineaIngreso.LinIngHoraIni));
                tEfectivo= fnc.HoraEfectivaEntreHoras24(HoraInicial,HoraFin);
                LocBD.execSQL(T_LineaIngreso.LineaIngreso_ActualizarHora(LinIng_Id,tEfectivo,HoraFin,0));

            }
        }
    }
}
