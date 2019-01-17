package com.pollra.http.files.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadVO {
    private int uploaded;
    private String fileName;
    private String url;
}