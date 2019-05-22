package com.ljc.testCase;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/4/9-8:57
 **/
public class Te {
    public static void main(String[] args) {

        Person p1=new Person();
        p1.name="zhangsan";
        Person p2=new Person();
        p2.name="lisi";

        p1.talk("zhang");
        p2.talk("li");

    }

}

class Person{
    String name;
    void talk(String name){
        System.out.println("My name is "+ this.name);
    }
}
