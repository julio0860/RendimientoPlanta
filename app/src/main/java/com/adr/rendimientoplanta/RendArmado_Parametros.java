package com.adr.rendimientoplanta;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.ConexionBD;
import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Agrupador;
import com.adr.rendimientoplanta.DATA.T_Cultivo;
import com.adr.rendimientoplanta.DATA.T_Empresa;
import com.adr.rendimientoplanta.DATA.T_Linea;
import com.adr.rendimientoplanta.DATA.T_Proceso;
import com.adr.rendimientoplanta.DATA.T_Subproceso;
import com.adr.rendimientoplanta.DATA.T_Sucursal;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class RendArmado_Parametros extends AppCompatActivity {

    //DECLARACION DE FUNCOONES
    private Funciones fnc;

    //DECLARACIÓN DE SPINNERS
    private Spinner spnEmpresa;
    private Spinner spnSucursal;
    private Spinner spnProceso;
    private Spinner spnSubproceso;
    private Spinner spnLinea;
    private Spinner spnLado;
    private Spinner spnCultivo;

    //DECLARACION DE ADAPTERS
    private SimpleCursorAdapter adspnEmpresa;
    private SimpleCursorAdapter adspnSucursal;
    private SimpleCursorAdapter adspnLinea;
    private SimpleCursorAdapter adspnProceso;
    private SimpleCursorAdapter adspnSubproceso;
    private ArrayAdapter adspnLado;
    private SimpleCursorAdapter adspnCultivo;

    //DECLARACION BOTONES
    private Button btnEstablecer;
    private Button btnSincronizar;
    private ImageButton imbRegresar;
    private ImageButton imbFecha;
    private ImageButton imbHoraIngreso;


    //DECLARACION FECHA
    private EditText edtFecha;
    private EditText edtHoraIngreso;

    //DECLARACION VARIABLES
    private int Emp_Id;
    private int Suc_Id;
    private int Pro_Id;

    //DECLARACION CONSTANTE LADO LINEA
    private String[] Lado = {"A", "B", "AB"};

    //DECLARACIONES ACCESO BD LOCAL
    LocalBD LBD;
    SQLiteDatabase LocBD;

    //VARIABLES RELOJ
    private int mYear;
    private int mMonth;
    private int mDay;
    private int pHour;
    private int pMinute;
    private EditText displayTime;
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;

    //VARIABLES PARA INSERTAR EL AGRUPADOR AL SERVIDOR
    private int IdregServidor;
    private int EmpId,SucId,ProId,Sub_Id,Lin_Id,Posicion,Mot_Id,Est_Id;
    private String Fecha,Lados,Dni,HoraLectura,HoraIngreso,HoraSalida;

    public void onBackPressed()
    {
        // Your Code Here. Leave empty if you want nothing to happen on back press.
        Intent NuevaActividad = new Intent(this,Principal_Menu.class);

        startActivity(NuevaActividad);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rend_armado_parametros);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //INICIO CONEXION BDLOCAL
        LBD = new LocalBD(RendArmado_Parametros.this);
        LocBD = LBD.getWritableDatabase();
        fnc = new Funciones();
        AsignacionControles();
        InicializarControles();
        EstablecerFecha();

        //EVENTO BOTON ESTABLECER
        btnEstablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapturarFecha();
                Variables.HoraIngreso1=edtHoraIngreso.getText().toString();
                ObtenerCultivo();
                ObtenerIdCampaña();

if (Variables.Cul_Id !=0)
{
    if (Variables.Cam_Id !=0)
    {
        ObtenerParametros();
        Intent ActividadNueva = new Intent(RendArmado_Parametros.this, RendArmado_Lista.class);
        ActividadNueva.putExtra("Documento","");
        Variables.Per_Dni="";
        startActivity(ActividadNueva);
    }
    else
    {
        Mensaje("LA FECHA SE ENCUENTRA FUERA DE CAMPAÑA");
    }

}
    else{
         Mensaje("ESPECIFIQUE EL CULTIVO");
    }
            }
        }  );

        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapturarFecha();

                Sincronizar();
            }
        });

        imbHoraIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayTime = edtHoraIngreso;
                showDialog(fnc.TIME_DIALOG_ID);
            }
        });
    }
    private void updateDisplay() {
        edtFecha.setText(new StringBuilder()
                //Mes es 0 por eso se agrega 1

                .append(fnc.pad(mDay)).append("/")
                .append(fnc.pad(mMonth + 1)).append("/")
                .append(mYear)
        );
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int MonthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = MonthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour = hourOfDay;
                    pMinute = minute;
                    fnc.EstablecerHoraEdt(displayTime,pHour,pMinute);
                    //EstablecerHoraEdt(displayTime);
                    //displayToast();
                }
            };
    @Override
    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,mTimeSetListener, pHour, pMinute,false);
        }
        return null;
    }
    private void Mensaje(String mensaje){
        AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(RendArmado_Parametros.this);
        alertDialog1.setTitle("AVISO");
        alertDialog1.setMessage(mensaje);
        alertDialog1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        alertDialog1.create();
        alertDialog1.show();
    }

   public final Boolean conectadoWifi(){
      ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
 /*   public final Boolean conectadoWifi(){
      //USAR CUANDO SE UTILIZA CON EL EMULADOR VIRTUAL
        return true;
    }*/
    private void AsignacionControles(){
        AsignacionSpinners();
        AsignacionEditText();
        AsignacionButtons();
    }
    private void AsignacionSpinners(){
        //ASIGNACION DE SPINNERS DE LAYOUT A VARIABLES
        spnEmpresa = (Spinner) findViewById(R.id.spnEmpresa);
        spnSucursal = (Spinner) findViewById(R.id.spnSucursal);
        spnProceso = (Spinner) findViewById(R.id.spnProceso);
        spnSubproceso = (Spinner) findViewById(R.id.spnSubproceso);
        spnLinea = (Spinner) findViewById(R.id.spnLinea);
        spnLado = (Spinner) findViewById(R.id.spnLado);
        spnCultivo=(Spinner)findViewById(R.id.spnCultivo);
    }
    private void AsignacionEditText(){
        //ASIGNACION DE EDITTEXT DE LAYOUT A VARIABLES
        edtFecha = (EditText) findViewById(R.id.lblFecha);
        edtHoraIngreso=(EditText)findViewById(R.id.edtHoraIngreso);
    }
    private void AsignacionButtons(){
        //ASIGNACION DE BUTTONS DE LAYOUT A VARIABLES
        btnEstablecer = (Button) findViewById(R.id.btnEstablecer);
        btnSincronizar=(Button)findViewById(R.id.btnRegistrar);
        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);
        imbFecha = (ImageButton) findViewById(R.id.imbFecha);
        imbHoraIngreso=(ImageButton)findViewById(R.id.imbHoraIngreso);
    }
    private void  InicializarControles()  {
        CargarEmpresa();
        CargarSucursal();
        CargarProceso();
        CargarSubProceso();
        CargarLinea();
        CargarLado();
        CargarCultivo();
    }
    private void CargarEmpresa(){
        Cursor Empresa = LocBD.rawQuery(T_Empresa._SELECT_EMP(-1), null);
        adspnEmpresa = new SimpleCursorAdapter(this,
                android.R.layout.simple_dropdown_item_1line, Empresa,//Layout simple
                new String[]{T_Empresa.EMPDESCRIPCION},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                , SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spnEmpresa.setAdapter(adspnEmpresa);

    }
    private void CargarSucursal() {

        spnEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v,
                                       int position, long id) {
                Cursor CurId = (Cursor) parent.getItemAtPosition(position);
                Emp_Id = CurId.getInt(CurId.getColumnIndex(BaseColumns._ID));

                Cursor Sucursal = LocBD.rawQuery(T_Sucursal._SELECT_SUC_EMP_PLANTA(Emp_Id, 2, 1), null);
                adspnSucursal = new SimpleCursorAdapter(RendArmado_Parametros.this, android.R.layout.simple_dropdown_item_1line,
                        Sucursal, new String[]{T_Sucursal.SUCDESCRIPCION}, new int[]{android.R.id.text1},
                        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                spnSucursal.setAdapter(adspnSucursal);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void CargarLinea()   {

        spnSucursal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v,
                                       int position, long id) {
                Cursor CurId = (Cursor) parent.getItemAtPosition(position);
                Suc_Id = CurId.getInt(CurId.getColumnIndex(BaseColumns._ID));
                Cursor Lineas = LocBD.rawQuery(T_Linea._SELECT_LIN(Suc_Id,2), null);
                adspnLinea = new SimpleCursorAdapter(RendArmado_Parametros.this,
                        android.R.layout.simple_dropdown_item_1line, Lineas,//Layout simple
                        //Todos los registros
                        new String[]{T_Linea.LINDESCRIPCION},//Mostrar solo el nombre
                        new int[]{android.R.id.text1}//View para el nombre
                        , SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                spnLinea.setAdapter(adspnLinea);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void CargarLado() {

        adspnLado = new ArrayAdapter<String>(RendArmado_Parametros.this, android.R.layout.simple_dropdown_item_1line, Lado);
        spnLado.setAdapter(adspnLado);

    }
    private void CargarProceso() {
        Cursor Proceso = LocBD.rawQuery(T_Proceso._SELECT_PROCESO(-1), null);
        adspnProceso = new SimpleCursorAdapter(this,
                android.R.layout.simple_dropdown_item_1line, Proceso,//Layout simple
                new String[]{T_Proceso.PRODESCRIPCION},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                , SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spnProceso.setAdapter(adspnProceso);
    }
    private void CargarSubProceso(){
        spnProceso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v,
                                       int position, long id) {
                Cursor CurId = (Cursor) parent.getItemAtPosition(position);
                Pro_Id = CurId.getInt(CurId.getColumnIndex(BaseColumns._ID));
                Cursor Subproceso = LocBD.rawQuery(T_Subproceso._SELECT_SUBPROCESO(Pro_Id, 2), null);
                adspnSubproceso = new SimpleCursorAdapter(RendArmado_Parametros.this,
                        android.R.layout.simple_dropdown_item_1line, Subproceso,//Layout simple
                        //Todos los registros
                        new String[]{T_Subproceso.SUBDESCRIPCION},//Mostrar solo el nombre
                        new int[]{android.R.id.text1}//View para el nombre
                        , SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                spnSubproceso.setAdapter(adspnSubproceso);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }
    private void CargarCultivo(){

        Cursor Cultivo = LocBD.rawQuery(T_Cultivo._SELECT_CULT(-1,-1),null);
        adspnCultivo = new SimpleCursorAdapter(this,
                android.R.layout.simple_dropdown_item_1line,Cultivo,//Layout simple
                new String[]{T_Cultivo.CULDESCRIPCION},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spnCultivo.setAdapter(adspnCultivo);
    }
    private void EstablecerFecha(){
        imbFecha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);

            }
        });
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();
    }
    private void CapturarFecha(){
        String mes,dia;
        Variables.FechaStr=edtFecha.getText().toString();
        if (mDay<10)
        {
            dia="0"+String.valueOf(mDay);
        }
        else
        {
            dia=String.valueOf(mDay);
        }
        if (mMonth+1<10)
        {
            mes="0"+String.valueOf(mMonth+1);
        }
        else
        {
            mes=String.valueOf(mMonth+1);
        }
        Variables.FechaStrBD=String.valueOf(mYear)+"-"+mes+"-"+dia;
    }
    private void Sincronizar(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RendArmado_Parametros.this);
        String alert_title = "AGRUPADOR";
        String alert_description = "¿Desea sincronizar los registros?";
        alertDialogBuilder.setTitle(alert_title);
        // set dialog message
        alertDialogBuilder
                .setMessage(alert_description)
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    // Lo que sucede si se pulsa yes
                    public void onClick(DialogInterface dialog, int id) {
                        if (conectadoWifi()){
                            //CONSULTA DATOS DE LA BD LOCAL PARA SINCRONIZAR
                            //CONSIDERAR QUE PARA EL FILTRADO DEL AGRUPADOR EN LA BDLOCAL UTILIZA FechaStrBD Y PARA EL SERVIDOR PUEDE SER FechaStr
                            Cursor CurReg = LocBD.rawQuery(T_Agrupador._SELECCIONAR_TODOS(Variables.FechaStrBD),null);
                            try {
                                Toast.makeText(RendArmado_Parametros.this, "REGISTROS "+CurReg.getCount(),Toast.LENGTH_SHORT).show();

                                Connection Cnn = ConexionBD.getInstance().getConnection();
                                Statement pstmt = Cnn.createStatement();
                                ResultSet Rse;

                                Rse=pstmt.executeQuery("SELECT DiaPro_EsCerrado FROM DiasProceso WHERE DiaPro_Fecha='"+Variables.FechaStr+"'");

                                while(Rse.next()){
                                    if(Rse.getInt(1)==0){
                                        Rse=null;
                                        if (CurReg.getCount()!=0){
                                            for (CurReg.moveToFirst();!CurReg.isAfterLast();CurReg.moveToNext()){
                                                IdregServidor=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.IDSERVIDOR));
                                                EmpId=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.EMPID));
                                                SucId=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.SUCID));
                                                ProId=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.PROID));
                                                Sub_Id=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.SUBID));
                                                Lin_Id=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.LINID));
                                                Mot_Id=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.MOTIVO));
                                                Est_Id=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.ESTADO));
                                                Posicion=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.POSICION));
                                                Fecha=CurReg.getString(CurReg.getColumnIndex(T_Agrupador.FECHA));
                                                Lados=CurReg.getString(CurReg.getColumnIndex(T_Agrupador.LADO));
                                                Dni=CurReg.getString(CurReg.getColumnIndex(T_Agrupador.DNI));
                                                HoraLectura=CurReg.getString(CurReg.getColumnIndex(T_Agrupador.HORALECTURA));
                                                HoraIngreso=CurReg.getString(CurReg.getColumnIndex(T_Agrupador.HORAINGRESO));
                                                HoraSalida=CurReg.getString(CurReg.getColumnIndex(T_Agrupador.HORASALIDA));
                                                if (HoraSalida=="null") {
                                                    HoraSalida =null;
                                                }
                                                if (IdregServidor==0) {
                                                    if (HoraSalida==null){
                                                        pstmt.executeUpdate(T_Agrupador._INSERT_LOCAL_SERVIDOR(EmpId, Fecha.trim(), SucId, ProId, Sub_Id, Lin_Id, Lados.trim(), Posicion, Dni.trim(), HoraLectura.trim(), HoraIngreso.trim(), HoraSalida, Mot_Id, Est_Id), pstmt.RETURN_GENERATED_KEYS);
                                                    }
                                                    else
                                                    {
                                                        pstmt.executeUpdate(T_Agrupador._INSERT_LOCAL_SERVIDOR1(EmpId, Fecha.trim(), SucId, ProId, Sub_Id, Lin_Id, Lados.trim(), Posicion, Dni.trim(), HoraLectura.trim(), HoraIngreso.trim(), HoraSalida, Mot_Id, Est_Id), pstmt.RETURN_GENERATED_KEYS);
                                                    }

                                                    Rse = pstmt.getGeneratedKeys();
                                                    if (Rse != null && Rse.next()) {
                                                        IdregServidor = Rse.getInt(1);
                                                        LocBD.execSQL(T_Agrupador.ActualizarIdServidorLocal(EmpId, Fecha.trim(), SucId, ProId, Sub_Id, Lin_Id, Lados.trim(), Posicion, Dni.trim(), Mot_Id, Est_Id, IdregServidor));
                                                    }
                                                }
                                                else
                                                {
                                                    if (HoraSalida!=null)
                                                    {
                                                        pstmt.execute(T_Agrupador.ACTUALIZAR_LOCAL_SERVIDOR1(EmpId, Fecha.trim(), SucId, ProId, Sub_Id, Lin_Id, Lados.trim(), Posicion, Dni.trim(), HoraLectura.trim(), HoraIngreso.trim(), HoraSalida, Mot_Id, Est_Id,IdregServidor));
                                                    }else
                                                    {
                                                        pstmt.execute(T_Agrupador.ACTUALIZAR_LOCAL_SERVIDOR(EmpId, Fecha.trim(), SucId, ProId, Sub_Id, Lin_Id, Lados.trim(), Posicion, Dni.trim(), HoraLectura.trim(), HoraIngreso.trim(), HoraSalida, Mot_Id, Est_Id,IdregServidor));
                                                    }

                                                }
                                            }

                                            Rse = pstmt.executeQuery("SELECT Agru_Id,Emp_Id,Fecha,Suc_Id,Pro_Id,Sub_Id,Lin_Id,Lado,Posicion,DNI," +
                                                    "LEFT(convert(VARCHAR(15),HoraLectura,112),8)+' '+ISNULL(LEFT(convert(VARCHAR(15),HoraLectura,108),8),'00:00:00') AS HoraLectura," +
                                                    "CONVERT(VARCHAR(50),HoraIngreso,108) AS HoraIngreso,CONVERT(VARCHAR(50),HoraSalida,108) AS HoraSalida,Motivo,Est_Id FROM  Agrupador WHERE Fecha='"+Variables.FechaStr+"'");
                                            while (Rse.next()){
                                                int IdAgrudServer=Rse.getInt(1);

                                                Cursor VarBdLocal= LocBD.rawQuery("SELECT  IFNULL(Eslocal,0) FROM Agrupador WHERE Agru_IdServidor='"+IdAgrudServer+"'",null);
                                                if (VarBdLocal.getCount()==0){
                                                    if (Rse.getString(13)==null)
                                                    {
                                                        LocBD.execSQL(T_Agrupador._INSERT_SERVIDOR_LOCAL(Rse.getInt(2),Rse.getString(3).trim(),Rse.getInt(4),Rse.getInt(5),Rse.getInt(6),Rse.getInt(7),Rse.getString(8).trim(),
                                                                Rse.getInt(9),Rse.getString(10).trim(),Rse.getString(11).trim(),Rse.getString(12).trim(),Rse.getString(13),Rse.getInt(14),Rse.getInt(15),Rse.getInt(1)));
                                                    }
                                                    else
                                                    {
                                                        LocBD.execSQL(T_Agrupador._INSERT_SERVIDOR_LOCAL1(Rse.getInt(2),Rse.getString(3).trim(),Rse.getInt(4),Rse.getInt(5),Rse.getInt(6),Rse.getInt(7),Rse.getString(8).trim(),
                                                                Rse.getInt(9),Rse.getString(10).trim(),Rse.getString(11).trim(),Rse.getString(12).trim(),Rse.getString(13),Rse.getInt(14),Rse.getInt(15),Rse.getInt(1)));
                                                    }
                                                }
                                                else
                                                {
                                                    if (VarBdLocal.moveToFirst()) {
                                                        do {
                                                            if (VarBdLocal.getInt(0)==0);
                                                            if (Rse.getString(13)==null)
                                                            {
                                                                LocBD.execSQL(T_Agrupador.ACTUALIZAR_SERVIDOR_LOCAL(Rse.getInt(2),Rse.getString(3).trim(),Rse.getInt(4),Rse.getInt(5),Rse.getInt(6),Rse.getInt(7),Rse.getString(8).trim(),
                                                                        Rse.getInt(9),Rse.getString(10).trim(),Rse.getString(11).trim(),Rse.getString(12).trim(),Rse.getString(13),Rse.getInt(14),Rse.getInt(15),Rse.getInt(1)));
                                                            }
                                                            else
                                                            {
                                                                LocBD.execSQL(T_Agrupador.ACTUALIZAR_SERVIDOR_LOCAL1(Rse.getInt(2),Rse.getString(3).trim(),Rse.getInt(4),Rse.getInt(5),Rse.getInt(6),Rse.getInt(7),Rse.getString(8).trim(),
                                                                        Rse.getInt(9),Rse.getString(10).trim(),Rse.getString(11).trim(),Rse.getString(12).trim(),Rse.getString(13),Rse.getInt(14),Rse.getInt(15),Rse.getInt(1)));
                                                            }


                                                        } while(VarBdLocal.moveToNext());
                                                    }

                                                }
                                            }
                                            Toast.makeText(RendArmado_Parametros.this, "SINCRONIZACION EXITOSA",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            //SI NO TIENE REGISTROS EN LA BD LOCAL
                                            //DESCARGA DATOS DEL SERVIDOR DE LA FECHA ACTUAL.
                                            Rse = pstmt.executeQuery("SELECT Agru_Id,Emp_Id,Fecha,Suc_Id,Pro_Id,Sub_Id,Lin_Id,Lado,Posicion,DNI," +
                                                    "LEFT(convert(VARCHAR(15),HoraLectura,112),8)+' '+ISNULL(LEFT(convert(VARCHAR(15),HoraLectura,108),8),'00:00:00') AS HoraLectura," +
                                                    "CONVERT(VARCHAR(50),HoraIngreso,108) AS HoraIngreso,CONVERT(VARCHAR(50),HoraSalida,108) AS HoraSalida,Motivo,Est_Id FROM  Agrupador WHERE Fecha='"+Variables.FechaStr+"'");

                                            while (Rse.next()){
                                                int IdAgrudServer=Rse.getInt(1);

                                                Cursor VarBdLocal= LocBD.rawQuery("SELECT IFNULL(Eslocal,0) as EsLocal  FROM Agrupador WHERE Agru_IdServidor='"+IdAgrudServer+"'",null);
                                                if (VarBdLocal.getCount()==0){
                                                    if (Rse.getString(13)==null) {
                                                        LocBD.execSQL(T_Agrupador._INSERT_SERVIDOR_LOCAL(Rse.getInt(2), Rse.getString(3).trim(), Rse.getInt(4), Rse.getInt(5), Rse.getInt(6), Rse.getInt(7), Rse.getString(8).trim(),
                                                                Rse.getInt(9), Rse.getString(10).trim(), Rse.getString(11).trim(), Rse.getString(12).trim(), Rse.getString(13), Rse.getInt(14), Rse.getInt(15), Rse.getInt(1)));
                                                    }
                                                    else{
                                                        LocBD.execSQL(T_Agrupador._INSERT_SERVIDOR_LOCAL1(Rse.getInt(2), Rse.getString(3).trim(), Rse.getInt(4), Rse.getInt(5), Rse.getInt(6), Rse.getInt(7), Rse.getString(8).trim(),
                                                                Rse.getInt(9), Rse.getString(10).trim(), Rse.getString(11).trim(), Rse.getString(12).trim(), Rse.getString(13), Rse.getInt(14), Rse.getInt(15), Rse.getInt(1)));
                                                    }
                                                }
                                                else
                                                {
                                                    if (VarBdLocal.moveToFirst()) {
                                                        do {
                                                            if (VarBdLocal.getInt(0)==0);
                                                            if (Rse.getString(13)==null)
                                                            {
                                                                LocBD.execSQL(T_Agrupador.ACTUALIZAR_SERVIDOR_LOCAL(Rse.getInt(2),Rse.getString(3).trim(),Rse.getInt(4),Rse.getInt(5),Rse.getInt(6),Rse.getInt(7),Rse.getString(8).trim(),
                                                                        Rse.getInt(9),Rse.getString(10).trim(),Rse.getString(11).trim(),Rse.getString(12).trim(),Rse.getString(13),Rse.getInt(14),Rse.getInt(15),Rse.getInt(1)));
                                                            }
                                                            else
                                                            {
                                                                LocBD.execSQL(T_Agrupador.ACTUALIZAR_SERVIDOR_LOCAL1(Rse.getInt(2),Rse.getString(3).trim(),Rse.getInt(4),Rse.getInt(5),Rse.getInt(6),Rse.getInt(7),Rse.getString(8).trim(),
                                                                        Rse.getInt(9),Rse.getString(10).trim(),Rse.getString(11).trim(),Rse.getString(12).trim(),Rse.getString(13),Rse.getInt(14),Rse.getInt(15),Rse.getInt(1)));
                                                            }

                                                        } while(VarBdLocal.moveToNext());
                                                    }

                                                }

                                            }
                                            Toast.makeText(RendArmado_Parametros.this, "DESCARGA DE DATOS DEL SERVIDOR EXITOSA",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        Mensaje("EL DIA ESTA CERRADO");
                                    }
                                }


                            }
                            catch (Exception e){
                                Toast.makeText(RendArmado_Parametros.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(RendArmado_Parametros.this, "NO HAY CONEXION, INTENTE LUEGO ",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // Si se pulsa no no hace nada
                        Toast.makeText(RendArmado_Parametros.this,"OPERACION CANCELADA",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
    private void ObtenerParametros(){

        Cursor CurEmpresa = (Cursor) spnEmpresa.getAdapter().getItem(spnEmpresa.getSelectedItemPosition());
        Variables.Emp_Id = CurEmpresa.getInt(CurEmpresa.getColumnIndex(BaseColumns._ID));
        Variables.Emp_Abrev = CurEmpresa.getString(CurEmpresa.getColumnIndex(T_Empresa.EMPABREV));
        Variables.Emp_Descripcion = CurEmpresa.getString(CurEmpresa.getColumnIndex(T_Empresa.EMPDESCRIPCION));

        Cursor CurSucursal = (Cursor) spnSucursal.getAdapter().getItem(spnSucursal.getSelectedItemPosition());
        Variables.Suc_Id = CurSucursal.getInt(CurSucursal.getColumnIndex(BaseColumns._ID));
        Variables.Suc_Descripcion = CurSucursal.getString(CurSucursal.getColumnIndex(T_Sucursal.SUCDESCRIPCION));

        Cursor CurProceso = (Cursor) spnProceso.getAdapter().getItem(spnProceso.getSelectedItemPosition());
        Variables.Pro_Id=CurProceso.getInt(CurProceso.getColumnIndex(BaseColumns._ID));
        Variables.Pro_Descripcion=CurProceso.getString(CurProceso.getColumnIndex(T_Proceso.PRODESCRIPCION));

        Cursor CurSubproceso = (Cursor) spnSubproceso.getAdapter().getItem(spnSubproceso.getSelectedItemPosition());
        Variables.Sub_Id=CurSubproceso.getInt(CurSubproceso.getColumnIndex(BaseColumns._ID));
        Variables.Sub_Descripcion=CurSubproceso.getString(CurSubproceso.getColumnIndex(T_Subproceso.SUBDESCRIPCION));

        Cursor CurLinea = (Cursor) spnLinea.getAdapter().getItem(spnLinea.getSelectedItemPosition());
        Variables.Lin_Id = CurLinea.getInt(CurLinea.getColumnIndex(BaseColumns._ID));
        Variables.Lin_Descripcion = CurLinea.getString(CurLinea.getColumnIndex(T_Linea.LINDESCRIPCION));
        Variables.Lin_Lado = spnLado.getSelectedItem().toString();
        Variables.FechaStr =edtFecha.getText().toString();


    }
    private void ObtenerIdCampaña(){
        Cursor FechaCampaña = LocBD.rawQuery("SELECT Cam_Id as _Id FROM Campaña WHERE Cul_Id='"+Variables.Cul_Id+"' AND '"+Variables.FechaStrBD+"' between cam_fechaIni AND Cam_FechaTer", null);
        if (FechaCampaña.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                Variables.Cam_Id= FechaCampaña.getInt(0);

            } while(FechaCampaña.moveToNext());
        }
    }
    private void ObtenerCultivo(){
        Cursor CurCultivo = (Cursor) spnCultivo.getAdapter().getItem(spnCultivo.getSelectedItemPosition());
        Variables.Cul_Id = CurCultivo.getInt(CurCultivo.getColumnIndex(BaseColumns._ID));
    }



}





