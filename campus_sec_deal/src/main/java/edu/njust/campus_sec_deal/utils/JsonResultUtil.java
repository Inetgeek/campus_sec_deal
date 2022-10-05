/**
 * @title: JsonResultUtil
 * @date: 2022/9/19 12:08
 * @projectName: campus_sec_deal
 * @author: Colyn
 * @builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.utils;

import lombok.Data;
import org.springframework.stereotype.Component;


@Component
@Data
public class JsonResultUtil<T> {
        private T data;
        private int code;
        private String msg;

        /**
         * 若没有数据返回，默认状态码为 200，提示信息为“success”
         */
        public JsonResultUtil() {
            this.code = 200;
            this.msg = "success";
        }

        /**
         * 若没有数据返回，可以人为指定状态码和提示信息
         * @param code
         * @param msg
         */
        public JsonResultUtil(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        /**
         * 有数据返回时，状态码为 200，默认提示信息为“success”
         * @param data
         */
        public JsonResultUtil(T data) {
            this.data = data;
            this.code = 200;
            this.msg = "success";
        }

        /**
         * 有数据返回，状态码为 200，人为指定提示信息
         * @param data
         * @param msg
         */
        public JsonResultUtil(T data, String msg) {
            this.data = data;
            this.code = 200;
            this.msg = msg;
        }
}
