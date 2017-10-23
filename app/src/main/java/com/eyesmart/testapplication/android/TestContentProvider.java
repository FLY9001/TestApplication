package com.eyesmart.testapplication.android;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * http://www.jianshu.com/p/ea8bc4aaf057
 * 内容提供者：向其它应用暴露数据
 * 原理：采用 Android中的Binder机制，TODO ContentResolver ----> 通过Uri间接调用 ----> ContentProvider
 * 优点：安全；访问简单、高效    （解耦了 底层数据的存储方式，统一了 数据的访问方式）
 * <p>
 * 清单文件配置authorities属性：可用全类名
 * 访问需通过Uri，如"content://com.eyesmart.testapplication.android.TestContentProvider/user"
 * 其中   content://为协议   全类名为authorities    user为资源
 */
public class TestContentProvider extends ContentProvider {
    void test() {
        /**ContentResolver进行数据操作*/
        ContentResolver resolver = getContext().getContentResolver();
        Uri uri = Uri.parse("content://com.eyesmart.testapplication.android.TestContentProvider/");
        getType(uri);                      //得到数据的MIME类型
        insert(uri, null);                 //增，返回新增数据 Uri
        delete(uri, null, null);           //删，返回删除数据 个数
        update(uri, null, null, null);     //改，返回更新数据 个数
        query(uri, null, null, null, null);//查，返回查询数据 Cursor

        /**ContentUris操作URI*/
        Uri resultUri = ContentUris.withAppendedId(uri, 1); //向URI追加一个id，+/1
        long personid = ContentUris.parseId(resultUri);     //从URL中获取ID，得到1

        /**UriMatcher注册URI*/

        /**ContentObserver观察数据变化*/
        resolver.registerContentObserver(uri, false, null);
        resolver.unregisterContentObserver(null);

        /**工具类，一些常量，方便对外公布*/
        Class<Words> wordsClass = Words.class;
    }

    public static final String AUTHORITY = "com.eyesmart.testapplication.android.TestContentProvider";// 设置ContentProvider的唯一标识
    private static final UriMatcher mMatcher;   //UriMatcher类使用:在ContentProvider中注册URI
    private static final int WORDS_CODE = 1;
    private static final int WORD_CODE = 2;
    private SQLiteDatabase db;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(Words.AUTHORITY, "words", WORDS_CODE); // 若URI资源路径 = content://cn.scu.myprovider/words ，则返回注册码WORDS_CODE
        mMatcher.addURI(Words.AUTHORITY, "word/#", WORD_CODE);
    }

    @Override
    public boolean onCreate() {
        db = TestDatabase.DbHelper.getInstance().getWritableDatabase();
        return true;
    }

    /**
     * 通过ContentValues新增
     * ContentValues values = new ContentValues();
     * values.put("key", "value");
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
//        long rowId = db.insert(TestDatabase.DbHelper.TABAL, null, values);
        switch (mMatcher.match(uri)) {
            case WORDS_CODE:
                long rowId = db.insert("dict", Words.Word._ID, values);
                if (rowId > 0) {
                    Uri wordUri = ContentUris.withAppendedId(uri, rowId);           // 在已有的 Uri的后面追加ID
                    getContext().getContentResolver().notifyChange(wordUri, null);  // 通知数据已经改变
                    return wordUri;
                }
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int num = 0;
        switch (mMatcher.match(uri)) {
            case WORDS_CODE:
                num = db.delete("dict", selection, selectionArgs);
                break;
            case WORD_CODE:
                // 解析出所需要删除的记录ID
                long id = ContentUris.parseId(uri);
                String selectionClause = Words.Word._ID + "=" + id;
                // 如果原来的selection子句存在，拼接selection子句
                if (selection != null && !selection.equals("")) {
                    selectionClause = selectionClause + " and " + selection;
                }
                num = db.delete("dict", selectionClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        // 通知数据已经改变
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int num = 0;
        switch (mMatcher.match(uri)) {
            case WORDS_CODE:
                num = db.update("dict", values, selection, selectionArgs);
                break;
            case WORD_CODE:
                // 解析出想修改的记录ID
                long id = ContentUris.parseId(uri);
                String selectionClause = Words.Word._ID + "=" + id;
                // 如果原来的selection子句存在，拼接selection子句
                if (selection != null && !selection.equals("")) {
                    selectionClause = selectionClause + " and " + selection;
                }
                num = db.update("dict", values, selectionClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        // 通知数据已经改变
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (mMatcher.match(uri)) {
            case WORDS_CODE:
                return db.query("dict", projection, selection, selectionArgs, null, null, sortOrder);
            case WORD_CODE:
                // 解析出想查询的记录ID
                long id = ContentUris.parseId(uri);
                String selectionClause = Words.Word._ID + "=" + id;
                // 如果原来的selection子句存在，拼接selection子句
                if (selection != null && !"".equals(selection)) {
                    selectionClause = selectionClause + " and " + selection;
                }
                return db.query("dict", projection, selectionClause, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (mMatcher.match(uri)) {
            case WORDS_CODE:
                return "vnd.android.cursor.dir/org.crazyit.dict";   // 如果操作的数据是多项记录
            case WORD_CODE:
                return "vnd.android.cursor.item/org.crazyit.dict";  // 如果操作的数据是单项记录
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
    }
}

final class Words {
    // 定义该ContentProvider的Authorities
    public static final String AUTHORITY = "com.eyesmart.testapplication.android.TestContentProvider";

    // 定义一个静态内部类，定义该ContentProvider所包含的数据列的列名
    public static final class Word implements BaseColumns {
        // 定义Content所允许操作的三个数据列
        public final static String _ID = "_id";
        public final static String WORD = "word";
        public final static String DETAIL = "detail";
        // 定义该Content提供服务的两个Uri
        public final static Uri DICT_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/words");
        public final static Uri WORD_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/word");
    }
}