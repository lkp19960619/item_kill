package com.program.kill.server.utils;
import org.joda.time.DateTime;

import	java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;


/**
 *  随机数生成util
 * @Author: 李开鹏
 * @Date: 2020/5/2 9:38
 * @Version 1.0
 */
public class RandomUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    public static String generateOrderCode(){
        //时间戳+N位随机数生成订单编号
        return dateFormat.format(DateTime.now().toDate())+ generateNumber(4);
    }

    public static String generateNumber(final int num){
        StringBuffer sb = new StringBuffer();
        for(int i = 1; i < num; i++){
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 1; i < 10000; i++){
            System.out.println(generateOrderCode());
        }
    }
}
