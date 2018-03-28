package com.acl.common;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    private  String subject;
    private  String message;
    private Set<String> Receivers;

}
