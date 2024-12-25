package com.tym.tract.RequestResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequest {
    String phNumber;
    String name;
    String email;
    String password;
    String amount;
}
