package com.adr.rendimientoplanta;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    LocalBD LBD;
    SQLiteDatabase LocBD;
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

        Cursor CurLineaParadas = LocBD.rawQuery(T_LineaParadas.LineaParadas_SeleccionarIdCabecera(RegLin_Id),null);
        //SimpleCursorAdapter AdaptadorListaParadas = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,
        //        CurLineaParadas,new String[]{T_LineaParadas.LinParParada,T_LineaParadas.MotParDescripcion},
        //        new int[]{android.R.id.text1,android.R.id.text2},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        //lstParadas.setAdapter(AdaptadorListaParadas);

        SimpleCursorAdapter AdaptadorListaParadas = new SimpleCursorAdapter(this,R.layout.listview_simple_4item ,
                CurLineaParadas,new String[]{T_LineaParadas.LinParHoraIni,T_LineaParadas.LinParHoraFin,T_LineaParadas.LinParParada,T_LineaParadas.MotParDescripcion},
                new int[]{R.id.text2,R.id.text1,R.id.text3,R.id.text4},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lstParadas.setAdapter(AdaptadorListaParadas);

        Cursor CurLineaIngresos = LocBD.rawQuery(T_LineaIngreso.LineaIngreso_SeleccionarIdCabecera(RegLin_Id),null);
        SimpleCursorAdapter AdaptadorListaIngresos = new SimpleCursorAdapter(this,R.layout.listview_simple_7item,
                CurLineaIngresos,new String[]{T_LineaIngreso.LinIngHoraIni,T_LineaIngreso.LinIngHoraFin,T_LineaIngreso.LinIngtEfectivo,
        T_LineaIngreso.ConDescripcionCor,T_LineaIngreso.LinIngCantidad,T_LineaIngreso.MatPriOriDescripcion,T_LineaIngreso.LinIngMix},
                new int[]{R.id.text1,R.id.text2,R.id.text3,R.id.text4,R.id.text5,R.id.text6
                        ,R.id.text7},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lstIngresos.setAdapter(AdaptadorListaIngresos);
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
                registerForContextMenu(lstParadas);
                return false;
            }
        }) ;
        lstIngresos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                registerForContextMenu(lstIngresos);
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
                menu.add(0, v.getId(), 0, "ANULAR PARADA SELECCIONADA");
                break;
            case R.id.lstIngresos:
                //TODO CODE
                menu.setHeaderTitle("REVISIÓN DE INGRESO");
                menu.add(1, v.getId(), 0, "ANULAR INGRESO SELECCIONADA");
                break;
        }

        //menu.setHeaderTitle("MESA NRO: "+Variables.Per_Ubicacion);
        //menu.add(0, v.getId(), 0, "ASIGNAR PERSONAL");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getGroupId())
        {
            case 0:
                ProcesoAnularParada(item.getGroupId());
                break;

            case 1:
                ProcesoAnularIngreso(item.getGroupId());
                break;
            default:
                return false;
        }
        return true;
        /*if(item.getTitle()=="ASIGNAR PERSONAL")
        {
            function1(item.getItemId());
        }
        else {
            return false;
        }
        return true;
        */
    }
    public void ProcesoAnularParada(int id){

        //Intent ActividadModificar = new Intent(IngresoJabas_Kardex.this, RegistroOperario.class);
        //startActivity(ActividadModificar);
        Toast.makeText(this, "PARADA ANULADA", Toast.LENGTH_SHORT).show();
    }
    public void ProcesoAnularIngreso(int id){

        //Intent ActividadModificar = new Intent(IngresoJabas_Kardex.this, RegistroOperario.class);
        //startActivity(ActividadModificar);
        Toast.makeText(this, "INGRESO ANULADO", Toast.LENGTH_SHORT).show();
    }
}
