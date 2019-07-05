package org.neep.test.rpc.server;

/**
 * @Title App
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-21 下午7:37
 */
public class App {
    public  static  void main(String[] args){
        int n = 8;
        String str1 = Integer.toBinaryString(n);
        String str2 = convert2(n,32);
        System.out.println(str1.equals(str2));
    }

    public static String convert(int data){
        int n = data;
        String buffer = "";
        while (n !=0){
            int bit = n % 2;
            n =  n / 2;
            buffer = bit + buffer;
        }
        return buffer;
    }

    public static String convert2(int data,int length){
        //byte[] buffer = new byte[length];
        String str ="";
        boolean flag = false;
        for(int i = length-1;i >= 0; i--){
            int bit = data >> i;
            System.out.println(i+"=>"+bit);
            if (bit != 0){
                 flag = true;
            }
            if (flag){
                str = str + bit;
            }
        }

//        boolean flag = false;
//        for (int j =buffer.length-1;j >= 0;j--){
//           if (buffer[j] != 0 && !flag) {
//               flag = true;
//           }
//           if (flag){
//               str = str + buffer[j];
//           }
//        }
        return str;
    }
}
