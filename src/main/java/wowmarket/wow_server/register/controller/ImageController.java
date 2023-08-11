package wowmarket.wow_server.register.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wowmarket.wow_server.global.S3Uploader;

@RequiredArgsConstructor
@RequestMapping("/wowmarket")
@RestController
public class ImageController {
    private final S3Uploader awsS3Uploader;

    @GetMapping("/image")
    @ResponseStatus(HttpStatus.OK)
    public String RegisterProject(String dirname){
        return awsS3Uploader.getPreSignedURL(dirname);
    }
}
