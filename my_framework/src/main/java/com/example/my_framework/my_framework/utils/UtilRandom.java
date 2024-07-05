package com.example.my_framework.my_framework.utils;

import java.util.Random;

public class UtilRandom {
    public static int getCasualNumber(int number){
        Random random = new Random();

        return random.nextInt(number);

    }

    public static int getGap(int minNumber, int maxNumber){

        return (int) (Math.random()*(maxNumber) + 1*Math.random());

    }

}
