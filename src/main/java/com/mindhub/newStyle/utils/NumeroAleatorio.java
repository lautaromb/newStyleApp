package com.mindhub.newStyle.utils;

public class NumeroAleatorio {

    static int min = 10000;
    static int max = 99999;

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
