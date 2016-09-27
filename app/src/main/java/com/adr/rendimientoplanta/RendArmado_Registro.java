package com.adr.rendimientoplanta;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Linea;
import com.adr.rendimientoplanta.DATA.T_PresentacionEnvase;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

public class RendArmado_Registro extends AppCompatActivity {

    private TextView lblEmpresa;
    private TextView lblSucursal;
    private TextView lblLinea;
    private TextView lblLado;
    private EditText edtFecha;

    private TextView lblMesa;
    private TextView lblDNI;
    private TextView lblNombres;

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
        edtFecha = (EditText) findViewById(R.id.lblFecha);

        lblMesa = (TextView) findViewById(R.id.lblMesa);
        lblDNI = (TextView) findViewById(R.id.lblDNI);
        lblNombres = (TextView) findViewById(R.id.lblNombres);
        //Poblar GridView
        dgvPresentacion = (GridView) findViewById(R.id.dgvPresentacion);
        Cursor CurPresentacion = LocBD.rawQuery(T_PresentacionEnvase.PresentacionEnvase_SeleccionarEstado (2),null);

        adspnPresentacion = new SimpleCursorAdapter(this,
                android.R.layout.simple_dropdown_item_1line,CurPresentacion,
                new String[]{T_PresentacionEnvase.PreEnvDescripcionCor}, new int[]{android.R.id.text1},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        dgvPresentacion.setAdapter(adspnPresentacion);

        //ASIGNACIÓN DE PARAMETROS A LA ACTIVIDAD
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblLinea.setText(Variables.Lin_Descripcion);
        lblLado.setText(Variables.Lin_Lado);
        edtFecha.setText(Variables.FechaStr);

        lblMesa.setText(String.valueOf(Variables.Per_Ubicacion));
        lblDNI.setText(Variables.Per_Dni);
        lblNombres.setText(Variables.Per_Nombres);


    }
}
