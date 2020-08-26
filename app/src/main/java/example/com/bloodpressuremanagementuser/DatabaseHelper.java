package example.com.bloodpressuremanagementuser;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/*
 SQLiteのテーブル名とカラム名・・・小文字とアンダースコア(_)
 なんでかはわからない
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // テーブル➀　あまり考えないで作ったやつ
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Bloodpressure.db";
    private static final String TABLE_NAME = "_bloodpressuredb";
    private static final String _ID = "_id";
    private static final String COLUMN_NAME_TITLE = "_maxBP"; // 最高血圧
    private static final String COLUMN_NAME_SUBTITLE = "_minBP"; // 最低血圧
    // テーブル➁　カラム名をちゃんと考えてみた
    private static final String TABLE_NAME2 = "_BPdb";
    private static final String PRIMARY_KEY = "date";    // 日時。データフォーマットでいけるかな？
    private static final String COLUMN_MAXBP = "_maxBP"; // 最高血圧
    private static final String COLUMN_MINBP = "_minBP"; // 最低血圧
    private static final String COLUMN_PULSE = "_pulse"; // 脈拍

    /*
     SQLでテーブルを作る
     _id ・・・ カラム名に「_id」と記述するとOSが自動的に主キーを取ってくれる。自動に番号が振られる
     */
    // テーブル➀
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    _ID + " INTEGER," +
                    COLUMN_NAME_TITLE + " INTEGER," +
                    COLUMN_NAME_SUBTITLE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    // テーブル➁
    private static final String SQL_CREATE_ENTRIES2 =
            "CREATE TABLE" + TABLE_NAME2 + "(" +
                    _ID + "INTEGER," +
                    PRIMARY_KEY + "TEXT," +
                    COLUMN_MAXBP + "INTEGER," +
                    COLUMN_MINBP + "INTEGER," +
                    COLUMN_PULSE + "INTEGER)";

    // コンストラクタ
    DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    /*
     SQLiteOpenHelperクラスを継承している。onCreate()とonUpgrade()の2つのオーバーライドがないとコンパエラー
     onCreate()は一回しか実行しないので、コメントアウトしてもsaveData()の値が消えないよ( ﾉД`)
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES); // 引数がテーブルだよ。上で作ってあるよ
        // 必要ないよね
//        saveData(db,105,71);
//        saveData(db,104,69);
//        saveData(db,108,71);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
    // 必要ないよね。BPAdditionの値を追加していくわけだし
//    public void saveData(SQLiteDatabase db, int maxBP, int minBP) {
//        ContentValues values = new ContentValues();
//        values.put("maxBP", maxBP);
//        values.put("minBP", minBP);
//
//        db.insert("bloodpressuredb",null,values);
//    }
}
