package com.delkappa.manos.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.*;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;


public class DatabaseHandler extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "eventManager";

    // Table name
    private static final String TABLE_EVENTS = "event";

    // Table columns names
    static final String dbName="demoDB";
    static final int dbVersion=1;
    static final String employeeTable="Employees";
    static final String colID="EmployeeID";
    static final String colName="EmployeeName";
    static final String colAge="Age";
    static final String colDept="Dept";
    static final String deptTable="Dept";
    static final String colDeptID="DeptID";
    static final String colDeptName="DeptName";


    public DatabaseHandler(Context context) {
        super(context, dbName, null, dbVersion);
        Log.d("MyApp","Constructor Log");
        // this.onUpgrade(this.getWritableDatabase(), this.dbVersion, this.dbVersion+1);
    }

    // Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQLiteDatabase dbt = this.getWritableDatabase();
        db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE " + deptTable + " (" + colDeptID + " INTEGER, " + colDeptName + " TEXT);");
        db.execSQL("CREATE TABLE " + employeeTable + " (" + colID + " INTEGER, " + colName + " TEXT, " + colAge + " INTEGER, " + colDept + " INTEGER);");
        Log.d("MyApp","I am here");

        ContentValues cv=new ContentValues();
        cv.put(colDeptID, 1);
        cv.put(colDeptName, "Sales");
        db.insert(deptTable, colDeptID, cv);
        //db.execSQL( "INSERT INTO " + deptTable + "(" + colDeptID + "," + colDeptName + ") values ('1', 'Sales' );" );
        Log.d("MyApp","I am here");

        cv=new ContentValues();
        cv.put(colDeptID, 2);
        cv.put(colDeptName, "Services");
        db.insert(deptTable, colDeptID, cv);
        //db.execSQL( "INSERT INTO " + deptTable + "(" + colDeptID + "," + colDeptName + ") values ('2', 'Services' );" );

        cv=new ContentValues();
        cv.put(colDeptID, 3);
        cv.put(colDeptName, "Communication");
        db.insert(deptTable, colDeptID, cv);
        //db.execSQL( "INSERT INTO " + deptTable + "(" + colDeptID + "," + colDeptName + ") values ('3', 'Communication' );" );

        cv=new ContentValues();
        cv.put(colDeptID, 4);
        cv.put(colDeptName, "Direction");
        db.insert(deptTable, colDeptID, cv);
        //db.execSQL( "INSERT INTO " + deptTable + "(" + colDeptID + "," + colDeptName + ") values ('4', 'Direction' );" );

        cv=new ContentValues();
        cv.put(colDeptID, 5);
        cv.put(colDeptName, "Informatique");
        db.insert(deptTable, colDeptID, cv);
        //db.execSQL( "INSERT INTO " + deptTable + "(" + colDeptID + "," + colDeptName + ") values ('5', 'Informatique' );" );
        // db.close();
        /*
        db.execSQL( "INSERT INTO " + deptTable + "(" + colDeptID + "," + colDeptName + ") values ('2', 'Services' );" );
        db.execSQL( "INSERT INTO " + deptTable + "(" + colDeptID + "," + colDeptName + ") values ('3', 'Communication' );" );
        db.execSQL( "INSERT INTO " + deptTable + "(" + colDeptID + "," + colDeptName + ") values ('4', 'Direction' );" );
        db.execSQL( "INSERT INTO " + deptTable + "(" + colDeptID + "," + colDeptName + ") values ('5', 'Informatique' );" );
        db.execSQL( "INSERT INTO " + deptTable + "(" + colDeptID + "," + colDeptName + ") values ('6', 'Marketing' );" );
    */
        // Log.d("testData",this.getReadableDatabase().rawQuery("SELECT * " + "FROM deptTable", null).toString());
        ////////////////////////////////////////////////////////////////////////////////////////////
        // TODO                                                                                   //
        ////////////////////////////////////////////////////////////////////////////////////////////

    }

    // Upgrade the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);

        // Create tables again
        onCreate(db);
    }

    // Add a new row
    void addRow(Event e) {

        ////////////////////////////////////////////////////////////////////////////////////////////
        // TODO                                                                                   //
        ////////////////////////////////////////////////////////////////////////////////////////////

    }

    // Get all rows
    public List<Event> getAllRows(SQLiteDatabase db) {
        List<Event> l = new ArrayList<>();
        // db = this.getReadableDatabase();
        // db = getReadableDatabase();
        StringBuilder txtMsg = new StringBuilder();

        String[] columns = {colDeptID, colDeptName};
        // Cursor myCur = db.query(deptTable, columns, null, null, null, null, colDeptID);
        Cursor myCur = db.query(deptTable, null, null, null, null, null, null);

        String[] columnNames = myCur.getColumnNames();

        Log.i("testCol", columnNames[0]);
        Log.i("testCol2", columnNames[1]);
        int idCol = myCur.getColumnIndex(colDeptID);
        int nameCol = myCur.getColumnIndex(colDeptName);
        // Log.i("testAppel",myCur.getString(myCur.getColumnIndex(colDeptID)));

        while ( myCur.moveToNext() ) {
            columns[0] = Integer.toString(myCur.getInt(idCol));
            columns[1] = myCur.getString(nameCol);

            Log.d("MyApp", "moveNext");
            txtMsg.append("\n" + columns[0]+ " " + columns[1] + " \n");
        }
        Log.e("error",txtMsg.toString());
        /*
        List<Event> l = new ArrayList<>();
        db = this.getReadableDatabase();
        // + deptTable + " (" + colDeptID + " INTEGER, " + colDeptName + " TEXT);");
        // + employeeTable + " (" + colID + " INTEGER, " + colName + " TEXT, " + colAge + " INTEGER, " + colDept + " INTEGER);");
        StringBuilder txtMsg = new StringBuilder();
        String [] columns = {colDeptID,colDeptName};
        Cursor myCur = db.query(deptTable, columns, null, null, null, null, colDeptID);

        Log.e("testMyCur",myCur.getString(1));
        Log.i("nombrColonn",String.valueOf(myCur.getColumnCount()));
        int idCol = myCur.getColumnIndex(colDeptID);
        int nameCol = myCur.getColumnIndex(colDeptName);

        Log.e("DisplayData", "lalalalala");

        while(myCur.moveToNext()) {
            columns[0] = Integer.toString(myCur.getInt(idCol));
            columns[1] = myCur.getString(nameCol);
            Log.d("MyApp", "moveNext");
            txtMsg.append("\n" + columns[0]+ " " + columns[1] + " \n");
        }

        Log.e("DisplayDatatest", txtMsg.toString());
        ////////////////////////////////////////////////////////////////////////////////////////////
        // TODO                                                                                   //
        // - Use the select all query                                                             //
        // - Loop through all the rows and add the to a list                                      //
        // - Return the list                                                                      //
        ////////////////////////////////////////////////////////////////////////////////////////////
        return l;
        */
        return l;
    }

    // Clear the table
    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EVENTS);
        db.close();
    }

}