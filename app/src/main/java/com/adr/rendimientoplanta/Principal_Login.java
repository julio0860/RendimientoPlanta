package com.adr.rendimientoplanta;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.ConexionBD;
import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Empresa;
import com.adr.rendimientoplanta.DATA.T_Estado;
import com.adr.rendimientoplanta.DATA.T_Menu;
import com.adr.rendimientoplanta.DATA.T_MenuUsuario;
import com.adr.rendimientoplanta.DATA.T_Usuario;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Principal_Login extends AppCompatActivity {

    LocalBD LBD;
    SQLiteDatabase LocBD;

    private Button btnIngresar;
    private Button btnCancelar;

    private static final String TAG = "Principal_Login";
    private ImageButton imbSincronizarUsuario;

    private EditText txtUsuario;
    private EditText txtPassword;

    private String Usuario;
    private String Password;
    Funciones Fnc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_login);

        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        imbSincronizarUsuario = (ImageButton) findViewById(R.id.imbSincronizarUsuario);

        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        LBD = new LocalBD(Principal_Login.this) ;
        LocBD = LBD.getWritableDatabase();

        btnIngresar.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  //LLama al Dialog

                 Usuario = txtUsuario.getText().toString();
                 Cursor Cur = null;
                 Cur= LocBD.rawQuery(T_Usuario._SELECT_USUARIO(Usuario),null);
                 Cur.moveToFirst();
                 Password = Fnc.Encriptar(txtPassword.getText().toString());

                  //OBTENCION DEL IMEI
                  TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                  Variables.IMEI=telephonyManager.getDeviceId();
                  //OBTENCION DE LA MAC
                  WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                  WifiInfo info = manager.getConnectionInfo();
                  Variables.MAC = info.getMacAddress();

                  if (Cur.getCount()!=0)
                  {
                      if (Usuario.equals(Cur.getString(0)) && Password.equals(Cur.getString(1)))
                      {
                          Variables.Usu_Alias =Cur.getString(2);
                          Toast.makeText(Principal_Login.this,"BIENVENIDO "+Cur.getString(2),Toast.LENGTH_SHORT).show();
                          Intent ActividadModificar = new Intent(Principal_Login.this, Principal_Menu.class);
                          Variables.Usu_Codigo = Cur.getString(4);
                          ActividadModificar.putExtra("USUID",Cur.getInt(3));
                          Variables.Usu_Id=Cur.getInt(3);

                          //Toast.makeText(Principal_Login.this,Variables.MAC,Toast.LENGTH_SHORT).show();
                          startActivity(ActividadModificar);
                      }else
                      {
                          Toast.makeText(Principal_Login.this,"CONTRASEÑA INCORRECTA",Toast.LENGTH_SHORT).show();
                      }
                  }else
                 {
                     Toast.makeText(Principal_Login.this,"USUARIO NO ENCONTRADO",Toast.LENGTH_SHORT).show();
                 }

              }
          }
        );
        imbSincronizarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Boolean Estado = false;
                    Connection Cnn = ConexionBD.getInstance().getConnection();
                    Statement Stmt = Cnn.createStatement();

                    LocBD.execSQL(T_Usuario._DELETE());
                    LocBD.execSQL(T_Menu._DELETE());
                    LocBD.execSQL(T_MenuUsuario._DELETE());
                    LocBD.execSQL(T_Estado._DELETE());
                    LocBD.execSQL(T_Empresa._DELETE());
                    ResultSet Rse=null;
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
                            Toast.makeText(Principal_Login.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
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
                            Toast.makeText(Principal_Login.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
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
                            }catch (Exception e)
                            {
                                Estado= false;
                                Log.e(TAG, "Error Exception: " + e);
                                Toast.makeText(Principal_Login.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        Rse=null;
                        Rse = Stmt.executeQuery(T_Estado._SELECT_EST(""));
                        while (Rse.next())
                        {
                            try
                            {
                                //Rse.next();
                                LocBD.execSQL(T_Estado._INSERT(Rse.getInt(1),Rse.getString(2),Rse.getString(3)));
                            }catch (Exception e)
                            {
                                Estado= false;
                                Log.e(TAG, "Error Exception: " + e);
                                Toast.makeText(Principal_Login.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
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
                                Toast.makeText(Principal_Login.this,"ERROR AL SINCRONIZAR" + e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }

                    if (Estado==true)
                    {
                        Toast.makeText(Principal_Login.this,"SINCRONIZACION REALIADA CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                        Stmt.close();
                        //Cnn.close();
                        Rse.close();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error Exception: " + e);
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Intent intent = new Intent(Intent.ACTION_MAIN);
                //intent.addCategory(Intent.CATEGORY_HOME);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intent);
            }
        });
    }
}
