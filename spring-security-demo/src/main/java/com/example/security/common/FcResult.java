package com.example.security.common;

import com.example.security.define.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;


/**
 * 返回结果对象
 * @author yfc
 */
@Data
public class FcResult<T> implements Serializable {

    private static final long serialVersionUID = 8094069767564096505L;


    /** 返回状态码 */
    private String code;

    /** 结果信息 */
    private String msg;

    /** 数据 */
    private T data;

    /**
     * 构造函数
     */
    public FcResult() {
        this.code = ResultCodeEnum.CODE_SUCCESS.getCode();
    }

    /**
     * 构造函数
     * @param code			状态码
     * @param msg			信息
     * @param data			数据
     */
    public FcResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public FcResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static FcResult success(String msg, Object data){
        return new FcResult(ResultCodeEnum.CODE_SUCCESS.getCode(), msg, data);
    }

    public static FcResult success(Object data){
        return success("", data);
    }

    public static FcResult success(){
        return success(null);
    }

    public static FcResult fail(String msg){
        return new FcResult(ResultCodeEnum.CODE_FAIL.getCode(), msg, null);
    }

    public static FcResult error(String msg){
        return new FcResult(ResultCodeEnum.CODE_ERROR.getCode(), msg, null);
    }



}
