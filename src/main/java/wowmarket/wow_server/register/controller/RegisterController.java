package wowmarket.wow_server.register.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wowmarket.wow_server.domain.Category;
import wowmarket.wow_server.global.S3Uploader;
import wowmarket.wow_server.register.dto.RegisterDemandProjectDto;
import wowmarket.wow_server.register.dto.RegisterProjectDto;
import wowmarket.wow_server.register.service.RegisterService;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/wowmarket/register")
@RestController
public class RegisterController {

    private final RegisterService registerService;
    private final S3Uploader awsS3Uploader;

    @PostMapping("/project")
    public ResponseEntity registerProject(@Valid @RequestBody RegisterProjectDto request) throws Exception {
        registerService.registerProject(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/demand")
    public ResponseEntity registerDemand(@Valid @RequestBody RegisterDemandProjectDto request) throws Exception {
        registerService.registerDemand(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * client에 presinged url 반환하여 이미지 직접 put 가능
     * @param dirname 버킷 내 폴더이름 - project, demand
     * @return presigned url
     */
    @GetMapping("/image")
    public ResponseEntity RegisterProject(String dirname){
        return new ResponseEntity(awsS3Uploader.getPreSignedURL(dirname), HttpStatus.OK);
    }

    @GetMapping(value = {"/project", "/demand"})
    public ResponseEntity sendCategories(){
        return new ResponseEntity<>(registerService.findCategories(), HttpStatus.OK);
    }




//    @PostMapping("/project")
//    @ResponseStatus(HttpStatus.OK)
//    public Long registerProject(@Valid @RequestPart(value = "dto") RegisterProjectDto request,
//                   @RequestPart(value = "files") List<MultipartFile> files) throws Exception {
//        List<String> uploaded = awsS3Uploader.upload(files, "project");
//        Long project_id = registerService.registerProject(request, uploaded);
//        return project_id;
//    }

//    @PostMapping("/demand")
//    @ResponseStatus(HttpStatus.OK)
//    public Long registerDemand(@Valid @RequestPart(value = "dto") RegisterDemandProjectDto request,
//                                @RequestPart(value = "files") List<MultipartFile> files) throws Exception {
//        List<String> uploaded = awsS3Uploader.upload(files, "demand");
//        Long demand_project_id = registerService.registerDemand(request, uploaded);
//        return demand_project_id;
//    }



}
