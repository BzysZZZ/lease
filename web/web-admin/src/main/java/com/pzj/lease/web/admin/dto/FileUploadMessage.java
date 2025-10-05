package com.pzj.lease.web.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadMessage {
    private String tackID;
    private String fileName;
    private Long fileSize;
    private String contentType;
    private String fileContent;
}
