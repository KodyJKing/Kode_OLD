package kode;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static void print(Object o){
        System.out.println(o);
    }

    public static List<String> splitToLength(String text, int length){
        ArrayList<String> result = new ArrayList<String>();

        for(int i = 0; i < text.length(); i += length)
            result.add(text.substring(i, Math.min(i + length, text.length())));

        return result;
    }

    public static int clamp(int low, int high, int x){
        if(x < low)
            return low;
        if(x > high)
            return high;
        return x;
    }

}
