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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.ConexionBD;
import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Linea;
import com.adr.rendimientoplanta.DATA.T_LineaIngreso;
import com.adr.rendimientoplanta.DATA.T_LineaParadas;
import com.adr.rendimientoplanta.DATA.T_LineaRegistro;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class IngresoJabas_Grilla extends AppCompatActivity {

    //Variables para la sincronización - LineaRegistro
    private String HoraFin="";
    private int LinReg_IdServidor;
    private int LinReg_IdMovil;

    //

    private TextView lblSucursal;
    private TextView lblCultivo;
    private TextView lblFecha;
    private TextView lblEmpresa;

    private GridView dgvLineas;
    private ImageButton imbConfigurar;

    private Button btnSincronizar;
    SimpleCursorAdapter adspnLineas;

    private ImageButton imbRegresar;

    private Funciones fnc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_jabas_grilla);

        LocalBD LBD = new LocalBD(IngresoJabas_Grilla.this) ;
        final SQLiteDatabase LocBD = LBD.getWritableDatabase();

        lblCultivo = (TextView) findViewById(R.id.lblCultivo);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblFecha = (TextView) findViewById(R.id.edtFecha);

        btnSincronizar = (Button) findViewById(R.id.btnSincronizar);

        lblFecha.setText(Variables.FechaStr);
        imbConfigurar = (ImageButton) findViewById(R.id.imbConfigurar);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblCultivo.setText(Variables.Cul_Descripcion);
        lblEmpresa.setText(Variables.Emp_Abrev);

        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);

        fnc= new Funciones();

        dgvLineas = (GridView) findViewById(R.id.dgvLineas);
        Cursor CurLineas = LocBD.rawQuery(T_Linea._SELECT_LIN(Variables.Suc_Id,2),null);

        adspnLineas = new SimpleCursorAdapter(IngresoJabas_Grilla.this,
                android.R.layout.simple_dropdown_item_1line,CurLineas,
                new String[]{"Lin_Descripcion"}, new int[]{android.R.id.text1},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        dgvLineas.setAdapter(adspnLineas);


        dgvLineas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent ActividadModificar = new Intent(IngresoJabas_Grilla.this, IngresoJabas_RegistroLinea.class);
                Cursor CursorCarga = (Cursor) parent.getItemAtPosition(position);
                Variables.Lin_Id = CursorCarga.getInt(CursorCarga.getColumnIndex("_id"));
                Variables.Lin_Descripcion = CursorCarga.getString(CursorCarga.getColumnIndex("Lin_Descripcion"));

                startActivity(ActividadModificar);

            }
        });

        btnSincronizar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){

                Cursor CurReg = LocBD.rawQuery(T_LineaRegistro.LineaRegistro_SeleccionarSincronizar(
                        Variables.FechaStr,Variables.Suc_Id,Variables.Cul_Id),null);
                boolean Resultado = true;
                try {
                    Toast.makeText(IngresoJabas_Grilla.this, "REGISTROS "+CurReg.getCount(),Toast.LENGTH_SHORT).show();

                    Connection Cnn = ConexionBD.getInstance().getConnection();
                    Statement Stmt = Cnn.createStatement();
                    ResultSet Rse;

                    if (CurReg.getCount()!=0)
                    {
                        //Si tiene registros, recorrer
                        for (CurReg.moveToFirst();!CurReg.isAfterLast();CurReg.moveToNext())
                        {

                            LinReg_IdMovil=CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegIdMovil));

                            HoraFin =CurReg.getString(CurReg.getColumnIndex(T_LineaRegistro.LinRegHoraFin));
                            if (HoraFin.equals("--"))
                            {
                                HoraFin="00:00:00";
                            }
                            Rse=null;
                            //Recorrer los registros para sincronizar
                            Rse= Stmt.executeQuery(T_LineaRegistro.LineaRegistro_SeleccionarLinea(
                                    CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinId)),
                                    Variables.FechaStr));
                            //Toast.makeText(IngresoJabas_Grilla.this, "Registros Servidor: "+Rse.getRow(),Toast.LENGTH_SHORT).show();
                            if (Rse.next())
                            {

                                //Si devuelve valor, actualizar
                                LinReg_IdServidor = CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegId));

                                Resultado = Stmt.execute(T_LineaRegistro.LineaRegistro_ActualizarServidor(
                                        LinReg_IdServidor,
                                        HoraFin,
                                        CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegCantidad)),
                                        CurReg.getDouble(CurReg.getColumnIndex(T_LineaRegistro.LinRegHoraEfectiva)),
                                        CurReg.getDouble(CurReg.getColumnIndex(T_LineaRegistro.LinRegParadas)),
                                        CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegNumParadas)),
                                        CurReg.getDouble(CurReg.getColumnIndex(T_LineaRegistro.LinRegCantidadPorHora)),
                                        fnc.HoraSistema(),
                                        CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.EstId))
                                ));
                                if (Resultado == false)
                                {
                                    Cursor CurPar = LocBD.rawQuery(T_LineaParadas.LineaParadas_SeleccionarSincronizar(LinReg_IdMovil,0),null);
                                    for (CurPar.moveToFirst();!CurPar.isAfterLast();CurPar.moveToNext()) {

                                        Resultado = Stmt.execute(T_LineaParadas.LineaParadas_InsertarServidor(
                                                LinReg_IdServidor,
                                                CurPar.getInt(CurPar.getColumnIndex(T_LineaParadas.MotId)),
                                                CurPar.getString(CurPar.getColumnIndex(T_LineaParadas.LinParHoraIni)),
                                                CurPar.getString(CurPar.getColumnIndex(T_LineaParadas.LinParHoraFin)),
                                                CurPar.getDouble(CurPar.getColumnIndex(T_LineaParadas.LinParParada)),
                                                CurPar.getInt(CurPar.getColumnIndex(T_LineaParadas.LinParSincronizado)),
                                                CurPar.getString(CurPar.getColumnIndex(T_LineaParadas.LinParFechaHora)),
                                                CurPar.getString(CurPar.getColumnIndex(T_LineaParadas.MotParDescripcion))
                                        ));
                                        if (Resultado == false) {
                                            //Toast.makeText(IngresoJabas_Grilla.this, "SINCRONIZACION COMPLETA "
                                            //        +CurReg.getCount(),Toast.LENGTH_SHORT).show();
                                            //Revisar - URGENTE
                                            LocBD.execSQL(T_LineaParadas.LineaParadas_ActualizarSincronizado(
                                                    CurPar.getInt(CurPar.getColumnIndex(BaseColumns._ID)), 1));
                                        } else {
                                            Toast.makeText(IngresoJabas_Grilla.this, "ERROR AL SINCRONIZAR "
                                                    + CurReg.getCount(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    Toast.makeText(IngresoJabas_Grilla.this, "SINCRONIZACION COMPLETA "
                                            +CurReg.getCount(),Toast.LENGTH_SHORT).show();

                                }
                                else
                                {
                                    Toast.makeText(IngresoJabas_Grilla.this, "ERROR AL SINCRONIZAR "
                                            +CurReg.getCount(),Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                    //Si no devuelve valor, insertar
                                    Resultado =Stmt.execute(T_LineaRegistro.LineaRegistro_InsertarServidor(
                                            CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegIdMovil)),
                                            CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinId)),
                                            CurReg.getString(CurReg.getColumnIndex(T_LineaRegistro.LinRegFecha)),
                                            CurReg.getString(CurReg.getColumnIndex(T_LineaRegistro.LinRegHoraIni)),
                                            HoraFin,
                                            CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegCantidad)),
                                            CurReg.getDouble(CurReg.getColumnIndex(T_LineaRegistro.LinRegHoraEfectiva)),
                                            CurReg.getDouble(CurReg.getColumnIndex(T_LineaRegistro.LinRegParadas)),
                                            CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegNumParadas)),
                                            CurReg.getDouble(CurReg.getColumnIndex(T_LineaRegistro.LinRegCantidadPorHora)),
                                            CurReg.getString(CurReg.getColumnIndex(T_LineaRegistro.LinRegMac)),
                                            CurReg.getString(CurReg.getColumnIndex(T_LineaRegistro.LinRegFechaHora)),
                                            //CurReg.getString(CurReg.getColumnIndex(T_LineaRegistro.LinRegUltimaSincro)),
                                            fnc.HoraSistema(),
                                            CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.EstId)),
                                            CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.UsuId)),
                                            CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.SucId)),
                                            CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.CulId))
                                    ));
                                    if (Resultado==false)
                                    {
                                        //Si se realiza la inserción correctamente, se procede a actualizar
                                        //el registro local y asignarle el Id generado en el servidor.
                                        Rse=null;
                                        Rse = Stmt.executeQuery(T_LineaRegistro.LineaRegistro_SeleccionarIdServidor(
                                                Variables.FechaStr,Variables.Suc_Id,Variables.Cul_Id,LinReg_IdMovil));
                                        Rse.next();
                                        //Actualiza a la tabla local el Id generado en el servidor
                                        LinReg_IdServidor = Rse.getInt(Rse.findColumn(T_LineaRegistro.LinRegId));

                                        LocBD.execSQL(T_LineaRegistro.LineaRegistro_ActualizarIdServidor(
                                                CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegIdMovil))
                                                ,LinReg_IdServidor
                                        ));
                                        //Actualiza a la tabla local la ultima Hora sincronización
                                        LocBD.execSQL(T_LineaRegistro.LineaRegistro_ActualizarHoraSincro(
                                                CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegIdMovil))
                                                ,fnc.HoraSistema()
                                        ));
                                        Cursor CurPar = LocBD.rawQuery(T_LineaParadas.LineaParadas_SeleccionarSincronizar(LinReg_IdMovil,0),null);
                                        for (CurPar.moveToFirst();!CurPar.isAfterLast();CurPar.moveToNext()) {

                                            Resultado = Stmt.execute(T_LineaParadas.LineaParadas_InsertarServidor(
                                                    LinReg_IdServidor,
                                                    CurPar.getInt(CurPar.getColumnIndex(T_LineaParadas.MotId)),
                                                    CurPar.getString(CurPar.getColumnIndex(T_LineaParadas.LinParHoraIni)),
                                                    CurPar.getString(CurPar.getColumnIndex(T_LineaParadas.LinParHoraFin)),
                                                    CurPar.getDouble(CurPar.getColumnIndex(T_LineaParadas.LinParParada)),
                                                    CurPar.getInt(CurPar.getColumnIndex(T_LineaParadas.LinParSincronizado)),
                                                    CurPar.getString(CurPar.getColumnIndex(T_LineaParadas.LinParFechaHora)),
                                                    CurPar.getString(CurPar.getColumnIndex(T_LineaParadas.MotParDescripcion))
                                            ));
                                            if (Resultado==false)
                                            {
                                                //Toast.makeText(IngresoJabas_Grilla.this, "SINCRONIZACION COMPLETA "
                                                //        +CurReg.getCount(),Toast.LENGTH_SHORT).show();
                                                //Revisar - URGENTE
                                                LocBD.execSQL(T_LineaParadas.LineaParadas_ActualizarSincronizado(
                                                CurPar.getInt(CurPar.getColumnIndex(BaseColumns._ID)),1));
                                            }else
                                            {
                                                Toast.makeText(IngresoJabas_Grilla.this, "ERROR AL SINCRONIZAR "
                                                        +CurReg.getCount(),Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        Toast.makeText(IngresoJabas_Grilla.this, "SINCRONIZACION COMPLETA "
                                                +CurReg.getCount(),Toast.LENGTH_SHORT).show();

                                    }else
                                    {
                                        Toast.makeText(IngresoJabas_Grilla.this, "ERROR AL SINCRONIZAR "
                                                +CurReg.getCount(),Toast.LENGTH_SHORT).show();
                                    }
                            }
                        }
                    }

                }catch (Exception e) {
                    Toast.makeText(IngresoJabas_Grilla.this, e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        imbRegresar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                //TextView HORA inicio
                //lblHoraInicio.setText(Formato.format(new Date(System.currentTimeMillis())));
                Intent NuevaActividad = new Intent(IngresoJabas_Grilla.this,Principal_Menu.class);
                startActivity(NuevaActividad);
            }
        });
        imbConfigurar.setOnClickListener(new View.OnClickListener()
                                         {
                                             @Override
                                             public void onClick (View v){
            //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
            Intent ActividadNueva = new Intent(IngresoJabas_Grilla.this, IngresoJabas_Parametros.class);
            //Se inicia la actividad nueva
            startActivity(ActividadNueva);
            }
            }
        );
    }
}
