package wowmarket.wow_server.global;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AbstractAmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wowmarket.wow_server.global.config.S3Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;


@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String getPreSignedURL(String dirName) {
        String preSignedURL = "";
        String fileName = dirName + "/" + UUID.randomUUID().toString();

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2; //2분
        expiration.setTime(expTimeMillis);


        try {
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucket, fileName)
                            .withMethod(HttpMethod.PUT) //메서드가 PUT인 Presigned URL을 생성
                            .withExpiration(expiration); //URL의 만료 시간을 설정

            generatePresignedUrlRequest.addRequestParameter(
                    Headers.S3_CANNED_ACL,
                    CannedAccessControlList.PublicRead.toString()); //생성된 URL에 대한 공개 읽기 권한을 설정

            //Amazon S3 클라이언트 객체를 사용하여 Presigned URL을 생성하는 메서드
            URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);

            preSignedURL = url.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return preSignedURL;
    }


    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public List<String> upload(List<MultipartFile> multipartFileList, String dirName) throws IOException {
        List<String> uploadUrl = new ArrayList<>();
        for (MultipartFile multipartFile: multipartFileList) {
            if(multipartFile != null){
                File uploadFile = convert(multipartFile)
                        .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File convert fail"));

                uploadUrl.add(upload(uploadFile, dirName));
            }
        }
        return uploadUrl;

    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);    // s3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // 로컬에 파일 생성
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    // S3에 파일업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        log.info("File Upload : " + fileName);
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 생성한 파일 삭제
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    public void delete(String fileName) {
        log.info("File Delete : " + fileName);
        amazonS3Client.deleteObject(bucket, fileName);
    }
}
