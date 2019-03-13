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
 * ContentProvider
 * 内容提供者：向其它应用暴露数据
 * 中间搬运者，真正操作数据的是原来的数据存储方式，如数据库、文件、xml、网络等
 * ContentResolver
 * ContentResolver通过Uri委托给对应的ContentProvider，间接间接调用对应的方法操作数据
 * TODO ContentResolver ----> 通过Uri间接调用 ----> ContentProvider
 * <p>
 * 原理：采用 Binder机制
 * 优点：安全；访问简单、高效    （解耦了 数据的存储，统一了 数据的访问）
 * <p>
 * 清单文件配置authorities属性：可用全类名
 * 访问需通过Uri，如"content://com.eyesmart.testapplication.android.TestContentProvider/words/1"
 * 其中   content://为协议   全类名为authorities（授权信息）    words为资源表名     1为ID
 * <p>
 * 系统自带的ContentProvider
 * ContactsProvider：用来查询联系人信息；
 * CalendarProvider：用来提供日历相关信息的查询；
 * MediaProvider：   用来查询磁盘上多媒体文件；
 * BookmarkProvider：用来提供书签信息的查询；
 */
public class TestProvider extends ContentProvider {
    void test() {
        /**使用步骤*/
        //1、继承ContentProvider，实现增删改查、getType等方法
        //2、清单文件注册该ContentProvider，指定authorities属性
        //3、ContentResolver进行数据操作
        ContentResolver resolver = getContext().getContentResolver();
        Uri uri = Uri.parse("content://com.eyesmart.testapplication.android.TestContentProvider/");
        resolver.getType(uri);                                                                  //得到数据的MIME类型
        resolver.insert(uri, null);                                                      //增，返回新增数据 Uri
        resolver.delete(uri, null, null);                                   //删，返回删除数据 个数
        resolver.update(uri, null, null, null);                     //改，返回更新数据 个数
        resolver.query(uri, null, null, null, null);//查，返回查询数据 Cursor

        /**辅助工具类*/
        //ContentUris，操作URI工具类
        Uri resultUri = ContentUris.withAppendedId(uri, 1);                        //向URI追加一个id，+/1
        long personid = ContentUris.parseId(resultUri);                               //从URL中获取ID，得到1

        //UriMatcher，注册URI工具类，便于分辨不同URI
        UriMatcher mMatcher = TestProvider.mMatcher;

        //自定义工具类，一些常量。可不使用，使用后更方便维护和对外
        Class<Words> wordsClass = Words.class;

        /**ContentObserver观察数据变化*/
        //如监听用户发送短信的内容
        resolver.registerContentObserver(uri, false, null);
        resolver.unregisterContentObserver(null);
    }

    /*********************************************************************************************************************/

    private static final UriMatcher mMatcher;   //UriMatcher类使用:在ContentProvider中注册URI
    private static final int WORDS_CODE = 1;
    private static final int WORD_CODE = 2;
    private SQLiteDatabase db;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(Words.AUTHORITY, "words", WORDS_CODE); // 若URI资源路径 = content://cn.scu.myprovider/words ，则返回注册码WORDS_CODE
        mMatcher.addURI(Words.AUTHORITY, "word/#", WORD_CODE);
    }

    //第一次创建时调用，该类只有一个示例
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
    /**
     * @param uri
     * @param values
     * @return 返回新插入记录的Uri
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
//        long rowId = db.insert(TestDatabase.DbHelper.TABAL, null, values);
        switch (mMatcher.match(uri)) {
            case WORDS_CODE:
                long rowId = db.insert("dict", Words.Word._ID, values);
                if (rowId > 0) {
                    Uri wordUri = ContentUris.withAppendedId(uri, rowId);                    // 在已有的 Uri的后面追加ID
                    getContext().getContentResolver().notifyChange(wordUri, null);  // 通知数据已经改变
                    return wordUri;
                }
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        return null;
    }

    /**
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return 返回被删除的记录条数
     */
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

    /**
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return 返回被更新的记录条数
     */
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

    /**
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return 返回查询到的Cursor
     */
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

    /**
     * @param uri
     * @return 表示该ContentProvider所提供数据的MIME类型
     */
    @Override
    public String getType(Uri uri) {
        switch (mMatcher.match(uri)) {
            case WORDS_CODE:
                return "vnd.android.cursor.dir/org.crazyit.dict";   // 如果操作的数据是多项记录，/后自定义
            case WORD_CODE:
                return "vnd.android.cursor.item/org.crazyit.dict";  // 如果操作的数据是单项记录，/后自定义
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