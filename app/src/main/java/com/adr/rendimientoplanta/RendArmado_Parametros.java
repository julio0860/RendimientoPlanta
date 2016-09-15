package com.adr.rendimientoplanta;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Empresa;
import com.adr.rendimientoplanta.DATA.T_Linea;
import com.adr.rendimientoplanta.DATA.T_Sucursal;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

public class RendArmado_Parametros extends AppCompatActivity {

    //DECLARACIÓN DE SPINNERS
    private Spinner spnEmpresa;
    private Spinner spnSucursal;
    private Spinner spnLinea;
    private Spinner spnLado;

    //DECLARACION DE ADAPTERS
    private SimpleCursorAdapter adspnEmpresa;
    private SimpleCursorAdapter adspnSucursal;
    private SimpleCursorAdapter adspnLinea;
    private ArrayAdapter adspnLado;

    //DECLARACION BOTONES
    private Button btnEstablecer;
    private ImageButton imbRegresar;

    //DECLARACION FECHA
    private EditText edtFecha;

    //DECLARACION VARIABLES
    private int Emp_Id;
    private int Suc_Id;

    //DECLARACION CONSTANTE LADO LINEA
    private String [] Lado = {"A","B","AB"};

    //DECLARACIONES ACCESO BD LOCAL
    LocalBD LBD;
    SQLiteDatabase LocBD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rend_armado_parametros);

        //INICIO CONEXION BDLOCAL
        LBD = new LocalBD(RendArmado_Parametros.this) ;
        LocBD = LBD.getWritableDatabase();

        //ASIGNACION DE SPINNERS DE LAYOUT A VARIABLES
        spnEmpresa = (Spinner) findViewById(R.id.spnEmpresa);
        spnSucursal = (Spinner) findViewById(R.id.spnSucursal);
        spnLinea = (Spinner) findViewById(R.id.spnLinea);
        spnLado = (Spinner) findViewById(R.id.spnLado);

        //ASIGNACION DE EDITTEXT DE LAYOUT A VARIABLES
        edtFecha = (EditText) findViewById(R.id.edtFecha);

        //ASIGNACION DE BUTTONS DE LAYOUT A VARIABLES
        btnEstablecer = (Button) findViewById(R.id.btnEstablecer);
        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);

        //CARGA INICIAL DE DATOS SPINNER
        Cursor Empresa = LocBD.rawQuery(T_Empresa._SELECT_EMP(-1),null);
        adspnEmpresa = new SimpleCursorAdapter(this ,
                android.R.layout.simple_dropdown_item_1line,Empresa,//Layout simple
                new String[]{T_Empresa.EMPDESCRIPCION},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spnEmpresa.setAdapter(adspnEmpresa);

        //ASIGNACION DE EVENTO A SPINNER EMPRESA
        spnEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,android.view.View v,
                                       int position, long id) {
                Cursor CurId = (Cursor) parent.getItemAtPosition(position);
                Emp_Id = CurId.getInt(CurId.getColumnIndex(BaseColumns._ID));

                Cursor Sucursal = LocBD.rawQuery(T_Sucursal._SELECT_SUC_EMP_PLANTA(Emp_Id,2,1), null);
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
            public void onItemSelected(AdapterView<?> parent,android.view.View v,
                                       int position, long id) {
                Cursor CurId = (Cursor) parent.getItemAtPosition(position);
                Suc_Id = CurId.getInt(CurId.getColumnIndex(BaseColumns._ID));
                Cursor Lineas = LocBD.rawQuery(T_Linea._SELECT_LIN(Suc_Id,2),null);
                adspnLinea = new SimpleCursorAdapter(RendArmado_Parametros.this ,
                        android.R.layout.simple_dropdown_item_1line  ,Lineas,//Layout simple
                        //Todos los registros
                        new String[]{T_Linea.LINDESCRIPCION},//Mostrar solo el nombre
                        new int[]{android.R.id.text1}//View para el nombre
                        ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                spnLinea.setAdapter(adspnLinea);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        adspnLado = new ArrayAdapter<String>(RendArmado_Parametros.this,android.R.layout.simple_dropdown_item_1line,Lado);
        spnLado.setAdapter(adspnLado);

        //EVENTO BOTON ESTABLECER
        btnEstablecer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View v){
                //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
                Cursor CurEmpresa = (Cursor) spnEmpresa.getAdapter().getItem(spnEmpresa.getSelectedItemPosition());
                Variables.Emp_Id= CurEmpresa.getInt(CurEmpresa.getColumnIndex(BaseColumns._ID));
                Variables.Emp_Abrev= CurEmpresa.getString(CurEmpresa.getColumnIndex(T_Empresa.EMPABREV));
                Variables.Emp_Descripcion= CurEmpresa.getString(CurEmpresa.getColumnIndex(T_Empresa.EMPDESCRIPCION));

                Cursor CurSucursal = (Cursor) spnSucursal.getAdapter().getItem(spnSucursal.getSelectedItemPosition());
                Variables.Suc_Id = CurSucursal.getInt(CurSucursal.getColumnIndex(BaseColumns._ID));
                Variables.Suc_Descripcion= CurSucursal.getString(CurSucursal.getColumnIndex(T_Sucursal.SUCDESCRIPCION));

                Cursor CurLinea = (Cursor) spnLinea.getAdapter().getItem(spnLinea.getSelectedItemPosition());
                Variables.Lin_Id = CurLinea.getInt(CurLinea.getColumnIndex(BaseColumns._ID));
                Variables.Lin_Descripcion = CurLinea.getString(CurLinea.getColumnIndex(T_Linea.LINDESCRIPCION));
                Variables.Lin_Lado=spnLado.getSelectedItem().toString();
                Variables.FechaStr = edtFecha.getText().toString();

                Intent ActividadNueva = new Intent(RendArmado_Parametros.this, RendArmado_Lista.class);
                startActivity(ActividadNueva);
            }
        }
        );


    }
}
