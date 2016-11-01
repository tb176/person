package com.mo9.thirdapi.domain.sharding;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public final class ShardingTableUtil {
  public static  String createUUIDByShardingTable()
  {
	  SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yy");
	  SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MM");
		Date now = new Date();
		StringBuilder sb = new StringBuilder();
		
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String year = YEAR_FORMAT.format(now);
		String month = MONTH_FORMAT.format(now);

		sb.append(uuid.substring(0, 8));
		sb.append(year);
		sb.append(uuid.substring(8,16));
		sb.append(month);
		sb.append(uuid.substring(16));
		return sb.toString();
  }
  
 
  public static String getShardingTableName(Date dete,String table_prefix)
  {
	  SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yy");
	  SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MM");
	  	String year = YEAR_FORMAT.format(dete);
		String month = MONTH_FORMAT.format(dete);
		StringBuilder sb = new StringBuilder();
		sb.append(table_prefix);
		sb.append("_20");
		sb.append(year);
		sb.append(month);
		return sb.toString();
  }
  
  
  public static String getShardingTableName(String id,String table_prefix)
  {
	  if(id.length()<36)
	  {
		  return null;
	  }
	  
	  String year = id.substring(8,10);
	  String month = id.substring(18,20);
	  StringBuilder sb = new StringBuilder();
	  sb.append(table_prefix);
	  sb.append("_20");
	  sb.append(year);
	  sb.append(month);
	  return sb.toString();
  }
  
  

	public static  String createShardingTableId()
	{
		 SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
				"yyyyMMddHHmmssSSS");
		Date now = new Date();
		StringBuilder sb = new StringBuilder();
		sb.append(DATE_FORMAT.format(now));
		sb.append(UUID.randomUUID().toString().replace("-", ""));
		return sb.toString();
	}
	
}
