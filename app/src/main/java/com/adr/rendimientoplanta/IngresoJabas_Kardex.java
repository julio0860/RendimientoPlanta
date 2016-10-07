package com.adr.rendimientoplanta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_LineaIngreso;
import com.adr.rendimientoplanta.DATA.T_LineaParadas;
import com.adr.rendimientoplanta.DATA.T_LineaRegistro;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

public class IngresoJabas_Kardex extends AppCompatActivity {

    private int RegLin_Id;

    private ImageButton imbRegresar;

    private ListView lstParadas;
    private ListView lstIngresos;

    //SMP: Declaración de variables TextView
    private TextView lblSucursal;
    private TextView lblCultivo;
    private TextView lblFecha;
    private TextView lblEmpresa;
    private TextView lblLinea;
    private int Est_IdPrincipal;

    private int LinPar_Id=0;
    private String ParHoraIni;
    private String ParHoraFin;
    private double ParTiempo;
    private String MensajeAnularParada="";

    private int LinIng_Id=0;
    private String IngHoraIni;
    private String IngHoraFin;
    private String IngLote;
    private int IngCantidad;
    private String MensajeAnularIngreso="";

    private boolean Ejecutar=false;
    private MenuItem men;



    LocalBD LBD;
    SQLiteDatabase LocBD;

    @Override
    public void onBackPressed()
    {
        Intent NuevaActividad = new Intent(IngresoJabas_Kardex.this,IngresoJabas_RegistroLinea.class);
        startActivity(NuevaActividad);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_jabas_kardex);

        LBD = new LocalBD(this) ;
        LocBD = LBD.getWritableDatabase();

        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);

        lstParadas = (ListView) findViewById(R.id.lstParadas);
        lstIngresos= (ListView) findViewById(R.id.lstIngresos);
        RegLin_Id = getIntent().getIntExtra("RegLin_Id",0);
        Est_IdPrincipal=getIntent().getIntExtra("Est_Id",0);

        //SMP: Asignación de variables TextView a Layout
        lblCultivo = (TextView) findViewById(R.id.lblCultivo);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblLinea = (TextView) findViewById(R.id.lblLinea);

        //SMP: Asignación de texto a variables TextView en base a parametros establecidos
        lblFecha.setText(Variables.FechaStr);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblCultivo.setText(Variables.Cul_Descripcion);
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblLinea.setText(Variables.Lin_Descripcion);

        CargarIngresos();
        CargarParadas();

        imbRegresar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent NuevaActividad = new Intent(IngresoJabas_Kardex.this,
                        IngresoJabas_RegistroLinea.class);
                startActivity(NuevaActividad);
            }
        });
        lstParadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
            }
        });
        lstIngresos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
            }
        });
        lstParadas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);

                if (Est_IdPrincipal==1) {
                    Cursor curParadas = (Cursor) parent.getItemAtPosition(position);
                    LinPar_Id = curParadas.getInt(curParadas.getColumnIndex(BaseColumns._ID));
                    ParHoraIni= curParadas.getString(curParadas.getColumnIndex(T_LineaParadas.LinParHoraIni));
                    ParHoraFin= curParadas.getString(curParadas.getColumnIndex(T_LineaParadas.LinParHoraFin));
                    ParTiempo= curParadas.getDouble(curParadas.getColumnIndex(T_LineaParadas.LinParParada));
                    MensajeAnularParada="INI "+ParHoraIni+" | FIN "+ParHoraFin+" | "+ParTiempo;

                    registerForContextMenu(lstParadas);
                }
                return false;
            }
        }) ;
        lstIngresos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);

                if (Est_IdPrincipal==1)
                {
                    Cursor curIngreso = (Cursor) parent.getItemAtPosition(position);
                    LinIng_Id= curIngreso.getInt(curIngreso.getColumnIndex(BaseColumns._ID));
                    IngHoraIni=curIngreso.getString(curIngreso.getColumnIndex(T_LineaIngreso.LinIngHoraIni));
                    IngHoraFin=curIngreso.getString(curIngreso.getColumnIndex(T_LineaIngreso.LinIngHoraFin));
                    IngLote=curIngreso.getString(curIngreso.getColumnIndex(T_LineaIngreso.ConDescripcionCor));
                    IngCantidad=curIngreso.getInt(curIngreso.getColumnIndex(T_LineaIngreso.LinIngCantidad));
                    MensajeAnularIngreso="INI: "+IngHoraIni+" | FIN: "+IngHoraFin+" | L: "+IngLote+" | JBS: "+IngCantidad;
                    registerForContextMenu(lstIngresos);
                }
                return false;
            }
        }) ;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        //MenuInflater inflater = new MenuInflater(this);

        switch(v.getId()){
            case R.id.lstParadas:
                //TODO CODE
                menu.setHeaderTitle("REVISIÓN DE PARADA");
                menu.add(0, v.getId(), 0, "ANULAR PARADA: "+MensajeAnularParada);
                break;
            case R.id.lstIngresos:
                //TODO CODE
                menu.setHeaderTitle("REVISIÓN DE INGRESO");
                menu.add(1, v.getId(), 0, "ANULAR INGRESO: "+MensajeAnularIngreso);
                break;
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        men=item;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IngresoJabas_Kardex.this);
        String alert_title = "REVISIÓN REGISTRO";
        String alert_description = "¿ESTA SEGURO QUE QUIERE ANULAR EL REGISTRO?";
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
                                ProcesoAnularParada(men.getGroupId());
                                break;

                            case 1:
                                ProcesoAnularIngreso(men.getGroupId());
                                break;
                        }

                    }

                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // Si se pulsa no no hace nada
                        Ejecutar=false;
                        Toast.makeText(IngresoJabas_Kardex.this,"Operación cancelada",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
        return true;
    }
    private void ProcesoAnularParada(int id){

        LocBD.execSQL(T_LineaParadas.LinPar_AnularRegistro(LinPar_Id,1));
        Toast.makeText(this, "PARADA ANULADA", Toast.LENGTH_SHORT).show();
        CargarParadas();
    }
    private void ProcesoAnularIngreso(int id){

        LocBD.execSQL(T_LineaIngreso.LinIng_AnularRegistro(LinIng_Id,1));
        Toast.makeText(this, "INGRESO ANULADO", Toast.LENGTH_SHORT).show();
        CargarIngresos();
    }
    private void CargarIngresos() {
        Cursor CurLineaIngresos = LocBD.rawQuery(T_LineaIngreso.LineaIngreso_SeleccionarIdCabecera(RegLin_Id),null);
        SimpleCursorAdapter AdaptadorListaIngresos = new SimpleCursorAdapter(this,R.layout.listview_simple_7item,
                CurLineaIngresos,new String[]{T_LineaIngreso.LinIngHoraIni,T_LineaIngreso.LinIngHoraFin,T_LineaIngreso.LinIngtEfectivo,
                T_LineaIngreso.ConDescripcionCor,T_LineaIngreso.LinIngCantidad,T_LineaIngreso.MatPriOriDescripcion,T_LineaIngreso.LinIngMix},
                new int[]{R.id.text1,R.id.text2,R.id.text3,R.id.text4,R.id.text5,R.id.text6
                        ,R.id.text7},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lstIngresos.setAdapter(AdaptadorListaIngresos);
    }
    private void CargarParadas() {
        Cursor CurLineaParadas = LocBD.rawQuery(T_LineaParadas.LineaParadas_SeleccionarIdCabecera(RegLin_Id),null);
        SimpleCursorAdapter AdaptadorListaParadas = new SimpleCursorAdapter(this,R.layout.listview_simple_4item ,
                CurLineaParadas,new String[]{T_LineaParadas.LinParHoraIni,T_LineaParadas.LinParHoraFin,T_LineaParadas.LinParParada,T_LineaParadas.MotParDescripcion},
                new int[]{R.id.text2,R.id.text1,R.id.text3,R.id.text4},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lstParadas.setAdapter(AdaptadorListaParadas);
    }

}
