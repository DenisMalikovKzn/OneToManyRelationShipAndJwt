package com.praveen.cms.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageListResponse {
    List<MessageDetailResponse> messageResponseList;
    CommonPaginationResponse commonPaginationResponse;

    public MessageListResponse(List<MessageDetailResponse> messageResponseList) {
        this.messageResponseList = messageResponseList;
    }
}
