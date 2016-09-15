package com.adr.rendimientoplanta;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.ConexionBD;
import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_IngresoJabas;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.sql.Connection;
import java.sql.Statement;

public class IngresoJabas_Lista extends AppCompatActivity {

    private Button btnNuevoIngresoJabas;
    //private SimpleAdapter AdaptadorLista;
    private SimpleCursorAdapter AdaptadorLista;
    private ListView lstIngresoJabas;
    //lbl
    //CHECKBOX
    private CheckBox cbxSync;
    private CheckBox cbxEstado;
    //

    private TextView lblSucursal;
    private TextView lblCultivo;
    private TextView lblFecha;
    private TextView lblEmpresa;
    //Nuevo
    private ImageButton imbConfigurar;
    //TEMPORAL
    private Button btnDesync;
    //---->
    //DECLARACION BOTONES
private ImageButton imbRegresar;

    private int Sync;
    private int Est;
    private Button btnSincronizar;
    LocalBD LBD;
    SQLiteDatabase LocBD;
    private static final String TAG = "IngresoJabas_Lista";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_jabas_lista);

        LBD = new LocalBD(IngresoJabas_Lista.this) ;
        LocBD = LBD.getWritableDatabase();

        lstIngresoJabas = (ListView) findViewById(R.id.lstIngresoJabas);

        btnNuevoIngresoJabas = (Button) findViewById(R.id.btnNuevoIngresoJabas);
        btnSincronizar = (Button) findViewById(R.id.BtnSincronizar);
        //Labels
        lblCultivo = (TextView) findViewById(R.id.lblCultivo);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        //------------>
        lblFecha = (TextView) findViewById(R.id.edtFecha);
        lblFecha.setText(Variables.FechaStr);
        //Image
        imbConfigurar = (ImageButton) findViewById(R.id.imbConfigurar);
        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);
        //TEMPORAL
        btnDesync = (Button) findViewById(R.id.btnDesync);
        //FIn
        cbxSync = (CheckBox) findViewById(R.id.cbxSync);
        cbxEstado = (CheckBox) findViewById(R.id.cbxEstado);

        //ASIGNACION
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblCultivo.setText(Variables.Cul_Descripcion);
        lblEmpresa.setText(Variables.Emp_Descripcion);
        if (cbxSync.isChecked())
        {
            Sync = 0;
        }else
        {
            Sync = 1;
        }
        if (cbxEstado.isChecked())
        {
            Est = 1;
        }else
        {
            Est = 2;
        }
        ConsultarIngreso(Sync,Est, Variables.FechaStr);
        cbxEstado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if (isChecked)
                {
                    Est =1;
                }else
                {
                    Est=2;
                }
                ConsultarIngreso(Sync,Est, Variables.FechaStr);
            }
        });
        cbxSync.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if (isChecked)
                {
                    Sync =0;
                }else
                {
                    Sync=1;
                }
                ConsultarIngreso(Sync,Est, Variables.FechaStr);
            }
        });
        imbConfigurar.setOnClickListener(new View.OnClickListener()
                                                {
                                                    @Override
                                                    public void onClick (View v){
                //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
                Intent ActividadNueva = new Intent(IngresoJabas_Lista.this, IngresoJabas_Parametros.class);
                //Se inicia la actividad nueva
                startActivity(ActividadNueva);
            }
        }
        );
        imbRegresar.setOnClickListener(new View.OnClickListener()
                                         {
                                             @Override
                                             public void onClick (View v){
                 //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
                 Intent ActividadNueva = new Intent(IngresoJabas_Lista.this, Principal_Menu.class);
                 //Se inicia la actividad nueva
                 startActivity(ActividadNueva);
             }
         }
        );

        btnNuevoIngresoJabas.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v){
                    //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
                    Intent ActividadNueva = new Intent(IngresoJabas_Lista.this, IngresoJabas_Registro.class);
                    //Pasar información de un activity a otra
                    ActividadNueva.putExtra("NUEVO",true);
                    //Se inicia la actividad nueva
                    startActivity(ActividadNueva);
                }
            }
        );

        //TEMPORAL
        btnDesync.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
                Cursor Cur = null;
                Cur = LocBD.rawQuery(T_IngresoJabas._SELECT_SYNC(-1,-1),null);
                for (Cur.moveToFirst();!Cur.isAfterLast();Cur.moveToNext()) {
                    LocBD.execSQL(T_IngresoJabas._UPDATE_SYNCSTATE(Cur.getInt(0), 0));
                }
            }
        }
        );
        //--------FINTEMPORAL---->
        btnSincronizar.setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick (View v){

            Cursor Cur = null;
                if (LocBD != null)
                {
                    Cur = LocBD.rawQuery(T_IngresoJabas._SELECT_SYNC(0,2),null);
                    Boolean Resultado=true;

                    try {
                        Connection Cnn = ConexionBD.getInstance().getConnection();
                        Statement Stmt = Cnn.createStatement();
                        //ResultSet Rse = Stmt.executeQuery("Select Per_Id,Per_Codigo," +
                        //        "Per_ApePaterno,Per_ApeMaterno,Per_Nombres From Personal");
                        Toast.makeText(IngresoJabas_Lista.this, "REGISTROS "+Cur.getCount(),Toast.LENGTH_SHORT).show();
                        if (Cur.getCount()!=0)
                        {
                            for (Cur.moveToFirst();!Cur.isAfterLast();Cur.moveToNext()) {

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
                                        Variables.MAC,
                                        Variables.IMEI,
                                        Cur.getString(13)
                                        ,Cur.getInt(14),
                                        Cur.getInt(15),
                                        Cur.getInt(16),
                                        Cur.getInt(17),
                                        Cur.getString(18),
                                        Cur.getInt(19),
                                        Cur.getInt(20),
                                        Cur.getInt(21),
                                        Cur.getInt(22)
                                ));
                            //LocBD.execSQL(T_IngresoJabas._UPDATE_SYNCSTATE(Cur.getInt(0),0));
                                if (Resultado==false)
                                {
                                    LocBD.execSQL(T_IngresoJabas._UPDATE_SYNCSTATE(Cur.getInt(0),1));
                                }
                                else
                                {
                                    Toast.makeText(IngresoJabas_Lista.this, "ERROR AL SINCRONIZAR",Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (Resultado==false)
                            {
                                Toast.makeText(IngresoJabas_Lista.this, "SINCRONIZACION COMPLETADA",Toast.LENGTH_SHORT).show();
                                Intent ActividadRecargar = new Intent(IngresoJabas_Lista.this, IngresoJabas_Lista.class);
                                startActivity(ActividadRecargar);
                            }
                            else
                            {
                                Toast.makeText(IngresoJabas_Lista.this, "NO SE HA PODIDO SINCRONIZAR, REVISE EL REGISTRO",Toast.LENGTH_SHORT).show();
                            }
                        }else
                        {
                            Toast.makeText(IngresoJabas_Lista.this, "NO HAY REGISTROS A SINCRONIZAR",Toast.LENGTH_SHORT).show();
                        }
                        Stmt.close();
                        //Cnn.close();

                    } catch (Exception e) {
                        Toast.makeText(IngresoJabas_Lista.this,e.toString(),Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error Exception: " + e);

                    }
                        Cur =null;
                }
        }});

        lstIngresoJabas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* String OpcionSeleccion;
                //OpcionSeleccion= ListaRendimiento.getAdapter().getItem(position).toString();
                //OPCION 1 REFERENCIANDO AL CONTROL
                OpcionSeleccion= ((TextView)view.findViewById(R.id.lstlblId)).getText().toString();
                //OPCION 2 REFERENCIANDO AL ITEM - ES NECESARIO UNA CLASE ADICIONAL
                //OpcionSeleccion=ListaRendimiento.getAdapter().getItem(position).toString()
                Intent ActividadModificar = new Intent(IngresoJabas_Lista.this, IngresoJabas_Registro.class);
                //Pasar información de un activity a otra
                */
                Intent ActividadModificar = new Intent(IngresoJabas_Lista.this, IngresoJabas_Registro.class);
                Cursor CursorCarga = (Cursor) parent.getItemAtPosition(position);

                ActividadModificar.putExtra("ID",CursorCarga.getInt(CursorCarga.getColumnIndex(BaseColumns._ID)));
                ActividadModificar.putExtra("FECHA",CursorCarga.getString(CursorCarga.getColumnIndex(T_IngresoJabas.ING_FECHA)));
                ActividadModificar.putExtra("HORAINI",CursorCarga.getString(CursorCarga.getColumnIndex(T_IngresoJabas.HORA_INI)));
                ActividadModificar.putExtra("HORAFIN",CursorCarga.getString(CursorCarga.getColumnIndex(T_IngresoJabas.HORA_FIN)));
                ActividadModificar.putExtra("PARADAS",CursorCarga.getString(CursorCarga.getColumnIndex(T_IngresoJabas.PARADAS)));
                ActividadModificar.putExtra("PLANTA",CursorCarga.getString(CursorCarga.getColumnIndex(T_IngresoJabas.PLANTA)));
                ActividadModificar.putExtra("LINEA",CursorCarga.getString(CursorCarga.getColumnIndex(T_IngresoJabas.LINEA)));
                ActividadModificar.putExtra("LOTE",CursorCarga.getString(CursorCarga.getColumnIndex(T_IngresoJabas.LOTE)));
                ActividadModificar.putExtra("JABAS",CursorCarga.getString(CursorCarga.getColumnIndex(T_IngresoJabas.JABASLOTE)));
                ActividadModificar.putExtra("ESCAMPO",CursorCarga.getInt(CursorCarga.getColumnIndex(T_IngresoJabas.ESCAMPO)));
                ActividadModificar.putExtra("ESREPRO",CursorCarga.getInt(CursorCarga.getColumnIndex(T_IngresoJabas.ESREPRO)));
                ActividadModificar.putExtra("ESTADO",CursorCarga.getInt(CursorCarga.getColumnIndex(T_IngresoJabas.ESTADO)));
                ActividadModificar.putExtra("SYNC",CursorCarga.getInt(CursorCarga.getColumnIndex(T_IngresoJabas.SINCRONIZADO)));
                //AGREGADO MIX
                ActividadModificar.putExtra("ESMIX",CursorCarga.getInt(CursorCarga.getColumnIndex(T_IngresoJabas.ESMIX)));
                ActividadModificar.putExtra("CONIDMIX",CursorCarga.getInt(CursorCarga.getColumnIndex(T_IngresoJabas.CONIDMIX)));
                ActividadModificar.putExtra("JABASMIX",CursorCarga.getString(CursorCarga.getColumnIndex(T_IngresoJabas.JABASMIX)));

                ActividadModificar.putExtra("NUEVO",false);

                startActivity(ActividadModificar);
                /*
                ActividadModificar.putExtra("ID",Integer.parseInt(((TextView)view.findViewById(R.id.lstlblId)).getText().toString()));
                ActividadModificar.putExtra("FECHA",((TextView)view.findViewById(R.id.lstlblIngJabasFecha)).getText().toString());
                ActividadModificar.putExtra("HORAINI",((TextView)view.findViewById(R.id.lstlblIngJabasHoraIni)).getText().toString());
                ActividadModificar.putExtra("HORAFIN",((TextView)view.findViewById(R.id.lstLblIngJabasHoraFin)).getText().toString());
                ActividadModificar.putExtra("PARADAS",((TextView)view.findViewById(R.id.lstlblIngJabasParadas)).getText().toString());
                ActividadModificar.putExtra("PLANTA",((TextView)view.findViewById(R.id.lstlblIngJabasPlanta)).getText().toString());
                ActividadModificar.putExtra("LINEA",((TextView)view.findViewById(R.id.lstlblIngJabasLinea)).getText().toString());
                ActividadModificar.putExtra("LOTE",((TextView)view.findViewById(R.id.lstlblIngJabasLote)).getText().toString());
                ActividadModificar.putExtra("JABAS",((TextView)view.findViewById(R.id.lstlblIngJabasJabas)).getText().toString());
                ActividadModificar.putExtra("ESCAMPO",Integer.parseInt(((TextView)view.findViewById(R.id.lstlblIngJabasCampo)).getText().toString()));
                ActividadModificar.putExtra("ESREPRO",Integer.parseInt(((TextView)view.findViewById(R.id.lstlblIngJabasRepro)).getText().toString()));
                ActividadModificar.putExtra("ESTADO",Integer.parseInt(((TextView)view.findViewById(R.id.lstlblIngJabasEstado)).getText().toString()));
                ActividadModificar.putExtra("SYNC",Integer.parseInt(((TextView)view.findViewById(R.id.lstlblIngJabasSync)).getText().toString()));
                ActividadModificar.putExtra("NUEVO",false);
                //Se inicia la actividad nueva
                startActivity(ActividadModificar);
                */
                //Toast.makeText(IngresoJabas_Lista.this, "Hiciste click en el registro " + OpcionSeleccion + " .",Toast.LENGTH_SHORT).show();
            }
        });




    }
    void ConsultarIngreso(int Sync,int Est,String Fecha)
    {
        try {
            Cursor Rse = null;
            if (LocBD != null)
            {
                Rse = LocBD.rawQuery(T_IngresoJabas._SELECT_LISTA(Sync,Est,Fecha),null);
            }

            AdaptadorLista = new SimpleCursorAdapter(IngresoJabas_Lista.this, android.R.layout.simple_expandable_list_item_2,Rse,
                    new String[]{T_IngresoJabas.LINEA,T_IngresoJabas.HORA_INI},new int[]{android.R.id.text1,android.R.id.text2},
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            lstIngresoJabas.setAdapter(AdaptadorLista);

            //Cnn.close();
        } catch (Exception e) {
            Log.e(TAG, "Error Exception: " + e);
        }
    }
    /*
    void ConsultarIngreso(int Sync,int Est,String Fecha)
    {
        try {
            Cursor Rse = null;
            if (LocBD != null)
            {
                //Rse = LocBD.rawQuery(T_IngresoJabas._SELECT_SYNC(0,1),null);
                Rse = LocBD.rawQuery(T_IngresoJabas._SELECT_LISTA(Sync,Est,Fecha),null);
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
                valores.put("C", Rse.getString(2));
                valores.put("D", Rse.getString(3));
                valores.put("E", Rse.getString(4));
                valores.put("F", Rse.getString(5));
                valores.put("G", Rse.getString(6));
                valores.put("H", Rse.getString(7));
                valores.put("I", Rse.getString(8));
                valores.put("J", Rse.getString(9));
                valores.put("K", Rse.getString(10));
                valores.put("L", Rse.getString(11));
                valores.put("M", Rse.getString(12));
                data.add(valores);
            }

            String[] from = {"A", "B","C","D","E","F","G","H","I","J","K","L","M"};
            int[] views = {R.id.lstlblId,R.id.lstlblIngJabasFecha,R.id.lstlblIngJabasHoraIni,
                    R.id.lstLblIngJabasHoraFin,R.id.lstlblIngJabasParadas,R.id.lstlblIngJabasPlanta,
                    R.id.lstlblIngJabasLinea,R.id.lstlblIngJabasLote,R.id.lstlblIngJabasJabas,
                    R.id.lstlblIngJabasCampo,R.id.lstlblIngJabasRepro,R.id.lstlblIngJabasEstado,R.id.lstlblIngJabasSync};
            AdaptadorLista = new SimpleAdapter(IngresoJabas_Lista_Lista.this, data, R.layout.listview_listaingresojabas, from, views);

            lstIngresoJabas.setAdapter(AdaptadorLista);
            //Cnn.close();
        } catch (Exception e) {
            Log.e(TAG, "Error Exception: " + e);
        }
    }
    */
}
