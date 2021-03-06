package com.praveen.cms.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerSignInRequest {
    private String email;
    private String password;
}
