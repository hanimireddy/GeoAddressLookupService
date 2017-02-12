package com.ncr.geo.api.util;

import java.sql.Timestamp;
import java.util.Date;

public class AppUtils {
	
	public static Timestamp getCurrentDateTime()
	{
		return new Timestamp(new Date().getTime());
	}

}
