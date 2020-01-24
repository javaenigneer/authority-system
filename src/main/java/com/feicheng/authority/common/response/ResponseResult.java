package com.feicheng.authority.common.response;

import com.feicheng.authority.common.enums.HttpEnum;
import lombok.Data;

import java.util.List;

/**
 * 数据回显Result
 * @author Lenovo
 */
@Data
public class ResponseResult<T> {

    private Integer code;

    private String msg;

    private List<T> data;

    private Long count;


    /**
     * 带数据的返回结果
     * @param code
     * @param msg
     * @param data
     * @param count
     */
    public ResponseResult(Integer code, String msg, List<T> data, Long count) {

        this.code = code;

        this.msg = msg;

        this.data = data;

        this.count = count;

    }

    /**
     * 不带数据的返回结果
     * @param code
     * @param msg
     */
    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
