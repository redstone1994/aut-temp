package com.ljc.testCase;

/**
 * @Author Lijc
 * @Description
 * @Date 2019/5/23-9:56
 **/
public class Test {
    public static void main(String[] args) {
        System.out.println(BX.c);
    }
}

class AX {
    static {
        System.out.println("A");
    }
}

class BX extends AX {
    static {
        System.out.println("B");
    }

    public static final String c = "C";
}