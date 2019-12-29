package com.example.control;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalculatorZP extends AppCompatActivity implements View.OnClickListener  {

    private Button btn_count;
    private EditText ed_oklad;
    private EditText ed_kol_clock;
    private EditText ed_year_norm;
    private EditText ed_prem;
    private EditText ed_kty;
    private TextView text_rez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_zp);

        btn_count= (Button) findViewById(R.id.btn_count);
        ed_oklad= (EditText) findViewById(R.id.ed_date);
        ed_kol_clock= (EditText) findViewById(R.id.ed_kol_clock);
        ed_year_norm= (EditText) findViewById(R.id.ed_year_norm);
        ed_prem= (EditText) findViewById(R.id.ed_prem);
        ed_kty= (EditText) findViewById(R.id.ed_kty);
        text_rez= (TextView) findViewById(R.id.text_rez);
        btn_count.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        float num_ed_oklad = 0;
        float num_ed_kol_clock = 0;
        float num_ed_year_norm = 0;
        float num_ed_prem = 0;
        float num_ed_kty = 0;
        float nume;
        float result = 0;

        // Проверяем поля на пустоту
        if (TextUtils.isEmpty(ed_oklad.getText().toString())
                || TextUtils.isEmpty(ed_kol_clock.getText().toString())
                || TextUtils.isEmpty(ed_year_norm.getText().toString())
                || TextUtils.isEmpty(ed_prem.getText().toString())
                || TextUtils.isEmpty(ed_kty.getText().toString())) {
            return;
        }
        // читаем EditText и заполняем переменные числами
        num_ed_oklad = Float.parseFloat(ed_oklad.getText().toString());
        num_ed_kol_clock = Float.parseFloat(ed_kol_clock.getText().toString());
        num_ed_year_norm = Float.parseFloat(ed_year_norm.getText().toString());
        num_ed_prem = Float.parseFloat(ed_prem.getText().toString());
        num_ed_kty = Float.parseFloat(ed_kty.getText().toString());

        switch (v.getId()) {
            case R.id.btn_count:
                nume = num_ed_oklad / num_ed_year_norm;
                result = (num_ed_kol_clock * num_ed_kty * nume) + num_ed_prem;


                break;
            default:
                break;
        }

        // формируем строку вывода
        // формируем строку вывода
        text_rez.setText(num_ed_oklad + " / "
                + num_ed_year_norm + " * "
                + num_ed_kol_clock + " * "
                + num_ed_kty + " + "
                + num_ed_prem + " = "
                + result + "  руб. ");
    }

}