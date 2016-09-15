package com.adr.rendimientoplanta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class ListaRendimientoPlanta extends AppCompatActivity {

    ArrayAdapter<String> adspnPlanta;
    private Spinner spnPlanta;

    private ListView lstRendimientoPlanta;
    private Button btnFiltrar;

    final String[] Plantas = new String[]{"PLANTA YANCAY","PLANTA DON CARLOS"};
    final String[] Lineas = new String[]{"LINEA 1 - PUN PY",
            "LINEA 2 - PUN PY",
            "LINEA 3 - PUN PY",
            "LINEA 4 - PUN PY",
            "LINEA 1 - PUN PDC",
            "LINEA 2 - PUN PDC",
            "LINEA 3 - PUN PDC",
            "LINEA 4 - PUN PDC",
            "LINEA 7 - LOO PDC",
            "LINEA 6 - LOO PDC",
            "LINEA 5 - LOO PDC",
            "LINEA 1 - LOO PY",
            "LINEA 2 - LOO PY",
            "LINEA 3 - LOO PY",
            "LINEA 4 - MINI LOO PDC",
            "LINEA 3 - MINI LOOSE PDC",
            "LINEA 2 - MINI LOOSE PDC",
            "LINEA 1 - MINI LOOSE PDC"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_rendimiento_planta);

        spnPlanta = (Spinner) findViewById(R.id.spnSucursal);
        btnFiltrar = (Button) findViewById(R.id.btnFiltrar);

        adspnPlanta =new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Plantas);
        spnPlanta.setAdapter(adspnPlanta);

        lstRendimientoPlanta = (ListView) findViewById(R.id.lstListaRendimientoPlanta);
        //    AdaptadorGrilla = new SimpleCursorAdapter(RendArmado_Lista.this, android.R.layout.simple_list_item_2,Rse, new String[]{BaseColumns._ID,"APE"},
        //            new int[]{android.R.id.text1,android.R.id.text2},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
               /* try {
            Cursor Rse = null;
            if (LocBD != null)
            {
                //String [] DNI= new String[1];
                //DNI[1]="46821478";
                //ResultSet Rse =  LocBD.execSQL("SELECT Per_Codigo,Per_Nombres FROM PERSONAL");
                //Rse = LocBD.rawQuery("SELECT Per_Codigo,Per_Nombres||' '||Per_ApePaterno||' '||Per_ApeMaterno FROM Personal",null);

                Rse = LocBD.rawQuery("SELECT COUNT(*) AS 'MESA',Per_ApePaterno||' '||Per_ApeMaterno AS 'APE' FROM Personal LIMIT 22",null);

                AdaptadorGrilla = new SimpleCursorAdapter(RendArmado_Lista.this, android.R.layout.simple_list_item_2,Rse, new String[]{"MESA","APE"},
                        new int[]{android.R.id.text1,android.R.id.text2},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                dgvPersonalRendimiento.setAdapter(AdaptadorGrilla);
                Toast.makeText(RendArmado_Lista.this,"HOLA"+ Rse.getString(1),Toast.LENGTH_SHORT).show();
            }


            //Connection Cnn = ConexionBD.getInstance().getConnection();
            //Statement Stmt = Cnn.createStatement();
            //ResultSet Rse = Stmt.executeQuery("Select Res_Codigo,Res_Descripcion From Responsable");
*/
        /*  IMPORTANTE OTRO METODO
            List<Map<String, Object>> data = null;
            data = new ArrayList<Map<String, Object>>();
            //while (Rse.moveToNext()) {
            //   Map<String, Object> valores = new HashMap<String, Object>();
            // valores.put("A", Rse.getString(1));
            //    valores.put("B", Rse.getString(2));
            //    data.add(valores);
            // }
            for (Rse.moveToFirst();!Rse.isAfterLast();Rse.moveToNext()) {
                Map<String, Object> valores = new HashMap<String, Object>();
                valores.put("A", Rse.getString(0));
                valores.put("B", Rse.getString(1));
                data.add(valores);
            }

            String[] from = {"A", "B"};
            int[] views = {R.id.textId, R.id.txtDescripcion};
            //AdaptadorLista = new SimpleAdapter(RendPlantaActivity.this,data,R.layout.listview_items,from,views);

           //original //AdaptadorLista = new SimpleAdapter(RendArmado_Lista.this, data, R.layout.listview_items, from, views);

            //AdaptadorLista = new SimpleAdapter(RendArmado_Lista.this, data, android.R.layout., from, views);
            //lstPersonalRendimiento.setAdapter(AdaptadorLista);
*/

        /*//Cnn.close();
        } catch (Exception e) {
            //Log.e(TAG, "Error Exception: " + e);
        }
*/



        btnFiltrar.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                //Se crea un Intent para llamar a una nueva Actividad(Pantalla)
                Intent ActividadNueva = new Intent(ListaRendimientoPlanta.this,RendArmado_Lista.class);
                //Se inicia la actividad nueva
                startActivity(ActividadNueva);
            }
        }
        );

        lstRendimientoPlanta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String OpcionSeleccion;
                //OpcionSeleccion= ListaRendimiento.getAdapter().getItem(position).toString();
                //OPCION 1 REFERENCIANDO AL CONTROL
                //OpcionSeleccion= ((TextView)view.findViewById(R.id.textId)).getText().toString();
                //OPCION 2 REFERENCIANDO AL ITEM - ES NECESARIO UNA CLASE ADICIONAL
                //OpcionSeleccion=ListaRendimiento.getAdapter().getItem(position).toString()
                //Toast.makeText(ListaRendimientoPlanta.this, "Hiciste click en el item " + OpcionSeleccion + " con el ID"+id,Toast.LENGTH_SHORT).show();

                //String OpcionSeleccion;
                //OpcionSeleccion= ListaRendimiento.getAdapter().getItem(position).toString();
                //OPCION 1 REFERENCIANDO AL CONTROL
                //OpcionSeleccion= ((TextView)view.findViewById(R.id.lstlblId)).getText().toString();
                //OPCION 2 REFERENCIANDO AL ITEM - ES NECESARIO UNA CLASE ADICIONAL
                //OpcionSeleccion=ListaRendimiento.getAdapter().getItem(position).toString()
                //Intent ActividadModificar = new Intent(RendArmado_Lista.this, RegistroRendimiento.class);
                //Pasar información de un activity a otra
                // ActividadModificar.putExtra("ID",((TextView)view.findViewById(R.id.textId)).getText().toString());
                //  ActividadModificar.putExtra("NOMBRES",((TextView)view.findViewById(R.id.txtDescripcion)).getText().toString());

                //ActividadModificar.putExtra("ID",((TextView)view.findViewById(android.R.id.text1)).getText().toString());
                //ActividadModificar.putExtra("NOMBRES",((TextView)view.findViewById(android.R.id.text2)).getText().toString());

                //Se inicia la actividad nueva
                //startActivity(ActividadModificar);


                //Toast.makeText(IngresoJabas_Lista.this, "Hiciste click en el registro " + OpcionSeleccion + " .",Toast.LENGTH_SHORT).show();


                Intent ActividadNueva = new Intent(ListaRendimientoPlanta.this,RendArmado_Lista.class);
                startActivity(ActividadNueva);
            }

        });
    }
}
