package com.feicheng.authority.common.enums;

/**
 * 自定义返回枚举类型
 * @author Lenovo
 */

public enum HttpEnum {

    /**
     * 状态码
     */

    成功(200),

    失败(500),

    参数错误(400)
    ;


    // 返回状态码
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    HttpEnum(Integer code) {
        this.code = code;
    }
}
