package com.fastcam.programming.dmaker.exception;

import lombok.Getter;


/* Custom Exception 클래스 */
@Getter
public class DMakerException extends RuntimeException{
    private DMakerErrorCode dMakerErrorCode;
    private String detailMessage;

    public DMakerException(DMakerErrorCode errorCode){
        super(errorCode.getMessage());
        this.dMakerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public DMakerException(DMakerErrorCode errorCode, String detailMessage){
        super(errorCode.getMessage());
        this.dMakerErrorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
