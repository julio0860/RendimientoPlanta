package com.adr.rendimientoplanta;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.adr.rendimientoplanta.DATA.LocalBD;
import com.adr.rendimientoplanta.LIBRERIA.Variables;

public class RegistroOperario extends AppCompatActivity {
    private TextView lblEmpresa;
    private TextView lblSucursal;
    private TextView lblProceso;
    private TextView lblSubproceso;
    private TextView lblLinea;
    private TextView lblLado;
    private EditText edtFecha;
    private EditText edtDni;
    private EditText edtPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_operario);
        LocalBD LBD = new LocalBD(RegistroOperario.this);

        final SQLiteDatabase LocBD = LBD.getWritableDatabase();

        lblEmpresa = (TextView) findViewById(R.id.lblEmpresa);
        lblSucursal = (TextView) findViewById(R.id.lblSucursal);
        lblProceso = (TextView) findViewById(R.id.lblProceso);
        lblSubproceso = (TextView) findViewById(R.id.lblSubproceso);
        lblLinea = (TextView) findViewById(R.id.lblLinea);
        lblLado = (TextView) findViewById(R.id.lblLado);
        edtFecha=(EditText) findViewById(R.id.edtFecha);
        edtDni=(EditText)findViewById(R.id.edtDni);
        edtPersonal=(EditText)findViewById(R.id.edtPersonal);

        //ASIGNACIÓN DE PARAMETROS A LA ACTIVIDAD
        lblEmpresa.setText(Variables.Emp_Abrev);
        lblSucursal.setText(Variables.Suc_Descripcion);
        lblProceso.setText(Variables.Pro_Descripcion);
        lblSubproceso.setText(Variables.Sub_Descripcion);
        lblLinea.setText(Variables.Lin_Descripcion);
        lblLado.setText("LADO: "+Variables.Lin_Lado);
        edtFecha.setText(Variables.FechaStr);

        edtDni.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //if (event.getAction() == KeyEvent.ACTION_UP ||event.getAction() == KeyEvent.KEYCODE_ENTER) {
                if (event.getAction() == KeyEvent.ACTION_DOWN ||keyCode==KeyEvent.KEYCODE_ENTER) {
                    //do something here
                    //if (keyCode==13)
                    if (keyCode==KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER|| event.getAction() == KeyEvent.KEYCODE_ENTER)
                    {
                        String Dni;
                        Dni=edtDni.getText().toString();
                        Cursor Rse=LocBD.rawQuery("SELECT Per_Codigo,Per_ApePaterno || ' '|| Per_ApeMaterno||' '||Per_Nombres AS 'PERSONAL' FROM PERSONAL WHERE Per_Codigo='"+Dni+"'",null);
                        if (Rse.moveToFirst()) {
                            //Recorremos el cursor hasta que no haya más registros
                            do {
                                String codigo= Rse.getString(0);
                                String Personal = Rse.getString(1);
                                edtPersonal.setText(Personal);
                            } while(Rse.moveToNext());
                        }
                    }
                }
                return false;
            }
        }
        );
}
}