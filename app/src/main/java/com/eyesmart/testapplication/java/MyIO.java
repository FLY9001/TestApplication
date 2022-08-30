package com.eyesmart.testapplication.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;

/**
 * 输入、输出
 * 1byte（字节）= 8bit（比特、位）
 * int为 4byte（字节）= 32bit（比特、位）
 * 1KB = 1024B（byte）
 * <p>
 * 1字符 = 2字节
 */

public class MyIO {
    void test() throws IOException {
        //对文件本身操作
        File file = new File("/mnt/sdcard" + File.separator + "test.txt");
        file.exists();                  //是否存在
        file.createNewFile();           //创建文件
        file.delete();                  //删除
        file.isDirectory();             //是否是文件夹
        file.mkdir();                   //创建文件夹
        File[] files = file.listFiles();//遍历文件夹中的文件

//**********************************************************************************************************
        //对文件内容操作，可进行随机读取
        RandomAccessFile rdf = new RandomAccessFile(file, "rw");//以读写方式打开，自动创建文件
        rdf.writeInt(123);           //写，此数长度4字节
        rdf.writeBytes("abc");
        rdf.skipBytes(4);            //往后跳4字节
        byte[] b = new byte[3];
        for (int i = 0; i < b.length; i++) {
            b[i] = rdf.readByte();      //读取1字节并后移
        }
        String abc = new String(b);     //得到"abc"
        rdf.seek(0);               //读指针移到0位置
        rdf.readInt();                  //读取1个int，即 123
        rdf.close();                    //关闭

//**********************************************************************************************************
        //File****，文件操作流，数据保存在文件
        //字节流
        //写
        OutputStream os = new FileOutputStream(file, false);//以覆盖（不追加）的方式
        byte[] osBytes = "abc\r\n".getBytes();
        os.write(osBytes);
        for (int i = 0; i < osBytes.length; i++) {
            os.write(osBytes[i]);                       //写一个字节
        }
        os.flush();
        os.close();

        //读
        InputStream is = new FileInputStream(file);
        is.available();                                 //文件大小
        byte[] isBytes = new byte[(int) file.length()];
        int len = is.read(isBytes);                     //一次性读取，len为一次读取的数据长度
        int temp = 0;
        for (int i = 0; (temp = is.read()) != -1; i++) {
            isBytes[i] = (byte) temp;                   //只读取一个字节，读到bytes1
        }
        is.close();

//**********************************************************************************************************
        //字符流，用到缓冲区，最终都是以字节流操作
        //写
        Writer w = new FileWriter(file, false);
        w = new OutputStreamWriter(os);                 //也可以用转换流
        w.write("abc");                             //执行完write，只是被写入到缓冲区，只有flush或close后，文件才真正被写入
        w.flush();                                      //清空缓冲区内容，写进Writer
        w.close();

        //读
        Reader r = new FileReader(file);
        r = new InputStreamReader(is);                  //可以用转换流
        char[] rBytes = new char[(int) file.length()];
        int read = r.read(rBytes);
        for (int i = 0; (temp = r.read()) != -1; i++) {
            rBytes[i] = (char) temp;                    //读取一个字符，读到bytes1
        }
        r.close();

//**********************************************************************************************************
        //内存操作流，数据保存在byte[]
        ByteArrayInputStream bis = new ByteArrayInputStream("abc".getBytes());//另有对应字符流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        //管道流，两个线程间通信
        new PipedInputStream().connect(new PipedOutputStream());//连接管道

        //ZipOutputStream、ZipInputStream压缩流

        //打印流
        PrintStream ps = new PrintStream(os);
        ps.printf("接收文件流后，在文件中打印字符串：%s", "abc");
        ps = System.out;                   //可向屏幕输出
        ps.close();
    }

//**********************************************************************************************************


    /**
     * 复制文件
     */
    public void copyFile(File fromFile, File toFile) throws IOException {
        InputStream is = new FileInputStream(fromFile);
        OutputStream os = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = is.read(b, 0, b.length)) != -1) {
            os.write(b, 0, len);
        }
        is.close();
        os.close();
    }
}
