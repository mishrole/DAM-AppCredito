package com.example.appcredito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Declarar objetos
    EditText edtPrecio, edtTasaAnual, edtTiempo;
    CheckBox chkDecimal;
    TextView tvTasaMensual, tvCuotaMensual;
    Button btnCalcular;

    // Declarar variables
    int decimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPrecio = (EditText) findViewById(R.id.edtPrecio);
        edtTasaAnual = (EditText) findViewById(R.id.edtTasaAnual);
        edtTiempo = (EditText) findViewById(R.id.edtTiempo);
        chkDecimal = (CheckBox) findViewById(R.id.chkDecimal);
        tvTasaMensual = (TextView) findViewById(R.id.tvTasaMensual);
        tvCuotaMensual = (TextView) findViewById(R.id.tvCuotaMensual);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);

        // Asignar evento click al botón
        btnCalcular.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == btnCalcular) {

            // Tasa Efectiva Anual
            double tea = Double.parseDouble(edtTasaAnual.getText().toString());
            // Monto de préstamo
            double precio = Double.parseDouble(edtPrecio.getText().toString());
            // Tiempo
            int tiempo = Integer.parseInt(edtTiempo.getText().toString());

            // Decimal format (a dos decimales)
            DecimalFormat df = new DecimalFormat("#.00");

            // Tasa Efectiva Mensual: (((1 + (tea / 100))^(1/12))-1)*100
            double tem = Double.parseDouble(df.format((Math.pow(1 + (tea / 100), ((double) 1 / (double) 12)) - 1) * 100));

                                                                                                    /*
                Cuota Fija Mensual: P * ((i/100) * ((1+(i/100))^n) / ((1+(i/100))^n) -1)

                Siendo:

                    # P     Monto de préstamo (precio)
                    # i     Tasa Efectiva mensual (tem) %
                    # n     Tiempo (tiempo)
                                                                                                    */

            double cfm = precio * ( ((tem / 100) * (Math.pow(1+(tem / 100), tiempo))) / ((Math.pow(1+(tem / 100), tiempo)) -1) );

            // Tasa Efectiva Mensual
            tvTasaMensual.setText("Tasa Mensual: " + tem);

            // Checkbox Decimal - Cuota Fija Mensual
            if(chkDecimal.isChecked()) {
                tvCuotaMensual.setText("Cuota Mensual: S/. " + df.format(cfm));
            } else {
                tvCuotaMensual.setText("Cuota Mensual: S/. " + Math.round(cfm));
            }

        }
    }
}