package org.lynn.designPattern.strategy.dto;

import lombok.Data;

@Data
public class PayResult<T> {

    private String status;
    private String msg;
    private T data;

}
