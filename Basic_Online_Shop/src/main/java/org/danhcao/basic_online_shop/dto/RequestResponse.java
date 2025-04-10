package org.danhcao.basic_online_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danhcao.basic_online_shop.entity.Banner;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestResponse {
    private final String status = "success";
    private String timestamp;
    private String message;
    private Object data;

    public RequestResponse(Object data) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = "";
        this.data = data;
    }

    public RequestResponse(String message) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = message;
        this.data = null;
    }

    public RequestResponse(String message, Object data) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = message;
        this.data = data;
    }

}