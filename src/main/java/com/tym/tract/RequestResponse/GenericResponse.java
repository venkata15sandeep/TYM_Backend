package com.tym.tract.RequestResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericResponse {
    public String message;
    public String status;
    public Object data;
}
