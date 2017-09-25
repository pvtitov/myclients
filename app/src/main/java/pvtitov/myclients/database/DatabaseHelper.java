package pvtitov.myclients.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static pvtitov.myclients.database.DatabaseWrapper.*;

/**
 * Created by Павел on 24.09.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "historyDatabase.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ClientsTable.NAME + "( _id integer primary key autoincrement, "
                + ClientsTable.Columns.FIRST_NAME + ", "
                + ClientsTable.Columns.LAST_NAME + ", "
                + ClientsTable.Columns.EMAIL + ", "
                + ClientsTable.Columns.PHONE + ", "
                + ClientsTable.Columns.NATIONALITY + ", "
                + ClientsTable.Columns.ADDRESS + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ClientsTable.NAME);
        onCreate(db);
    }
}
