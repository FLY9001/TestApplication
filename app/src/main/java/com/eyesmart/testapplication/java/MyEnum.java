package com.eyesmart.testapplication.java;

/**
 * Created by 1 on 2017/6/18 0018.
 */

public class MyEnum {
    void test() {
        for (Color c : Color.values()) {
            c.name();       //RED
            c.ordinal();    //序号：0
        }
    }
}

enum Color {
    RED, GREEN, BULE;
}
