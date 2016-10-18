package com.adr.rendimientoplanta;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.database.sqlite.SQLiteDatabase;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_MenuUsuario;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

public class Principal_Menu extends AppCompatActivity {

    private Button btnRendimientoPlanta;
    private Button btnConfiguracion;
    private Button btnIngresoJabas;
    private Button btnAgrupacion;
    private Button btnRendimientoCampo;
    private Integer USUID;


    //<----------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se asigna el layout asociado a la clase java
        setContentView(R.layout.activity_principal_menu);

        //Se asigna a la variable (Button), el control equivalente en el layout principal
        btnRendimientoPlanta = (Button) findViewById(R.id.btnRendimientoPlanta);
        btnConfiguracion = (Button)findViewById(R.id.btnConfiguración);
        btnIngresoJabas = (Button)findViewById(R.id.btnIngresoJabas);
        btnAgrupacion = (Button) findViewById(R.id.btnAgrupacion);
        btnRendimientoCampo = (Button) findViewById(R.id.btnRendimientoCampo);


        btnRendimientoPlanta.setEnabled(false);
        btnConfiguracion.setEnabled(false);
        btnIngresoJabas.setEnabled(false);
        btnAgrupacion.setEnabled(false);
        btnRendimientoCampo.setEnabled(false);
        //--------------IMPLEMENTACIÓN DE BASE DE DATOS LOCAL------------------------
        //BDLOCAL --> INICIO DE BASE DE DATOS LOCAL
        LocalBD LBD = new LocalBD(this);
        //ABRIR BASE DE DATOS LOCAL2
        SQLiteDatabase LocBD = LBD.getWritableDatabase();
        //-----------------------FIN BASE

        //Variables.ActividadPrevia = Principal_Menu.class;
        //USUID = getIntent().getIntExtra("USUID",0);
        USUID = Variables.Usu_Id;
        Cursor Privilegios= null;
        Privilegios= LocBD.rawQuery(T_MenuUsuario._SELECTPRIVILEGIOS(USUID),null);

        if (Privilegios.getCount()!=0) {
            for (Privilegios.moveToFirst(); !Privilegios.isAfterLast(); Privilegios.moveToNext()) {
                if (Privilegios.getString(0).equals("btnRendimientoPlanta")&&Privilegios.getInt(5)==1)
                {
                    btnRendimientoPlanta.setEnabled(true);
                }
                if (Privilegios.getString(0).equals("btnIngresoJabas")&&Privilegios.getInt(5)==1)
                {
                    if (Privilegios.getInt(5)==1)
                    {
                        btnIngresoJabas.setEnabled(true);
                    }
                    if(Privilegios.getInt(Privilegios.getColumnIndex(T_MenuUsuario.MENUSUEDITAR)) == 1)
                    {
                        Variables.IngresoJabas_Editar=1;
                    }

                }
                if (Privilegios.getString(0).equals("btnAgrupacion")&&Privilegios.getInt(5)==1)
                {
                    btnAgrupacion.setEnabled(true);
                }
                if (Privilegios.getString(0).equals("btnRendimientoCampo")&&Privilegios.getInt(5)==1)
                {
                    btnRendimientoCampo.setEnabled(true);
                }
                if (Privilegios.getString(0).equals("btnConfiguracion")&&Privilegios.getInt(5)==1)
                {
                    btnConfiguracion.setEnabled(true);
                }
            }
        }
        //Toast.makeText(Principal_Menu.this,Variables.Usu_Codigo,Toast.LENGTH_SHORT).show();

        //Se asigna un Event Listener al boton determinado para realizar una acción al hacer click
        btnRendimientoPlanta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
                    //Intent ActividadNueva = new Intent(Principal_Menu.this,RendPlantaActivity.class);
                    //Se inicia la actividad nueva
                    Intent ActividadNueva = new Intent(Principal_Menu.this,RendArmado_Parametros.class);
                    startActivity(ActividadNueva);
                }
            }
        );
        //4:46 inicio sobredosis
        //Se asigna un Event Listener al boton de configuración
        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
                    Intent ActividadNueva = new Intent(Principal_Menu.this,Principal_Configuracion.class);
                    //Se inicia la actividad nueva
                    startActivity(ActividadNueva);
                }
            }
        );
        //LISTA INGRESO JABAS
        btnIngresoJabas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
                    //Intent ActividadNueva = new Intent(Principal_Menu.this,IngresoJabas_Lista.class);
                    //Se inicia la actividad nueva
                    //startActivity(ActividadNueva);
                    //Intent Param = new Intent(Principal_Menu.this,IngresoJabas_Parametros.class);
                    Intent Param = new Intent(Principal_Menu.this,IngresoJabas_Parametros.class);
                    startActivity(Param);
                }
            }
        );
        btnAgrupacion.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
               //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
               //Se inicia la actividad nueva
               //startActivity(ActividadNueva);
               Intent Param = new Intent(Principal_Menu.this,Tareo_Parametros.class);
               startActivity(Param);
           }
       }
        );

    }
}
