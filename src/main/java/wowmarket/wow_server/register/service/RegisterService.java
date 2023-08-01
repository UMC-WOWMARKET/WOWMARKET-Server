package wowmarket.wow_server.register.service;
import org.springframework.web.multipart.MultipartFile;
import wowmarket.wow_server.domain.Category;
import wowmarket.wow_server.register.dto.RegisterDemandProjectDto;
import wowmarket.wow_server.register.dto.RegisterProjectDto;

import java.util.List;

public interface RegisterService {
    public Long registerProject(RegisterProjectDto requestDto, List<String> uploaded) throws Exception;
    public List<Category> findCategories();

    public Long registerDemand(RegisterDemandProjectDto requestDto, List<String> uploaded) throws Exception;
}
