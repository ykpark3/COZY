package com.example.myeyes;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private static Database databaseInstance = null;

    public static Database getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = new Database(context, Constant.ADRRESS_DATABASE_TABLE_NAME, null, 1);
        }
        return databaseInstance;
    }

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createTable(SQLiteDatabase database) {

        String sqlString = "CREATE TABLE " + Constant.ADRRESS_DATABASE_TABLE_NAME + " (adrress TEXT)";

        try {
            database.execSQL(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertAdrress(SQLiteDatabase database, String userAdrress) {
        database.beginTransaction();
        try {
            String sqlString = "insert into " + Constant.ADRRESS_DATABASE_TABLE_NAME + "(adrress)" + " values('" + userAdrress + "')";
            database.execSQL(sqlString);
            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getAdrress(SQLiteDatabase database,int index){
        String sqlSelect = "SELECT * FROM "+ Constant.ADRRESS_DATABASE_TABLE_NAME;
        Cursor cursor = null ;
        int count = 0;

        cursor = database.rawQuery(sqlSelect,null);

        //만약 인덱스가 데이터베이스 안에있는 수보다 크다면, 내 주소 저장하기를 반환.
        if(cursor.getCount()- 1 < index )
            return Constant.REGISER_MY_ADRRESS;

        while(cursor.moveToNext()){

            String adrress = cursor.getString(0);
            if(count == index)
                return adrress;
            count++;
        }
        return null;
    }
}
