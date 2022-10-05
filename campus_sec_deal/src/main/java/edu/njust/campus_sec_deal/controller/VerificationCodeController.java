/**
 * @title: VerificationCodeController
 * @date: 2022/9/18 20:21
 * @projectName: campus_sec_deal
 * @author: Colyn
 * @builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.controller;

import edu.njust.campus_sec_deal.utils.JsonResultUtil;
import edu.njust.campus_sec_deal.utils.RandomDataUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController()
@RequestMapping(value = "/api/v1", produces = "application/json;charset=UTF-8")
public class VerificationCodeController {


    /**
     * 验证码接口
     *
     * @return ver_code
     */
    @GetMapping("/ver_code")
    public JsonResultUtil<?> getVerCode() {

        HashMap<String, String> data = new HashMap<>();

        // 业务逻辑代码
        data.put("ver_code", RandomDataUtil.getVerCode());
        return new JsonResultUtil<>(data);
    }
}
