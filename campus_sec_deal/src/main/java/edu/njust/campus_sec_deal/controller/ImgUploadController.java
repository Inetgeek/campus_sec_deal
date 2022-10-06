/**
 * @title: ImgUploadController
 * @date: 2022/9/19 23:26
 * @projectName: campus_sec_deal
 * @author: Colyn
 * @builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import edu.njust.campus_sec_deal.entity.CampusUser;
import edu.njust.campus_sec_deal.mapper.CampusUserMapper;
import edu.njust.campus_sec_deal.utils.FileUploadUtil;
import edu.njust.campus_sec_deal.utils.JWTUtil;
import edu.njust.campus_sec_deal.utils.JsonResultUtil;
import edu.njust.campus_sec_deal.config.ImgTypeConf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import edu.njust.campus_sec_deal.config.ReturnCodeConf;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@PropertySource("classpath:application.properties")
@RequestMapping(value = "/api/v1")
public class ImgUploadController {

    @Value("${campus.file.upload-location}")
    private String path;

    @Value("${campus.file.upload-pattern}")
    private String pattern;

    @Autowired
    CampusUserMapper userMapper;


    /**
     * 图片上传接口
     *
     * @param file
     * @param type
     * @param request
     * @return img_url
     * @decribe: 前端上传图片须携带token，校验通过后才能上传。返回图片相对路径
     */
    @PostMapping("/upload")
    public JsonResultUtil<?> imgUpload(@RequestParam("file") MultipartFile file, @RequestParam("img_type") int type, HttpServletRequest request) throws IOException {

        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "userId");
        if (uid == null || uid.equals("")) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误");
        } else {

            //写入图片并获得图片url
            String img_url = FileUploadUtil.SaveFile(file, type, this.path, this.pattern);

            if (img_url != null) {

                /**
                 * 将图片写入相应的数据表中
                 */
                switch (type) {
                    case ImgTypeConf.AVATAR:
                        CampusUser User = new CampusUser().setImgUrl(img_url);
                        try {
                            userMapper.update(User, Wrappers.<CampusUser>lambdaUpdate().eq(CampusUser::getUserId, uid));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }

                //封装返回json的data键值对
                Map<String, String> data = new HashMap<>();
                data.put("imgUrl", img_url);
                return new JsonResultUtil<>(data);
            } else return new JsonResultUtil<>(ReturnCodeConf.OPT_FAIL, "图片上传失败");
        }
    }
}
