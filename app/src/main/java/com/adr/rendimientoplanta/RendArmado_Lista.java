package com.adr.rendimientoplanta;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.LIBRERIA.Variables;


public class RendArmado_Lista extends AppCompatActivity {
    private SimpleAdapter AdaptadorLista;
    private SimpleCursorAdapter AdaptadorGrilla;
    //private ListView lstPersonalRendimiento;
    private GridView dgvPersonalRendimiento;

    private TextView lblEmpresa;
    private TextView lblSucursal;
    private TextView lblLinea;
    private TextView lblLado;
    private EditText edtFecha;

    private ImageButton imbRegresar;
    private ImageButton imbConfigurar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rend_armado_lista);

        //lstPersonalRendimiento = (ListView) findViewById(R.id.dgvPersonalRendimiento);
        dgvPersonalRendimiento = (GridView) findViewById(R.id.dgvPersonalRendimiento);

        LocalBD LBD = new LocalBD(RendArmado_Lista.this) ;
        final SQLiteDatabase LocBD = LBD.getWritableDatabase();

        //ASIGNACION DE VARIABLES A ELEMENTOS DEL LAYOUT
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblLinea = (TextView) findViewById(R.id.lblLinea);
        lblLado = (TextView) findViewById(R.id.lblLado);
        edtFecha = (EditText) findViewById(R.id.edtFecha);

        //ASIGNACION DE VARIABLES A ELEMENTOS DEL LAYOUT
        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);
        imbConfigurar = (ImageButton) findViewById(R.id.imbConfigurar);

        //ASIGNACIÓN DE PARAMETROS A LA ACTIVIDAD
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblLinea.setText(Variables.Lin_Descripcion);
        lblLado.setText(Variables.Lin_Lado);
        edtFecha.setText(Variables.FechaStr);


        Cursor Rse = LocBD.rawQuery("SELECT Per_Id AS '_id',1 AS 'MESA',Per_Nombres||' '||Per_ApePaterno||' '||Per_ApeMaterno AS 'APE',Per_Codigo FROM Personal LIMIT 22",null);


        AdaptadorGrilla = new SimpleCursorAdapter(RendArmado_Lista.this, android.R.layout.simple_list_item_2,Rse, new String[]{"_id","APE"},
                new int[]{android.R.id.text1,android.R.id.text2},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        dgvPersonalRendimiento.setAdapter(AdaptadorGrilla);

        dgvPersonalRendimiento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ActividadModificar = new Intent(RendArmado_Lista.this, RendArmado_Registro.class);

                Cursor curPersonal = (Cursor) parent.getItemAtPosition(position);
                Variables.Per_Ubicacion = curPersonal.getInt(curPersonal.getColumnIndex("_id"));
                Variables.Per_Nombres = curPersonal.getString(curPersonal.getColumnIndex("APE"));
                Variables.Per_Dni = curPersonal.getString(curPersonal.getColumnIndex("Per_Codigo"));
                /* //ANTIGUA FORMA

                ActividadModificar.putExtra("ID",((TextView)view.findViewById(android.R.id.text1)).getText().toString());
                ActividadModificar.putExtra("NOMBRES",((TextView)view.findViewById(android.R.id.text2)).getText().toString());
               */ //----

                //Se inicia la actividad nueva
                startActivity(ActividadModificar);


                //Toast.makeText(IngresoJabas_Lista.this, "Hiciste click en el registro " + OpcionSeleccion + " .",Toast.LENGTH_SHORT).show();
            }
        });
        imbConfigurar.setOnClickListener(new View.OnClickListener()
                                         {
                                             @Override
                                             public void onClick (View v){
            //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
            Intent ActividadNueva = new Intent(RendArmado_Lista.this, RendArmado_Parametros.class);
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
            Intent ActividadNueva = new Intent(RendArmado_Lista.this, Principal_Menu.class);
            //Se inicia la actividad nueva
            startActivity(ActividadNueva);
        }
        }
        );

    }
}
