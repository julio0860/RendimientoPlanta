package com.adr.rendimientoplanta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import com.adr.rendimientoplanta.DATA.T_Agrupador;
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
    private TextView lblLado;
    private EditText edtFecha;
    private ImageButton imbRegresar;
    private ImageButton imbConfigurar;
    private Button btnSincronizar;
    //VARIABLES PARA INSERTAR EL AGRUPADOR AL SERVIDOR
    private int IdregServidor;
    private int Emp_Id,Suc_Id,Pro_Id,Sub_Id,Lin_Id,Posicion,Mot_Id,Est_Id;
    private String Fecha,Lado,Dni,HoraLectura,HoraIngreso,HoraSalida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rend_armado_lista);
        LocalBD LBD = new LocalBD(RendArmado_Lista.this);
        final SQLiteDatabase LocBD = LBD.getWritableDatabase();

        AsignarVariables();
        MostrarVariables();



        Cursor Rse = LocBD.rawQuery(" SELECT M.POSICION AS '_id',IFNULL(A.DNI,' ') AS 'DNI',IFNULL(p.Per_ApePaterno || ' '|| p.Per_ApeMaterno||' '||p.Per_Nombres,' ') AS 'PER',IFNULL(A.Agru_Id ,0) AS 'AGRUID',IFNULL(HoraIngreso,' ') AS 'HORAIN'  \n" +
                "FROM MESA M lEFT JOIN  Agrupador A ON M.POSICION = A.Posicion AND A.Fecha='" + Variables.FechaStr + "' AND A.Suc_Id='" + Variables.Suc_Id + "'  AND  \n" +
                "A.Pro_Id='" + Variables.Pro_Id + "'  AND A.Sub_Id='" + Variables.Sub_Id + "'  AND A.Lin_Id='" + Variables.Lin_Id + "' AND A.Lado='" + Variables.Lin_Lado + "' AND A.Est_Id=1  left join Personal p on p.Per_Codigo=A.DNI \n" +
                " WHERE M.posicion <=\n" +
                "(SELECT MESA FROM MesaLinea where Cam_Id='"+Variables.Cam_Id+"' AND Pro_Id='" + Variables.Pro_Id + "'  AND Sub_Id='" + Variables.Sub_Id + "' AND Lin_Id='" + Variables.Lin_Id + "' AND Lado='" + Variables.Lin_Lado + "')", null);

        AdaptadorGrilla = new SimpleCursorAdapter(RendArmado_Lista.this, R.layout.simple_list_item_3, Rse, new String[]{"_id", "DNI", "PER", "AGRUID", "HORAIN"},
                new int[]{R.id.text1, R.id.text2, R.id.text3}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
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

        btnSincronizar.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {

                                                  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RendArmado_Lista.this);
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
                                                                      Cursor CurReg = LocBD.rawQuery(T_Agrupador._SELECCIONAR_TODOS(Variables.FechaStr),null);
                                                                      boolean Resultado = true;
                                                                      try {
                                                                          Toast.makeText(RendArmado_Lista.this, "REGISTROS "+CurReg.getCount(),Toast.LENGTH_SHORT).show();

                                                                         Connection Cnn = ConexionBD.getInstance().getConnection();
                                                                          Statement pstmt = Cnn.createStatement();

                                                                          ResultSet Rse;
                                                                          if (CurReg.getCount()!=0){
                                                                              for (CurReg.moveToFirst();!CurReg.isAfterLast();CurReg.moveToNext())
                                                                              {
                                                                                  IdregServidor=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.IDSERVIDOR));

                                                                                  Emp_Id=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.EMPID));
                                                                                  Suc_Id=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.SUCID));
                                                                                  Pro_Id=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.PROID));
                                                                                  Sub_Id=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.SUBID));
                                                                                  Lin_Id=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.LINID));
                                                                                  Mot_Id=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.MOTIVO));
                                                                                  Est_Id=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.ESTADO));
                                                                                  Posicion=CurReg.getInt(CurReg.getColumnIndex(T_Agrupador.POSICION));
                                                                                  Fecha=CurReg.getString(CurReg.getColumnIndex(T_Agrupador.FECHA));
                                                                                  Lado=CurReg.getString(CurReg.getColumnIndex(T_Agrupador.LADO));
                                                                                  Dni=CurReg.getString(CurReg.getColumnIndex(T_Agrupador.DNI));
                                                                                  HoraLectura=CurReg.getString(CurReg.getColumnIndex(T_Agrupador.HORALECTURA));
                                                                                  HoraIngreso=CurReg.getString(CurReg.getColumnIndex(T_Agrupador.HORAINGRESO));
                                                                                 HoraSalida=CurReg.getString(CurReg.getColumnIndex(T_Agrupador.HORASALIDA));
                                                                                  if (HoraSalida==null){
                                                                                      HoraSalida="00:00:00";
                                                                                  }

                                                                                  if (IdregServidor>0){

                                                                                      Toast.makeText(RendArmado_Lista.this, "COMPARA CON EL SERVIDOR Y ACTUALIZA EN LA FECHA ",Toast.LENGTH_SHORT).show();
                                                                                  }
                                                                                  else
                                                                                  {

                                                                                      pstmt.executeUpdate(T_Agrupador._INSERTSERVIDOR(Emp_Id,Fecha,Suc_Id,Pro_Id,Sub_Id,Lin_Id,Lado,Posicion,Dni,HoraLectura,HoraIngreso,HoraSalida,Mot_Id,Est_Id),pstmt.RETURN_GENERATED_KEYS);
                                                                                      Rse = pstmt.getGeneratedKeys();
                                                                                      if (Rse != null && Rse.next()) {
                                                                                           IdregServidor = Rse.getInt(1);
                                                                                          LocBD.execSQL(T_Agrupador.ActualizarIdServidorLocal(Emp_Id,Fecha,Suc_Id,Pro_Id,Sub_Id,Lin_Id,Lado,Posicion,Dni,Mot_Id,Est_Id,IdregServidor));
                                                                                      }

                                                                                      Toast.makeText(RendArmado_Lista.this, "INSERTA AL SERVIDOR  ",Toast.LENGTH_SHORT).show();
                                                                                  }

                                                                              }

                                                                          }


                                                                      }
                                                                      catch (Exception e){
                                                                          Toast.makeText(RendArmado_Lista.this, e.toString(), Toast.LENGTH_SHORT).show();
                                                                      }

                                                                  }
                                                                  else
                                                                  {
                                                                      Toast.makeText(RendArmado_Lista.this, "NO HAY CONEXION, INTENTE LUEGO ",Toast.LENGTH_SHORT).show();
                                                                  }
                                                              }
                                              })
                                                  .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                                      public void onClick(DialogInterface dialog,int id) {
                                                          // Si se pulsa no no hace nada
                                                          Toast.makeText(RendArmado_Lista.this,"Operación cancelada",Toast.LENGTH_SHORT).show();
                                                          dialog.cancel();
                                                      }
                                                  });
                                                  AlertDialog alertDialog = alertDialogBuilder.create();
                                                  // show it
                                                  alertDialog.show();
                                          }
        });

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

    }
    public void AsignarVariables()
    {
        dgvPersonalRendimiento = (GridView) findViewById(R.id.dgvPersonalRendimiento);
        //ASIGNACION DE VARIABLES A ELEMENTOS DEL LAYOUT
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblLinea = (TextView) findViewById(R.id.lblLinea);
        lblLado = (TextView) findViewById(R.id.lblLado);
        edtFecha = (EditText) findViewById(R.id.lblFecha);
        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);
        imbConfigurar = (ImageButton) findViewById(R.id.imbConfigurar);
        btnSincronizar=(Button)findViewById(R.id.btnSincronizar);
    }
    public void MostrarVariables()
    {
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblLinea.setText(Variables.Lin_Descripcion);
        lblLado.setText("LADO: " + Variables.Lin_Lado);
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
        Toast.makeText(this, "function 1 called", Toast.LENGTH_SHORT).show();
    }

    public final Boolean conectadoWifi(){
     /*   ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;*/
        return true;
    }

}


