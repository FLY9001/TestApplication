package com.eyesmart.testapplication.java;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JAVA常用类库
 */

public class APIs {
    public void test() throws IOException, ParseException, CloneNotSupportedException {
//--------------------------------------------------------------------------------------------------
        Runtime run = Runtime.getRuntime(); //运行时操作类，一个JVM对应一个Runtime
        run.maxMemory();                    //JVM最大内存量
        run.freeMemory();                   //JVM空闲内存量
        run.gc();                           //进行垃圾回收
        Process process = run.exec("su");   //运行本机可执行程序或脚本
        process.getInputStream();           //获取子进程的输入（出）流
        process.getOutputStream();
        process.destroy();                  //杀掉子进程
//--------------------------------------------------------------------------------------------------
        //System.arraycopy();               //数组复制
        System.out.print("abc");            //输出
        System.getProperties();             //取得系统全部属性
        System.gc();                        //垃圾回收，调用Runtime.gc
        System.exit(1);                     //系统提出，非0表示退出

//--------------------------------------------------------------------------------------------------
        StringBuffer buffer = new StringBuffer();      //StringBuffer，频繁修改字符串比String有效率
        buffer.reverse().append("abc").delete(1, 2).replace(0, 0, "b").indexOf("a");  //反转及增删改查

        Pattern pattern = Pattern.compile("[0-9]+");   //正则表达式，用于字符串的匹配、替换、拆分
        Matcher matcher = pattern.matcher("1234567890");
        matcher.matches();

        new Student().clone();              //对象的克隆
//--------------------------------------------------------------------------------------------------
        Random random = new Random();
        random.nextInt(100);                //随机生成最大值为100的int值
        random.nextDouble();

        NumberFormat nf = NumberFormat.getInstance();
        nf.format(1000000);                 //1,000,000
        DecimalFormat decf = new DecimalFormat("###,###,###%");
        decf.format(12.345);                //1,234,5%

        BigInteger bi = new BigInteger("123456");   //当长度大于long时
        BigDecimal bd = new BigDecimal("12.3456");  //当需要精确计算时
//--------------------------------------------------------------------------------------------------
        Date date = new Date();             //Sun Oct 19 09:43:32 CST 2017
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.get(Calendar.YEAR);        //年

        DateFormat df = DateFormat.getDateTimeInstance();
        df.format(new Date());              //2017-12-2 16:25:11

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH 时 mm 分 ss 秒 SSS 毫秒");
        sdf.format(new Date());
        sdf.parse("2017-12-2 16:25:11");    //格式化日期时间
//--------------------------------------------------------------------------------------------------

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("覆写此方法可执行对象被释放前的操作");
    }
}

//比较器Comparable
class Student implements Comparable<Student>, Cloneable {
    public int age;

    @Override
    public boolean equals(Object obj) {          //equals方法可以被覆写
        return super.equals(obj);
    }

    @Override
    public int compareTo(@NonNull Student stu) { //实现Comparable接口，覆写compareTo
        if (this.age > stu.age) {                //通过0，-1，1表示顺序，此时为从大到小排列
            return -1;
        } else if (this.age < stu.age) {
            return 1;
        }
        return 0;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

//比较器Comparator
class StuComparator implements Comparator<Student> {

    @Override
    public int compare(Student stu1, Student stu2) {
        if (stu1.age > stu2.age) {                  //此时为从小到大排列
            return 1;
        } else if (stu1.age < stu2.age) {
            return -1;
        }
        return 0;
    }
}