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
import com.adr.rendimientoplanta.DATA.T_PresentacionEnvase;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

public class RendArmado_Registro extends AppCompatActivity {

    private TextView lblEmpresa;
    private TextView lblSucursal;
    private TextView lblLinea;
    private TextView lblLado;
    private TextView lblFecha;

    private TextView lblMesa;
    private TextView lblDNI;
    private TextView lblNombres;

    private ImageButton imbRegresar;

    private GridView dgvPresentacion;
    SimpleCursorAdapter adspnPresentacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rend_armado_registro);


        LocalBD LBD = new LocalBD(this) ;
        final SQLiteDatabase LocBD = LBD.getWritableDatabase();
        //ASIGNACION DE VARIABLES A ELEMENTOS DEL LAYOUT
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblLinea = (TextView) findViewById(R.id.lblLinea);
        lblLado = (TextView) findViewById(R.id.lblLado);
        lblFecha = (TextView) findViewById(R.id.lblFecha);

        lblMesa = (TextView) findViewById(R.id.lblMesa);
        lblDNI = (TextView) findViewById(R.id.lblDNI);
        lblNombres = (TextView) findViewById(R.id.lblNombres);

        imbRegresar = (ImageButton)findViewById(R.id.imbRegresar);
        //Poblar GridView
        dgvPresentacion = (GridView) findViewById(R.id.dgvPresentacion);
        Cursor CurPresentacion = LocBD.rawQuery(T_PresentacionEnvase.PresentacionEnvase_SeleccionarEstado (2),null);

        adspnPresentacion = new SimpleCursorAdapter(this,
                R.layout.gridview_itemborde,CurPresentacion,
                new String[]{T_PresentacionEnvase.PreEnvDescripcionCor}, new int[]{R.id.text1},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        dgvPresentacion.setAdapter(adspnPresentacion);
        dgvPresentacion.setNumColumns(4);
        //ASIGNACIÓN DE PARAMETROS A LA ACTIVIDAD
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblLinea.setText(Variables.Lin_Descripcion);
        lblLado.setText(Variables.Lin_Lado);
        lblFecha.setText(Variables.FechaStr);

        lblMesa.setText(String.valueOf(Variables.Per_Ubicacion));
        lblDNI.setText(Variables.Per_Dni);
        lblNombres.setText(Variables.Per_Nombres);

        dgvPresentacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent ActividadModificar = new Intent(RendArmado_Registro.this, RendArmado_Kardex.class);

                Cursor CursorCarga = (Cursor) parent.getItemAtPosition(position);

                Variables.PreEnv_Id= CursorCarga.getInt(CursorCarga.getColumnIndex("_id"));
                Variables.Pre_Id = CursorCarga.getInt(CursorCarga.getColumnIndex(T_PresentacionEnvase.PreId));
                Variables.PreEnv_CantidadTorre = CursorCarga.getInt(CursorCarga.getColumnIndex(T_PresentacionEnvase.PreEnvCantidadTorre));
                Variables.PreEnv_PesoTorre = CursorCarga.getDouble(CursorCarga.getColumnIndex(T_PresentacionEnvase.PreEnvPesoTorre));
                Variables.PreEnv_DescripcionCor = CursorCarga.getString(CursorCarga.getColumnIndex(T_PresentacionEnvase.PreEnvDescripcionCor));
                Variables.Pre_Descripcion = CursorCarga.getString(CursorCarga.getColumnIndex(T_PresentacionEnvase.PreDescripcion));
                Variables.PreEnv_Factor = CursorCarga.getDouble(CursorCarga.getColumnIndex(T_PresentacionEnvase.PreEnvFactor));
                startActivity(ActividadModificar);

            }
        });
        imbRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NuevaActividad = new Intent(RendArmado_Registro.this,RendArmado_Lista.class);
                startActivity(NuevaActividad);
           }
       });
    }
}
