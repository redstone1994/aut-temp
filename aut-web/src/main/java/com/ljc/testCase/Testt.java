package com.ljc.testCase;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/5/23-10:02
 **/
public class Testt extends A{
    public int a = 1;
    public void fun(){
        System.out.println("B");
    }

    public static void main(String[] args) {
        A classA=new Testt();
        System.out.println(classA.a);
        classA.fun();
    }
}

class A{
    public int a=0;
    public void fun(){
        System.out.println("A");
    }
}