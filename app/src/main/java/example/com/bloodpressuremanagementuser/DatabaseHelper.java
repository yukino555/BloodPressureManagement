package example.com.bloodpressuremanagementuser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_BLOODPRESSURE = "bloodpressure.db";
//    private static final String DATABASE_LOWERBLOODPRESSURE = "lowerbloodpressure.db";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DATABASE_BLOODPRESSURE,null,DATABASE_VERSION);
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
