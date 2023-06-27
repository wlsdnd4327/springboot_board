package org.koreait.commons.constants;

public enum Regex {
    LOWER_CASE("[a-z]+"),
    UPPER_CASE("[A-Z]+"),
    IGNORE_CASE("[a-zA-Z]+"),
    NUM("[\\d]+"),
    SPECIAL("[~`!@#$%\\^&*\\(\\)\\-_+=]+");

    private String value;

    Regex(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
