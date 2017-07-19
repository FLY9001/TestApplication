package com.eyesmart.testapplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * TCP/IP是传输层协议，主要解决 数据如何在网络中传输。
 * HTTP  是应用层协议，主要解决 如何包装数据。一问一答，每次请求都需要服务器回送响应，在请求结束后，会主动释放连接
 * <p>
 * socket则是对TCP/IP协议的封装和应用（TCP/IP层上的一个抽象接口）。
 * socket一旦建立连接双方都可以自由通信
 * <p>
 * TCP：三次握手，四次挥手；和UDP区别
 */

public class TestNet {
    //此为单线程
    private void startTCPServer() {
        final int port = 8989;  //端口号应该大于等于1024，因为0~1023内都被系统内部占用了
        //端口号范围为0-65535，其中0-1023位为系统保留
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket server = null;
                try {
                    server = new ServerSocket(port);                    //1、创建ServerSocket服务器套接字
                    server.setSoTimeout(8000);// 设置连接超时时间，不设置，则是一直阻塞等待
                    while (true) {
                        Socket socket = server.accept();                //2、等待被连接。在连接超时时间内连接有效，超时则抛异常，
                        System.out.println("connected...");
                        socket.setSoTimeout(5000);// 设置读取流的超时时间，不设置，则是一直阻塞读取

                        InputStream is = socket.getInputStream();       //3、获取输入流和输出流
                        OutputStream os = socket.getOutputStream();

                        byte[] buff = new byte[1024];
                        int len = is.read(buff);                        //4、读取数据
                        String receData = new String(buff, 0, len, Charset.forName("UTF-8"));
                        System.out.println("received data from client: " + receData);

                        byte[] responseBuf = "Hi, I am Server".getBytes(Charset.forName("UTF-8"));
                        os.write(responseBuf, 0, responseBuf.length);   //5、发送响应数据
                        socket.close();
                    }
                } catch (IOException e) {
                    System.out.println("Exception：" + e.toString());
                    e.printStackTrace();
                } finally {
                    if (server != null) {
                        try {
                            server.close();
                            server = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

    }

    private void startTCPClient() {
        final String host = "192.168.1.214";
        final int port = 8989;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                try {
                    socket = new Socket(host, port);                    // 1、创建连接
                    if (socket.isConnected()) {
                        System.out.println("connect to Server success");
                    }
                    socket.setSoTimeout(8000);                          // 2、设置读流的超时时间

                    OutputStream os = socket.getOutputStream();         // 3、获取输出流与输入流
                    InputStream is = socket.getInputStream();

                    byte[] sendData = "Hello, I am client".getBytes(Charset.forName("UTF-8"));
                    os.write(sendData, 0, sendData.length);             // 4、发送信息
                    os.flush();

                    byte[] buf = new byte[1024];
                    int len = is.read(buf);                             // 5、接收信息
                    String receData = new String(buf, 0, len, Charset.forName("UTF-8"));
                    System.out.println(receData);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e.toString());
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                            socket = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
