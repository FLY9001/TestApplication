package com.eyesmart.testapplication.android;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Serializable
 * Java提供的序列化接口
 */

public class UserS implements Serializable {
    //避免类的一些改变造成的反序列化失败
    private static final long serialVersionUID = 9527L;

    public static void test() throws Exception{
        //序列化（保存对象到文件）
        UserS userS = new UserS();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("userS.txt"));
        oos.writeObject(userS);
        oos.close();

        //反序列化
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("userS.txt"));
        userS = (UserS) ois.readObject();
        ois.close();
    }
}

