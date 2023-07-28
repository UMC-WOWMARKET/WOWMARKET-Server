package wowmarket.wow_server.register.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.domain.Category;
import wowmarket.wow_server.register.dto.RegisterDemandProjectDto;
import wowmarket.wow_server.register.dto.RegisterProjectDto;
import wowmarket.wow_server.register.service.RegisterService;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/wowmarket/register")
@RestController
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/project")
    @ResponseStatus(HttpStatus.OK)
    public Long registerProject(@Valid @RequestBody RegisterProjectDto request) throws Exception {
        return registerService.registerProject(request);
    }

    @GetMapping(value = {"/project", "/demand"})
    @ResponseStatus(HttpStatus.OK)
    public List<Category> sendCategories(){
        return registerService.findCategories();
    }

    @PostMapping("/demand")
    @ResponseStatus(HttpStatus.OK)
    public Long registerDemand(@Valid @RequestBody RegisterDemandProjectDto request) throws Exception {
        return registerService.registerDemand(request);
    }



}
