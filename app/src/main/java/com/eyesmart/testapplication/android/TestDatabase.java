package com.eyesmart.testapplication.android;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * 性能优化：
 * 1、execSql，rawQuery方法执行效率更高（大于1000，数据量越大越明显）
 * 2、批量操作时，执行事务（将先缓存在内存中，COMMIT时一次写入数据库，数据库文件只被打开关闭了一次，提高效率）
 */

public class TestDatabase {
    void test() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/mnt/db/test.db", null);//打开或创建数据库文件，需绝对路径

        db.beginTransaction();              // 开启事务
        try {
            //TODO 某些操作
            if (true) {// 在这里手动抛出一个异常，让事务失败
                throw new NullPointerException();
            }
            db.setTransactionSuccessful();  //事务已经执行成功
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();            // 结束事务
        }

        db.close();
    }

    public static class DbTool {
        private static SQLiteDatabase db;

        public static long insert(String value) {
            if (db == null || !db.isOpen()) {
                db = DbHelper.getInstance().getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(DbHelper.COLUMN, value);
            long rowId = db.insert(DbHelper.TABAL, null, values);
            Log.d(TAG, "插入行id：" + rowId);   //-1表示失败

            db.execSQL("insert into <表名>(列名1, 列名2...) values(value1,value2...)");
            db.execSQL("insert into " + DbHelper.TABAL + "(" + DbHelper.COLUMN + ", " + DbHelper.COLUMN + ") values(" + value + "," + value + ")");

            //db.replace()      //避免重复添加，当表有一个PRIMARY KEY或UNIQUE索引才有意义
            return rowId;
        }

        public static int update(String value) {
            if (db == null || !db.isOpen()) {
                db = DbHelper.getInstance().getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(DbHelper.COLUMN, value);
            int rows = db.update(DbHelper.TABAL, values, DbHelper.COLUMN + " = ?", new String[]{"arg"});
            Log.d(TAG, "更新行个数：" + rows);

            db.execSQL("update <表名> set 列名1=value1, 列名2=value2... where <whereClause>");
            //<whereClause>:列名1=value1 and/or 列名2=value2
            return rows;
        }

        public static int delete(long id) {
            if (db == null || !db.isOpen()) {
                db = DbHelper.getInstance().getWritableDatabase();
            }
            int rows = db.delete(DbHelper.TABAL, "id <> ?", new String[]{id + ""});//不等于
            Log.d(TAG, "删除行个数：" + rows);

            db.execSQL("delete <表名> where <whereClause>");
            return rows;
        }

        public static int query(long id) {
            if (db == null || !db.isOpen()) {
                db = DbHelper.getInstance().getWritableDatabase();
            }
            Cursor cursor = db.query(DbHelper.TABAL, null, null, null, null, null, null);
            cursor = db.query(DbHelper.TABAL, new String[]{DbHelper.COLUMN}, DbHelper.COLUMN + " = ?", new String[]{id + ""}, null, null, null);
            int queryId = 0;
            while (cursor.moveToNext()) {
                queryId = cursor.getInt(cursor.getColumnIndex(DbHelper.COLUMN));
            }
            cursor.close();
            db.execSQL("select 列名1，sum(列名2) from <表名> where <whereClause> group by 列名1 having sum(列名2)>10");
            //group by 以列名1分组
            //having   组内列名2的值的和大于10
            db.execSQL("select 列名1，列名2... from <表名> where <whereClause> order by 列名1 asc,列名2 desc");
            //order by 排序：asc 升序(默认)，dese 降序
            return queryId;
        }
    }

    public static class DbHelper extends SQLiteOpenHelper {
        public static final String TABAL = "tabal";     //表名
        public static final String COLUMN = "column";   //列名

        private static DbHelper dbHelper;

        public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public static DbHelper getInstance() {
            if (dbHelper == null) {
                dbHelper = new DbHelper(null, "Test.db", null, 1);  //文件地址：/data/data/<package name>/databases/xxx.db
            }
            return dbHelper;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {                 //第一次创建数据库时
            String sqlCreate = "create table if not exists " + TABAL + "("
                    + "id" + " integer PRIMARY KEY AUTOINCREMENT,"//主键，自增长
                    + COLUMN + " integer,"                        //整型
                    + COLUMN + " text,"                           //文本
                    + COLUMN + " real,"                           //浮点型
                    + COLUMN + " blob)";                          //二进制
            db.execSQL(sqlCreate);                                //自动建表
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
