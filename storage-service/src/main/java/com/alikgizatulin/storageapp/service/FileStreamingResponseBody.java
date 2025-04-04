package com.alikgizatulin.storageapp.service;

import com.alikgizatulin.storageapp.dto.FileResponse;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


public interface FileStreamingResponseBody extends StreamingResponseBody {
    FileResponse getFile();
}
