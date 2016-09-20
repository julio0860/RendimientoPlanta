package com.adr.rendimientoplanta;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Empresa;
import com.adr.rendimientoplanta.DATA.T_Linea;
import com.adr.rendimientoplanta.DATA.T_Proceso;
import com.adr.rendimientoplanta.DATA.T_Subproceso;
import com.adr.rendimientoplanta.DATA.T_Sucursal;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.util.Calendar;

public class RendArmado_Parametros extends AppCompatActivity {

    //DECLARACIÓN DE SPINNERS
    private Spinner spnEmpresa;
    private Spinner spnSucursal;
    private Spinner spnProceso;
    private Spinner spnSubproceso;
    private Spinner spnLinea;
    private Spinner spnLado;

    //DECLARACION DE ADAPTERS
    private SimpleCursorAdapter adspnEmpresa;
    private SimpleCursorAdapter adspnSucursal;
    private SimpleCursorAdapter adspnLinea;
    private SimpleCursorAdapter adspnProceso;
    private SimpleCursorAdapter adspnSubproceso;
    private ArrayAdapter adspnLado;

    //DECLARACION BOTONES
    private Button btnEstablecer;
    private ImageButton imbRegresar;
    private ImageButton imbFecha;

    //DECLARACION FECHA
    private EditText edtFecha;

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
    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rend_armado_parametros);

        //INICIO CONEXION BDLOCAL
        LBD = new LocalBD(RendArmado_Parametros.this);
        LocBD = LBD.getWritableDatabase();

        //ASIGNACION DE SPINNERS DE LAYOUT A VARIABLES
        spnEmpresa = (Spinner) findViewById(R.id.spnEmpresa);
        spnSucursal = (Spinner) findViewById(R.id.spnSucursal);
        spnProceso = (Spinner) findViewById(R.id.spnProceso);
        spnSubproceso = (Spinner) findViewById(R.id.spnSubproceso);
        spnLinea = (Spinner) findViewById(R.id.spnLinea);
        spnLado = (Spinner) findViewById(R.id.spnLado);

        //ASIGNACION DE EDITTEXT DE LAYOUT A VARIABLES
        edtFecha = (EditText) findViewById(R.id.edtFecha);

        //ASIGNACION DE BUTTONS DE LAYOUT A VARIABLES
        btnEstablecer = (Button) findViewById(R.id.btnEstablecer);
        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);
        imbFecha = (ImageButton) findViewById(R.id.imbFecha);


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


        //CARGA INICIAL DE DATOS SPINNER
        Cursor Empresa = LocBD.rawQuery(T_Empresa._SELECT_EMP(-1), null);
        adspnEmpresa = new SimpleCursorAdapter(this,
                android.R.layout.simple_dropdown_item_1line, Empresa,//Layout simple
                new String[]{T_Empresa.EMPDESCRIPCION},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                , SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spnEmpresa.setAdapter(adspnEmpresa);

        //ASIGNACION DE EVENTO A SPINNER EMPRESA
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

        //simple_dropdown_item_1line    - 1 linea
        //simple_spinner_dropdown_item     - Pequeña

        //ASIGNACION DE EVENTO A SINNER SUCURSAL
        spnSucursal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v,
                                       int position, long id) {
                Cursor CurId = (Cursor) parent.getItemAtPosition(position);
                Suc_Id = CurId.getInt(CurId.getColumnIndex(BaseColumns._ID));
                Cursor Lineas = LocBD.rawQuery(T_Linea._SELECT_LIN(Suc_Id, 2), null);
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
        adspnLado = new ArrayAdapter<String>(RendArmado_Parametros.this, android.R.layout.simple_dropdown_item_1line, Lado);
        spnLado.setAdapter(adspnLado);

        Cursor Proceso = LocBD.rawQuery(T_Proceso._SELECT_PROCESO(-1), null);
        adspnProceso = new SimpleCursorAdapter(this,
                android.R.layout.simple_dropdown_item_1line, Proceso,//Layout simple
                new String[]{T_Proceso.PRODESCRIPCION},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                , SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spnProceso.setAdapter(adspnProceso);

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

        //EVENTO BOTON ESTABLECER
        btnEstablecer.setOnClickListener(new View.OnClickListener() {

                                             @Override
                                             public void onClick(View v) {
                                                 //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
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
                                                 Variables.FechaStr = edtFecha.getText().toString();

                                                 Intent ActividadNueva = new Intent(RendArmado_Parametros.this, RendArmado_Lista.class);
                                                 startActivity(ActividadNueva);
                                             }
                                         }
        );



}
    private void updateDisplay() {
        edtFecha.setText(new StringBuilder()
                //Mes es 0 por eso se agrega 1
                .append(mDay).append("/")
                .append(mMonth + 1).append("/")
                .append(mYear).append("")

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
    @Override
    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
        }
        return null;
    }
}



