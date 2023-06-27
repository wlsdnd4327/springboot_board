package org.koreait.exceptions.lecture;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

import java.util.ResourceBundle;

public class LectureNotFoundException extends CommonException {

    public static String message;
    static {
        ResourceBundle bundle = ResourceBundle.getBundle("messages.validation");
        message = bundle.getString("Validation.lecture.NotFound");
    }

    public LectureNotFoundException(){
        super(message, HttpStatus.BAD_REQUEST);
    }


 }
