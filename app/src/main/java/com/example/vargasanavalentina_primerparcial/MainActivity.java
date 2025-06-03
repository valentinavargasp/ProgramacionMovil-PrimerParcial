package com.example.vargasanavalentina_primerparcial;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioButton rb1, rb2, rb3;
    private CheckBox checkCasco;
    private EditText et1;
    private TextView tv1;
    private Spinner spinnerPago;
    private Button btn1, btnLimpiar;
    private Switch switchMoneda;
    private RadioGroup radioGroupBicis;

    String[] mediosPago = {"Efectivo", "Transferencia", "Tarjeta en cuotas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        checkCasco = findViewById(R.id.checkCasco);
        et1 = findViewById(R.id.et1);
        tv1 = findViewById(R.id.tv1);
        spinnerPago = findViewById(R.id.spinnerPago);
        btn1 = findViewById(R.id.btn1);
        switchMoneda = findViewById(R.id.switchMoneda);
        radioGroupBicis = findViewById(R.id.radioGroupBicis);
        btnLimpiar = findViewById(R.id.btnLimpiar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mediosPago);
        spinnerPago.setAdapter(adapter);

        radioGroupBicis.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton seleccionado = findViewById(checkedId);
            if (seleccionado != null && seleccionado.isPressed()) {
                Toast.makeText(MainActivity.this, "Seleccionaste: " + seleccionado.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        switchMoneda.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(MainActivity.this, "Precios en USD", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Precios en ARS", Toast.LENGTH_SHORT).show();
            }
        });


        btn1.setOnClickListener(view -> {
            int precioPorHora = 0;
            String tipo = "";

            if (rb1.isChecked()) {
                precioPorHora = 1000;
                tipo = rb1.getText().toString();
            } else if (rb2.isChecked()) {
                precioPorHora = 2000;
                tipo = rb2.getText().toString();
            } else if (rb3.isChecked()) {
                precioPorHora = 4000;
                tipo = rb3.getText().toString();
            } else {
                Toast.makeText(MainActivity.this, "Seleccione un tipo de bicicleta", Toast.LENGTH_SHORT).show();
                return;
            }


            int horas = 0;
            try {
                horas = Integer.parseInt(et1.getText().toString());
            } catch (NumberFormatException e) {
                et1.setError("Ingrese un número válido");
                return;
            }

            double total = precioPorHora * horas;

            if (checkCasco.isChecked()) {
                total = (int) (total * 1.2); // sumamos el 20% al total
            }

            String medioPago = spinnerPago.getSelectedItem().toString();
            switch (medioPago) {
                case "Efectivo":
                    total *= 0.8; // 20% des
                    break;
                case "Transferencia":
                    total *= 0.9; // 10% desc
                    break;
                case "Tarjeta en cuotas":
                    total *= 1.1; // 10% recargo
                    break;
            }

            if (switchMoneda.isChecked()) {
                // Mostrar en USD
                double totalUSD = total / 1000.0;
                tv1.setText(String.format("$%.2f USD", totalUSD));
            } else {
                // Mostrar en ARS
                tv1.setText(String.format("$%.2f ARS", total));
            }
        });

        // Listener botón limpiar para resetear el formulario
        btnLimpiar.setOnClickListener(v -> {
            radioGroupBicis.clearCheck();
            checkCasco.setChecked(false);
            et1.setText("");
            spinnerPago.setSelection(0);
            switchMoneda.setChecked(false);
            tv1.setText("$0,00");
        });
    }
}
