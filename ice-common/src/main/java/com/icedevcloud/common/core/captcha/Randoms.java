package com.icedevcloud.common.core.captcha;

import java.util.Random;

/**
 * 随机数工具类
 *
 * @author XiaoBingBy
 * @date 2018-12-18 22:24
 * @since 1.0
 */
public class Randoms {
    protected static final Random RANDOM = new Random();
    // 定义验证码字符.去除了O和I等容易混淆的字母
    public static final char ALPHA[] = {'2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'G', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    protected static final int NUM_MAX_INDEX = 8;  // 数字的最大索引，不包括最大值
    protected static final int CHAR_MIN_INDEX = NUM_MAX_INDEX;  // 字符的最小索引，包括最小值
    protected static final int CHAR_MAX_INDEX = ALPHA.length;  // 字符的最大索引，不包括最大值
    protected static final int UPPER_MIN_INDEX = CHAR_MIN_INDEX;  // 大写字符最小索引
    protected static final int UPPER_MAX_INDEX = UPPER_MIN_INDEX + 23;  // 大写字符最大索引
    protected static final int LOWER_MIN_INDEX = UPPER_MAX_INDEX;  // 小写字母最小索引
    protected static final int LOWER_MAX_INDEX = CHAR_MAX_INDEX;  // 小写字母最大索引

    /**
     * 产生两个数之间的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static int num(int min, int max) {
        return min + RANDOM.nextInt(max - min);
    }

    /**
     * 产生0-num的随机数,不包括num
     *
     * @param num 最大值
     * @return 随机数
     */
    public static int num(int num) {
        return RANDOM.nextInt(num);
    }

    /**
     * 返回ALPHA中的随机字符
     *
     * @return 随机字符
     */
    public static char alpha() {
        return ALPHA[num(ALPHA.length)];
    }

    /**
     * 返回ALPHA中第0位到第num位的随机字符
     *
     * @param num 到第几位结束
     * @return 随机字符
     */
    public static char alpha(int num) {
        return ALPHA[num(num)];
    }

    /**
     * 返回ALPHA中第min位到第max位的随机字符
     *
     * @param min 从第几位开始
     * @param max 到第几位结束
     * @return 随机字符
     */
    public static char alpha(int min, int max) {
        return ALPHA[num(min, max)];
    }
}
