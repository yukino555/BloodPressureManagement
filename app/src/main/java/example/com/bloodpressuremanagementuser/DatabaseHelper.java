package example.com.bloodpressuremanagementuser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/*
 SQLiteのテーブル名とカラム名・・・小文字とアンダースコア(_)
 なんでかはわからない
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Bloodpressure.db";
    private static final String _ID = "_id";
    private static final String TABLE_NAME = "_BPtable";
    private static final String COLUMN_DATE = "_date";    // 日時。データフォーマットでいけるかな？
    private static final String COLUMN_MAXBP = "_maxBP"; // 最高血圧
    private static final String COLUMN_MINBP = "_minBP"; // 最低血圧
    private static final String COLUMN_PULSE = "_pulse"; // 脈拍

    // コンストラクタ
    DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    /*
     SQLでテーブルを作る
     _id ・・・ カラム名に「_id」と記述するとOSが自動的に主キーを取ってくれる。自動に番号が振られる
     */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_MAXBP + " INTEGER," +
                    COLUMN_MINBP + " INTEGER," +
                    COLUMN_PULSE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    /*
     SQLiteOpenHelperクラスを継承している。onCreate()とonUpgrade()の2つのオーバーライドがないとコンパエラー
     onCreate()は一回しか実行しないので、コメントアウトしてもsaveData()の値が消えないよ( ﾉД`)
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES); // 引数がテーブルだよ。上で作ってあるよ
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}
