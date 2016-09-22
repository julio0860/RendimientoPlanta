package com.adr.rendimientoplanta;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Consumidor;
import com.adr.rendimientoplanta.DATA.T_IngresoJabas;
import com.adr.rendimientoplanta.DATA.T_Linea;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class IngresoJabas_Registro extends AppCompatActivity {

    private int mMes,mAño,mDia,sDia,sMes,sAño;
    private Date mFecha;
    private Date sFecha;

    //PARA SCROLL HORA
    private EditText displayTime;
    //private Button pickTime;

    private int pHour;
    private int pMinute;
    //
    private Funciones fnc;

    private ImageButton imbScrollInicio;
    private ImageButton imbScrollFinal;
    private ImageButton imbRegresar;

    //private EditText txtDia;
    //private EditText txtMes;
    //private EditText txtAño;
    private EditText txtFecha;
    private EditText txtParadas;
    private EditText txtCantidad;

    private ImageButton btnBuscarFecha;
    private Button btnGuardar;

    private RadioButton rbCampo;
    private RadioButton rbReProceso;

    private ImageButton btnInicio;
    private ImageButton btnFinal;
    private Button btnTerminar;

    private CheckBox cbxEsMix;
    private EditText lblHoraInicio;
    private EditText lblHoraFin;
    private EditText edtFecha;


    private TextView lblPlanta;
    //private Spinner spnPlanta;
    private Spinner spnLinea;
    private Spinner spnLote;
    static final int DATE_ID=0;

    private SimpleDateFormat df;

    //VARIABLES DE GUARDADO, LECTURA
    private String Fecha="";
    private String HoraIni="";
    private String HoraFin="";
    private int Paradas = 0;
    private String Planta="";
    private String Linea="";
    private String Lote="";
    private int Jabas=0;
    private int Campo=0;
    private int RePro=0;
    private int Estado =0;
    private int Sync=0;
    private String FechaReg ="";
    //CAMPOS ADICIONALES
    private int Suc_Id=0;
    private int Lin_Id=0;
    private int Con_Id=0;
    private int Con_IdMix=0;
    private String LoteMix="";
    private int JabasLote=0;
    private int JabasMix=0;
    private int EsMix=0;

    private Date FechaHoy = new Date();

    //DATOS DE INTENT
    private boolean EsNuevo = true;

    private TextView lblConsumidor2;
    private TextView lblCantidad2;
    private EditText txtCantidadMix;
    private Spinner spnConsumidorMix;

    //FIN DATOS INTENT
    SimpleDateFormat Formato = new SimpleDateFormat("HH:mm:ss",java.util.Locale.getDefault());
    SimpleDateFormat FormatoFecha = new SimpleDateFormat ("yyyy-mm-dd",java.util.Locale.getDefault());
    private static final String TAG = "IngresoJabas_Registro";

    //DECLARACION ADAPTERS
    //ArrayAdapter<String> adspnPlanta;
    //SimpleCursorAdapter adspnPlanta;
    SimpleCursorAdapter adspnLinea;
    SimpleCursorAdapter adspnLote;
    SimpleCursorAdapter adspnLoteMix;
    //DECLARACION BASE DE DATOS
    LocalBD LBD;
    SQLiteDatabase LocBD;
    //DECLARACION SPINNERS
    static final int TIME_DIALOG_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_jabas_registro);

        //ACCESO A BASE DE DATOS LOCAL
        LBD = new LocalBD(IngresoJabas_Registro.this) ;
        LocBD = LBD.getWritableDatabase();
        //FIN ACCESO BDLOCAL

        fnc = new Funciones();
        //HORA----------->
        imbScrollInicio = (ImageButton) findViewById(R.id.imbHoraIni);
        imbScrollFinal = (ImageButton) findViewById(R.id.imbScrollFinal);
        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);
        //ASIGNACION DE CONTROLES DE EDIT TEXT A VARIABLES EQUIVALENTES
        //txtDia = (EditText) findViewById(R.id.txtDia);
        //txtMes = (EditText) findViewById(R.id.txtMes);
        //txtAño = (EditText) findViewById(R.id.txtAño);
        txtFecha = (EditText) findViewById(R.id.edtFecha);
        txtParadas = (EditText) findViewById(R.id.txtParadas);
        txtCantidad = (EditText) findViewById(R.id.txtCantidad);

        lblHoraInicio = (EditText) findViewById(R.id.lblNumIngresos);
        lblHoraFin = (EditText) findViewById(R.id.lblHoraFin);
        //FIN ASIGNACION EDITTEXT
        lblPlanta = (TextView) findViewById(R.id.lblSucursal);
        lblPlanta.setText(Variables.Suc_Descripcion);
        //ASIGNACION DE BOTONES A VARIABLES EQUIVALENTES
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnInicio = (ImageButton) findViewById(R.id.btnInicio);
        btnFinal = (ImageButton) findViewById(R.id.btnFinal);
        btnBuscarFecha = (ImageButton) findViewById(R.id.btnBuscarFecha) ;
        btnTerminar = (Button) findViewById(R.id.btnTerminar);
        //FIN BOTONES VARIABLES EQUIVALENTES

        //ASIGNACION DE RADIO BUTTON A VARIABLES RESPECTIVAS
        rbCampo = (RadioButton) findViewById(R.id.rbCampo);
        rbReProceso = (RadioButton) findViewById(R.id.rbReProceso);

        cbxEsMix = (CheckBox) findViewById(R.id.cbxEsMix);
        lblCantidad2 = (TextView) findViewById(R.id.lblCantidad2);
        lblConsumidor2 = (TextView) findViewById(R.id.lblConsumidor2);
        txtCantidadMix = (EditText) findViewById(R.id.txtCantidadMix);
        spnConsumidorMix = (Spinner) findViewById(R.id.spnConsumidorMix);

        //FIN ASIGNACION DE RADIO BUTTON A VARIABLES RESPECTIVAS

        //ASIGNACION DE SPINNERS A VARIABLES RESPECTIVAS E INCLUSION DEL ADAPTADOR
        //SPN PLANTA

       // spnPlanta = (Spinner) findViewById(R.id.spnPlanta);
        //ArrayAdapter<String> adspnPlanta = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Plantas);
//        adspnPlanta = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Plantas);
//        spnPlanta.setAdapter(adspnPlanta);

        //Cursor Plantas = LocBD.rawQuery(T_Sucursal._SELECT_SUC(1,2),null);
        /*
        adspnPlanta = new SimpleCursorAdapter(this ,
                android.R.layout.simple_spinner_item,Plantas,//Layout simple
                //Todos los registros
                new String[]{T_Sucursal.SUCDESCRIPCION},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spnPlanta.setAdapter(adspnPlanta);
*/

        //SPN LINEA
       spnLinea = (Spinner) findViewById(R.id.SpnLinea);
       // adspnLinea = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Lineas);
       // spnLinea.setAdapter(adspnLinea);
        //NUEVO

        //SPNLOTE
        Cursor Lineas = LocBD.rawQuery(T_Linea._SELECT_LIN(Variables.Suc_Id,2),null);
        adspnLinea = new SimpleCursorAdapter(IngresoJabas_Registro.this ,
                android.R.layout.simple_spinner_dropdown_item ,Lineas,//Layout simple
                //Todos los registros
                new String[]{T_Linea.LINDESCRIPCION},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spnLinea.setAdapter(adspnLinea);
        if (Variables.Lin_Id!=0)
        {
            fnc.setIndexInt(spnLinea,BaseColumns._ID, Variables.Lin_Id);
        }


        spnLote = (Spinner) findViewById(R.id.SpnLote);
        //adspnLote = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Lotes);
        //spnLote.setAdapter(adspnLote);

        Cursor Consumidor = LocBD.rawQuery(T_Consumidor._SELECT_CON(1,2, Variables.Cul_Id, Variables.Emp_Id),null);
        adspnLote = new SimpleCursorAdapter(this ,
                android.R.layout.simple_spinner_dropdown_item,Consumidor,//Layout simple
                //Todos los registros
                new String[]{T_Consumidor.CONDESCRIPCIONCOR},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        spnLote.setAdapter(adspnLote);
        //FIN ASIGNACION SPINNERS

        EsNuevo = getIntent().getBooleanExtra("NUEVO",true);
        //PROPIEDADES INICIALES DE CONTROLES
        //EDIT TEXT
        //txtDia.setEnabled(false);
        //txtMes.setEnabled(false);
        //txtAño.setEnabled(false);

        //RADIOBUTTON

        //FIN PROPIEDADES


        //VARIABLES CONTROL FECHA
        final Calendar C = Calendar.getInstance();
        sAño = C.get(Calendar.YEAR);
        sMes = C.get(Calendar.MONTH);
        sDia = C.get(Calendar.DAY_OF_MONTH);

        //ASIGNACION DIA HOY A EDIT TEXT
        //txtDia.setText(new StringBuilder().append(sDia));
        //txtMes.setText(new StringBuilder().append(sMes + 1));
        //txtAño.setText(new StringBuilder().append(sAño));

        //Asignar Fecha a texto
        //txtFecha.setText(FormatoFecha.format(new Date()));
        //txtFecha.setText(new StringBuilder().append(sDia)+"/"+new StringBuilder().append(sMes+1)+"/"+new StringBuilder().append(sAño));

        //Fin Control fecha
        //Asignación de Eventos

        //PARA CREAR UN DIALOGO DE CONFIRMACIÓN

        if (EsNuevo ==true)
        {
            NuevoFormulario();
        }else
        {
            CargarFormulario();
        }

        cbxEsMix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                    VisibilidadConsumidor(isChecked);
                    if (isChecked==true)
                    {
                        CargarLoteMix();
                    }
            }
        });

        btnBuscarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //LLama al Dialog
                //edtFecha = txtFecha;
                //showDialog(DATE_ID);

                edtFecha = txtFecha;
                showDialog(fnc.DATE_ID);
            }
        }
        );
        imbRegresar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                //TextView HORA inicio
                //lblHoraInicio.setText(Formato.format(new Date(System.currentTimeMillis())));
                Intent NuevaActividad = new Intent(IngresoJabas_Registro.this,IngresoJabas_Lista.class);
                startActivity(NuevaActividad);
            }
        });
        imbScrollInicio.setOnClickListener(new View.OnClickListener()
       {
           @Override
            public void onClick (View v){
               //TextView HORA inicio
               //lblHoraInicio.setText(Formato.format(new Date(System.currentTimeMillis())));
               //displayTime = lblHoraInicio;
               //showDialog(TIME_DIALOG_ID);

               displayTime = lblHoraInicio;
               showDialog(fnc.TIME_DIALOG_ID);
               }
       });
        imbScrollFinal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                //TextView HORA inicio
                //lblHoraInicio.setText(Formato.format(new Date(System.currentTimeMillis())));

                //displayTime = lblHoraFin;
                //showDialog(TIME_DIALOG_ID);

                displayTime = lblHoraFin;
                showDialog(TIME_DIALOG_ID);
            }
        });
        btnInicio.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v){
                    //TextView HORA inicio
                    lblHoraInicio.setText(Formato.format(new Date(System.currentTimeMillis())));
                }
            }
        );
        btnFinal.setOnClickListener(new View.OnClickListener()
             {
                 @Override
                 public void onClick (View v){
                     //TextView HORA Final
                     lblHoraFin.setText(Formato.format(new Date(System.currentTimeMillis())));
                 }
             }
        );
        btnGuardar.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick (View v){
                    //REGISTRO DE DATOS
                    Estado =1;
                    RegistroIngreso();

                }
            }
        );
        btnTerminar.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick (View v){
                 //TextView HORA inicio
                 //spnLote.setSelection(0);
                 if (getIntent().getIntExtra("ESTADO",1)==1)
                 {
                     Estado =2;
                 }
                 else
                 {
                     Estado=1;
                 }

                 RegistroIngreso();
             }
         }
        );

/*        spnPlanta.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        //if (spnPlanta.getSelectedItem().toString()=="PLANTA DON CARLOS")
                        //{
                        //    adspnLinea = new ArrayAdapter<String>(IngresoJabas_Registro.this,android.R.layout.simple_dropdown_item_1line,LineasPDC);
                        //    spnLinea.setAdapter(adspnLinea);
                        //}else if (spnPlanta.getSelectedItem().toString() =="PLANTA YANCAY")
                        //{
                        //    adspnLinea = new ArrayAdapter<String>(IngresoJabas_Registro.this,android.R.layout.simple_dropdown_item_1line,LineasPYA);
                        //    spnLinea.setAdapter(adspnLinea);
                        //}
                        Cursor CurId = (Cursor) parent.getItemAtPosition(position);
                        Integer Suc_Id = CurId.getInt(CurId.getColumnIndex(BaseColumns._ID));

                        Toast.makeText(IngresoJabas_Registro.this, Suc_Id.toString(),Toast.LENGTH_LONG).show();
                        Cursor Lineas = LocBD.rawQuery(T_Linea._SELECT_LIN(Suc_Id,2),null);
                        adspnLinea = new SimpleCursorAdapter(IngresoJabas_Registro.this ,
                                android.R.layout.simple_spinner_item,Lineas,//Layout simple
                                //Todos los registros
                                new String[]{T_Linea.LINDESCRIPCION},//Mostrar solo el nombre
                                new int[]{android.R.id.text1}//View para el nombre
                        ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                        spnLinea.setAdapter(adspnLinea);

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                */
    }
    private void VisibilidadConsumidor(Boolean Est)
    {
        if (Est==true) {
            spnConsumidorMix.setVisibility(View.VISIBLE);
            lblConsumidor2.setVisibility(View.VISIBLE);
            lblCantidad2.setVisibility(View.VISIBLE);
            txtCantidadMix.setVisibility(View.VISIBLE);
        }else
        {
            spnConsumidorMix.setVisibility(View.INVISIBLE);
            lblConsumidor2.setVisibility(View.INVISIBLE);
            lblCantidad2.setVisibility(View.INVISIBLE);
            txtCantidadMix.setVisibility(View.INVISIBLE);
        }
    }
    private void RegistroIngreso()
    {
        try
        {
            Calendar Cal = new GregorianCalendar();
            Fecha = txtFecha.getText().toString();
            HoraIni = lblHoraInicio.getText().toString();
            HoraFin = lblHoraFin.getText().toString();
            Paradas = Integer.parseInt(txtParadas.getText().toString());
            Planta = Variables.Suc_Descripcion;
            //Linea = spnLinea.getSelectedItem().toString();

            Cursor CurLinea = (Cursor) spnLinea.getAdapter().getItem(spnLinea.getSelectedItemPosition());
            //Variables.Suc_Id = CurSucursal.getInt(CurSucursal.getColumnIndex(BaseColumns._ID));
            Linea= CurLinea.getString(CurLinea.getColumnIndex(T_Linea.LINDESCRIPCION));

            Cursor CurLote = (Cursor) spnLote.getAdapter().getItem(spnLote.getSelectedItemPosition());
            Lote= CurLote.getString(CurLote.getColumnIndex(T_Consumidor.CONDESCRIPCIONCOR));
            Variables.Con_Id =CurLote.getInt(CurLote.getColumnIndex(BaseColumns._ID));
            //Lote = spnLote.getSelectedItem().toString();
            JabasLote = Integer.parseInt(txtCantidad.getText().toString());
            if (rbCampo.isChecked() == true) Campo= 1;else Campo=0;
            if (rbReProceso.isChecked() == true) RePro =1; else RePro=0;
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            FechaReg =df.format(Cal.getInstance().getTime());

            //Toast.makeText(IngresoJabas_Registro.this, FechaReg,Toast.LENGTH_LONG).show();

            //CAMPOS ADICIONALES
            Suc_Id= Variables.Suc_Id;
            Lin_Id=CurLinea.getInt(CurLinea.getColumnIndex(BaseColumns._ID));
            if (Estado==2)
            {
                Variables.Lin_Id=Lin_Id;
                Variables.UltimaHoraFinal=HoraFin;
            }
            Con_Id =CurLote.getInt(CurLote.getColumnIndex(BaseColumns._ID));

            if (cbxEsMix.isChecked())
            {
                Cursor CurLoteMix = (Cursor) spnConsumidorMix.getAdapter().getItem(spnConsumidorMix.getSelectedItemPosition());
                Con_IdMix=CurLoteMix.getInt(CurLoteMix.getColumnIndex(BaseColumns._ID));
                LoteMix= CurLoteMix.getString(CurLoteMix.getColumnIndex(T_Consumidor.CONDESCRIPCIONCOR));
                JabasMix=Integer.parseInt(txtCantidadMix.getText().toString());
                EsMix=1;
            }else{
                Con_IdMix=0;
                LoteMix="";
                JabasMix=0;
                EsMix=0;
            }
            Jabas=JabasLote+JabasMix;

            Sync =0;
            if (EsNuevo==true)
            {

                Estado =1;
                LocBD.execSQL(T_IngresoJabas._INSERT(Fecha,HoraIni,HoraFin,Paradas,Planta,
                        Linea,Lote,Jabas,Campo,RePro,Estado,Sync,FechaReg,
                        Suc_Id,Lin_Id,Con_Id,Con_IdMix,LoteMix,JabasLote,JabasMix,EsMix, Variables.Usu_Id
                        ));

                Toast.makeText(IngresoJabas_Registro.this, "REGISTRO GUARDADO CORRECTAMENTE ",Toast.LENGTH_LONG).show();
            }else
            {
                Sync =getIntent().getIntExtra("SYNC",0);
                LocBD.execSQL(T_IngresoJabas._UPDATE(getIntent().getIntExtra("ID",0),Fecha,HoraIni,HoraFin,Paradas,Planta,
                        Linea,Lote,Jabas,Campo,RePro,Estado,Sync,
                        Suc_Id,Lin_Id,Con_Id,Con_IdMix,LoteMix,JabasLote,JabasMix,EsMix, Variables.Usu_Id
                ));
                Toast.makeText(IngresoJabas_Registro.this, "REGISTRO ACTUALIZADO CORRECTAMENTE ",Toast.LENGTH_LONG).show();
            }
            if (Estado ==2)
            {
                Intent ActividadNueva = new Intent(IngresoJabas_Registro.this, IngresoJabas_Registro.class);
                //Se inicia la actividad nueva
                startActivity(ActividadNueva);
            }else
            {
                Intent ActividadNueva = new Intent(IngresoJabas_Registro.this, IngresoJabas_Lista.class);
                //Se inicia la actividad nueva
                startActivity(ActividadNueva);
            }

        }
        catch (Exception e)
        {
            Log.e(TAG, "Error Exception: " + e);
        }
    }
    private void CargarFormulario()
    {
        Bundle Bnd = getIntent().getExtras();
        //Toast.makeText(IngresoJabas_Registro.this, "Es campo? ",Toast.LENGTH_LONG).show();
        //Toast.makeText(IngresoJabas_Registro.this, "Es rerpo? "+Bnd.getInt("ESREPRO"),Toast.LENGTH_LONG).show();
        if(Bnd.getInt("ESCAMPO")==1)
        {
            rbCampo.setChecked(true);
        }
        else if (Bnd.getInt("ESREPRO")==1)
        {
            rbReProceso.setChecked(true);
        }
        txtFecha.setText(Bnd.getString("FECHA"));
        txtParadas.setText(Bnd.getString("PARADAS"));
        txtCantidad.setText(Bnd.getString("JABAS"));
        lblHoraInicio.setText(Bnd.getString("HORAINI"));
        if (Bnd.getString("HORAFIN").equals("00:00:00"))
        {
            lblHoraFin.setText(Formato.format(new Date(System.currentTimeMillis())));
        }else
        {
            lblHoraFin.setText(Bnd.getString("HORAFIN"));
        }

        fnc.setIndexStr(spnLote,T_Consumidor.CONDESCRIPCIONCOR,Bnd.getString("LOTE"));
        fnc.setIndexStr(spnLinea,T_Linea.LINDESCRIPCION,Bnd.getString("LINEA"));

        if (Bnd.getInt("ESMIX")==1)
        {
            cbxEsMix.setChecked(true);
            VisibilidadConsumidor(true);
            txtCantidadMix.setText(Bnd.getString("JABASMIX"));
            CargarLoteMix();
            fnc.setIndexInt(spnConsumidorMix,BaseColumns._ID,Bnd.getInt("CONIDMIX"));
        }else
        {
            cbxEsMix.setChecked(false);
            VisibilidadConsumidor(false);
        }

        //spnLote.setSelection(Variables.Con_Id);
        //spnLote.setSelection(adspnLote.getCursor().getColumnIndex(Bnd.getString("LOTE")));
        //spnLote.setSelection(1);
        //spnPlanta.setSelection(adspnPlanta.getPosition(Bnd.getString("PLANTA")));

        //if (spnPlanta.getSelectedItem().toString()=="PLANTA DON CARLOS")
        //{
        //    adspnLinea = new ArrayAdapter<String>(IngresoJabas_Registro.this,android.R.layout.simple_dropdown_item_1line,LineasPDC);
        //    spnLinea.setAdapter(adspnLinea);
        //}else if (spnPlanta.getSelectedItem().toString() =="PLANTA YANCAY")
        //{
        //    adspnLinea = new ArrayAdapter<String>(IngresoJabas_Registro.this,android.R.layout.simple_dropdown_item_1line,LineasPYA);
        //    spnLinea.setAdapter(adspnLinea);
        //}

       //spnLinea.setSelection(adspnLinea.getPosition(Bnd.getString("LINEA")));

        btnInicio.setEnabled(false);
        //imbScrollInicio.setEnabled(false);
        btnFinal.setEnabled(true);
        imbScrollFinal.setEnabled(true);
        if (Bnd.getInt("ESTADO")==2)
        {
            ModoSoloLectura(false);
        }

    }
    private void ModoSoloLectura(boolean Est)
    {
        txtFecha.setEnabled(Est);
        txtCantidad.setEnabled(Est);
        txtParadas.setEnabled(Est);
        spnLote.setEnabled(Est);
        spnLinea.setEnabled(Est);
        //spnPlanta.setEnabled(Est);
        btnInicio.setEnabled(Est);
        btnFinal.setEnabled(Est);
        lblHoraInicio.setEnabled(Est);
        lblHoraFin.setEnabled(Est);
        txtFecha.setEnabled(Est);
        btnGuardar.setEnabled(Est);
        //btnTerminar.setEnabled(Est);
        btnBuscarFecha.setEnabled(Est);
        rbCampo.setEnabled(Est);
        rbReProceso.setEnabled(Est);
        imbScrollInicio.setEnabled(Est);
        imbScrollFinal.setEnabled(Est);
        cbxEsMix.setEnabled(Est);
    }
    private void NuevoFormulario()
    {
        //txtFecha.setText(new StringBuilder().append(sDia)+"/"+new StringBuilder().append(sMes+1)+"/"+new StringBuilder().append(sAño));
        txtFecha.setText(Variables.FechaStr);
        rbCampo.setChecked(true);
        btnInicio.setEnabled(true);
        imbScrollInicio.setEnabled(true);
        imbScrollFinal.setEnabled(false);
        btnFinal.setEnabled(false);
        btnTerminar.setEnabled(false);

        if (Variables.UltimaHoraFinal.length()<=0)
        {
            lblHoraInicio.setText(Formato.format(new Date(System.currentTimeMillis())));
        }else
        {
            lblHoraInicio.setText(Variables.UltimaHoraFinal);
        }
    }

    private void ColocarFecha(EditText edtFecha)
    {
        //ASIGNACION DE FECHA
        edtFecha.setText(new StringBuilder().append(fnc.pad(mDia))
                .append("/").append(fnc.pad(mMes+1)).append("/").append(mAño));
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            mAño=year;
            mMes=monthOfYear;
            mDia=dayOfMonth;
            ColocarFecha(edtFecha);
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour = hourOfDay;
                    pMinute = minute;
                    //EstablecerHoraEdt(displayTime);
                    //displayToast();
                }
            };
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case DATE_ID:
                return new DatePickerDialog(this,mDateSetListener,sAño,sMes,sDia);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,mTimeSetListener, pHour, pMinute,false);


        }
        return null;
    }

    /** Displays a notification when the time is updated */
    private void displayToast() {
        Toast.makeText(this, new StringBuilder().append("Time choosen is ").append(lblHoraInicio.getText()),	Toast.LENGTH_SHORT).show();
    }
    private void CargarLoteMix()
    {
        Cursor ConsumidorMix = LocBD.rawQuery(T_Consumidor._SELECT_CON(1,2, Variables.Cul_Id, Variables.Emp_Id),null);
        adspnLoteMix = new SimpleCursorAdapter(IngresoJabas_Registro.this ,
                android.R.layout.simple_spinner_dropdown_item,ConsumidorMix,//Layout simple
                //Todos los registros
                new String[]{T_Consumidor.CONDESCRIPCIONCOR},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spnConsumidorMix.setAdapter(adspnLoteMix);
    }

}
