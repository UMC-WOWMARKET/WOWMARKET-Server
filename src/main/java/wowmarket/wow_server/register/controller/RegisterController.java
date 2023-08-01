package wowmarket.wow_server.register.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public Long registerProject(@Valid @RequestPart(value = "dto") RegisterProjectDto request,
                   @RequestPart(value = "files") List<MultipartFile> files) throws Exception {
        List<String> uploaded = awsS3Uploader.upload(files, "project");
        Long project_id = registerService.registerProject(request, uploaded);
        return project_id;
    }

    @GetMapping(value = {"/project", "/demand"})
    @ResponseStatus(HttpStatus.OK)
    public List<Category> sendCategories(){
        return registerService.findCategories();
    }

//    @PostMapping("/demand")
//    @ResponseStatus(HttpStatus.OK)
//    public Long registerDemand(@Valid @RequestBody RegisterDemandProjectDto request) throws Exception {
//        return registerService.registerDemand(request);
//    }

    @PostMapping("/demand")
    @ResponseStatus(HttpStatus.OK)
    public Long registerDemand(@Valid @RequestPart(value = "dto") RegisterDemandProjectDto request,
                                @RequestPart(value = "files") List<MultipartFile> files) throws Exception {
        List<String> uploaded = awsS3Uploader.upload(files, "demand");
        Long demand_project_id = registerService.registerDemand(request, uploaded);
        return demand_project_id;
    }



}
