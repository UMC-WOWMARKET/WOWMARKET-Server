package wowmarket.wow_server.register.service;
import wowmarket.wow_server.domain.Category;
import wowmarket.wow_server.register.dto.RegisterDemandProjectDto;
import wowmarket.wow_server.register.dto.RegisterProjectDto;

import java.util.List;

public interface RegisterService {
    public Long registerProject(RegisterProjectDto requestDto) throws Exception;
    public List<Category> findCategories();

    public Long registerDemand(RegisterDemandProjectDto requestDto) throws Exception;
}
