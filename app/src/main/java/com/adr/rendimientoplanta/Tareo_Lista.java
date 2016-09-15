package com.adr.rendimientoplanta;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Tareo;

public class Tareo_Lista extends AppCompatActivity {

    private ImageButton imbConfigurar;
    private Button btnNuevo;

    private ListView lstTareo;

    private RadioButton rbnTodos;
    private RadioButton rbnPendiente;
    private  RadioButton rbnFinal;

    private ImageButton imbRegresar;

    private SimpleCursorAdapter AdaptadorLista;

    LocalBD LBD;
    SQLiteDatabase LocBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareo_lista);

        LBD = new LocalBD(this);
        LocBD = LBD.getWritableDatabase();
        //ASIGNACION DE VARIABLES A CONTROLES LAYOUT
        imbConfigurar = (ImageButton)findViewById(R.id.imbConfigurar);
        btnNuevo = (Button)findViewById(R.id.btnNuevo);

        lstTareo = (ListView)findViewById(R.id.lstTareo);

        rbnTodos = (RadioButton) findViewById(R.id.rbnTodos);
        rbnPendiente = (RadioButton) findViewById(R.id.rbnPendiente);
        rbnFinal = (RadioButton) findViewById(R.id.rbnFinal);

        imbRegresar = (ImageButton)findViewById(R.id.imbRegresar);

        AsignarFiltroLista();
        //ASIGNACION DE EVENTOS
        btnNuevo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent ActividadNueva = new Intent(Tareo_Lista.this, Tareo_Registro.class);
                startActivity(ActividadNueva);
            }
        });
        imbRegresar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent ActividadNueva = new Intent(Tareo_Lista.this, Principal_Menu.class);
                startActivity(ActividadNueva);
            }
        });
        imbConfigurar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent ActividadNueva = new Intent(Tareo_Lista.this, Tareo_Parametros.class);
                startActivity(ActividadNueva);
            }
        });

        lstTareo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ActividadModificar= new Intent(Tareo_Lista.this,Tareo_Registro.class);
                Cursor CursorCarga = (Cursor) parent.getItemAtPosition(position);
                ActividadModificar.putExtra("ID",CursorCarga.getInt(CursorCarga.getColumnIndex(BaseColumns._ID)));
                ActividadModificar.putExtra("ESNUEVO",false);
                ActividadModificar.putExtra("ESTADO",CursorCarga.getInt(CursorCarga.getColumnIndex(T_Tareo.ESTID)));
                startActivity(ActividadModificar);
            }
        });
        rbnPendiente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                AsignarFiltroLista();
            }
        });
        rbnFinal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                AsignarFiltroLista();
            }
        });
        rbnTodos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                AsignarFiltroLista();
            }
        });
    }
    private void AsignarFiltroLista()
    {
        if (rbnPendiente.isChecked())
        {
            LLenarList(1);
        }
        if (rbnFinal.isChecked())
        {
            LLenarList(2);
        }
        if (rbnTodos.isChecked())
        {
            LLenarList(-1);
        }
    }

    private  void LLenarList(int Estado)
    {
        //ASIGNACION DE DATOS A LIST VIEW
        Cursor Rse = null;
        if (LocBD != null)
        {
            Rse = LocBD.rawQuery(T_Tareo._SELECT_TAREOLISTA(Estado),null);
        }

        AdaptadorLista = new SimpleCursorAdapter(Tareo_Lista.this, android.R.layout.simple_list_item_2,Rse,
                new String[]{"ENCABEZADO","DETALLE"},new int[]{android.R.id.text1,android.R.id.text2},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lstTareo.setAdapter(AdaptadorLista);
    }
}
