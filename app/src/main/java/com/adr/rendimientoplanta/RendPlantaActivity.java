package com.adr.rendimientoplanta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.ConexionBD;

import java.sql.*;
import java.util.*;

public class RendPlantaActivity extends AppCompatActivity {
    private static final String TAG = "RendPlantaActivity";
    private ListView ListaRendimiento;
    private SimpleAdapter AdaptadorLista;
    private Button BtnConsultar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendimiento_planta);

        //Se asigna a las variables los controles del layout correspondiente a la clase
        ListaRendimiento = (ListView)findViewById(R.id.ListaRendimiento);
        BtnConsultar = (Button)findViewById(R.id.BtnConsultar);

        //Region - Listeners
        BtnConsultar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    Connection Cnn = ConexionBD.getInstance().getConnection();
                    Statement Stmt = Cnn.createStatement();
                    ResultSet Rse = Stmt.executeQuery("Select Res_Codigo,Res_Descripcion From Responsable");

                    List<Map<String, Object>> data = null;
                    data = new ArrayList<Map<String, Object>>();
                    while (Rse.next()) {
                        Map<String, Object> valores = new HashMap<String, Object>();
                        valores.put("A", Rse.getString("Res_Codigo"));
                        valores.put("B", Rse.getString("Res_Descripcion"));
                        data.add(valores);
                    }
                    String[] from = {"A","B"};
                    int[] views ={R.id.textId,R.id.txtDescripcion};
                    //AdaptadorLista = new SimpleAdapter(RendPlantaActivity.this,data,R.layout.listview_items,from,views);

                    AdaptadorLista = new SimpleAdapter(RendPlantaActivity.this,data,R.layout.listview_items,from,views);
                    ListaRendimiento.setAdapter(AdaptadorLista);
                    //Cnn.close();
                }catch (Exception e)
                {
                    Log.e(TAG, "Error Exception: " + e);
                }
            }
        }
        );
        //Listener de listview
       ListaRendimiento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String OpcionSeleccion;
               //OpcionSeleccion= ListaRendimiento.getAdapter().getItem(position).toString();
               //OPCION 1 REFERENCIANDO AL CONTROL
               OpcionSeleccion= ((TextView)view.findViewById(R.id.textId)).getText().toString();
               //OPCION 2 REFERENCIANDO AL ITEM - ES NECESARIO UNA CLASE ADICIONAL
               //OpcionSeleccion=ListaRendimiento.getAdapter().getItem(position).toString()
               Toast.makeText(RendPlantaActivity.this, "Hiciste click en el item " + OpcionSeleccion + " con el ID"+id,Toast.LENGTH_SHORT).show();
           }
       });
        //Fin Listener
        //EndRegion

        //Fin del listener del boton consultar (Click)



    }
}
