package com.higa.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class ReqBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String cert_nasc_base64;
}
