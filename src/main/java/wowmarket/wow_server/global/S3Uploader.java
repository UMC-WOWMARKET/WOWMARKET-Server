package wowmarket.wow_server.global;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

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