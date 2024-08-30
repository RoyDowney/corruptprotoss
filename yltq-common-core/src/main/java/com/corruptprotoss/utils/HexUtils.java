package com.corruptprotoss.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RoyDowney
 * @date 2024/7/26
 */
public class HexUtils {
    // 将十六进制字符串转换为整数
    public static int hex2int(String hex) {
        int len = hex.length();
        int[] a = new int[len];
        int code;
        for (int i = 0; i < len; i++) {
            char c = hex.charAt(i);
            if (c >= '0' && c <= '9') {
                code = c - '0';
            } else if (c >= 'A' && c <= 'F') {
                code = c - 'A' + 10;
            } else if (c >= 'a' && c <= 'f') {
                code = c - 'a' + 10;
            } else {
                throw new IllegalArgumentException("Invalid hex character: " + c);
            }
            a[i] = code;
        }

        int result = 0;
        for (int num : a) {
            result = 16 * result + num;
        }

        return result; // 注意：这里返回的是int，如果hex过长可能溢出
    }

    // 反转十六进制字符串（每两个字符一组）
    public static String reverseHex(String hex) {
        StringBuilder result = new StringBuilder();
        for (int i = hex.length() - 2; i >= 0; i -= 2) {
            result.append(hex.substring(i, i + 2));
        }
        return result.toString();
    }

    // 测试函数，将字节数组转换为十六进制字符串，然后反转并转换为BigInteger
    public static void test(byte[] receive) {
        StringBuilder reversedData = new StringBuilder();
        for (int i = receive.length - 1; i >= 0; i--) {
            String hv = Integer.toHexString(receive[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                reversedData.append('0');
            }
            reversedData.append(hv);
        }

        if (!reversedData.toString().isEmpty()) {
            try {
                BigInteger bigint = new BigInteger("0x" + reversedData.toString(), 16);
                System.out.println("转换后的值: " + bigint.toString());
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("card string buffer is empty");
        }
    }

    /**
     * 检查并打印通电的端子。
     * @param value 从MCN单片机读取的值
     */
    public static List<Integer> checkPins(int value) {
        List<Integer> terminalNumberList = new ArrayList<>();
        for (int i = 1; i <= 32; i++) {
            if ((value & (1 << (i - 1))) != 0) {
                terminalNumberList.add(i);
                System.out.println("端子 " + i + " 通电");
            }
        }
        return terminalNumberList;
    }

    private static String binaryToHex(String binaryString) {
        BigInteger bi = new BigInteger(binaryString, 2); // 2表示二进制
        return bi.toString(16); // 16表示十六进制
    }

    public static int strToHex(String returnValue) {
        return (int) Long.parseLong(returnValue, 16);
    }

    public static String hexToBinary(String hex) {
        StringBuilder binary = new StringBuilder();
        for (char c : hex.toCharArray()) {
            binary.append(Integer.toBinaryString(Character.digit(c, 16)));
        }
        return binary.toString();
    }

    public static String decimalToHex(int decimal) {
        return Integer.toHexString(decimal).toUpperCase();
    }
}
