package com.adr.rendimientoplanta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.BaseColumns;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.ConexionBD;
import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Consumidor;
import com.adr.rendimientoplanta.DATA.T_Linea;
import com.adr.rendimientoplanta.DATA.T_LineaIngreso;
import com.adr.rendimientoplanta.DATA.T_LineaParadas;
import com.adr.rendimientoplanta.DATA.T_LineaRegistro;
import com.adr.rendimientoplanta.DATA.T_MenuUsuario;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class IngresoJabas_Grilla extends AppCompatActivity {

    //Variables para la sincronización - LineaRegistro
    private String HoraFin="";
    private int LinReg_IdServidor;
    private int LinReg_IdMovil;
    private int Reg_Id;

    private int RegLinId;
    private int EstId;

    //

    private TextView lblSucursal;
    private TextView lblCultivo;
    private TextView lblFecha;
    private TextView lblEmpresa;

    private TextView lblJabasCampo;
    private TextView lblJabasRepro;
    private TextView lblJabasHora;

    private GridView dgvLineas;

    private ImageButton imbConfigurar;
    private ImageButton imbDescargar;

    private Button btnSincronizar;
    SimpleCursorAdapter adspnLineas;

    private ImageButton imbRegresar;

    private Funciones fnc;

    private LocalBD LBD;
    private SQLiteDatabase LocBD;

    private Connection Cnn=null;
    private Statement Stmt=null;
    private ResultSet Rse=null;

private boolean Estado = false;
    private boolean Ejecutar=false;
    private MenuItem men;

    @Override
    public void onBackPressed()
    {
        // Your Code Here. Leave empty if you want nothing to happen on back press.
        Intent NuevaActividad = new Intent(IngresoJabas_Grilla.this,Principal_Menu.class);
        startActivity(NuevaActividad);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_jabas_grilla);

        //VARIABLES LOCALES
        LBD = new LocalBD(IngresoJabas_Grilla.this) ;
        LocBD = LBD.getWritableDatabase();

        //CONEXION BD SERVIDOR
        try
        {
            //Cnn = ConexionBD.getInstance().getConnection();
            //Stmt = Cnn.createStatement();
            Cnn = ConexionBD.getInstance().getConnection();
            if (Cnn!=null)
            {
                Stmt = Cnn.createStatement();
            }
        }catch (Exception e)
        {
            Toast.makeText(IngresoJabas_Grilla.this,e.toString(),Toast.LENGTH_SHORT).show();
        }

        lblCultivo = (TextView) findViewById(R.id.lblCultivo);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblFecha = (TextView) findViewById(R.id.lblFecha);

        btnSincronizar = (Button) findViewById(R.id.btnSincronizar);

        lblFecha.setText(Variables.FechaStr);
        imbConfigurar = (ImageButton) findViewById(R.id.imbConfigurar);
        imbDescargar = (ImageButton)findViewById(R.id.imbDescargar);

        lblSucursal.setText(Variables.Suc_Descripcion);
        lblCultivo.setText(Variables.Cul_Descripcion);
        lblEmpresa.setText(Variables.Emp_Abrev);

        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);

        lblJabasCampo= (TextView) findViewById(R.id.lblJabasCampo);
        lblJabasRepro =(TextView) findViewById(R.id.lblJabasRepro);
        lblJabasHora =(TextView) findViewById(R.id.lblJabasHora);

        lblJabasCampo.setText(String.valueOf(Jabas(Variables.FechaStr,Variables.Suc_Id,Variables.Cul_Id,1)));
        lblJabasRepro.setText(String.valueOf(Jabas(Variables.FechaStr,Variables.Suc_Id,Variables.Cul_Id,2)));
        lblJabasHora.setText(String.valueOf(JabasHora(Variables.FechaStr,Variables.Suc_Id,Variables.Cul_Id)));

        //PRIVILEGIOS - EDICION

        fnc= new Funciones();

        RegLinId=getIntent().getIntExtra("RegLin_Id",0);
        EstId=getIntent().getIntExtra("Est_Id",0);
        if (RegLinId!=0)
        {
            boolean Ejecutado=false;
            if (conectadoWifi())
            {
                //Registros a sincronizar:
                Cursor CurRegistro = LocBD.rawQuery(T_LineaRegistro.LineaRegistro_SeleccionarId(RegLinId),null);
                Ejecutado=SincronizarRegistro(CurRegistro);
                if(Ejecutado==true)
                {
                    Toast.makeText(IngresoJabas_Grilla.this, "OPERACIÓN TERMINADA",Toast.LENGTH_SHORT).show();
                }
            }else
            {
                Toast.makeText(IngresoJabas_Grilla.this, "NO HAY CONEXION, INTENTE LUEGO ",Toast.LENGTH_SHORT).show();
            }
        }

        dgvLineas = (GridView) findViewById(R.id.dgvLineas);
        //ANTIGUA VERSION
        //Cursor CurLineas = LocBD.rawQuery(T_Linea._SELECT_LIN(Variables.Suc_Id,2),null);
        Cursor CurLineas = LocBD.rawQuery(T_Linea.Linea_SeleccionarEstado(Variables.Suc_Id,2,Variables.FechaStr,Variables.Cul_Id),null);
        /*adspnLineas = new SimpleCursorAdapter(IngresoJabas_Grilla.this,
                android.R.layout.simple_dropdown_item_1line,CurLineas,
                new String[]{"Lin_Descripcion"}, new int[]{android.R.id.text1},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        */
        adspnLineas = new SimpleCursorAdapter(IngresoJabas_Grilla.this,
                R.layout.gridview_itemborde,CurLineas,
                new String[]{"Lin_Descripcion"}, new int[]{R.id.text1},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        adspnLineas.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                    //int LinReg_Id=0;
                EstId = cursor.getInt(cursor.getColumnIndex("Est_Id"));
                switch(EstId) {
                    case 0: ((TextView) view).setBackgroundColor(Color.GRAY); break;
                    case 1: ((TextView) view).setBackgroundColor(Color.GREEN); break;
                    case 2: ((TextView) view).setBackgroundColor(Color.BLUE); break;
            /* etc. */
                }
                   // ((TextView) view).setTextColor(Color.RED);
                   // ((TextView) view).setBackgroundColor(Color.BLACK);
                return false;
            }
        });

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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IngresoJabas_Grilla.this);
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
                        // Código propio del método borrado para ejemplo
                        boolean Ejecutado=false;
                        if (conectadoWifi())
                        {
                            //Registros a sincronizar:
                            Cursor CurRegistro = LocBD.rawQuery(T_LineaRegistro.LineaRegistro_SeleccionarSincronizar(
                                    Variables.FechaStr,Variables.Suc_Id,Variables.Cul_Id),null);
                            Ejecutado=SincronizarRegistro(CurRegistro);
                            if(Ejecutado==true)
                            {
                                Toast.makeText(IngresoJabas_Grilla.this, "OPERACIÓN TERMINADA",Toast.LENGTH_SHORT).show();
                            }
                        }else
                        {
                            Toast.makeText(IngresoJabas_Grilla.this, "NO HAY CONEXION, INTENTE LUEGO ",Toast.LENGTH_SHORT).show();
                        }
                        }

                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // Si se pulsa no no hace nada
                            Toast.makeText(IngresoJabas_Grilla.this,"Operación cancelada",Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
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
        imbDescargar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
            if (conectadoWifi()) {
                LocBD.execSQL(T_Consumidor._DELETE());
                Rse=null;
                try{
                    Rse = Stmt.executeQuery(T_Consumidor._SELECT_CON(-1,2,-1,-1));
                    while (Rse.next()) {
                        LocBD.execSQL(T_Consumidor._INSERT(Rse.getInt(1),Rse.getInt(2),Rse.getInt(3),Rse.getInt(4)
                                ,Rse.getString(5),Rse.getString(6),Rse.getString(7),Rse.getInt(8)
                                ,Rse.getInt(9),Rse.getString(10),Rse.getInt(11)));
                        Estado = true;
                    }
                }catch (Exception e)
                {
                    Estado = false;
                    Toast.makeText(IngresoJabas_Grilla.this,"ERROR AL SINCRONIZAR " + e.toString(),Toast.LENGTH_SHORT).show();
                }
                if (Estado=true)
                {
                    Toast.makeText(IngresoJabas_Grilla.this,"SINCRONIZACION COMPLETA",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(IngresoJabas_Grilla.this,"ERROR AL SINCRONIZAR",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(IngresoJabas_Grilla.this, "NO HAY CONEXION, INTENTE LUEGO ",Toast.LENGTH_SHORT).show();
            }

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
        dgvLineas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);

                //if (Est_IdPrincipal==1)
                //{
            if(Variables.IngresoJabas_Editar==1)
            {
                Cursor curRegistro = (Cursor) parent.getItemAtPosition(position);
                Reg_Id =curRegistro.getInt(curRegistro.getColumnIndex(T_LineaRegistro.LinRegIdMovil));
                EstId=curRegistro.getInt(curRegistro.getColumnIndex(T_LineaRegistro.EstId));
                if (EstId==2)
                {
                    //  LinIng_Id= curIngreso.getInt(curIngreso.getColumnIndex(BaseColumns._ID));
                    // IngHoraIni=curIngreso.getString(curIngreso.getColumnIndex(T_LineaIngreso.LinIngHoraIni));
                    //IngHoraFin=curIngreso.getString(curIngreso.getColumnIndex(T_LineaIngreso.LinIngHoraFin));
                    // IngLote=curIngreso.getString(curIngreso.getColumnIndex(T_LineaIngreso.ConDescripcionCor));
                    // IngCantidad=curIngreso.getInt(curIngreso.getColumnIndex(T_LineaIngreso.LinIngCantidad));
                    // MensajeAnularIngreso="INI: "+IngHoraIni+" | FIN: "+IngHoraFin+" | L: "+IngLote+" | JBS: "+IngCantidad;
                    registerForContextMenu(dgvLineas);
                }
            }
                //}
                return false;
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        //MenuInflater inflater = new MenuInflater(this);

        switch(v.getId()){
            case R.id.dgvLineas:
                //TODO CODE
                menu.setHeaderTitle("OPCIONES LINEA");
                menu.add(0, v.getId(), 0, "MODIFICAR LINEA ");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        men=item;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IngresoJabas_Grilla.this);
        String alert_title = "MODIFICAR LINEA";
        String alert_description = "¿ESTA SEGURO QUE QUIERE ABRIR LA LINEA?";
        alertDialogBuilder.setTitle(alert_title);
        // set dialog message
        alertDialogBuilder
                .setMessage(alert_description)
                .setCancelable(false)
                .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                    // Lo que sucede si se pulsa yes
                    public void onClick(DialogInterface dialog,int id) {
                        // Código propio del método borrado para ejemplo
                        Ejecutar=true;
                        switch(men.getGroupId())
                        {
                            case 0:
                                ProcesoModificarLinea(Reg_Id);
                                //Toast.makeText(IngresoJabas_Grilla.this,men.getGroupId(),Toast.LENGTH_SHORT).show();
                                break;

                            //case 1:
                            //    //ProcesoAnularIngreso(men.getGroupId());
                            //    Toast.makeText(IngresoJabas_Grilla.this,men.getGroupId(),Toast.LENGTH_SHORT).show();
                            //    break;
                        }

                    }

                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // Si se pulsa no no hace nada
                        Ejecutar=false;
                        Toast.makeText(IngresoJabas_Grilla.this,"Operación cancelada",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
        return true;
    }
    private void ProcesoModificarLinea(int id){

        LocBD.execSQL(T_LineaRegistro.LinReg_ModificarEstado(Reg_Id,1));
        Toast.makeText(this, "LINEA MODIFICADA", Toast.LENGTH_SHORT).show();
        Intent NuevaActividad = new Intent(IngresoJabas_Grilla.this,IngresoJabas_Grilla.class);
        startActivity(NuevaActividad);
        //CargarParadas();
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
    private Boolean SincronizarParada(int IdMovil)
    {
        Boolean Estado=false;
        //CODIGO
        Rse=null;
        int IdServidor=0;
        //OBTIENE REGISTROS A SINCRONIZAR
        Cursor CurPar = LocBD.rawQuery(T_LineaParadas.LineaParadas_SeleccionarSincronizar(IdMovil),null);
        if (CurPar.getCount()!=0)
        {
            for (CurPar.moveToFirst();!CurPar.isAfterLast();CurPar.moveToNext()) {
                //SI AUN NO HA SIDO SINCRONIZADO ENTONCES INSERTA AL SERVIDOR
                try
                {
                    IdServidor=CurPar.getInt(CurPar.getColumnIndex(T_LineaParadas.LinParIdServidor));
                    if (IdServidor>0)
                    {
                        //SI YA HA SIDO SINCRONIZADO ACTUALIZA REGISTRO EN EL SERVIDOR
                        Rse=null;
                        Stmt.executeUpdate(T_LineaParadas.LinPar_ActualizarServidor(IdServidor,
                                CurPar.getInt(CurPar.getColumnIndex(T_LineaIngreso.EstId))),Stmt.RETURN_GENERATED_KEYS);
                        Rse=Stmt.getGeneratedKeys();
                        if(Rse.next()) {
                            //Toast.makeText(IngresoJabas_Grilla.this, "ACTUALIZADO", Toast.LENGTH_SHORT).show();
                            Estado = true;
                        }else{
                            //Toast.makeText(IngresoJabas_Grilla.this, "ERROR AL SINCRONIZAR", Toast.LENGTH_SHORT).show();
                            Estado = false;
                        }
                    }
                    else {
                        Rse = null;
                        Stmt.execute(T_LineaParadas.LineaParadas_InsertarServidor(
                                LinReg_IdServidor,
                                CurPar.getInt(CurPar.getColumnIndex(T_LineaParadas.MotId)),
                                CurPar.getString(CurPar.getColumnIndex(T_LineaParadas.LinParHoraIni)),
                                CurPar.getString(CurPar.getColumnIndex(T_LineaParadas.LinParHoraFin)),
                                CurPar.getDouble(CurPar.getColumnIndex(T_LineaParadas.LinParParada)),
                                CurPar.getInt(CurPar.getColumnIndex(T_LineaParadas.LinParSincronizado)),
                                CurPar.getString(CurPar.getColumnIndex(T_LineaParadas.LinParFechaHora)),
                                CurPar.getString(CurPar.getColumnIndex(T_LineaParadas.MotParDescripcion)),
                                CurPar.getInt(CurPar.getColumnIndex(T_LineaParadas.EstId))), Stmt.RETURN_GENERATED_KEYS);
                        Rse = Stmt.getGeneratedKeys();
                        if (Rse.next()) {
                            //ACTUALIZAR SINCRONIZADO Y ASIGNAR IDSERVIDOR
                            LocBD.execSQL(T_LineaParadas.LineaParadas_ActualizarSincronizado(
                                    CurPar.getInt(CurPar.getColumnIndex(BaseColumns._ID)), 1, Rse.getInt(1)));
                            Estado = true;
                        } else {
                            Estado = false;
                            //Toast.makeText(IngresoJabas_Grilla.this, "ERROR AL SINCRONIZAR", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e)
                {
                    Estado=false;
                    Toast.makeText(IngresoJabas_Grilla.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        return Estado;
    }
    private Boolean SincronizarIngreso(int IdMovil)
    {
        Boolean Estado=false;
        //CODIGO
        Rse=null;
        int IdServidor=0;
        Cursor CurIng = LocBD.rawQuery(T_LineaIngreso.LineaIngreso_SeleccionarSincronizar(IdMovil),null);
        if (CurIng.getCount()!=0)
        {
            for (CurIng.moveToFirst();!CurIng.isAfterLast();CurIng.moveToNext()) {
                try {
                    IdServidor=CurIng.getInt(CurIng.getColumnIndex(T_LineaIngreso.LinIngIdServidor));
                    if (IdServidor>0)
                    {
                        //SI YA HA SINCRONIZADO, ACTUALIZAR EN EL SERVIDOR
                        Rse=null;
                        Stmt.executeUpdate(T_LineaIngreso.LinIng_ActualizarServidor(IdServidor,
                                CurIng.getInt(CurIng.getColumnIndex(T_LineaIngreso.EstId))),Stmt.RETURN_GENERATED_KEYS);
                        Rse=Stmt.getGeneratedKeys();
                        if(Rse.next()) {
                            //Toast.makeText(IngresoJabas_Grilla.this, "ACTUALIZADO", Toast.LENGTH_SHORT).show();
                            Estado = true;
                        }else{
                            //Toast.makeText(IngresoJabas_Grilla.this, "ERROR AL SINCRONIZAR", Toast.LENGTH_SHORT).show();
                            Estado = false;
                        }
                    }
                    else {
                        //SI AUN NO HA SINCRONIZADO INSERTAR EN EL SERVIDOR
                        Rse = null;
                        Stmt.execute(T_LineaIngreso.LineaIngreso_InsertarServidor(
                                LinReg_IdServidor,
                                CurIng.getInt(CurIng.getColumnIndex(T_LineaIngreso.ConId)),
                                CurIng.getString(CurIng.getColumnIndex(T_LineaIngreso.ConDescripcionCor)),
                                CurIng.getInt(CurIng.getColumnIndex(T_LineaIngreso.LinIngCantidad)),
                                CurIng.getInt(CurIng.getColumnIndex(T_LineaIngreso.MatPriOriId)),
                                CurIng.getString(CurIng.getColumnIndex(T_LineaIngreso.MatPriOriDescripcion)),
                                CurIng.getDouble(CurIng.getColumnIndex(T_LineaIngreso.MatPriOriFactor)),
                                CurIng.getDouble(CurIng.getColumnIndex(T_LineaIngreso.LinIngEquivalente)),
                                CurIng.getString(CurIng.getColumnIndex(T_LineaIngreso.LinIngHoraIni)),
                                CurIng.getString(CurIng.getColumnIndex(T_LineaIngreso.LinIngHoraFin)),
                                CurIng.getDouble(CurIng.getColumnIndex(T_LineaIngreso.LinIngtEfectivo)),
                                CurIng.getInt(CurIng.getColumnIndex(T_LineaIngreso.LinIngMix)),
                                CurIng.getString(CurIng.getColumnIndex(T_LineaIngreso.LinIngFechaHora)),
                                CurIng.getInt(CurIng.getColumnIndex(T_LineaIngreso.LinIngSincronizado)),
                                CurIng.getInt(CurIng.getColumnIndex(T_LineaIngreso.EstId))), Stmt.RETURN_GENERATED_KEYS);
                        Rse = Stmt.getGeneratedKeys();
                        if (Rse.next()) {
                            //ACTUALIZAR SINCRONIZADO Y ASIGNAR IDSERVIDOR
                            LocBD.execSQL(T_LineaIngreso.LineaIngreso_ActualizarSincronizado(
                                    CurIng.getInt(CurIng.getColumnIndex(BaseColumns._ID)), 1, Rse.getInt(1)));
                            Estado=true;
                        } else {
                            //Toast.makeText(IngresoJabas_Grilla.this, "ERROR AL SINCRONIZAR", Toast.LENGTH_SHORT).show();
                            Estado=false;
                        }
                    }
                }catch (Exception e)
                {
                    Estado=false;
                    Toast.makeText(IngresoJabas_Grilla.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        return Estado;
    }
    private boolean SincronizarRegistro(Cursor CurReg)
    {
        boolean Resultado = true;
        boolean ResultadoParada=false;
        boolean ResultadoIngreso=false;

        String Mensaje="";
        try {
            Toast.makeText(IngresoJabas_Grilla.this, "REGISTROS "+CurReg.getCount(),Toast.LENGTH_SHORT).show();
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
                    //Recorrer los registros en el servidor para sincronizar
                    Rse= Stmt.executeQuery(T_LineaRegistro.LineaRegistro_SeleccionarLinea(
                            CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinId)),
                            Variables.FechaStr,Variables.Cul_Id));
                    //Si existe un registro
                    if (Rse.next())
                    {
                        //Si devuelve valor, actualizar
                        //LinReg_IdServidor = CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegId));
                        LinReg_IdServidor=Rse.getInt(T_LineaRegistro.LinRegId);
                        Rse=null;
                        Stmt.execute(T_LineaRegistro.LineaRegistro_ActualizarServidor(
                                LinReg_IdServidor,
                                HoraFin,
                                CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegCantidad)),
                                CurReg.getDouble(CurReg.getColumnIndex(T_LineaRegistro.LinRegHoraEfectiva)),
                                CurReg.getDouble(CurReg.getColumnIndex(T_LineaRegistro.LinRegParadas)),
                                CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegNumParadas)),
                                CurReg.getDouble(CurReg.getColumnIndex(T_LineaRegistro.LinRegCantidadPorHora)),
                                fnc.HoraSistema(),
                                CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.EstId)),
                                CurReg.getDouble(CurReg.getColumnIndex(T_LineaRegistro.LinRegTiempoTotal)),
                                CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegNumIngresos)),
                                CurReg.getString(CurReg.getColumnIndex(T_LineaRegistro.LinRegHoraIni))
                        ),Stmt.RETURN_GENERATED_KEYS);
                        Rse=Stmt.getGeneratedKeys();
                        if (Rse.next())
                        {
                            Resultado=true;
                            //SI DEVOLVIO UN REGISTRO
                            //--> SINCRONIZACION TABLA PARADAS
                            ResultadoParada = SincronizarParada(LinReg_IdMovil);
                            //--> SINCRONIZACION TABLA INGRESOS
                            ResultadoIngreso = SincronizarIngreso(LinReg_IdMovil);
                        }
                        else
                        {
                            Resultado=false;
                            //Toast.makeText(IngresoJabas_Grilla.this, "ERROR AL SINCRONIZAR"
                            //        +CurReg.getCount(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    //--> SINO SI NO EXISTE AUN REGISTRO
                    else
                    {
                        //Si no devuelve valor, insertar
                        Rse=null;
                        Stmt.execute(T_LineaRegistro.LineaRegistro_InsertarServidor(
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
                                CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.CulId)),
                                CurReg.getDouble(CurReg.getColumnIndex(T_LineaRegistro.LinRegTiempoTotal)),
                                CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegNumIngresos))
                        ),Stmt.RETURN_GENERATED_KEYS);
                        Rse=Stmt.getGeneratedKeys();

                        if (Rse.next())
                        {
                            Resultado=true;
                            LinReg_IdServidor = Rse.getInt(1);
                            LocBD.execSQL(T_LineaRegistro.LineaRegistro_ActualizarIdServidor(
                                    CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegIdMovil))
                                    ,LinReg_IdServidor
                            ));
                            //Actualiza a la tabla local la ultima Hora sincronización
                            LocBD.execSQL(T_LineaRegistro.LineaRegistro_ActualizarHoraSincro(
                                    CurReg.getInt(CurReg.getColumnIndex(T_LineaRegistro.LinRegIdMovil))
                                    ,fnc.HoraSistema()
                            ));
                            //INSERTAR SERVIDOR PARADA
                            ResultadoParada = SincronizarParada(LinReg_IdMovil);
                            //INSERTAR SERVIDOR INGRESO
                            ResultadoIngreso = SincronizarIngreso(LinReg_IdMovil);

                        }else
                        {
                            Resultado=false;
                           // Toast.makeText(IngresoJabas_Grilla.this, "ERROR AL SINCRONIZAR "
                            //        +CurReg.getCount(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            if(Resultado)
            {
                Mensaje="LINEA SINCRONIZADA ";
                if(ResultadoParada) {
                    Mensaje+="| PARADAS SINCRONIZADAS ";
                    //Toast.makeText(IngresoJabas_Grilla.this, "PARADAS SINCRONIZADAS", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Mensaje+="| SIN SINCRONIZAR PARADAS ";
                }
                if(ResultadoIngreso) {
                    Mensaje+="| INGRESOS SINCRONIZADOS ";
                    //Toast.makeText(IngresoJabas_Grilla.this, "INGRESOS SINCRONIZADOS", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Mensaje+="| SIN SINCRONIZAR INGRESOS ";
                }
            }else
            {
                Mensaje="ERROR SINCRONIZAR LINEA ";
                //Toast.makeText(IngresoJabas_Grilla.this, "ERROR AL SINCRONIZAR", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(IngresoJabas_Grilla.this, Mensaje, Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            Toast.makeText(IngresoJabas_Grilla.this, e.toString(),Toast.LENGTH_SHORT).show();
        }
        return Resultado;
    }
    private int Jabas(String Fecha,int SucId,int CulId,int MatOrigen)
    {
        Cursor curJabas = LocBD.rawQuery(T_LineaIngreso.LinIng_CantidadJabas(Fecha,SucId,CulId,MatOrigen),null);
        if (curJabas.getCount()>0)
        {
            curJabas.moveToFirst();
            return curJabas.getInt(0);
        }
        else
            return 0;
    }
    private double JabasHora(String Fecha,int SucId,int CulId)
    {
        Cursor curJabasHora = LocBD.rawQuery(T_LineaRegistro.LinReg_JabasHora(Fecha,SucId,CulId) ,null);
        if (curJabasHora.getCount()>0) {
            curJabasHora.moveToFirst();
            return curJabasHora.getInt(0);
        }else
        {
            return 0;
        }
    }


}
