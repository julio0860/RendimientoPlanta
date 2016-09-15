package com.adr.rendimientoplanta;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Consumidor;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

public class IngresoJabas_RegistroJabas extends AppCompatActivity {

    private TextView lblSucursal;
    private TextView lblCultivo;
    private TextView lblFecha;
    private TextView lblEmpresa;

    private TextView lblLinea;

    private ImageButton imbRegresar;

    private CheckBox cbxMix;
    private EditText edtCantidadMix;
    private Spinner spnConsumidorMix;
    private RadioGroup rbnGrupoMix;

    private Spinner spnConsumidor;
    SimpleCursorAdapter adspnConsumidor;

    LocalBD LBD;
    SQLiteDatabase LocBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_jabas_registro_jabas);

        LBD = new LocalBD(IngresoJabas_RegistroJabas.this) ;
        LocBD = LBD.getWritableDatabase();

        lblCultivo = (TextView) findViewById(R.id.lblCultivo);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblLinea = (TextView) findViewById(R.id.lblLinea);

        cbxMix = (CheckBox) findViewById(R.id.cbxMix);
        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);

        edtCantidadMix = (EditText) findViewById(R.id.edtCantidadMix);
        spnConsumidorMix = (Spinner) findViewById(R.id.spnConsumidorMix);
        rbnGrupoMix = (RadioGroup) findViewById(R.id.rbnGrupoMix);

        lblFecha.setText(Variables.FechaStr);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblCultivo.setText(Variables.Cul_Descripcion);
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblLinea.setText(Variables.Lin_Descripcion);

        spnConsumidor = (Spinner) findViewById(R.id.spnConsumidor);
        Cursor Consumidor = LocBD.rawQuery(T_Consumidor._SELECT_CON(1,2, Variables.Cul_Id, Variables.Emp_Id),null);
        adspnConsumidor = new SimpleCursorAdapter(this ,
                android.R.layout.simple_dropdown_item_1line,Consumidor,//Layout simple
                //Todos los registros
                new String[]{T_Consumidor.CONDESCRIPCIONCOR},//Mostrar solo el nombre
                new int[]{android.R.id.text1}//View para el nombre
                ,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spnConsumidor.setAdapter(adspnConsumidor);

        imbRegresar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                //TextView HORA inicio
                //lblHoraInicio.setText(Formato.format(new Date(System.currentTimeMillis())));
                Intent NuevaActividad = new Intent(IngresoJabas_RegistroJabas.this,IngresoJabas_RegistroLinea.class);
                startActivity(NuevaActividad);
            }
        });
        cbxMix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                VisibilidadConsumidor(isChecked);
                //if (isChecked==true)
                //{
                //    CargarLoteMix();
                //}
            }
        });

    }
    private void VisibilidadConsumidor(Boolean Est)
    {
        if (Est==true) {
            spnConsumidorMix.setVisibility(View.VISIBLE);
            edtCantidadMix.setVisibility(View.VISIBLE);
            rbnGrupoMix.setVisibility(View.VISIBLE);
        }else
        {
            spnConsumidorMix.setVisibility(View.INVISIBLE);
            edtCantidadMix.setVisibility(View.INVISIBLE);
            rbnGrupoMix.setVisibility(View.INVISIBLE);

        }
    }
}
