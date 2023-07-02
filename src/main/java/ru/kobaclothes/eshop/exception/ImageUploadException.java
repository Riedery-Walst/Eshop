package ru.kobaclothes.eshop.exception;

import java.io.IOException;

public class ImageUploadException extends Throwable {
    public ImageUploadException(String errorOccurredDuringImageUpload, IOException e) {
    }
}
