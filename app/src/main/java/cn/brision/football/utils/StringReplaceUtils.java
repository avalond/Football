package cn.brision.football.utils;

public class StringReplaceUtils {

    public static StringBuilder replacePhoneNumber(String str){
        StringBuilder builder = new StringBuilder(str);
        StringBuilder replace = builder.replace(3, 7, "****");
        return replace;
    }
}
