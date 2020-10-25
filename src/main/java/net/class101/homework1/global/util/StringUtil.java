package net.class101.homework1.global.util;

import java.util.regex.Pattern;

public class StringUtil {
    public static final String REGEX_NUMERIC = "^[0-9]*$";

    public static boolean isNumeric(String str) {
        boolean isNumeric = Pattern.matches(REGEX_NUMERIC, str.trim());

        if (!isNumeric) {
            System.out.println("상품번호와 수량은 자연수만 입력 가능 합니다.");
        }

        return isNumeric;
    }

    public static boolean isNotNumeric(String str) {
        return !isNumeric(str);
    }

    public static String getTrimString(String str) {
        return str.trim();
    }
}
