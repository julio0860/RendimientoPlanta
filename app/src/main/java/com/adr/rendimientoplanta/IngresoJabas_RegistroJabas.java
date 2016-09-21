package com.adr.rendimientoplanta;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.DATA.T_Consumidor;
import com.adr.rendimientoplanta.DATA.T_LineaIngreso;
import com.adr.rendimientoplanta.DATA.T_MotivoParada;
import com.adr.rendimientoplanta.LIBRERIA.Funciones;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

public class IngresoJabas_RegistroJabas extends AppCompatActivity {

    private Funciones fnc;

    //SMP: Variables para recuperar datos a insertar:
    private int EsMix;
    private String HoraIni;
    private int Con_Id;
    private String Con_DescripcionCor;
    private int Con_IdMix;
    private String Con_DescripcionCorMix;
    private int IngJab_Cantidad;
    private int IngJab_CantidadMix;

    //SMP: Variables tipo TextView
    private TextView lblSucursal;
    private TextView lblCultivo;
    private TextView lblFecha;
    private TextView lblEmpresa;
    private TextView lblLinea;

    //SMP: Variables tipo ImageButton
    private ImageButton imbRegresar;

    //SMP: Variables tipo Button
    private Button btnAgregar;

    //SMP: Variables tipo CheckBox
    private CheckBox cbxMix;

    //SMP: Variables tipo EditText
    private EditText edtCantidadMix;
    private EditText edtHoraIni;

    //SMP: Variables tipo Spinner
    private Spinner spnConsumidorMix;
    private Spinner spnConsumidor;

    //SMP: Variables tipo RadioGroup
    private RadioGroup rbnGrupoMix;

    //SMP: Variable tipo Base d datos
    LocalBD LBD;
    SQLiteDatabase LocBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_jabas_registro_jabas);

        //SMP: Iniciación base de datos local
        LBD = new LocalBD(IngresoJabas_RegistroJabas.this) ;
        LocBD = LBD.getWritableDatabase();

        //SMP: Asignación clase funciones
        fnc = new Funciones();

        //SMP: Asignación de variables TextView a Layout
        lblCultivo = (TextView) findViewById(R.id.lblCultivo);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblLinea = (TextView) findViewById(R.id.lblLinea);
        lblFecha.setText(Variables.FechaStr);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblCultivo.setText(Variables.Cul_Descripcion);
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblLinea.setText(Variables.Lin_Descripcion);

        //SMP: Asignación de variables Button a Layout
        btnAgregar = (Button) findViewById(R.id.btnAgregar);

        //SMP: Asignación de variables ImageButton a Layout
        imbRegresar = (ImageButton) findViewById(R.id.imbRegresar);

        //SMP: Asignación de variables CheckBox a Layout
        cbxMix = (CheckBox) findViewById(R.id.cbxMix);

        //SMP: Asignación de variables EditText a Layout
        edtCantidadMix = (EditText) findViewById(R.id.edtCantidadMix);
        edtHoraIni=(EditText)findViewById(R.id.edtHoraIni);

        //SMP: Asignación de variables Spinner a Layout
        spnConsumidorMix = (Spinner) findViewById(R.id.spnConsumidorMix);
        spnConsumidor = (Spinner) findViewById(R.id.spnConsumidor);

        //SMP: Asignación de variables RadioGroup a Layout
        rbnGrupoMix = (RadioGroup) findViewById(R.id.rbnGrupoMix);

        //SMP: Asignación de varialbes RadioButton a Layout



        //Asignación de carga inicial de Spinners
        Cursor Consumidor = LocBD.rawQuery(T_Consumidor._SELECT_CON(1,2, Variables.Cul_Id, Variables.Emp_Id),null);
        spnConsumidor.setAdapter(fnc.AdaptadorSpinnerSimpleLarge(this,Consumidor, T_Consumidor.CONDESCRIPCIONCOR));

        Cursor ConsumidorMix = LocBD.rawQuery(T_Consumidor._SELECT_CON(1,2, Variables.Cul_Id, Variables.Emp_Id),null);
        spnConsumidorMix.setAdapter(fnc.AdaptadorSpinnerSimpleLarge(this,ConsumidorMix, T_Consumidor.CONDESCRIPCIONCOR));

        //Asignación de Eventos y Acciones a los componentes
        btnAgregar.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v){
                    HoraIni=edtHoraIni.getText().toString();

                    if (HoraIni.equals("--"))
                    {
                        Toast.makeText(IngresoJabas_RegistroJabas.this,"Ingrese la Hora",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Cursor CurMotPar = (Cursor) spnConsumidor.getAdapter().getItem(spnConsumidor.getSelectedItemPosition());
                        Con_Id= CurMotPar.getInt(CurMotPar.getColumnIndex(BaseColumns._ID));
                        Con_DescripcionCor= CurMotPar.getString(CurMotPar.getColumnIndex(T_LineaIngreso.ConDescripcionCor));

                        if (EsMix==1)
                        {

                        }
                    }

                }
            }
        );
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
                if (isChecked==true)
                {
                    EsMix=1;
                }else{
                    EsMix=0;
                }
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
