package com.example.lib;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyClass {
    public static void main(String[] args){
        System.out.print("1111");
        Set<String> set =new HashSet<>();
        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()){
            iterator.next();
        }
    }
}