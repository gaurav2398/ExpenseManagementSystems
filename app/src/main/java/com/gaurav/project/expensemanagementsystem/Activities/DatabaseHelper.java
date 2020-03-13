package com.gaurav.project.expensemanagementsystem.Activities;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "expense.db";
    public static final String TABLE_NAME = "expense_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "name";
    public static final String COL_3 = "money";
    public static final String COL_4 = "date";

    private Activity mContext;

    public DatabaseHelper(@Nullable Activity context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT , NAME TEXT , MONEY LONG , DATE DATE)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long getTotalOfAmountHome() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='HOME' " , null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountHomeDay(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='HOME' AND date='"+currentDate+"' ORDER BY  date",null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountWeekWise(String str,String week1,String week2) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='" + str + "' AND date BETWEEN '" + week1 + "' AND '" + week2+ "'",null) ;
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountHomeMonth(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='HOME' AND date LIKE '%"+currentDate+"' ORDER BY  date",null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountEntertainment() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='ENTERTAINMENT'" , null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountEntertainmentDay(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='ENTERTAINMENT' AND date='"+currentDate+"' ORDER BY  date" , null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountEntertainmentMonth(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='ENTERTAINMENT' AND date LIKE '%"+currentDate+"' ORDER BY  date" , null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountYearWise(String str,String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='"+str+"' AND date LIKE '%"+currentDate+"' ORDER BY  date" , null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountTravelling() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='TRAVELLING'" , null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountTravellingDay(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='TRAVELLING' AND date='"+currentDate+"' ORDER BY  date"  , null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountTravellingMonth(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='TRAVELLING' AND date LIKE '%"+currentDate+"'"  , null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountCloth() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='CLOTH'" , null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountClothDay(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='CLOTH' AND date='"+currentDate+"' ORDER BY  date"   , null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountClothMonth(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='CLOTH' AND date LIKE '%"+currentDate+"' ORDER BY  date"   , null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountSport() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='SPORT'" , null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountSportDay(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='SPORT' AND date='"+currentDate+"' ORDER BY  date", null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountSportMonth(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='SPORT' AND date LIKE '%"+currentDate+"' ORDER BY  date", null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='INCOME'" , null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }
    public long getTotalOfAmountIncomeDay(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='INCOME' AND date='"+currentDate+"' ORDER BY  date", null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }

    public long getTotalOfAmountIncomeMonth(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(money) FROM " + TABLE_NAME+ " WHERE name='INCOME' AND date LIKE '%"+currentDate+"' ORDER BY  date", null);
        c.moveToFirst();
        double i = c.getDouble(0);
        c.close();
        return (long) i;
    }

    public boolean inertData(String name,String money,String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,money);
        contentValues.put(COL_4,date);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
        {
            return  false;
        }
        else {
            return true;
        }
    }
    public long inertData1()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,"HOME");
        contentValues.put(COL_3,"500");
        contentValues.put(COL_4,"02-March-2020");
        long result = db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"ENTERTAINMENT");
        contentValues.put(COL_3,"200");
        contentValues.put(COL_4,"01-March-2050");
        db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"HOME");
        contentValues.put(COL_3,"10");
        contentValues.put(COL_4,"09-March-2020");
        db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"HOME");
        contentValues.put(COL_3,"11");
        contentValues.put(COL_4,"10-March-2020");
        db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"HOME");
        contentValues.put(COL_3,"12");
        contentValues.put(COL_4,"05-March-2020");
        db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"SPORT");
        contentValues.put(COL_3,"12");
        contentValues.put(COL_4,"05-March-2020");
        db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"CLOTH");
        contentValues.put(COL_3,"14");
        contentValues.put(COL_4,"03-March-2020");
        db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"SPORT");
        contentValues.put(COL_3,"12");
        contentValues.put(COL_4,"05-March-2020");
        db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"CLOTH");
        contentValues.put(COL_3,"14");
        contentValues.put(COL_4,"03-March-2020");
        db.insert(TABLE_NAME,null,contentValues);


        contentValues.put(COL_2,"ENTERTAINMENT");
        contentValues.put(COL_3,"200");
        contentValues.put(COL_4,"01-March-2050");
        db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"INCOME");
        contentValues.put(COL_3,"200");
        contentValues.put(COL_4,"09-March-2000");
        db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"CLOTH");
        contentValues.put(COL_3,"200");
        contentValues.put(COL_4,"09-March-2000");
        db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"TRAVELLING");
        contentValues.put(COL_3,"200");
        contentValues.put(COL_4,"09-March-2000");
        db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"SPORT");
        contentValues.put(COL_3,"200");
        contentValues.put(COL_4,"09-March-2000");
        db.insert(TABLE_NAME,null,contentValues);
        contentValues.put(COL_2,"ENTERTAINMENT");
        contentValues.put(COL_3,"200");
        contentValues.put(COL_4,"05-March-2020");
        db.insert(TABLE_NAME,null,contentValues);


        contentValues.put(COL_2,"ENTERTAINMENT");
        contentValues.put(COL_3,"200");
        contentValues.put(COL_4,"05-January-2020");
        db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"ENTERTAINMENT");
        contentValues.put(COL_3,"200");
        contentValues.put(COL_4,"05-February-2020");
        db.insert(TABLE_NAME,null,contentValues);

        contentValues.put(COL_2,"INCOME");
        contentValues.put(COL_3,"200");
        contentValues.put(COL_4,"01-March-2020");
        db.insert(TABLE_NAME,null,contentValues);

        return result;
    }
    public Cursor getHomeData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='HOME' ORDER BY  id" ;
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getHomeDataDateWise(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='HOME' AND date='"+currentDate +"' ORDER BY  date";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getHomeDataWeekWise(String str,String week1,String week2) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='"+str+"' AND date BETWEEN '" + week1 + "' AND '" + week2+ "' ORDER BY  date" ;
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getHomeDataMonthWise(String monthdata) {
        SQLiteDatabase db = this.getReadableDatabase();
        String s = "%"+monthdata+"%";
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='HOME' AND date LIKE '"+s+"'";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getHomeDataYearWise(String str,String yeardata) {
        SQLiteDatabase db = this.getReadableDatabase();
        String s = "%"+yeardata;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='"+str+"' AND date LIKE '"+s+"' ORDER BY  date";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getEntertainmentData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='ENTERTAINMENT' ORDER BY  id" ;
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getEntertainmentDateWise(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='ENTERTAINMENT' AND date='"+currentDate +"' ORDER BY  date";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getEntertainmentMonthWise(String monthdata) {
        SQLiteDatabase db = this.getReadableDatabase();
        String s = "%"+monthdata+"%";
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='ENTERTAINMENT' AND date LIKE '"+s+"' ORDER BY  date";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor gettTravellingData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='TRAVELLING' ORDER BY  id" ;
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor gettTravellingDateWise(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='TRAVELLING' AND date='"+currentDate +"' ORDER BY  date";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor gettTravellingMonthWise(String monthdata) {
        SQLiteDatabase db = this.getReadableDatabase();
        String s = "%"+monthdata+"%";
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='TRAVELLING'  AND date LIKE '"+s+"' ORDER BY  date";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getcClothData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='CLOTH' ORDER BY  id" ;
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getcClothDateWise(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='CLOTH' AND date='"+currentDate +"' ORDER BY  date";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getcClothMonthWise(String monthdata) {
        SQLiteDatabase db = this.getReadableDatabase();
        String s = "%"+monthdata+"%";
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='CLOTH'  AND date LIKE '"+s+"'";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getSportData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='SPORT' ORDER BY  id" ;
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getSportDateWise(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='SPORT' AND date='"+currentDate +"' ORDER BY  date";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getSportMonthWise(String monthdata) {
        SQLiteDatabase db = this.getReadableDatabase();
        String s = "%"+monthdata+"%";
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='SPORT' AND date LIKE '"+s+"' ORDER BY  date";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }


    public Cursor getIncomeData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='INCOME' ORDER BY  id" ;
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getIncomeDateWise(String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='INCOME' AND date='"+currentDate +"' ORDER BY  date";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getIncomeMonthWise(String monthdata) {
        SQLiteDatabase db = this.getReadableDatabase();
        String s = "%"+monthdata+"%";
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='INCOME'  AND date LIKE '"+s+"' ORDER BY  date";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select name from "+TABLE_NAME+" where id="+id+"", null );

        if(res != null && res.moveToNext()) {
            return res;
        }

        return res;

    }

    public boolean updateData(String id, String name, String money){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,money);
        db.update(TABLE_NAME,contentValues,"ID = ?", new String[] { id });

        return true;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_NAME,"ID = ?",new String[] { id });
    }
}
