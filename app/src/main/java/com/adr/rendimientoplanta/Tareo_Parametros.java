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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Empresa;
import com.adr.rendimientoplanta.DATA.T_Sucursal;
import com.adr.rendimientoplanta.DATA.T_TareoOrigen;
import com.adr.rendimientoplanta.DATA.T_TareoSubTipo;
import com.adr.rendimientoplanta.DATA.T_TareoTipo;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

public class Tareo_Parametros extends AppCompatActivity {

    //DECLARACION DE SPINNERS
    private Spinner spnEmpresa;
    private Spinner spnTareoOrigen;
    private Spinner spnTareoTipo;
    private Spinner spnTareoSubTipo;
    private Spinner spnSucursal;

    //DECLARACION DE CURSORADAPTERS PARA LOS SPINNER
    private SimpleCursorAdapter adspnEmpresa;
    private SimpleCursorAdapter adspnTareoOrigen;
    private SimpleCursorAdapter adspnTareoTipo;
    private SimpleCursorAdapter adspnTareoSubTipo;
    private SimpleCursorAdapter adspnSucursal;
    //DECLARACION DE BOTONES
    private Button btnGuardar;
    private ImageButton imbRegresar;

    //VARIABLES PARA PARAMETROS
    private int Emp_Id;
    private int TarTip_Id;
    //DECLARACIONES ACCESO BD LOCAL
    LocalBD LBD;
    SQLiteDatabase LocBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareo_parametros);

        LBD = new LocalBD(Tareo_Parametros.this) ;
        LocBD = LBD.getWritableDatabase();

        //ASIGNACION DE SPINNERS DE LAYOUT A VARIABLES
        spnEmpresa = (Spinner) findViewById(R.id.spnEmpresa);
        spnTareoOrigen = (Spinner) findViewById(R.id.spnTareoOrigen);
        spnTareoTipo = (Spinner) findViewById(R.id.spnTareoTipo);
        spnTareoSubTipo = (Spinner) findViewById(R.id.spnTareoSubTipo);
        spnSucursal = (Spinner) findViewById(R.id.spnSucursal);
        //ASIGNACION DE BUTTONS DE LAYOUT A VARIABLES
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);

        //CARGA INICIAL DE SPINNERS
        Cursor Empresa = LocBD.rawQuery(T_Empresa._SELECT_EMP(-1),null);
        adspnEmpresa = new SimpleCursorAdapter(this ,
                android.R.layout.simple_dropdown_item_1line,Empresa,//Layout simple
                //android.R.layout.simple_spinner_item,Empresa layout simple
                //Todos los registros
                new String[]{T_Empresa.EMPDESCRIPCION},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spnEmpresa.setAdapter(adspnEmpresa);
        //ASIGNACION DE CONTROL DE EVENTOS A CONTROLES
        spnEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,android.view.View v,
                                       int position, long id) {
             Cursor CurId = (Cursor) parent.getItemAtPosition(position);
             Emp_Id = CurId.getInt(CurId.getColumnIndex(BaseColumns._ID));

                Cursor TareoOrigen = LocBD.rawQuery(T_TareoOrigen._SELECT_TAREO_ORIGEN(Emp_Id),null);
                adspnTareoOrigen = new SimpleCursorAdapter(Tareo_Parametros.this,android.R.layout.simple_dropdown_item_1line,
                        TareoOrigen,new String[]{T_TareoOrigen.TARORIDESCRIPCION},new int[]{android.R.id.text1},
                        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                spnTareoOrigen.setAdapter(adspnTareoOrigen);

                Cursor TareoTipo = LocBD.rawQuery(T_TareoTipo._SELECT_TAREO_TIPO(Emp_Id),null);
                adspnTareoTipo = new SimpleCursorAdapter(Tareo_Parametros.this,android.R.layout.simple_dropdown_item_1line,
                        TareoTipo,new String[]{T_TareoTipo.TARTIPDESCRIPCION},new int[]{android.R.id.text1},
                        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                spnTareoTipo.setAdapter(adspnTareoTipo);

                Cursor Sucursal = LocBD.rawQuery(T_Sucursal._SELECT_SUC_EMP(Emp_Id),null);
                adspnSucursal = new SimpleCursorAdapter(Tareo_Parametros.this,android.R.layout.simple_dropdown_item_1line,
                        Sucursal,new String[]{T_Sucursal.SUCDESCRIPCION},new int[]{android.R.id.text1},
                        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                spnSucursal.setAdapter(adspnSucursal);
            }
                   public void onNothingSelected(AdapterView<?> parent) {
            }
                });
        spnTareoTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,android.view.View v,
                                       int position, long id) {
                Cursor CurId = (Cursor) parent.getItemAtPosition(position);
                TarTip_Id = CurId.getInt(CurId.getColumnIndex(BaseColumns._ID));

                Cursor TareoSubTipo = LocBD.rawQuery(T_TareoSubTipo._SELECT_TAREOSUBTIPO(TarTip_Id,Emp_Id),null);
                adspnTareoSubTipo = new SimpleCursorAdapter(Tareo_Parametros.this,android.R.layout.simple_dropdown_item_1line ,
                        TareoSubTipo,new String[]{T_TareoSubTipo.TARSUBTIPDESCRIPCION},new int[]{android.R.id.text1},
                        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                spnTareoSubTipo.setAdapter(adspnTareoSubTipo);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener()
                                         {
                                             @Override
                                             public void onClick (View v){
             //Se crea un Intent para llamar a una nueva Actividad(Pantalla)

             //Pasar información de un activity a otra
             //Variables.Suc_Id = spnSucursal.getSelectedItem().getClass()
             Cursor CurEmpresa = (Cursor) spnEmpresa.getAdapter().getItem(spnEmpresa.getSelectedItemPosition());
             Variables.Emp_Id= CurEmpresa.getInt(CurEmpresa.getColumnIndex(BaseColumns._ID));
             Variables.Emp_Descripcion= CurEmpresa.getString(CurEmpresa.getColumnIndex(T_Empresa.EMPDESCRIPCION));

             Cursor CurSucursal = (Cursor) spnSucursal.getAdapter().getItem(spnSucursal.getSelectedItemPosition());
             Variables.Suc_Id = CurSucursal.getInt(CurSucursal.getColumnIndex(BaseColumns._ID));
             Variables.Suc_Descripcion= CurSucursal.getString(CurSucursal.getColumnIndex(T_Sucursal.SUCDESCRIPCION));
             Variables.Suc_EsPlanta = CurSucursal.getInt(CurSucursal.getColumnIndex(T_Sucursal.SUCESPLANTA));

             Cursor CurTareoOrigen = (Cursor) spnTareoOrigen.getAdapter().getItem(spnTareoOrigen.getSelectedItemPosition());
             Variables.TarOri_Id= CurTareoOrigen.getInt(CurTareoOrigen.getColumnIndex(BaseColumns._ID));
             Variables.TarOri_Descripcion= CurTareoOrigen.getString(CurTareoOrigen.getColumnIndex(T_TareoOrigen.TARORIDESCRIPCION));
             Variables.TarOri_Codigo= CurTareoOrigen.getString(CurTareoOrigen.getColumnIndex(T_TareoOrigen.TARORICODIGO));

             Cursor CurTareoTipo = (Cursor) spnTareoTipo.getAdapter().getItem(spnTareoTipo.getSelectedItemPosition());
             Variables.TarTipo_Id= CurTareoTipo.getInt(CurTareoTipo.getColumnIndex(BaseColumns._ID));
             Variables.TarTipo_Descripcion= CurTareoTipo.getString(CurTareoTipo.getColumnIndex(T_TareoTipo.TARTIPDESCRIPCION));
             Variables.TarTipo_Codigo= CurTareoTipo.getString(CurTareoTipo.getColumnIndex(T_TareoTipo.TARTIPCODIGO));

             Cursor CurTareoSubTipo = (Cursor) spnTareoSubTipo.getAdapter().getItem(spnTareoSubTipo.getSelectedItemPosition());
             Variables.TarSubTipo_Id= CurTareoSubTipo.getInt(CurTareoSubTipo.getColumnIndex(BaseColumns._ID));
             Variables.TarSubTipo_Descripcion= CurTareoSubTipo.getString(CurTareoSubTipo.getColumnIndex(T_TareoSubTipo.TARSUBTIPDESCRIPCION));
             //               Toast.makeText(IngresoJabas_Parametros.this, Variables.Suc_Id.toString(),Toast.LENGTH_LONG).show();
             //               Toast.makeText(IngresoJabas_Parametros.this, Variables.Suc_Descripcion.toString(),Toast.LENGTH_LONG).show();

             //Se inicia la actividad nueva

             //Intent ActividadNueva = new Intent(IngresoJabas_Parametros.this, supper.cl);
             //startActivity(ActividadNueva);

             Intent ActividadNueva = new Intent(Tareo_Parametros.this, Tareo_Lista.class);
             startActivity(ActividadNueva);
         }
     }
        );


    }
}
