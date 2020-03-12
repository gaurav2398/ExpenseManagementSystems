package com.gaurav.project.expensemanagementsystem.Activities;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
    public Cursor getHomeChooseDateData(String choosedate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='HOME' AND date='"+choosedate+"' ORDER BY  date";
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
    public Cursor getEntertainmentChooseDateData(String choosedate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='ENTERTAINMENT' AND date='"+choosedate+"' ORDER BY  date";
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

    public Cursor gettTravellingChooseDateData(String choosedate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='TRAVELLING' AND date='"+choosedate+"' ORDER BY  date";
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
    public Cursor getcClothDateChooseDateDate(String choosedate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='CLOTH' AND date='"+choosedate+"' ORDER BY  date";
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

    public Cursor getSportChooseDate(String choosedate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name='SPORT' AND date='"+choosedate+"' ORDER BY  date";
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
    public void backup(String outFileName) {

        //database path
        final String inFileName = mContext.getDatabasePath(DATABASE_NAME).toString();

        try {

            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);


            OutputStream output = new FileOutputStream(outFileName);


            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }


            output.flush();
            output.close();
            fis.close();

            Toast.makeText(mContext, "Backup Completed", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(mContext, "Unable to backup database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void importDB(String inFileName) {

        final String outFileName = mContext.getDatabasePath(DATABASE_NAME).toString();

        try {

            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);


            OutputStream output = new FileOutputStream(outFileName);


            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }


            output.flush();
            output.close();
            fis.close();

            Toast.makeText(mContext, "Import Completed", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(mContext, "Unable to import database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void createPdf() throws FileNotFoundException, DocumentException {
        Permissions.verifyStoragePermissions(mContext);

        String dir = Environment.getExternalStorageDirectory()+File.separator+"Expenses Pdf";
        File folder = new File(dir);
        folder.mkdirs();

        File file = new File(dir, "Expense.pdf");

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c1 =db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 35.0f, Font.BOLD, BaseColor.BLACK);

        Paragraph p3 = new Paragraph();
        p3.setAlignment(Paragraph.ALIGN_CENTER);
        p3.setFont(f);
        p3.add("Your total expenses \n\n\n");

        document.add(p3);

        PdfPTable table = new PdfPTable(4);

        table.addCell(" ID");
        table.addCell(" NAME");
        table.addCell(" MONEY");
        table.addCell(" DATE");
        table.addCell(" ");
        table.addCell(" ");
        table.addCell(" ");
        table.addCell("");

        while (c1.moveToNext()) {
            String date = c1.getString(0);
            String start = c1.getString(1);
            String end = c1.getString(2);
            String total = c1.getString(3);

            table.addCell(date);
            table.addCell(start);
            table.addCell(end);
            table.addCell(total);
        }

        document.add(table);
        document.addCreationDate();
        document.close();
    }
}
