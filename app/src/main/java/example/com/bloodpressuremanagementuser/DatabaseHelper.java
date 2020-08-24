package example.com.bloodpressuremanagementuser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Bloodpressure.db";
    private static final String TABLE_NAME = "bloodpressuredb";
    private static final String _ID = "_id";
    private static final String COLUMN_NAME_TITLE = "upperBloodPressure";
    private static final String COLUMN_NAME_SUBTITLE = "lowerBloodPressure";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " INTEGER," +
                    COLUMN_NAME_SUBTITLE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE bloodpressure (");
        sb.append("_id INTEGER PRIMARY KEY,");
        sb.append("upperBloodPressure TEXT");
        sb.append("lowerBloodPressure TEXT");
        sb.append(");");
        String sql = sb.toString();

        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        
    }
}
