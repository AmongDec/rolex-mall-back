package com.rolex.mall.util;

import java.util.Random;
/**
 * @Author tanghh
 * 验证码生成器
 * @Date 2019/12/30 16:07
 */

public class CheckSumBuilder {
    /**
     * @param length 字符串长度
     * @param type   类型 (0: 仅数字; 2:仅字符; 别的数字:数字和字符)
     * @return String  随机串
     * @desc ：1.随机产生字符串
     */
    public static String getRandomStr(int length, int type) {
        String str = "";
        int beginChar = 'a';
        int endChar = 'z';

        // 只有数字
        if (type == 0) {
            beginChar = 'z' + 1;
            endChar = 'z' + 10;
        }
        // 只有小写字母
        else if (type == 2) {
            beginChar = 'a';
            endChar = 'z';
        }
        // 有数字和字母
        else {
            beginChar = 'a';
            endChar = 'z' + 10;
        }

        // 生成随机类
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int tmp = (beginChar + random.nextInt(endChar - beginChar));
            // 大于'z'的是数字
            if (tmp > 'z') {
                tmp = '0' + (tmp - 'z');
            }
            str += (char) tmp;
        }

        return str;
    }

    /**
     * @return String  6位数字验证码
     * @desc ：2.获取6位数字验证码
     */
    public static String getCheckSum() {
        return getRandomStr(6, 0);
    }

}
