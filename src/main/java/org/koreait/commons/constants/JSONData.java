package org.koreait.commons.constants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class JSONData<T> {

    private boolean success;    //성공 여부

    private HttpStatus status = HttpStatus.OK;  //상태코드(기본값 200)

    private String message; //메세지

    private T data; //성공 데이터
}
