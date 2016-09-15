package com.adr.rendimientoplanta;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Linea;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

public class IngresoJabas_Grilla extends AppCompatActivity {

    private TextView lblSucursal;
    private TextView lblCultivo;
    private TextView lblFecha;
    private TextView lblEmpresa;

    private GridView dgvLineas;
    private ImageButton imbConfigurar;
    SimpleCursorAdapter adspnLineas;

    private ImageButton imbRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_jabas_grilla);

        LocalBD LBD = new LocalBD(IngresoJabas_Grilla.this) ;
        final SQLiteDatabase LocBD = LBD.getWritableDatabase();

        lblCultivo = (TextView) findViewById(R.id.lblCultivo);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblFecha = (TextView) findViewById(R.id.lblFecha);

        lblFecha.setText(Variables.FechaStr);
        imbConfigurar = (ImageButton) findViewById(R.id.imbConfigurar);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblCultivo.setText(Variables.Cul_Descripcion);
        lblEmpresa.setText(Variables.Emp_Abrev);

        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);

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
