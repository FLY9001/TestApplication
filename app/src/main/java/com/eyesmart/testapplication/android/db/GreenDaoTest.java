package com.eyesmart.testapplication.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.eyesmart.testapplication.android.db.dao.DaoMaster;
import com.eyesmart.testapplication.android.db.dao.DaoSession;
import com.eyesmart.testapplication.android.db.dao.UserDao;

import java.util.List;

/**
 * https://blog.csdn.net/qq_36699930/article/details/81540781
 *
 * GreenDAO 是一个将对象映射到 SQLite 数据库中的轻量且快速的ORM(“对象/关系映射”)解决方案。
 *
 * 优点：
 * 1、高性能，可能是Android平台上最快的ORM框架
 * 2、易于使用的强大API，涵盖关系和连接；
 * 3、最小的内存消耗;
 * 4、小库大小（<100KB）以保持较低的构建时间并避免65k方法限制;
 * 5、数据库加密：greenDAO支持SQLCipher，以确保用户的数据安全;
 *
 *
 * 1、配置GreenDao，Project和app的build.gradle
 * 2、创建存储对象实体类，如User
 * 3、编译后，自动生成文件，DaoMaster、DaoSession、Dao
 * 4、初始化GreenDao，及增删改查操作，建议用封装类
 */

public class GreenDaoTest {
    private static DaoSession daoSession;

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    public static void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "greendao.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    /**
     * 增删改查
     */
    public void test() {
        User user = new User();

        //增
        GreenDaoTest.getDaoSession().getUserDao().insert(user);         //注意：Long型id，如果传入null，则GreenDao会默认设置自增长的值。
        GreenDaoTest.getDaoSession().getUserDao().insertOrReplace(user);//推荐，数据无则插入有则更新
        GreenDaoTest.getDaoSession().getUserDao().save(user);           //有key有数据执行更新，无key的执行插入，有key无数据执行失败

        //删
        GreenDaoTest.getDaoSession().getUserDao().delete(user);             //从数据库中删除给定的实体
        GreenDaoTest.getDaoSession().getUserDao().deleteByKey(user.getId());//从数据库中删除给定Key所对应的实体
        GreenDaoTest.getDaoSession().getUserDao().deleteAll();              //删除全部

        //改
        GreenDaoTest.getDaoSession().getUserDao().update(user);         //更新

        //查
        User user0 = GreenDaoTest.getDaoSession().getUserDao().load(user.getId());//根据主键key
        User user1 = GreenDaoTest.getDaoSession().getUserDao().loadByRowId(1);    //根据行号
        List<User> userList = GreenDaoTest.getDaoSession().getUserDao().loadAll();//查询全部

        UserDao userDao = GreenDaoTest.getDaoSession().getUserDao();

        //条件查询

        userList = userDao.queryBuilder().list();//查询全部

        //查询 name等于xyh8的数据
        userList = userDao.queryBuilder().where(UserDao.Properties.Name.eq("xyh8")).list();

        //查询 name不等于xyh8的数据
        userList = userDao.queryBuilder().where(UserDao.Properties.Name.notEq("xyh8")).list();

        //like  模糊查询
        //查询 name以xyh3开头的数据
        userList = userDao.queryBuilder().where(UserDao.Properties.Name.like("xyh3%")).list();

        //between 区间查询 年龄在20到30之间
        userList = userDao.queryBuilder().where(UserDao.Properties.Age.between(20, 30)).list();

        //gt: greater than 半开区间查询，年龄大于18
        userList = userDao.queryBuilder().where(UserDao.Properties.Age.gt(18)).list();

        //ge: greater equal 半封闭区间查询，年龄大于或者等于18
        userList = userDao.queryBuilder().where(UserDao.Properties.Age.ge(18)).list();

        //lt: less than 半开区间查询，年龄小于18
        userList = userDao.queryBuilder().where(UserDao.Properties.Age.lt(18)).list();

        //le: less equal 半封闭区间查询，年龄小于或者等于18
        userList = userDao.queryBuilder().where(UserDao.Properties.Age.le(18)).list();

        //排序

        //名字以xyh8开头，年龄升序排序
        userList = userDao.queryBuilder()
                .where(UserDao.Properties.Name.like("xyh8%"))
                .orderAsc(UserDao.Properties.Age)
                .list();

        //名字以xyh8开头，年龄降序排序
        userList = userDao.queryBuilder()
                .where(UserDao.Properties.Name.like("xyh8%"))
                .orderDesc(UserDao.Properties.Age)
                .list();
    }
}
