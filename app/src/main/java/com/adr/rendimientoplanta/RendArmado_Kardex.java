package com.adr.rendimientoplanta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_LineaRegistro;
import com.adr.rendimientoplanta.DATA.T_PresentacionEnvase;
import com.adr.rendimientoplanta.DATA.T_RendimientoArmado;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

import java.math.BigDecimal;

public class RendArmado_Kardex extends AppCompatActivity {
    //DECLARACION BD LOCAL
    private String TipoOperacion="AGREGAR ENTREGA";

    //DECLARACION FUNCIONES
    private Funciones fnc;
    //DECLARACIÓN DE VARIABLES TEXTVIEW
    private TextView lblEmpresa;
    private TextView lblSucursal;
    private TextView lblLinea;
    private TextView lblLado;
    private TextView lblFecha;
    private TextView lblMesa;
    private TextView lblDNI;
    private TextView lblNombres;
    private TextView lblPresentacion;

    //DECLARACION DE VARIABLES BUTTON
    private Button btnRegistrar;
    private Button btnCambiarPresentacion;
    private ImageButton imbRegresar;

    //DECLARACION DEL RADIOGROUP
    private RadioGroup rbgGrupoTipo;
    private RadioButton rbnEntrega;
    private RadioButton rbnDevolucion;

    //DECLARACIÓN DE VARIABLES TEXTVIEW
    private EditText edtCantidad;
    private EditText edtPeso;

    //DECLARACION DE VARIABLE LISTVIEW
    private ListView lstIngresos;

    SimpleCursorAdapter adspnEntrega;

   LocalBD LBD;
   SQLiteDatabase LocBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rend_armado_kardex);

        //LocalBD LBD = new LocalBD(this) ;
        //final SQLiteDatabase LocBD = LBD.getWritableDatabase();
        LBD = new LocalBD(this) ;
        LocBD = LBD.getWritableDatabase();
        //ASIGNACION DE VARIABLES A TEXTVIEW DE LAYOUT
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblLinea = (TextView) findViewById(R.id.lblLinea);
        lblLado = (TextView) findViewById(R.id.lblLado);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblMesa = (TextView) findViewById(R.id.lblMesa);
        lblDNI = (TextView) findViewById(R.id.lblDNI);
        lblNombres = (TextView) findViewById(R.id.lblNombres);
        lblPresentacion = (TextView) findViewById(R.id.lblPresentacion);

        //ASIGNACION DE VARIABLES A LISTVIEW
        lstIngresos = (ListView) findViewById(R.id.lstIngresos);

        //DECLARACION FUNCIONES
        fnc=new Funciones();

        //ASIGNAR VARIABLES A BUTTON EN LAYOUT
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnCambiarPresentacion = (Button)findViewById(R.id.btnCambiarPresentacion);

        imbRegresar = (ImageButton)findViewById(R.id.imbRegresar);
        //ASIGNACION DE VARIABLES A EDITTEXT DE LAYOUT
        edtCantidad = (EditText) findViewById(R.id.edtCantidad);
        edtPeso = (EditText) findViewById(R.id.edtPeso);

        //ASIGNACION DE RADIOGROUP Y RADIOBUTTON A LAYOUT
        rbgGrupoTipo = (RadioGroup) findViewById(R.id.rbgGrupoTipo);
        rbnEntrega = (RadioButton)findViewById(R.id.rbnEntrega);
        rbnDevolucion=(RadioButton)findViewById(R.id.rbnDevolucion);

        //ASIGNACIÓN DE PARAMETROS A LA ACTIVIDAD
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblLinea.setText(Variables.Lin_Descripcion);
        lblLado.setText(Variables.Lin_Lado);
        lblFecha.setText(Variables.FechaStr);
        lblMesa.setText(String.valueOf(Variables.Per_Ubicacion));
        lblDNI.setText(Variables.Per_Dni);
        lblNombres.setText(Variables.Per_Nombres);
        lblPresentacion.setText(Variables.PreEnv_DescripcionCor);

        //ASIGNACION DE EDITTEXT A ACTIVITY
        edtPeso.setText(String.valueOf((int)(Variables.PreEnv_PesoTorre*1000)));
        edtCantidad.setText(String.valueOf(Variables.PreEnv_CantidadTorre));

        ActualizarLista();
        //Cursor CurIngresos = LocBD.rawQuery(T_RendimientoArmado.RendimientoArmado_SeleccionarPorPersona(
        //Variables.FechaStr,Variables.Per_Dni,Variables.Suc_Id,Variables.Pro_Id,Variables.Sub_Id,
        //Variables.Lin_Id,Variables.Lin_Lado,Variables.PreEnv_Id ),null);

        //Cursor CurIngresos = LocBD.rawQuery(T_RendimientoArmado.RendimientoArmado_SeleccionarTodos(),null);

        //Toast.makeText(RendArmado_Kardex.this,String.valueOf(CurIngresos.getCount()), Toast.LENGTH_SHORT).show();
       // adspnEntrega = new SimpleCursorAdapter(this,
        //        android.R.layout.simple_list_item_2 ,CurIngresos,
        //        new String[]{T_RendimientoArmado.RenArmHoraIni,T_RendimientoArmado.RenArmCantidad}, new int[]{android.R.id.text1,android.R.id.text2},
        //        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
       // lstIngresos.setAdapter(adspnEntrega);

        edtPeso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                if (edtPeso.hasFocus()==true)
                {
                    if(s.length() != 0)
                        edtCantidad.setText(String.valueOf(CalcularCantidad(Integer.parseInt(edtPeso.getText().toString()))));
                    else
                        edtCantidad.setText("0");
                }
            }
        });
        edtCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtCantidad.hasFocus()==true)
                {
                    if(s.length() != 0)
                        edtPeso.setText(String.valueOf(CalcularPeso(Integer.parseInt(edtCantidad.getText().toString()))));
                    else
                        edtPeso.setText("0");
                }
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Cant;
                final String Pes;

                Cant =edtCantidad.getText().toString();
                Pes=edtPeso.getText().toString();

                if (Cant.length()!=0 && Pes.length()!=0)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RendArmado_Kardex.this);
                    String alert_title = TipoOperacion;
                    String alert_description = "¿DESEA "+TipoOperacion+" ?";
                    alertDialogBuilder.setTitle(alert_title);
                    // set dialog message
                    alertDialogBuilder
                            .setMessage(alert_description)
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                // Lo que sucede si se pulsa yes
                                public void onClick(DialogInterface dialog, int id) {
                                    // Código propio del método borrado para ejemplo
                                    int Cantidad = Integer.parseInt(Cant);
                                    int Peso = Integer.parseInt(Pes);
                                    int Factor;
                                    int Total;
                                    double Equivalente;

                                    if (rbnEntrega.isChecked())
                                    {
                                        Factor = 1;
                                        Total=Cantidad*Factor;
                                        Equivalente = fnc.RedondeoDecimal(Total*Variables.PreEnv_Factor,2, BigDecimal.ROUND_HALF_UP);
                                        try{
                                            LocBD.execSQL(T_RendimientoArmado.RendimientoArmado_Insertar(
                                                    Variables.FechaStr,Variables.Per_Dni,Variables.Suc_Id,Variables.Pro_Id,
                                                    Variables.Sub_Id,Variables.Lin_Id,Variables.Lin_Lado,fnc.HoraSistema(),
                                                    Variables.Usu_Id,Variables.MAC,Variables.Pre_Id,Variables.Pre_Descripcion,
                                                    Variables.PreEnv_Id,Variables.PreEnv_DescripcionCor,Cantidad,
                                                    0,Peso,1,Total,Equivalente,fnc.HoraCorta(),2,0));
                                            Toast.makeText(RendArmado_Kardex.this,"ENTREGA REGISTRADA", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e)
                                        {
                                            Toast.makeText(RendArmado_Kardex.this, e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }else if(rbnDevolucion.isChecked())
                                    {
                                        Factor = -1;
                                        Total=Cantidad*Factor;
                                        Equivalente = fnc.RedondeoDecimal(Total*Variables.PreEnv_Factor,2, BigDecimal.ROUND_HALF_UP);
                                        try{
                                            LocBD.execSQL(T_RendimientoArmado.RendimientoArmado_Insertar(
                                                    Variables.FechaStr,Variables.Per_Dni,Variables.Suc_Id,Variables.Pro_Id,
                                                    Variables.Sub_Id,Variables.Lin_Id,Variables.Lin_Lado,fnc.HoraSistema(),
                                                    Variables.Usu_Id,Variables.MAC,Variables.Pre_Id,Variables.Pre_Descripcion,
                                                    Variables.PreEnv_Id,Variables.PreEnv_DescripcionCor,0,
                                                    Cantidad,Peso,1,Total,Equivalente,fnc.HoraCorta(),2,0));
                                            Toast.makeText(RendArmado_Kardex.this,"DEVOLUCION REGISTRADA", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e)
                                        {
                                            Toast.makeText(RendArmado_Kardex.this, e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    edtCantidad.setText("");
                                    edtPeso.setText("");
                                    ActualizarLista();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Si se pulsa no no hace nada
                                    Toast.makeText(RendArmado_Kardex.this, "Operación cancelada", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();

                }else
                {
                    Toast.makeText(RendArmado_Kardex.this,"INGRESE DATOS", Toast.LENGTH_SHORT).show();
                }
            }
            }
        );
        rbgGrupoTipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rbnEntrega){
                    TipoOperacion = "AGREGAR ENTREGA";
                    btnRegistrar.setText(TipoOperacion);
                    edtCantidad.requestFocus();
                    edtCantidad.setText("");

                }else if (checkedId == R.id.rbnDevolucion){
                    TipoOperacion = "AGREGAR DEVOLUCION";
                    btnRegistrar.setText(TipoOperacion);
                    edtPeso.requestFocus();
                    edtPeso.setText("");
                }
            }
        });
        btnCambiarPresentacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent NuevaActividad = new Intent(RendArmado_Kardex.this,RendArmado_Registro.class);
                    startActivity(NuevaActividad);
                }
            }
        );
        imbRegresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent NuevaActividad = new Intent(RendArmado_Kardex.this,RendArmado_Lista.class);
                    startActivity(NuevaActividad);
                }
            }
        );
    }

    private int CalcularCantidad(int Peso) {
        return (int) ((Peso * Variables.PreEnv_CantidadTorre) / (Variables.PreEnv_PesoTorre * 1000));
    }

    private int CalcularPeso(int Cantidad) {
        return (int) ((Cantidad * (Variables.PreEnv_PesoTorre * 1000)) / Variables.PreEnv_CantidadTorre);
    }
    private void ActualizarLista()
    {
        Cursor CurIngresos = LocBD.rawQuery(T_RendimientoArmado.RendimientoArmado_SeleccionarPorPersona(
                Variables.FechaStr,Variables.Per_Dni,Variables.Suc_Id,Variables.Pro_Id,Variables.Sub_Id,
                Variables.Lin_Id,Variables.Lin_Lado,Variables.PreEnv_Id ),null);

        adspnEntrega = new SimpleCursorAdapter(this,
                R.layout.listview_kardexarmado4item,CurIngresos,
                new String[]{T_RendimientoArmado.RenArmHoraIni,T_RendimientoArmado.PreEnvDescripcionCor,
                        T_RendimientoArmado.RenArmEntrega,T_RendimientoArmado.RenArmDevolucion
                }, new int[]{R.id.text1,R.id.text2,R.id.text3,R.id.text4},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lstIngresos.setAdapter(adspnEntrega);
    }

}
