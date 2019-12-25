package com.example.control;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.awt.font.TextAttribute;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utility {
	public static void callDatePicker(Context context, final EditText dateEt) {
		// получаем текущую дату
		int mYear, mMonth, mDay;
		final Calendar cal = Calendar.getInstance();
		mYear = cal.get(Calendar.YEAR);
		mMonth = cal.get(Calendar.MONTH);
		mDay = cal.get(Calendar.DAY_OF_MONTH);

		// инициализируем диалог выбора даты текущими значениями
		DatePickerDialog datePickerDialog = new DatePickerDialog(context,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
						dateEt.setText(editTextDateParam);
					}
				}, mYear, mMonth, mDay);
		datePickerDialog.show();
	}
	public static long convertDateTextToLong(String dateString){
		try {
			return new SimpleDateFormat("dd.MM.yyyy").parse(dateString).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
