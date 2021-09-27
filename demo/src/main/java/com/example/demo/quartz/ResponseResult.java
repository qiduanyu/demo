package com.example.demo.quartz;

public class ResponseResult {
    private int code;
    private String info;
    private Object result;

    public ResponseResult() {
    }

    public ResponseResult(int code, String info, Object result) {
        this.code = code;
        this.info = info;
        this.result = result;
    }

    public static ResponseResult success(String info, Object result){
        return new ResponseResult(200,info, result );
    }

    public static ResponseResult error(int code, String info){
        return new ResponseResult(code, info, null );
    }

    public static ResponseResult error(int code, String info, Object result){
        return new ResponseResult(code, info, result );
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
