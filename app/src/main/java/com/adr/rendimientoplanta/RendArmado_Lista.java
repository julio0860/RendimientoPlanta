package com.adr.rendimientoplanta;

import android.app.AlertDialog;
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
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.ConexionBD;
import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_RendimientoArmado;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class RendArmado_Lista extends AppCompatActivity {

    private SimpleCursorAdapter AdaptadorGrilla;
    private GridView dgvPersonalRendimiento;
    private TextView lblEmpresa;
    private TextView lblSucursal;
    private TextView lblLinea;
    private TextView lblProceso;
    private TextView lblSubproceso;
    private TextView lblLado;
    private EditText edtFecha;
    private ImageButton imbRegresar;
    private ImageButton imbConfigurar;

    private Funciones fnc;
    private Button btnSincronizar;
    private int IdServidor;
    private int IdLocal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rend_armado_lista);
        LocalBD LBD = new LocalBD(RendArmado_Lista.this);
        final SQLiteDatabase LocBD = LBD.getWritableDatabase();

        fnc=new Funciones();
        AsignarVariables();
        MostrarVariables();



        Cursor Rse = LocBD.rawQuery(" SELECT M.POSICION AS '_id',IFNULL(A.DNI,' ') AS 'DNI',IFNULL(p.Per_ApePaterno || ' '|| p.Per_ApeMaterno||' '||p.Per_Nombres,' ') AS 'PER',IFNULL(A.Agru_Id ,0) AS 'AGRUID',IFNULL(HoraIngreso,' ') AS 'HORAIN'  \n" +
                "FROM MESA M lEFT JOIN  Agrupador A ON M.POSICION = A.Posicion AND A.Fecha='" + Variables.FechaStrBD + "' AND A.Suc_Id='" + Variables.Suc_Id + "'  AND  \n" +
                "A.Pro_Id='" + Variables.Pro_Id + "'  AND A.Sub_Id='" + Variables.Sub_Id + "'  AND A.Lin_Id='" + Variables.Lin_Id + "' AND A.Lado='" + Variables.Lin_Lado + "' AND A.Est_Id=1  left join Personal p on p.Per_Codigo=A.DNI \n" +
                " WHERE M.posicion <=\n" +
                "(SELECT MESA FROM MesaLinea where Cam_Id='"+Variables.Cam_Id+"' AND Pro_Id='"+ Variables.Pro_Id + "'  AND Sub_Id='" + Variables.Sub_Id + "' AND Lin_Id='" + Variables.Lin_Id + "' AND Lado='" + Variables.Lin_Lado + "')", null);

        AdaptadorGrilla = new SimpleCursorAdapter(RendArmado_Lista.this, R.layout.simple_list_item_3, Rse, new String[]{"_id", "DNI", "PER", "AGRUID", "HORAIN"},
                new int[]{R.id.text2, R.id.text1, R.id.text3}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        dgvPersonalRendimiento.setAdapter(AdaptadorGrilla);
        dgvPersonalRendimiento.setNumColumns(4);


        dgvPersonalRendimiento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor curPersonal = (Cursor) parent.getItemAtPosition(position);
                Variables.Per_Ubicacion = curPersonal.getInt(curPersonal.getColumnIndex("_id"));
                Variables.Per_Nombres = curPersonal.getString(curPersonal.getColumnIndex("PER"));
                Variables.Per_Dni = curPersonal.getString(curPersonal.getColumnIndex("DNI"));
                Variables.Agru_Id = curPersonal.getInt(curPersonal.getColumnIndex("AGRUID"));
                Variables.HoraIngreso = curPersonal.getString(curPersonal.getColumnIndex("HORAIN"));
                if (Variables.Agru_Id>0){
                    Intent ActividadRegistrar = new Intent(RendArmado_Lista.this, RendArmado_Registro.class);
                    startActivity(ActividadRegistrar);
                }

            }
        });

        dgvPersonalRendimiento.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor curPersonal = (Cursor) parent.getItemAtPosition(position);
                Variables.Per_Ubicacion = curPersonal.getInt(curPersonal.getColumnIndex("_id"));
                Variables.Per_Nombres = curPersonal.getString(curPersonal.getColumnIndex("PER"));
                Variables.Per_Dni = curPersonal.getString(curPersonal.getColumnIndex("DNI"));
                Variables.Agru_Id = curPersonal.getInt(curPersonal.getColumnIndex("AGRUID"));
                Variables.HoraIngreso = curPersonal.getString(curPersonal.getColumnIndex("HORAIN"));
                registerForContextMenu(dgvPersonalRendimiento);

                return false;
            }
        })
        ;


        imbConfigurar.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
                                                 Intent ActividadNueva = new Intent(RendArmado_Lista.this, RendArmado_Parametros.class);
                                                 //Se inicia la actividad nueva
                                                 startActivity(ActividadNueva);
                                             }
                                         }
        );
        imbRegresar.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
                                               Intent ActividadNueva = new Intent(RendArmado_Lista.this, Principal_Menu.class);
                                               //Se inicia la actividad nueva
                                               startActivity(ActividadNueva);
                                           }
                                       }
        );
        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
                if(conectadoWifi())
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RendArmado_Lista.this);
                    String alert_title = "Iniciar Linea";
                    String alert_description = "¿Desea sincronizar los registros?";
                    alertDialogBuilder.setTitle(alert_title);
                    // set dialog message
                    alertDialogBuilder
                            .setMessage(alert_description)
                            .setCancelable(false)
                            .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                                // Lo que sucede si se pulsa yes
                                public void onClick(DialogInterface dialog,int id) {

                                    boolean Realizado = false;

                                    try {

                                        Connection Cnn = ConexionBD.getInstance().getConnection();
                                        Statement stmt = Cnn.createStatement();
                                        ResultSet Rse;
                                        Cursor curDatosSinc = LocBD.rawQuery(T_RendimientoArmado.RendimientoArmado_SeleccionarSincronizar(
                                                Variables.FechaStr, Variables.Suc_Id, Variables.Pro_Id, Variables.Sub_Id, Variables.Lin_Id,
                                                Variables.Lin_Lado), null);

                                        for (curDatosSinc.moveToFirst(); !curDatosSinc.isAfterLast(); curDatosSinc.moveToNext()) {
                                            IdLocal = curDatosSinc.getInt(curDatosSinc.getColumnIndex(BaseColumns._ID));
                                            IdServidor = curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.RenArmIdServidor));
                                            if (IdServidor == 0) {
                                                //Si no se ha insertado datos
                                                stmt.executeUpdate(T_RendimientoArmado.RendimientoArmado_InsertarServidor(
                                                        curDatosSinc.getString(curDatosSinc.getColumnIndex(T_RendimientoArmado.RenArmFecha)),
                                                        curDatosSinc.getString(curDatosSinc.getColumnIndex(T_RendimientoArmado.PerDni)),
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.SucId)),
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.ProId)),
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.SubId)),
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.LinId)),
                                                        curDatosSinc.getString(curDatosSinc.getColumnIndex(T_RendimientoArmado.LinLado)),
                                                        curDatosSinc.getString(curDatosSinc.getColumnIndex(T_RendimientoArmado.RenArmFechaReg)),
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.UsuId)),
                                                        curDatosSinc.getString(curDatosSinc.getColumnIndex(T_RendimientoArmado.RenArmMac)),
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.PreId)),
                                                        curDatosSinc.getString(curDatosSinc.getColumnIndex(T_RendimientoArmado.PreDescripcion)),
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.PreEnvId)),
                                                        curDatosSinc.getString(curDatosSinc.getColumnIndex(T_RendimientoArmado.PreEnvDescripcionCor)),
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.RenArmEntrega)),
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.RenArmDevolucion)),
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.RenArmPeso)),
                                                        curDatosSinc.getDouble(curDatosSinc.getColumnIndex(T_RendimientoArmado.RenArmFactor)),
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.RenArmCantidad)),
                                                        curDatosSinc.getDouble(curDatosSinc.getColumnIndex(T_RendimientoArmado.RenArmEquivalente)),
                                                        curDatosSinc.getString(curDatosSinc.getColumnIndex(T_RendimientoArmado.RenArmHoraIni)),
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.EstId)),
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.RenArmSincronizado)),
                                                        fnc.HoraSistema()), stmt.RETURN_GENERATED_KEYS);

                                                Rse = stmt.getGeneratedKeys();
                                                if (Rse.next()) {
                                                    IdServidor = Rse.getInt(1);
                                                    LocBD.execSQL(T_RendimientoArmado.RendimientoArmado_ActualizarIdServidor(IdServidor, IdLocal, 1));
                                                    Realizado = true;

                                                } else {
                                                    Toast.makeText(RendArmado_Lista.this, "NO SE HA PODIDO SINCRONIZAR", Toast.LENGTH_SHORT).show();
                                                    Realizado = false;
                                                }
                                            } else {
                                                stmt.executeUpdate(T_RendimientoArmado.Servidor_ActualizarEstado(
                                                        curDatosSinc.getInt(curDatosSinc.getColumnIndex(T_RendimientoArmado.EstId)),
                                                        IdServidor, fnc.HoraSistema()), stmt.RETURN_GENERATED_KEYS);
                                                Rse = stmt.getGeneratedKeys();
                                                if (Rse.next()) {
                                                    Realizado = true;
                                                } else {
                                                    Realizado = false;
                                                }
                                            }
                                        }
                                        if (Realizado == true) {
                                            Toast.makeText(RendArmado_Lista.this, "SINCRONIZACION REALIZADA", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(RendArmado_Lista.this, "ERROR AL SINCRONIZAR", Toast.LENGTH_SHORT).show();
                                        }


                                    } catch (Exception e) {
                                        Toast.makeText(RendArmado_Lista.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    }


                                }})
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // Si se pulsa no no hace nada
                                    Toast.makeText(RendArmado_Lista.this,"OPERACION CANCELADA",Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }
                else {
                    Toast.makeText(RendArmado_Lista.this, "NO HAY CONEXION, INTENTE LUEGO ",Toast.LENGTH_SHORT).show();
                }
            }
            }
        );

    }
    public void AsignarVariables()
    {
        dgvPersonalRendimiento = (GridView) findViewById(R.id.dgvPersonalRendimiento);
        //ASIGNACION DE VARIABLES A ELEMENTOS DEL LAYOUT
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblLinea = (TextView) findViewById(R.id.lblLinea);
        lblLado = (TextView) findViewById(R.id.lblLado);
        lblProceso=(TextView)findViewById(R.id.lblProceso);
        lblSubproceso=(TextView)findViewById(R.id.lblSubproceso);
        edtFecha = (EditText) findViewById(R.id.lblFecha);
        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);
        imbConfigurar = (ImageButton) findViewById(R.id.imbConfigurar);

        btnSincronizar = (Button) findViewById(R.id.btnSincronizar);

    }
    public void MostrarVariables()
    {
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblLinea.setText(Variables.Lin_Descripcion);
        lblLado.setText("LADO: " + Variables.Lin_Lado);
        lblProceso.setText(Variables.Pro_Descripcion);
        lblSubproceso.setText(Variables.Sub_Descripcion);
        edtFecha.setText(Variables.FechaStr);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("MESA NRO: "+Variables.Per_Ubicacion);
        menu.add(0, v.getId(), 0, "ASIGNAR PERSONAL");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle()=="ASIGNAR PERSONAL"){function1(item.getItemId());}
        else {return false;}
        return true;
    }

    public void function1(int id){

        Intent ActividadModificar = new Intent(RendArmado_Lista.this, RegistroOperario.class);
        startActivity(ActividadModificar);
        //Toast.makeText(this, "function 1 called", Toast.LENGTH_SHORT).show();
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


}


