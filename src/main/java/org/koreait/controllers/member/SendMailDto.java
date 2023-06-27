package org.koreait.controllers.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor @NoArgsConstructor
public class SendMailDto {
    private String title;
    private String address;
    private String message;
}
