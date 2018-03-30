package com.github.soonboylena.myflow.Auth.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*   @author lungern xiii.at.cn@gmail.com
*   @date 2018/2/2
*
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message<T> {

    private int code;

    private String description;

    private T data;

    public Message(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public Message(String description, T data) {
        code = 200;
        this.description = description;
        this.data = data;
    }
}
