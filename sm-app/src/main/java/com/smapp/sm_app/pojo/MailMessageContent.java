package com.smapp.sm_app.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailMessageContent {
    private String username;
    private String email;
    private String activationToken;
}
