package com.alikgizatulin.storageapp.service.impl;

import com.alikgizatulin.storageapp.dto.FileResponse;
import com.alikgizatulin.storageapp.exception.StorageException;
import com.alikgizatulin.storageapp.service.FileStreamingResponseBody;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import lombok.AllArgsConstructor;

import java.io.InputStream;
import java.io.OutputStream;

@AllArgsConstructor
public class MinioFileStreamingResponseBody implements FileStreamingResponseBody {
    private final MinioClient minioClient;

    private final String bucket;

    private final String path;

    private final FileResponse file;
    private static final int BUFFER_SIZE = 8192;

    @Override
    public FileResponse getFile() {
        return this.file;
    }

    @Override
    public void writeTo(OutputStream outputStream) {
        try(InputStream inputStream = this.minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(path)
                        .build())
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                outputStream.flush();
            }
        } catch (Exception e) {
            throw new StorageException(
                    String.format("Error getting object from bucket =%s at path =%s, message=%s",
                            bucket, path, e.getMessage()));
        }
    }

}
