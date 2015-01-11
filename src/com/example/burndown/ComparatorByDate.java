package com.example.burndown;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import android.annotation.SuppressLint;
import com.example.burndown.ArrayListDate;

public class ComparatorByDate implements Comparator<ArrayListDate> {

	@SuppressLint("SimpleDateFormat")
	@Override
	public int compare(ArrayListDate arg0, ArrayListDate arg1) {
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Date d1 = null;
	        Date d2 = null;
	            try {
					d1 = formatter.parse(arg1.getDate());
					d2 = formatter.parse(arg0.getDate());
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        int d = d1.compareTo(d2);
	        return d;
	}

}





