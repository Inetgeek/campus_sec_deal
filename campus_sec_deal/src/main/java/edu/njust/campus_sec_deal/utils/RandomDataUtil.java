/**
 * @title: RandomDataUtil
 * @date: 2022/9/18 21:02
 * @projectName: campus_sec_deal
 * @author: Colyn
 * @builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


@Component
public class RandomDataUtil {

    public static String getIDByTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyMMddHHmmss");
        return format.format(date);
    }

    public static String getSendTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static LocalDate getDate() {
        return LocalDate.now();
    }

    public static LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }

    public static LocalTime getTime() {
        return LocalTime.now();
    }

    public static String getUUID32() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .toLowerCase();
    }

    public static String getUUID36() {
        return UUID.randomUUID().toString().toLowerCase();
    }

    public static String getVerCode() {
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str = "2zxcv7bnmlkjhg9fdsaqwe8rtyuiopQ6WERT0YUIOPAS4DFGH5JKL3ZXCVBNM1";
        //由Random生成随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //长度为几就循环几次
        for (int i = 0; i < 4; ++i) {
            //产生0-61的数字
            int number = random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }
}
