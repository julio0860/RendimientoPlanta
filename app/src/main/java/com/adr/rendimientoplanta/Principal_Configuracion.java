package com.adr.rendimientoplanta;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.ConexionBD;
import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Actividad;
import com.adr.rendimientoplanta.DATA.T_Consumidor;
import com.adr.rendimientoplanta.DATA.T_Cultivo;
import com.adr.rendimientoplanta.DATA.T_Empresa;
import com.adr.rendimientoplanta.DATA.T_EmpresaUsuario;
import com.adr.rendimientoplanta.DATA.T_Estado;
import com.adr.rendimientoplanta.DATA.T_IngresoJabas;
import com.adr.rendimientoplanta.DATA.T_Labor;
import com.adr.rendimientoplanta.DATA.T_Linea;
import com.adr.rendimientoplanta.DATA.T_Menu;
import com.adr.rendimientoplanta.DATA.T_MenuUsuario;
import com.adr.rendimientoplanta.DATA.T_Mesa;
import com.adr.rendimientoplanta.DATA.T_MesasPorLinea;
import com.adr.rendimientoplanta.DATA.T_MotivoParada;
import com.adr.rendimientoplanta.DATA.T_Personal;
import com.adr.rendimientoplanta.DATA.T_Presentacion;
import com.adr.rendimientoplanta.DATA.T_Proceso;
import com.adr.rendimientoplanta.DATA.T_Responsable;
import com.adr.rendimientoplanta.DATA.T_Subproceso;
import com.adr.rendimientoplanta.DATA.T_Sucursal;
import com.adr.rendimientoplanta.DATA.T_Tareo;
import com.adr.rendimientoplanta.DATA.T_TareoDetalle;
import com.adr.rendimientoplanta.DATA.T_TareoOrigen;
import com.adr.rendimientoplanta.DATA.T_TareoSubTipo;
import com.adr.rendimientoplanta.DATA.T_TareoTipo;
import com.adr.rendimientoplanta.DATA.T_Usuario;
import com.adr.rendimientoplanta.DATA.T_Variedad;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Principal_Configuracion extends AppCompatActivity {

    private Button btnCargarDatos;
    private Button btnDescargarDatos;
    private CheckBox cbxIngresoJabas;
    private CheckBox cbxTareo;

    private SimpleAdapter AdaptadorLista;
    private static final String TAG = "Principal_Configuracion";
    private ListView ListaRendimiento;

    private TextView txtFecha;

    //TABLAS A SINCRONIZAR
    private CheckBox cbxSucursal;
    private CheckBox cbxConsumidor;
    private CheckBox cbxCultivo;
    private CheckBox cbxVariedad;
    private CheckBox cbxAgrupacion;
    private CheckBox cbxPresentacion;
    private CheckBox cbxLinea;
    private CheckBox cbxPersonal;
    private CheckBox cbxEstado;
    private CheckBox cbxUsuario;
    private CheckBox cbxMenu;
    private CheckBox cbxMenuUsuario;

    private CheckBox cbxEmpresa;
    private CheckBox cbxEmpresaUsuario;
    private CheckBox cbxTareoOrigen;
    private CheckBox cbxTareoTipo;
    private CheckBox cbxTareoSubTipo;
    private CheckBox cbxResponsable;
    private CheckBox cbxActividad;
    private CheckBox cbxLabor;
    private CheckBox cbxProceso;
    private CheckBox cbxSubProceso;
    private CheckBox cbxMotivoParadas;
    private CheckBox cbxMesasPorLinea;
    private CheckBox cbxMesa;

    private CheckBox cbxMarcarTodas;

    private ProgressBar pgbCargaDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_configuracion);

        //ASIGNACION DE VARIABLES A CONTROLES DEL LAYOUT
        cbxSucursal = (CheckBox) findViewById(R.id.cbxSucursal);
        cbxConsumidor = (CheckBox) findViewById(R.id.cbxConsumidor);
        cbxCultivo = (CheckBox) findViewById(R.id.cbxCultivo);
        cbxVariedad = (CheckBox) findViewById(R.id.cbxVariedad);
        cbxAgrupacion = (CheckBox) findViewById(R.id.cbxAgrupacion);
        cbxPresentacion = (CheckBox) findViewById(R.id.cbxPresentacion);
        cbxLinea = (CheckBox) findViewById(R.id.cbxLinea);
        cbxPersonal = (CheckBox) findViewById(R.id.cbxPersonal);
        cbxEstado = (CheckBox) findViewById(R.id.cbxEstado);
        cbxUsuario = (CheckBox) findViewById(R.id.cbxUsuario);
        cbxMenu = (CheckBox) findViewById(R.id.cbxMenu);
        cbxMenuUsuario = (CheckBox) findViewById(R.id.cbxMenuUsuario);

        cbxEmpresa = (CheckBox) findViewById(R.id.cbxEmpresa);
        cbxEmpresaUsuario= (CheckBox) findViewById(R.id.cbxEmpresaUsuario);
        cbxTareoOrigen= (CheckBox) findViewById(R.id.cbxTareoOrigen);
        cbxTareoTipo= (CheckBox) findViewById(R.id.cbxTareoTipo);
        cbxTareoSubTipo= (CheckBox) findViewById(R.id.cbxTareoSubTipo);
        cbxResponsable= (CheckBox) findViewById(R.id.cbxResponsable);
        cbxActividad= (CheckBox) findViewById(R.id.cbxActividad);
        cbxLabor= (CheckBox) findViewById(R.id.cbxLabor);

        //agregado por Jcasas
        //-----------------------------------------------------------
        cbxProceso=(CheckBox) findViewById(R.id.cbxProceso);
        cbxSubProceso=(CheckBox)findViewById(R.id.cbxSubProceso) ;
        cbxMesasPorLinea=(CheckBox)findViewById(R.id.cbxMesasPorLinea);
        cbxMesa=(CheckBox)findViewById(R.id.cbxMesa);
        //-----------------------------------------------------------
        //SMP:
        cbxMotivoParadas = (CheckBox) findViewById(R.id.cbxMotivoParadas);
        //
        cbxMarcarTodas = (CheckBox) findViewById(R.id.cbxMarcarTodas);

        //
        pgbCargaDatos = (ProgressBar) findViewById(R.id.pgbCargaDatos);
        Variables vg;
        btnDescargarDatos = (Button) findViewById(R.id.btnDescargarDatos);
        btnCargarDatos = (Button) findViewById(R.id.btnCargarDatos);
        ListaRendimiento = (ListView) findViewById(R.id.ListaRendimiento);

        cbxIngresoJabas = (CheckBox) findViewById(R.id.cbxIngresoJabas);
        cbxTareo = (CheckBox)findViewById(R.id.cbxTareo);

        txtFecha = (TextView) findViewById(R.id.edtFecha);

        LocalBD LBD = new LocalBD(Principal_Configuracion.this);
        final SQLiteDatabase LocBD = LBD.getWritableDatabase();

        Calendar Cal = new GregorianCalendar();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        txtFecha.setText(df.format(Cal.getInstance().getTime()).toString());

        cbxMarcarTodas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                MarcarCheck(isChecked);
            }
        });
        btnDescargarDatos.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              try
              {
                  Boolean Estado = false;
                  Connection Cnn = ConexionBD.getInstance().getConnection();
                  Statement Stmt = Cnn.createStatement();
                  ResultSet Rse;

                  Rse=null;
                  if (cbxUsuario.isChecked())
                  {
                      LocBD.execSQL(T_Usuario._DELETE());
                      Rse = Stmt.executeQuery(T_Usuario._SELECT_SYNC(1,2));
                      while (Rse.next()) {
                          try {
                              LocBD.execSQL(T_Usuario._INSERT(Rse.getInt(1),Rse.getString(2),Rse.getString(3)
                                      ,Rse.getString(4),Rse.getString(5),Rse.getInt(6),Rse.getString(7)
                                      ,Rse.getInt(8)));
                              Estado = true;
                          } catch (Exception e) {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxMenu.isChecked())
                  {
                      LocBD.execSQL(T_Menu._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Menu._SELECT_SYNC("AMP",2));
                      while (Rse.next())
                      {
                          try {
                              LocBD.execSQL(T_Menu._INSERT(Rse.getString(1),Rse.getString(2),Rse.getString(3)
                                      ,Rse.getString(4),Rse.getString(5),Rse.getInt(6)));
                              Estado = true;
                          } catch (Exception e) {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                 if (cbxMenuUsuario.isChecked())
                 {
                     LocBD.execSQL(T_MenuUsuario._DELETE());
                     Rse=null;
                     Rse = Stmt.executeQuery(T_MenuUsuario._SELECT_MODULO("AMP"));
                     while (Rse.next())
                     {
                         try
                         {
                             //Rse.next();
                             LocBD.execSQL(T_MenuUsuario._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getString(3)
                                     ,Rse.getInt(4),Rse.getInt(5),Rse.getInt(6),Rse.getInt(7),Rse.getInt(8)
                                     ,Rse.getInt(9),Rse.getInt(10),Rse.getInt(11),Rse.getInt(12)));
                             Estado = true;
                         }catch (Exception e)
                         {
                             Estado= false;
                             Log.e(TAG, "Error Exception: " + e);
                             Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                         }
                     }
                 }
                  if (cbxPersonal.isChecked())
                  {
                      LocBD.execSQL(T_Personal._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Personal._SELECT_PER(-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_Personal._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getInt(3),Rse.getString(4)
                                      ,Rse.getString(5),Rse.getString(6),Rse.getString(7),Rse.getString(8),Rse.getString(9)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxPresentacion.isChecked())
                  {
                      LocBD.execSQL(T_Presentacion._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Presentacion._SELECT_PRE(-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_Presentacion._INSERT(Rse.getInt(1),Rse.getString(2)
                                      ,Rse.getString(3),Rse.getInt(4),Rse.getInt(5),Rse.getDouble(6)
                                      ,Rse.getDouble(7)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                 if (cbxEstado.isChecked())
                 {
                     LocBD.execSQL(T_Estado._DELETE());
                     Rse=null;
                     Rse = Stmt.executeQuery(T_Estado._SELECT_EST(""));
                     while (Rse.next())
                     {
                         try
                         {
                             //Rse.next();
                             LocBD.execSQL(T_Estado._INSERT(Rse.getInt(1),Rse.getString(2),Rse.getString(3)));
                             Estado = true;
                         }catch (Exception e)
                         {
                             Estado= false;
                             Log.e(TAG, "Error Exception: " + e);
                             Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                         }
                     }
                 }
                  if (cbxConsumidor.isChecked())
                  {
                      LocBD.execSQL(T_Consumidor._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Consumidor._SELECT_CON(-1,2,-1,-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_Consumidor._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getInt(3),Rse.getInt(4)
                                      ,Rse.getString(5),Rse.getString(6),Rse.getString(7),Rse.getInt(8)
                                      ,Rse.getInt(9),Rse.getString(10),Rse.getInt(11)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxVariedad.isChecked())
                  {
                      LocBD.execSQL(T_Variedad._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Variedad._SELECT_VAR(-1,-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_Variedad._INSERT(Rse.getInt(1),Rse.getInt(2)
                                      ,Rse.getString(3),Rse.getString(4),Rse.getInt(5)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxCultivo.isChecked())
                  {
                      LocBD.execSQL(T_Cultivo._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Cultivo._SELECT_CULT(-1,2));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_Cultivo._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getString(3),Rse.getString(4)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxLinea.isChecked())
                  {
                      LocBD.execSQL(T_Linea._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Linea._SELECT_LIN(-1,2));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_Linea._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getInt(3),Rse.getInt(4)
                                      ,Rse.getString(5),Rse.getString(6)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxSucursal.isChecked())
                  {
                      LocBD.execSQL(T_Sucursal._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Sucursal._SELECT_SUC(-1,2));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_Sucursal._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getInt(3)
                                      ,Rse.getString(4),Rse.getString(5),Rse.getString(6),Rse.getString(7)
                                      ,Rse.getInt(8),Rse.getString(9)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxEmpresa.isChecked())
                  {
                      LocBD.execSQL(T_Empresa._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Empresa._SELECT_EMP(-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_Empresa._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getString(3)
                                      ,Rse.getString(4),Rse.getString(5),Rse.getString(6)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxEmpresaUsuario.isChecked())
                  {
                      LocBD.execSQL(T_EmpresaUsuario._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_EmpresaUsuario._SELECT_EMPUSU(-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_EmpresaUsuario._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getInt(3)
                                      ,Rse.getInt(4)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxTareoOrigen.isChecked())
                  {
                      LocBD.execSQL(T_TareoOrigen._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_TareoOrigen._SELECT_TAREO_ORIGEN(-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_TareoOrigen._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getInt(3)
                                      ,Rse.getString(4),Rse.getString(5)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxTareoTipo.isChecked())
                  {
                      LocBD.execSQL(T_TareoTipo._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_TareoTipo._SELECT_TAREO_TIPO(-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_TareoTipo._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getInt(3)
                                      ,Rse.getString(4),Rse.getString(5)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxTareoSubTipo.isChecked())
                  {
                      LocBD.execSQL(T_TareoSubTipo._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_TareoSubTipo._SELECT_TAREOSUBTIPO(-1,-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_TareoSubTipo._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getInt(3)
                                      ,Rse.getInt(4),Rse.getString(5),Rse.getString(6)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxResponsable.isChecked())
                  {
                      LocBD.execSQL(T_Responsable._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Responsable._SELECT_RESPONSABLE(-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_Responsable._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getString(3)
                                      ,Rse.getString(4),Rse.getString(5),Rse.getInt(6),Rse.getInt(7),Rse.getString(8)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxActividad.isChecked())
                  {
                      LocBD.execSQL(T_Actividad._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Actividad._SELECT_ACTIVIDAD(-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_Actividad._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getString(3)
                                      ,Rse.getString(4),Rse.getInt(5)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxLabor.isChecked())
                  {
                      LocBD.execSQL(T_Labor._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Labor._SELECT_LABOR(-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_Labor._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getInt(3),Rse.getString(4)
                                      ,Rse.getString(5),Rse.getInt(6)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxMotivoParadas.isChecked())
                  {
                      LocBD.execSQL(T_MotivoParada.Eliminar());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_MotivoParada.MotivoParada_SeleccionarTodos());
                      while (Rse.next())
                      {
                          try
                          {
                              LocBD.execSQL(T_MotivoParada.MotivoParada_Insertar(Rse.getInt(1),
                                      Rse.getString(2),Rse.getInt(3),Rse.getInt(4)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxProceso.isChecked())
                  {
                      LocBD.execSQL(T_Proceso._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Proceso._SELECT_PROCESO(-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_Proceso._INSERT(Rse.getInt(1),Rse.getString(2)
                                      ,Rse.getString(3)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxSubProceso.isChecked())
                  {
                      LocBD.execSQL(T_Subproceso._DELETE());
                      Rse=null;
                      Rse = Stmt.executeQuery(T_Subproceso._SELECT_SUBPROCESO(1,-1));
                      while (Rse.next())
                      {
                          try
                          {
                              //Rse.next();
                              LocBD.execSQL(T_Subproceso._INSERT(Rse.getInt(1),Rse.getInt(2)
                                      ,Rse.getString(3)));
                              Estado = true;
                          }catch (Exception e)
                          {
                              Estado= false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  if (cbxMesasPorLinea.isChecked()) {
                      LocBD.execSQL(T_MesasPorLinea._DELETE());
                      Rse = null;
                      Rse = Stmt.executeQuery(T_MesasPorLinea._SELECT_MESALINEA(-1));
                      while (Rse.next()) {
                          try {
                              //Rse.next();
                              LocBD.execSQL(T_MesasPorLinea._INSERT(Rse.getInt(1), Rse.getInt(2)
                                      , Rse.getInt(3),Rse.getInt(4),Rse.getInt(5), Rse.getString(6).trim(),Rse.getInt(7)));
                              Estado = true;
                          } catch (Exception e) {
                              Estado = false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this, "ERROR AL SINCRONIZAR" + e.toString(), Toast.LENGTH_SHORT).show();
                          }
                      }
                  }

                  if (cbxMesa.isChecked()) {
                      LocBD.execSQL(T_Mesa._DELETE());
                      Rse = null;
                      Rse = Stmt.executeQuery(T_Mesa._SELECT_MESA());
                      while (Rse.next()) {
                          try {
                              //Rse.next();
                              LocBD.execSQL(T_Mesa._INSERT(Rse.getInt(1), Rse.getString(2)
                                    ));
                              Estado = true;
                          } catch (Exception e) {
                              Estado = false;
                              Log.e(TAG, "Error Exception: " + e);
                              Toast.makeText(Principal_Configuracion.this, "ERROR AL SINCRONIZAR" + e.toString(), Toast.LENGTH_SHORT).show();
                          }
                      }
                  }
                  //VALIDACION
                  if (Estado==true)
                  {
                      Toast.makeText(Principal_Configuracion.this,"SINCRONIZACION REALIZADA CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                      Stmt.close();
                      //Cnn.close();
                      Rse.close();
                  }else
                  {
                      Toast.makeText(Principal_Configuracion.this,"OCURRIO UN ERROR AL REALIZAR LA SINCRONIZACION",Toast.LENGTH_SHORT).show();
                  }
              } catch (Exception e) {
                  Log.e(TAG, "Error Exception: " + e);
              }
          }
      });


        btnCargarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String MAC;
                String IMEI;
                //OBTENCION DEL IMEI
                TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                IMEI=telephonyManager.getDeviceId();
                //OBTENCION DE LA MAC
                WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = manager.getConnectionInfo();
                MAC = info.getMacAddress();



                if (cbxIngresoJabas.isChecked())
                {
                    Cursor Cur = null;
                    Cursor CurExiste = null;
                    if (LocBD != null)
                    {
                        Cur = LocBD.rawQuery(T_IngresoJabas._SELECT_SYNC(-1,2),null);
                        Boolean Resultado=true;
                        try {
                            Connection Cnn = ConexionBD.getInstance().getConnection();
                            Statement Stmt = Cnn.createStatement();
                            Statement StmtExist = Cnn.createStatement();
                            ResultSet Rse;
                            //ResultSet Rse = Stmt.executeQuery("Select Per_Id,Per_Codigo," +
                            //        "Per_ApePaterno,Per_ApeMaterno,Per_Nombres From Personal");
                            Toast.makeText(Principal_Configuracion.this, "REGISTROS "+Cur.getCount(),Toast.LENGTH_SHORT).show();


                            if (Cur.getCount()!=0)
                            {

                                for (Cur.moveToFirst();!Cur.isAfterLast();Cur.moveToNext()) {

                                    //CurExiste = LocBD.rawQuery(T_IngresoJabas._SELECT_EXIST(Cur.getInt(0),IMEI,Cur.getString(13)),null);
                                    Rse = StmtExist.executeQuery(T_IngresoJabas._SELECT_EXIST(Cur.getInt(0),IMEI,Cur.getString(13)));
                                    if (Rse.wasNull()==true)
                                    {
                                        Resultado = Stmt.execute(T_IngresoJabas._INSERT_SYNC(
                                                Cur.getInt(0)
                                                ,Cur.getString(1)
                                                ,Cur.getString(2)
                                                ,Cur.getString(3)
                                                ,Cur.getInt(4)
                                                ,Cur.getString(5)
                                                ,Cur.getString(6)
                                                ,Cur.getString(7)
                                                ,Cur.getInt(8)
                                                ,Cur.getInt(9)
                                                ,Cur.getInt(10)
                                                ,Cur.getInt(11)
                                                ,1,
                                                MAC,
                                                IMEI,
                                                Cur.getString(13)
                                                //ADICIONAL
                                                ,Cur.getInt(14),
                                                Cur.getInt(15),
                                                Cur.getInt(16),
                                                Cur.getInt(17),
                                                Cur.getString(18),
                                                Cur.getInt(19),
                                                Cur.getInt(20),
                                                Cur.getInt(21),
                                                Variables.Usu_Id
                                        ));

                                        //LocBD.execSQL(T_IngresoJabas._UPDATE_SYNCSTATE(Cur.getInt(0),0));
                                        if (Resultado==false)
                                        {
                                            LocBD.execSQL(T_IngresoJabas._UPDATE_SYNCSTATE(Cur.getInt(0),1));
                                        }
                                        else
                                        {
                                            Toast.makeText(Principal_Configuracion.this, "ERROR AL SINCRONIZAR",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        Rse.next();
                                        Resultado = Stmt.execute(T_IngresoJabas._UPDATE_SYNCSQL(
                                                Rse.getInt("Ing_Id")
                                                ,Cur.getString(1)
                                                ,Cur.getString(2)
                                                ,Cur.getString(3)
                                                ,Cur.getInt(4)
                                                ,Cur.getString(5)
                                                ,Cur.getString(6)
                                                ,Cur.getString(7)
                                                ,Cur.getInt(8)
                                                ,Cur.getInt(9)
                                                ,Cur.getInt(10)
                                                ,Cur.getInt(11)
                                                ,1
                                                //ADICIONAL
                                                ,Cur.getInt(14),
                                                Cur.getInt(15),
                                                Cur.getInt(16),
                                                Cur.getInt(17),
                                                Cur.getString(18),
                                                Cur.getInt(19),
                                                Cur.getInt(20),
                                                Cur.getInt(21),
                                                Variables.Usu_Id
                                        ));

                                        //LocBD.execSQL(T_IngresoJabas._UPDATE_SYNCSTATE(Cur.getInt(0),0));
                                        if (Resultado==false)
                                        {
                                            LocBD.execSQL(T_IngresoJabas._UPDATE_SYNCSTATE(Cur.getInt(0),1));
                                        }
                                        else
                                        {
                                            Toast.makeText(Principal_Configuracion.this, "ERROR AL ACTUALIZAR",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }
                                if (Resultado==false)
                                {
                                    Toast.makeText(Principal_Configuracion.this, "SINCRONIZACION COMPLETADA",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(Principal_Configuracion.this, "NO SE HA PODIDO SINCRONIZAR, REVISE EL REGISTRO",Toast.LENGTH_SHORT).show();
                                }
                            }else
                            {
                                Toast.makeText(Principal_Configuracion.this, "NO HAY REGISTROS A SINCRONIZAR",Toast.LENGTH_SHORT).show();
                            }
                            Stmt.close();
                            //Cnn.close();

                        } catch (Exception e) {
                            Toast.makeText(Principal_Configuracion.this,e.toString(),Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Error Exception: " + e);

                        }
                        Cur =null;
                    }
                }
                if (cbxTareo.isChecked())
                {
                    boolean Resultado=true;
                    //SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                    String Error="";
                    try {
                        Cursor curTareo=LocBD.rawQuery(T_Tareo._SELECT_TAREOSYNC(2),null);
                        Connection Cnn = ConexionBD.getInstance().getConnection();
                        Statement Stmt = Cnn.createStatement();

                        ResultSet Rse;
                        //ResultSet Rse = Stmt.executeQuery("Select Per_Id,Per_Codigo," +
                        //        "Per_ApePaterno,Per_ApeMaterno,Per_Nombres From Personal");
                        Toast.makeText(Principal_Configuracion.this, "REGISTROS " + curTareo.getCount(), Toast.LENGTH_SHORT).show();


                        if (curTareo.getCount() != 0) {

                            for (curTareo.moveToFirst(); !curTareo.isAfterLast(); curTareo.moveToNext()) {

                               Resultado= Stmt.execute("EXEC Tareo_InsertarMovil '"
                                        + curTareo.getInt(curTareo.getColumnIndex(BaseColumns._ID))+"','"
                                        + curTareo.getInt(curTareo.getColumnIndex(T_Tareo.EMPID))+"','"
                                        + curTareo.getInt(curTareo.getColumnIndex(T_Tareo.ESTID))+"','"
                                        + curTareo.getInt(curTareo.getColumnIndex(T_Tareo.TARORIID))+"','"
                                        + curTareo.getInt(curTareo.getColumnIndex(T_Tareo.TARTIPID))+"','"
                                        + curTareo.getInt(curTareo.getColumnIndex(T_Tareo.TARSUBTIPID))+"','"
                                        + curTareo.getInt(curTareo.getColumnIndex(T_Tareo.LINPROID))+"','"
                                        //+ curTareo.getInt(curTareo.getColumnIndex(T_Tareo.RESID))+"','"
                                       + 255+"','"
                                        + curTareo.getString(curTareo.getColumnIndex(T_Tareo.TARFECHA))+"','"
                                        + curTareo.getInt(curTareo.getColumnIndex(T_TareoDetalle.PERID))+"','"
                                        + curTareo.getInt(curTareo.getColumnIndex(T_Tareo.ACTID))+"','"
                                        + curTareo.getInt(curTareo.getColumnIndex(T_Tareo.LABID))+"','"
                                        + curTareo.getInt(curTareo.getColumnIndex(T_Tareo.CONID))+"','"
                                       //+ df.format(new StringBuilder().append(curTareo.getColumnIndex(T_Tareo.TARFECHA)).append(" ").append(curTareo.getColumnIndex(T_TareoDetalle.TARDETFECHAHORINI)))+"','"
                                        + new StringBuilder().append(curTareo.getString(curTareo.getColumnIndex(T_Tareo.TARFECHA))).append(" ").append(curTareo.getString(curTareo.getColumnIndex(T_TareoDetalle.TARDETFECHAHORINI)))+"','"
                                       + new StringBuilder().append(curTareo.getString(curTareo.getColumnIndex(T_Tareo.TARFECHA))).append(" ").append(curTareo.getString(curTareo.getColumnIndex(T_TareoDetalle.TARDETFECHAHORFIN)))+"','"
                                       //+ curTareo.getString(curTareo.getColumnIndex(T_TareoDetalle.TARDETFECHAHORFIN))+"','"
                                        + curTareo.getString(curTareo.getColumnIndex(T_TareoDetalle.PERFOTOCHECK))+"'"
                                );

                            }
                            if (Resultado==false)
                            {
                                Toast.makeText(Principal_Configuracion.this, "SINCRONIZACION COMPLETADA",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(Principal_Configuracion.this, "NO SE HA PODIDO SINCRONIZAR, REVISE EL REGISTRO",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Principal_Configuracion.this, "NO HAY REGISTROS A SINCRONIZAR",Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e)
                    {
                        Toast.makeText(Principal_Configuracion.this,e.toString(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(Principal_Configuracion.this,Error,Toast.LENGTH_SHORT).show();
                    }
                    }

            }});


       /* BtnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Connection Cnn = ConexionBD.getInstance().getConnection();
                    Statement Stmt = Cnn.createStatement();
                    ResultSet Rse = Stmt.executeQuery("Select Per_Id,Per_Codigo," +
                            "Per_ApePaterno,Per_ApeMaterno,Per_Nombres From Personal");

                    Cursor Temporal = null;
                    while (Rse.next()) {
                        try {
                       //     Temporal = LocBD.rawQuery(T_Personal._SELECT_CODIGO(Rse.getString(2)),null);

                        //   if (Temporal.getCount() >0)
                       //    {
                        //       LocBD.execSQL(T_Personal._UPDATE(Rse.getInt(1), Rse.getString(2), Rse.getString(3), Rse.getString(4), Rse.getString(5), 1));
                        //   }
                       //   else
                      //    {
                              LocBD.execSQL(T_Personal._INSERT(Rse.getInt(1), Rse.getString(2), Rse.getString(3), Rse.getString(4), Rse.getString(5), 1));
                       //   }
                        } catch (Exception e) {
                            Log.e(TAG, "Error Exception: " + e);
                        }
                    }
                    Rse.close();
                    Cnn.close();
                } catch (Exception e) {
                    Log.e(TAG, "Error Exception: " + e);
                }
            }
        });

        BtnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cursor Rse = null;
                    if (LocBD != null)
                    {
                        //String [] DNI= new String[1];
                        //DNI[1]="46821478";
                        //ResultSet Rse =  LocBD.execSQL("SELECT Per_Codigo,Per_Nombres FROM PERSONAL");
                        Rse = LocBD.rawQuery("SELECT Per_Codigo,Per_Nombres||' '||Per_ApePaterno||' '||Per_ApeMaterno FROM T_PERSONAL",null);
                    }

                    //Connection Cnn = ConexionBD.getInstance().getConnection();
                    //Statement Stmt = Cnn.createStatement();
                    //ResultSet Rse = Stmt.executeQuery("Select Res_Codigo,Res_Descripcion From Responsable");

                    List<Map<String, Object>> data = null;
                    data = new ArrayList<Map<String, Object>>();
                   //while (Rse.moveToNext()) {
                     //   Map<String, Object> valores = new HashMap<String, Object>();
                       // valores.put("A", Rse.getString(1));
                    //    valores.put("B", Rse.getString(2));
                    //    data.add(valores);
                   // }
                    for (Rse.moveToFirst();!Rse.isAfterLast();Rse.moveToNext()) {
                        Map<String, Object> valores = new HashMap<String, Object>();
                        valores.put("A", Rse.getString(0));
                        valores.put("B", Rse.getString(1));
                        data.add(valores);
                    }
                    String[] from = {"A", "B"};
                    int[] views = {R.id.textId, R.id.txtDescripcion};
                    //AdaptadorLista = new SimpleAdapter(RendPlantaActivity.this,data,R.layout.listview_items,from,views);

                    AdaptadorLista = new SimpleAdapter(Principal_Configuracion.this, data, R.layout.listview_items, from, views);
                    ListaRendimiento.setAdapter(AdaptadorLista);
                    //Cnn.close();
                } catch (Exception e) {
                    Log.e(TAG, "Error Exception: " + e);
                }
            }
        });
        */



    }
    private void MarcarCheck(Boolean Est)
    {
        cbxSucursal.setChecked(Est);
        cbxConsumidor.setChecked(Est);
        cbxCultivo.setChecked(Est);
        cbxVariedad.setChecked(Est);
        cbxAgrupacion.setChecked(Est);
        cbxPresentacion.setChecked(Est);
        cbxLinea.setChecked(Est);
        cbxPersonal.setChecked(Est);
        cbxEstado.setChecked(Est);
        cbxUsuario.setChecked(Est);
        cbxMenu.setChecked(Est);
        cbxMenuUsuario.setChecked(Est);

        cbxEmpresa.setChecked(Est);
        cbxEmpresaUsuario.setChecked(Est);
        cbxTareoOrigen.setChecked(Est);
        cbxTareoTipo.setChecked(Est);
        cbxTareoSubTipo.setChecked(Est);
        cbxResponsable.setChecked(Est);
        cbxActividad.setChecked(Est);
        cbxLabor.setChecked(Est);
        cbxProceso.setChecked(Est);
        cbxSubProceso.setChecked(Est);
        cbxMesasPorLinea.setChecked(Est);
        cbxMotivoParadas.setChecked(Est);
    }
}
