package com.acl.utils;

import java.util.Random;

/**
 * Created by 65766 on 2018/1/28.
 */
public class PasswordUtil {

    private static final String words[] = {
            "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M",
            "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };
    private static final String nums[] = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };

    public static String generatPassword() {

        final Random random = new Random(System.currentTimeMillis());
        int passwordLength = random.nextInt(3) + 8;
        StringBuffer sb = new StringBuffer(passwordLength + 16);
        boolean flag = true;
        for (int i = 0; i < passwordLength; i++) {
            if (flag) {
                sb.append(words[random.nextInt(words.length)]);
            } else {
                sb.append(nums[random.nextInt(nums.length)]);
                flag = !flag;
            }
        }
        return sb.toString();
    }


}
