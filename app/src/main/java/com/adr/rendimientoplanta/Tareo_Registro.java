package com.adr.rendimientoplanta;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Actividad;
import com.adr.rendimientoplanta.DATA.T_Consumidor;
import com.adr.rendimientoplanta.DATA.T_Labor;
import com.adr.rendimientoplanta.DATA.T_Linea;
import com.adr.rendimientoplanta.DATA.T_Tareo;
import com.adr.rendimientoplanta.DATA.T_TareoDetalle;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class Tareo_Registro extends AppCompatActivity {

    //DECLARACION BDLOCAL
    LocalBD LBD;
    SQLiteDatabase LocBD;

    //TEMPORAL
    private int mMes,mAño,mDia,sDia,sMes,sAño;
    private int pHour;
    private int pMinute;
    static final int DATE_ID=0;
    static final int TIME_DIALOG_ID = 1;
    private EditText displayTime;
private boolean ActLabor = true;
    private int imgSecure;
    //DECLARACION VARIABLES
    private String Codigo = "";
    private boolean Leer=false;
    private Vector<String> vLecturas;
    private Funciones fnc;
    private AlertDialog dialog;

    //VARIABLE PARA REGISTRO DE DATOS
    private int Tar_Id=0;
    private int Emp_Id=0;
    private int Est_Id=0;
    private int TarOri_Id=0;
    private int TarTip_Id=0;
    private int TarSubTip_Id=0;
    private int Suc_Id=0;
    private int Con_Id=0;
    private int Act_Id=0;
    private int Lab_Id=0;
    private int LinPro_Id=0;
    private String Tar_Lado="";
    private int Res_Id=0;
    private int Tar_CantidadIni=0;
    private int Tar_CantidadFin=0;
    private String Tar_Fecha="";
    private int Per_Id=0;
    private String HoraIni="";
    private String HoraFin="";
    private String Per_Fotocheck="";

    private Boolean EsNuevo=true;
    //DECLARACION DE CHECKBOX
    private CheckBox cbxLinea;

    //DECLARACION TEXTVIEW
    private TextView lblSupervisor;
    private TextView lblTareo;
    private TextView lblSucursal;
    private TextView lblLado;
    private TextView lblCantidad;

    //DECLARACION EDITTEXT
    private EditText txtFecha;
    private EditText txtHoraIni;
    private EditText txtHoraFin;
    private EditText txtLectura;
    //ADICIONAL
    private EditText edtFecha;


    //DECLARACION SPINNERS
    private Spinner spnConsumidor;
    private Spinner spnActividad;
    private Spinner spnLabor;
    private Spinner spnLinea;
    private Spinner spnLado;

    //DECLARACION RADIO BUTTON
    private RadioButton rbnAgregar;
    private RadioButton rbnQuitar;

    private ImageButton imbGrabar;

    private ImageButton imbFecha;
    private ImageButton imbHoraIni;
    private ImageButton imbHoraFin;
    private ImageButton imbHoraIniDialog;
    private ImageButton imbHoraFinDialog;
    private ImageButton imbVerLectura;
    private ImageButton imbFinalizar;

    private ImageButton imbRegresar;

    private Button btnLeer;
    //DECLARACION ADAPTERS
    private SimpleCursorAdapter adspnConsumidor;
    private SimpleCursorAdapter adspnActividad;
    private SimpleCursorAdapter adspnLabor;
    private SimpleCursorAdapter adspnLinea;

    private SimpleCursorAdapter AdaptadorLecturas;

    private ArrayAdapter adspnLado;

    SimpleDateFormat Formato = new SimpleDateFormat("HH:mm:ss",java.util.Locale.getDefault());

    private String [] Lado = {"A","B","AB"};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareo_registro);

        //APERTURA DE BASE DE DATOS
        LBD = new LocalBD(this);
        LocBD = LBD.getWritableDatabase();

        vLecturas = new Vector<String>();

        //INICIALIZACION DE VECTOR


        //ASIGNACION DE VARIABLES A ELEMENTOS LAYOUT
        lblSupervisor = (TextView) findViewById(R.id.lblSupervisor);
        lblTareo = (TextView) findViewById(R.id.lblTareo);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblLado = (TextView)findViewById(R.id.lblLado);
        lblCantidad = (TextView)findViewById(R.id.lblCantidad);

        lblSupervisor.setText(Variables.Usu_Alias);
        lblTareo.setText(Variables.TarOri_Descripcion);
        lblSucursal.setText(Variables.Suc_Descripcion);

        txtFecha = (EditText) findViewById(R.id.lblFecha);
        txtHoraIni = (EditText) findViewById(R.id.txtHoraIni);
        txtHoraFin = (EditText) findViewById(R.id.txtHoraFin);
        txtLectura = (EditText) findViewById(R.id.txtLectura);

        rbnAgregar = (RadioButton)findViewById(R.id.rbnAgregar);
        rbnQuitar = (RadioButton)findViewById(R.id.rbnQuitar);

        spnConsumidor = (Spinner) findViewById(R.id.spnConsumidor);
        spnActividad = (Spinner) findViewById(R.id.spnActividad);
        spnLabor = (Spinner) findViewById(R.id.spnLabor);
        spnLinea = (Spinner) findViewById(R.id.spnLinea);
        spnLado = (Spinner) findViewById(R.id.spnLado);

        cbxLinea = (CheckBox)findViewById(R.id.cbxLinea);

        imbFecha = (ImageButton)findViewById(R.id.imbFecha);
        imbHoraIni = (ImageButton)findViewById(R.id.imbHoraIni);
        imbHoraFin = (ImageButton)findViewById(R.id.imbHoraFin);
        imbHoraIniDialog=(ImageButton)findViewById(R.id.imbHoraIniDialog);
        imbHoraFinDialog = (ImageButton)findViewById(R.id.imbHoraFinDialog);
        imbVerLectura = (ImageButton)findViewById(R.id.imbVerLectura);
        imbFinalizar = (ImageButton)findViewById(R.id.imbFinalizar);

        imbRegresar = (ImageButton)findViewById(R.id.imbRegresar);

        imbGrabar = (ImageButton)findViewById(R.id.imbGrabar);
        btnLeer = (Button)findViewById(R.id.btnLeer);

        //REQUEST FOCUS
        txtLectura.setEnabled(false);
        //ASIGNACION DE CODIGO FILTRO A ACTIVIDAD
        switch (Variables.TarOri_Codigo)
        {
            case "PLA":
                Codigo = "110";
            case "CAM":
                switch (Variables.TarTipo_Codigo)
                {
                    case "POD":
                        Codigo ="002";
                        break;
                    case "AMA":
                        Codigo ="002";
                        break;
                    case "CAN":
                        Codigo ="003";
                        break;
                    case "BAJ":
                        Codigo ="008";
                        break;
                    case "RAL":
                        Codigo ="004";
                        break;
                    case "DES":
                        Codigo ="004";
                        break;
                    case "LIM":
                        Codigo ="007";
                        break;
                }
        }

        //ASINGACION DE DATOS A CURSORES
        Cursor curConsumidor = LocBD.rawQuery(T_Consumidor._SELECT_CON_SUC(Variables.Suc_Id), null);
        Cursor curActividad = LocBD.rawQuery(T_Actividad._SELECT_ACTIVIDAD_TAREO(Codigo), null);

        //DEFINICION DE SIMPLEADAPTERS
        adspnConsumidor = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, curConsumidor,//Layout simple
                //Todos los registros
                new String[]{T_Consumidor.CONDESCRIPCION},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                , SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        adspnActividad = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, curActividad,//Layout simple
                //Todos los registros
                new String[]{T_Actividad.ACTDESCRIPCION},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                , SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        //ASIGNACION DE SIMPLE ADAPTERS A SPINNERS
        spnConsumidor.setAdapter(adspnConsumidor);
        spnActividad.setAdapter(adspnActividad);

        //ASIGNACION DE SOLO LECTURA A EDITTEXT
        VisibilidadLinea(false);
        txtFecha.setEnabled(false);
        txtHoraIni.setEnabled(false);
        txtHoraFin.setEnabled(false);
        if (Variables.Suc_EsPlanta==1)
        {
            cbxLinea.setEnabled(true);

        }else
        {
            cbxLinea.setEnabled(false);
        }


        //ASIGNACION DE DATOS DEFECTO DIALOGS
        final Calendar C = Calendar.getInstance();
        sAño = C.get(Calendar.YEAR);
        sMes = C.get(Calendar.MONTH);
        sDia = C.get(Calendar.DAY_OF_MONTH);
        pHour = C.get(Calendar.HOUR_OF_DAY);
        pMinute = C.get(Calendar.MINUTE);


        //ASIGNACION DE LISTENER A SPINNERS DINAMICOS
        spnActividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v,
                                       int position, long id) {
                int Act_Id;
                Cursor CurId = (Cursor) parent.getItemAtPosition(position);
                Act_Id = CurId.getInt(CurId.getColumnIndex(BaseColumns._ID));

               // if (ActLabor==true) {
                    Cursor curLabor = LocBD.rawQuery(T_Labor._SELECT_LABOR(Act_Id), null);
                    adspnLabor = new SimpleCursorAdapter(Tareo_Registro.this, android.R.layout.simple_spinner_dropdown_item,
                            curLabor, new String[]{T_Labor.LABDESCRIPCION}, new int[]{android.R.id.text1},
                            SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                    spnLabor.setAdapter(adspnLabor);
               // }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        imbRegresar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent ActividadNueva = new Intent(Tareo_Registro.this, Tareo_Lista.class);
                startActivity(ActividadNueva);
            }
        });

        cbxLinea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
            VisibilidadLinea(isChecked);
                if (isChecked==true)
                {
                    CargarLinea();
                }
            }
        });

        //ASIGNACION DE INTENT VERIFICACION SI ES NUEVO O MODIFICAR
        EsNuevo = getIntent().getBooleanExtra("ESNUEVO",true);
        //Est_Id=getIntent().getIntExtra("ESTADO",0);
        if (EsNuevo==false)
        {
            Tar_Id = getIntent().getIntExtra("ID",0);
            CargarDatos();
            fnc.setIndexInt(spnLabor,BaseColumns._ID,Lab_Id);
        }
        else
        {
            ActLabor = false;
            txtLectura.setEnabled(false);
            imbFinalizar.setEnabled(false);
        }
        imbFinalizar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                //ImagenFinalizar(Est_Id);
                switch (Est_Id)
                {
                    case 1: {
                        Est_Id = 2;
                        Grabar(Est_Id);
                        break;
                    }
                    case 2: {
                        Est_Id = 1;
                        Grabar(Est_Id);
                        break;
                    }
                }
            }
        });
        imbHoraIni.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                txtHoraIni.setText(Formato.format(new Date(System.currentTimeMillis())));
            }
        });
        imbHoraFin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                txtHoraFin.setText(Formato.format(new Date(System.currentTimeMillis())));
            }
        });
        imbVerLectura.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                AlertDialog.Builder builder=new AlertDialog.Builder(Tareo_Registro.this);
                builder.setTitle("LECTURAS");
                ListView ListaLecturas=new ListView(Tareo_Registro.this);
                Cursor CurLecturas = LocBD.rawQuery(T_TareoDetalle._SELECT_TAREODETLIST(Tar_Id),null);
                AdaptadorLecturas=new SimpleCursorAdapter(Tareo_Registro.this, android.R.layout.simple_list_item_2,CurLecturas,
                        new String[]{T_TareoDetalle.PERFOTOCHECK,"NOMBRES"},new int[]{android.R.id.text1,android.R.id.text2},
                        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                ListaLecturas.setAdapter(AdaptadorLecturas);

                builder.setView(ListaLecturas);
                dialog=builder.create();
                dialog.show();
            }
        });

        imbHoraIniDialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                displayTime = txtHoraIni;
                showDialog(TIME_DIALOG_ID);
            }
        });
        imbHoraFinDialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                displayTime = txtHoraFin;
                showDialog(TIME_DIALOG_ID);
            }
        });
        imbFecha.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                edtFecha = txtFecha;
                showDialog(DATE_ID);
            }
        });
        btnLeer.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(Leer==false)
                 {
                     ModoSoloLectura(Leer);
                     btnLeer.setText("TERMINAR");
                     Leer=true;
                 }else
                 {
                     ModoSoloLectura(Leer);
                     btnLeer.setText("LEER");
                     Leer=false;
                 }
             }
         });
        imbGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                  //REGISTRO DE DATOS
                Grabar(1);
            }
        });
    txtLectura.setOnKeyListener(new View.OnKeyListener()
    {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            //if (event.getAction() == KeyEvent.ACTION_UP ||event.getAction() == KeyEvent.KEYCODE_ENTER) {
            if (event.getAction() == KeyEvent.ACTION_DOWN ||keyCode==KeyEvent.KEYCODE_ENTER) {
                //do something here
                //if (keyCode==13)
                if (keyCode==KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER|| event.getAction() == KeyEvent.KEYCODE_ENTER)
                {
                    String Texto="";
                    int Indice=-1;
                    Texto = txtLectura.getText().toString();
                    Indice =vLecturas.indexOf(Texto);
                    if (Indice>=0)
                    {
                        if (rbnQuitar.isChecked())
                        {
                            LimpiarTextLectura(txtLectura);
                            vLecturas.remove(Indice);
                        }
                        else
                        {
                            LimpiarTextLectura(txtLectura);
                            Toast.makeText(Tareo_Registro.this,"DNI REPETIDO",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        if (rbnAgregar.isChecked()) {
                            if (Texto.length()==8)
                            {
                                vLecturas.add(Texto);
                                LimpiarTextLectura(txtLectura);
                            }
                            else
                            {
                                LimpiarTextLectura(txtLectura);
                                Toast.makeText(Tareo_Registro.this,"DNI INVALIDO (8 CARACTERES)",Toast.LENGTH_SHORT).show();
                            }
                        }else
                        {
                            LimpiarTextLectura(txtLectura);
                            Toast.makeText(Tareo_Registro.this,"DNI NO ENCONTRADO",Toast.LENGTH_SHORT).show();
                        }
                    }
                    lblCantidad.setText(String.valueOf(vLecturas.size()));
                }
            }
            return false;
        }
    }
    );
    }
    private void Grabar(int Est)
    {
        Intent NuevaActividad = new Intent(Tareo_Registro.this,Tareo_Lista.class);
        Emp_Id= Variables.Emp_Id;

        TarOri_Id= Variables.TarOri_Id;
        TarTip_Id= Variables.TarTipo_Id;
        TarSubTip_Id= Variables.TarSubTipo_Id;
        Suc_Id= Variables.Suc_Id;

        Cursor CurConsumidor = (Cursor) spnConsumidor.getAdapter().getItem(spnConsumidor.getSelectedItemPosition());
        Con_Id = CurConsumidor.getInt(CurConsumidor.getColumnIndex(BaseColumns._ID));

        Cursor CurActividad = (Cursor) spnActividad.getAdapter().getItem(spnActividad.getSelectedItemPosition());
        Act_Id=CurActividad.getInt(CurActividad.getColumnIndex(BaseColumns._ID));

        Cursor CurLabor = (Cursor) spnLabor.getAdapter().getItem(spnLabor.getSelectedItemPosition());
        Lab_Id=CurLabor.getInt(CurLabor.getColumnIndex(BaseColumns._ID));

        if (cbxLinea.isChecked())
        {
            Cursor CurLinea = (Cursor) spnLinea.getAdapter().getItem(spnLinea.getSelectedItemPosition());
            LinPro_Id=CurLinea.getInt(CurLinea.getColumnIndex(BaseColumns._ID));
            Tar_Lado=spnLado.getSelectedItem().toString();
        }


        Res_Id = Variables.Res_Id;
        Tar_CantidadIni=vLecturas.size();
        Tar_CantidadFin=0;
        Tar_Fecha=txtFecha.getText().toString();

        HoraIni=txtHoraIni.getText().toString();
        HoraFin=txtHoraFin.getText().toString();
        if (vLecturas.size()>0)
        {
            if (EsNuevo==true)
            {
                Est_Id=1;
                LocBD.execSQL(T_Tareo._INSERT(Emp_Id,Est_Id,TarOri_Id,TarTip_Id,TarSubTip_Id,Suc_Id,Con_Id,Act_Id,
                        Lab_Id,LinPro_Id,Res_Id,Tar_CantidadIni,Tar_CantidadFin,HoraIni,HoraFin,Tar_Fecha,Tar_Lado));
                //OBTENCION DEL ULTIMO REGISTRO
                Cursor UltimoId = LocBD.rawQuery(T_Tareo._SELECTMAX(),null);
                UltimoId.moveToFirst();
                Tar_Id=UltimoId.getInt(0);

                Toast.makeText(Tareo_Registro.this,"REGISTRO EXITOSO",Toast.LENGTH_SHORT).show();

                //startActivity(NuevaActividad);
            }
            else
            {
                Est_Id=Est;
                LocBD.execSQL(T_Tareo._UPDATE(Tar_Id,Emp_Id,Est_Id,TarOri_Id,TarTip_Id,TarSubTip_Id,Suc_Id,Con_Id,Act_Id,
                        Lab_Id,LinPro_Id,Res_Id,Tar_CantidadIni,Tar_CantidadFin,HoraIni,HoraFin,Tar_Fecha,Tar_Lado));
                LocBD.execSQL(T_TareoDetalle._DELETE(Tar_Id));
                Toast.makeText(Tareo_Registro.this,"REGISTRO ACTUALIZADO",Toast.LENGTH_SHORT).show();

                //startActivity(NuevaActividad);
            }
            if (Tar_Id!=0)
            {
                for (int i=0;i<vLecturas.size();i++)
                {
                    Per_Fotocheck=vLecturas.elementAt(i);
                    LocBD.execSQL(T_TareoDetalle._INSERT(Tar_Id,Per_Id,HoraIni,HoraFin,Per_Fotocheck));
                }
            }
            startActivity(NuevaActividad);
        }else
        {
            Toast.makeText(Tareo_Registro.this,"DEBE CAPTURAR LOS DNI",Toast.LENGTH_SHORT).show();
        }
    }

    private void CargarDatos()
    {
        Cursor CurTareo = LocBD.rawQuery(T_Tareo._SELECT_TAREOID(Tar_Id),null);
        Cursor CurTareoDetalle = LocBD.rawQuery(T_TareoDetalle._SELECT_TAREODET(Tar_Id),null);

        CurTareo.moveToFirst();
        Emp_Id = CurTareo.getInt(CurTareo.getColumnIndex(T_Tareo.EMPID));
        Est_Id = CurTareo.getInt(CurTareo.getColumnIndex(T_Tareo.ESTID));
        TarOri_Id = CurTareo.getInt(CurTareo.getColumnIndex(T_Tareo.TARORIID));
        TarTip_Id=CurTareo.getInt(CurTareo.getColumnIndex(T_Tareo.TARTIPID));
        TarSubTip_Id=CurTareo.getInt(CurTareo.getColumnIndex(T_Tareo.TARSUBTIPID));
        Suc_Id=CurTareo.getInt(CurTareo.getColumnIndex(T_Tareo.SUCID));
        Con_Id=CurTareo.getInt(CurTareo.getColumnIndex(T_Tareo.CONID));
        Act_Id=CurTareo.getInt(CurTareo.getColumnIndex(T_Tareo.ACTID));
        Lab_Id=CurTareo.getInt(CurTareo.getColumnIndex(T_Tareo.LABID));
        LinPro_Id=CurTareo.getInt(CurTareo.getColumnIndex(T_Tareo.LINPROID));
        Tar_Lado = CurTareo.getString(CurTareo.getColumnIndex(T_Tareo.TARLADO));
        Res_Id=CurTareo.getInt(CurTareo.getColumnIndex(T_Tareo.RESID));
        Tar_CantidadIni = CurTareo.getInt(CurTareo.getColumnIndex(T_Tareo.TARCANTIDADINI));
        Tar_CantidadFin=CurTareo.getInt(CurTareo.getColumnIndex(T_Tareo.TARCANTIDADFIN));
        HoraIni = CurTareo.getString(CurTareo.getColumnIndex(T_Tareo.TARHORAINI));
        HoraFin = CurTareo.getString(CurTareo.getColumnIndex(T_Tareo.TARHORAFIN));
        Tar_Fecha = CurTareo.getString(CurTareo.getColumnIndex(T_Tareo.TARFECHA));

        if (CurTareoDetalle.getCount()!=0) {
            for (CurTareoDetalle.moveToFirst(); !CurTareoDetalle.isAfterLast(); CurTareoDetalle.moveToNext()) {
                vLecturas.add(CurTareoDetalle.getString(CurTareoDetalle.getColumnIndex(T_TareoDetalle.PERFOTOCHECK)));
            }
        }
        fnc.setIndexInt(spnConsumidor,BaseColumns._ID,Con_Id);
        fnc.setIndexInt(spnActividad,BaseColumns._ID,Act_Id);
        Cursor curLabor = LocBD.rawQuery(T_Labor._SELECT_LABOR(Act_Id), null);
        adspnLabor = new SimpleCursorAdapter(Tareo_Registro.this, android.R.layout.simple_spinner_dropdown_item,
                curLabor, new String[]{T_Labor.LABDESCRIPCION}, new int[]{android.R.id.text1},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spnLabor.setAdapter(adspnLabor);
        if (LinPro_Id!=0)
        {
            cbxLinea.setChecked(true);
            fnc.setIndexInt(spnLinea,BaseColumns._ID,LinPro_Id);
            spnLado.setSelection(adspnLado.getPosition(Tar_Lado));
        }
        txtHoraIni.setText(HoraIni);
        txtHoraFin.setText(HoraFin);
        txtFecha.setText(Tar_Fecha);
        lblCantidad.setText(String.valueOf(vLecturas.size()));
        ImagenFinalizar(Est_Id);
        fnc.setIndexInt(spnLabor,BaseColumns._ID,Lab_Id);
        ActLabor=true;
    }
    private void ImagenFinalizar(int Est)
    {
        switch (Est)
        {
            case 1: {
                imgSecure = android.R.drawable.ic_partial_secure;
                break;
            }
            case 2: {
                imgSecure = android.R.drawable.ic_secure;
                ModoSoloLectura(false);
                txtLectura.setEnabled(false);
                btnLeer.setEnabled(false);
                break;
            }
        }
        imbFinalizar.setImageResource(imgSecure);
    }
    private void LimpiarTextLectura(EditText txt)
    {
        txt.setText("");
        txt.requestFocus();
    }
    private void VisibilidadLinea(Boolean Est) {
       if (Est==true)
       {
           spnLinea.setVisibility(View.VISIBLE);
           spnLado.setVisibility(View.VISIBLE);
           lblLado.setVisibility(View.VISIBLE);
       }else
       {
           spnLinea.setVisibility(View.INVISIBLE);
           spnLado.setVisibility(View.INVISIBLE);
           lblLado.setVisibility(View.INVISIBLE);
       }
    }
    private void ModoSoloLectura(boolean Est)
    {
        imbFecha.setEnabled(Est);
        spnConsumidor.setEnabled(Est);
        spnActividad.setEnabled(Est);
        spnLabor.setEnabled(Est);
        spnLinea.setEnabled(Est);
        spnLado.setEnabled(Est);
        imbHoraIni.setEnabled(Est);
        imbHoraFin.setEnabled(Est);
        imbHoraIniDialog.setEnabled(Est);
        imbHoraFinDialog.setEnabled(Est);
        imbGrabar.setEnabled(Est);
        cbxLinea.setEnabled(Est);
        txtLectura.setEnabled(!Est);
        txtLectura.requestFocus();
    }
    private void CargarLinea()
    {
        Cursor curLinea = LocBD.rawQuery(T_Linea._SELECT_LIN(Variables.Suc_Id,-1),null);
        adspnLinea = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, curLinea,//Layout simple
                //Todos los registros
                new String[]{T_Linea.LINDESCRIPCION},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spnLinea.setAdapter(adspnLinea);

        adspnLado = new ArrayAdapter<String>(Tareo_Registro.this,android.R.layout.simple_dropdown_item_1line,Lado);
        spnLado.setAdapter(adspnLado);
    }

    //----------------> TEMPORAL PARA DIALOG
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
                    updateDisplay(displayTime);
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
    private void updateDisplay(EditText lblHora) {
        lblHora.setText(
                new StringBuilder()
                        //      .append(pad(pHour)).append(":")
                        //      .append(pad(pMinute)).append(":")
                        //      .append(pad(0)));
                        .append(fnc.pad(pHour)).append(":")
                        .append(fnc.pad(pMinute)).append(":")
                        .append(fnc.pad(0)));
    }
}
