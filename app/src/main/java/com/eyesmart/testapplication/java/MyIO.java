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
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;

/**
 * 输入、输出
 * 1字节 == 8bit
 * int为4字节，即32位
 */

public class MyIO {
    void test() throws IOException {
        //对文件本身操作
        File file = new File("/mnt/sdcard" + File.separator + "test.txt");
        file.exists();                  //是否存在
        file.createNewFile();           //创建文件
        file.delete();                  //删除
        file.isDirectory();             //是否时文件夹
        file.mkdir();                   //创建文件夹
        File[] files = file.listFiles();//遍历文件夹中的文件

        //对文件内容操作，可进行随机读取
        RandomAccessFile rdf = new RandomAccessFile(file, "rw");//以读写方式打开，自动创建文件
        rdf.writeInt(123);              //写，此数长度4字节
        rdf.writeBytes("abc");
        rdf.skipBytes(4);               //往后跳4字节
        byte[] b = new byte[3];
        for (int i = 0; i < b.length; i++) {
            b[i] = rdf.readByte();      //读取1字节并后移
        }
        String abc = new String(b);     //得到"abc"
        rdf.seek(0);                    //读指针移到0位置
        rdf.readInt();                  //读取1个int，即 123
        rdf.close();                    //关闭

        //字节流
        OutputStream os = new FileOutputStream(file, false);//以覆盖（不追加）的方式
        byte[] osBytes = "abc\r\n".getBytes();
        os.write(osBytes);
        for (int i = 0; i < osBytes.length; i++) {
            os.write(osBytes[i]);         //写一个字节
        }
        os.close();

        InputStream is = new FileInputStream(file);
        is.available();                     //输入流数据大小
        byte[] isBytes = new byte[(int) file.length()];
        int len = is.read(isBytes);         //isBytes，len为一次读取的数据长度
        int temp = 0;
        for (int i = 0; (temp = is.read()) != -1; i++) {
            isBytes[i] = (byte) temp;       //读取一个字节，读到bytes1
        }
        is.close();

        //字符流，用到缓冲区，最终都是以字节流操作
        Writer w = new FileWriter(file, false);
        //w = new OutputStreamWriter(os);   //可以用转换流
        w.write("abc");
        w.flush();                          //清空缓冲区内容，写进Writer
        w.close();

        Reader r = new FileReader(file);
        //r = new InputStreamReader(is);    //可以用转换流
        char[] rBytes = new char[(int) file.length()];
        int read = r.read(rBytes);
        for (int i = 0; (temp = r.read()) != -1; i++) {
            rBytes[i] = (char) temp;       //读取一个字符，读到bytes1
        }
        r.close();

        //内存操作流
        ByteArrayInputStream bis = new ByteArrayInputStream("abc".getBytes());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        //管道流，两个线程间通讯
        new PipedInputStream().connect(new PipedOutputStream());

        //打印流
        PrintStream ps = new PrintStream(os);
        ps.printf("在文件中打印字符串：%s", "abc");
        ps = System.out;                //可向屏幕输出
        ps.close();
    }

    /**
     * 复制文件
     */
    public void copyFile(File fromFile, File toFile) throws IOException {
        InputStream is = new FileInputStream(fromFile);
        OutputStream os = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n = 0;
        while ((n = is.read(b)) != -1) {
            os.write(b, 0, b.length);
        }
        is.close();
        os.close();
    }
}
