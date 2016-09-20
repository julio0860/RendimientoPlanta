package com.adr.rendimientoplanta.DATA;

/**
 * Created by smachado on 2016/04/22.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adr.rendimientoplanta.LIBRERIA.Variables;


public class LocalBD extends SQLiteOpenHelper{

    public LocalBD(Context Contexto)
    {
        //super(Contexto,NombreBD,CurFactory,Version);
        super(Contexto, Variables.BaseLocal,null, Variables.VersionBD);
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLES
        db.execSQL(T_Rendimiento.CREATE_T_RENDIMIENTO);
        db.execSQL(T_IngresoJabas.CREATE_T_INGRESOJABAS);

        //----------------------------->
        db.execSQL(T_Usuario.CREATE_T_USUARIO);
        db.execSQL(T_Menu.CREATE_T_MENU);
        db.execSQL(T_MenuUsuario.CREATE_T_MENUUSUARIO);
        //------------------->
        db.execSQL(T_Consumidor.CREATE_T_CONSUMIDOR);
        db.execSQL(T_Variedad.CREATE_T_VARIEDAD);
        db.execSQL(T_Cultivo.CREATE_T_CULTIVO);
        db.execSQL(T_Estado.CREATE_T_ESTADO);
        db.execSQL(T_Linea.CREATE_T_LINEA);
        db.execSQL(T_Sucursal.CREATE_T_SUCURSAL);
        db.execSQL(T_Personal.CREATE_T_PERSONAL);
        db.execSQL(T_Presentacion.CREATE_T_PRESENTACION);
        db.execSQL(T_Empresa.CREATE_T_EMPRESA);
        db.execSQL(T_EmpresaUsuario.CREATE_T_EMPRESAUSUARIO);
        db.execSQL(T_TareoOrigen.CREATE_T_TAREO_ORIGEN);
        db.execSQL(T_TareoTipo.CREATE_T_TAREO_TIPO);
        db.execSQL(T_TareoSubTipo.CREATE_T_TAREOSUBTIPO);
        db.execSQL(T_Responsable.CREATE_T_RESPONSABLE);
        db.execSQL(T_Actividad.CREATE_T_ACTIVIDAD);
        db.execSQL(T_Labor.CREATE_T_LABOR);
        db.execSQL(T_Tareo.CREATE_T_TAREO);
        db.execSQL(T_Proceso.CREATE_T_PROCESO);
        db.execSQL(T_Subproceso.CREATE_T_SUBPROCESO);
        db.execSQL(T_TareoDetalle.CREATE_T_TAREODETALLE);
        //NUEVAS TABLAS
        db.execSQL(T_LineaRegistro.Create_LineaRegistro);
        db.execSQL(T_MotivoParada.Create_MotivoParada);
        db.execSQL(T_LineaParadas.Create_LineaParadas);
        db.execSQL(T_MesasPorLinea.CREATE_T_MESALINEA);
        db.execSQL(T_Agrupador.CREATE_T_AGRUPADOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DROP TABLES
        db.execSQL(T_Rendimiento.DROP_T_RENDIMIENTO);
        db.execSQL(T_IngresoJabas.DROP_T_INGRESOJABAS);
        //----------------------------------->
        db.execSQL(T_Usuario.DROP_T_USUARIO);
        db.execSQL(T_Menu.DROP_T_MENU);
        db.execSQL(T_MenuUsuario.DROP_T_MENUUSUARIO);
        //----------------------------------->
        db.execSQL(T_Consumidor.DROP_T_CONSUMIDOR);
        db.execSQL(T_Variedad.DROP_T_VARIEDAD);
        db.execSQL(T_Cultivo.DROP_T_CULTIVO);
        db.execSQL(T_Estado.DROP_T_ESTADO);
        db.execSQL(T_Linea.DROP_T_LINEA);
        db.execSQL(T_Sucursal.DROP_T_SUCURSAL);
        db.execSQL(T_Personal.DROP_T_PERSONAL);
        db.execSQL(T_Presentacion.DROP_T_PRESENTACION);
        db.execSQL(T_Empresa.DROP_T_EMPRESA);
        db.execSQL(T_EmpresaUsuario.DROP_T_EMPRESAUSUARIO);
        db.execSQL(T_TareoOrigen.DROP_T_TAREO_ORIGEN);
        db.execSQL(T_TareoTipo.DROP_T_TAREO_TIPO);
        db.execSQL(T_TareoSubTipo.DROP_T_TAREOSUBTIPO);
        db.execSQL(T_Responsable.DROP_T_RESPONSABLE);
        db.execSQL(T_Actividad.DROP_T_ACTIVIDAD);
        db.execSQL(T_Labor.DROP_T_LABOR);
        db.execSQL(T_Tareo.DROP_T_TAREO);
        db.execSQL(T_Proceso.DROP_T_PROCESO);
        db.execSQL(T_Subproceso.DROP_T_SUBPROCESO);
        db.execSQL(T_TareoDetalle.DROP_T_TAREODETALLE);
        db.execSQL(T_MesasPorLinea.DROP_T_MESALINEA);

        //Nuevas tablas
        db.execSQL(T_LineaRegistro.Drop_LineaRegistro);
        db.execSQL(T_MotivoParada.Drop_MotivoParada);
        db.execSQL(T_LineaParadas.Drop_LineaParadas);
        db.execSQL(T_Agrupador.DROP_T_AGRUPADOR);
        //NEW

        onCreate(db);
        }
}

