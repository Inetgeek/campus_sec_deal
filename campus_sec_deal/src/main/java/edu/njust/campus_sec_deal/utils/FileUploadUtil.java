/**
 * @Class: FileUploadUtil
 * @Date: 2022/9/20
 * @Project: demo
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
public class FileUploadUtil {

    public static String SaveFile(MultipartFile file, int type, String path, String pattern) throws IOException {

        if (!file.isEmpty()) {
            //获取文件名
            String fileName = file.getOriginalFilename();
            //获取文件后缀名。也可以在这里添加判断语句，规定特定格式的图片才能上传，否则拒绝保存。
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //支持的图片文件类型
            List<String> strings = Arrays.asList(".webp", ".png", ".jpg", ".jpeg", ".PNG", ".JPG", ".JPEG", ".WEBP");
            if (strings.contains(suffixName)) {
                //利用36位UUID重命名文件
                fileName = (RandomDataUtil.getUUID36()) + suffixName;
                System.out.println("fileName: " + fileName);
                System.out.println("Path: " + path + "imgs" + pattern + type + pattern + fileName);
                //文件转存到本地文件目录
                try {
                    file.transferTo(new File(path + "imgs" + pattern + type + pattern + fileName));
                    return "/imgs/" + type + "/" + fileName;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } else {
            return null;
        }
        return null;
    }
}
