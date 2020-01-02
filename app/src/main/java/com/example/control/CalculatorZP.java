package com.example.control;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;

public class CalculatorZP extends AppCompatActivity implements View.OnClickListener  {

    private Button btn_count, btnCancel;
    private EditText ed_oklad;
    private EditText ed_kol_clock;
    private EditText ed_year_norm;
    private EditText ed_prem;
    private TextView text_rez,text_rez2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_zp);

        Toast.makeText(this, "ВНИМАНИЕ ВСЕ ПОЛЯ ОБЯЗАТЕЛЬНЫЕ ДЛЯ ЗАПОЛНЕНИЯ", Toast.LENGTH_SHORT).show();

        btn_count= (Button) findViewById(R.id.btn_count);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        ed_oklad= (EditText) findViewById(R.id.ed_date);
        ed_kol_clock= (EditText) findViewById(R.id.ed_kol_clock);
        ed_year_norm= (EditText) findViewById(R.id.ed_year_norm);
        ed_prem= (EditText) findViewById(R.id.ed_prem);
        text_rez= (TextView) findViewById(R.id.text_rez);
        text_rez2= (TextView) findViewById(R.id.text_rez2);
        btn_count.setOnClickListener(this);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_cancel:
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

      double num_ed_oklad = 0;
      double num_ed_kol_clock = 0;
      double num_ed_year_norm = 0;
      double num_ed_prem = 0;
      double nume;
      double result = 0;

        // Проверяем поля на пустоту
        if (TextUtils.isEmpty(ed_oklad.getText().toString())
                || TextUtils.isEmpty(ed_kol_clock.getText().toString())
                || TextUtils.isEmpty(ed_year_norm.getText().toString())
                || TextUtils.isEmpty(ed_prem.getText().toString())) {
            return;
        }
        // читаем EditText и заполняем переменные числами
        num_ed_oklad = Double.parseDouble(ed_oklad.getText().toString());
        num_ed_kol_clock = Double.parseDouble(ed_kol_clock.getText().toString());
        num_ed_year_norm = Double.parseDouble(ed_year_norm.getText().toString());
        num_ed_prem = Double.parseDouble(ed_prem.getText().toString());

        switch (v.getId()) {
            case R.id.btn_count:
                // формируем строку вывода
                text_rez2.setText("(" + num_ed_oklad + " / "
                        + num_ed_year_norm + ")" + " * "
                        + num_ed_kol_clock + " + "
                        + num_ed_prem);
                try {
                    if (!text_rez2.getText().toString().isEmpty()) {
                        Expression expression = new Expression(text_rez2.getText().toString());

                        BigDecimal result1 = expression.eval(false);
                        text_rez.setText("ИТОГО: " + result1.toString() + " РУБ.");
                    }


                }
                catch (ArithmeticException e ){
                    text_rez.setText("На ноль делить нельзя");
                }
                catch (Expression.ExpressionException e ){
                    text_rez.setText("Неверное выражение");
                }
               catch (Exception ex){
                   text_rez.setText(ex.getMessage());
               }


                break;
            default:
        }

    }

}