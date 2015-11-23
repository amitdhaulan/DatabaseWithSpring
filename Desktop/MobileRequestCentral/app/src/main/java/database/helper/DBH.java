//package database.helper;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import java.util.ArrayList;
//
///**
// * Created by amitk on 7/6/2015.
// */
//public class DBH extends SQLiteOpenHelper {
//    public DBH(Context context) {
//        super(context, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSON);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//
//    public void saveTime() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHandler.KEY_TIME, "\\   15000     \\\\");
//        db.insert(DatabaseHandler.TABLE_Cncn, null, values);
//        db.close();
//    }
//    public long read(){
//        Cursor c = null;
//        long i = 0;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        try {
//            c = db.query(DatabaseHandler.TABLE_Cncn,
//                    new String[] { DatabaseHandler.KEY_TIME },null, null, null, null, null);
//        } catch (Exception exception) {
//            System.out.println(exception.getLocalizedMessage());
//        }
//        if (c.moveToFirst()) {
//            do {
//                String time = c.getString(c.getColumnIndex(DatabaseHandler.KEY_TIME));
//                i = Long.parseLong(time);
//            } while (c.moveToNext());
//            c.close();
//        }
//        return i;
//    }
//}
