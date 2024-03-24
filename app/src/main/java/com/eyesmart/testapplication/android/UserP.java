package com.eyesmart.testapplication.android;

import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcelable
 * Android中的序列化接口
 */

public class UserP implements Parcelable {
    public int userID;
    public UserP userP2;

    public static void test() {

    }

    //文件描述
    @Override
    public int describeContents() {
        return 0;   //几乎所有情况下都为0
    }

    //序列化，通过Parcel.write
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userID);
        dest.writeParcelable(userP2, flags);    //flags几乎所有情况下都为0
    }

    //反序列化，通过Parcel.read
    public static final Creator<UserP> CREATOR = new Creator<UserP>() {
        @Override
        public UserP createFromParcel(Parcel in) {
            return new UserP(in);
        }

        @Override
        public UserP[] newArray(int size) {
            return new UserP[size];
        }
    };

    private UserP(Parcel in) {
        userID = in.readInt();
        //为可序列化对象，传递当前线程的上下文类加载器，否则会报无法找到类的错误
        userP2 = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }
}

