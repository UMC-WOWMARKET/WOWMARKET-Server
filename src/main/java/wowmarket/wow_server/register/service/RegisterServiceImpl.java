package wowmarket.wow_server.register.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.*;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.register.dto.RegisterDemandProjectDto;
import wowmarket.wow_server.register.dto.RegisterProjectDto;
import wowmarket.wow_server.repository.*;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class RegisterServiceImpl implements RegisterService{

    private final ProjectRepository projectRepository;
    private final ItemRepository itemRepository;
    private final DemandProjectRepository demandProjectRepository;
    private final DemandItemRepository demandItemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    public Long registerProject(RegisterProjectDto requestDto, List<String> uploaded) throws Exception {
        Project project = requestDto.toEntity();

        //category 연관관계 설정
        project.setCategory(categoryRepository.findById(requestDto.getCategory_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        //user 연관관계 설정
        project.setUser(userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        //이미지 컬럼에 저장
        project.setImage(uploaded);

        projectRepository.save(project);

        for (int i = 0; i < requestDto.getItem().size(); i++) {
            Item item = requestDto.getItem().get(i).toItemEntity();
            item.setProject(project);
            itemRepository.save(item);
        }
        return project.getId();
    }
    public List<Category> findCategories(){
        return categoryRepository.findAll();
    }

    @Override
    public Long registerDemand(RegisterDemandProjectDto requestDto, List<String> uploaded) throws Exception {
        DemandProject demandProject = requestDto.toEntity();
        //category 연관관계 설정
        demandProject.setCategory(categoryRepository.findById(requestDto.getCategory_id())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        //user 연관관계 설정
        demandProject.setUser(userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST)));

        demandProject.setImage(uploaded);
        demandProjectRepository.save(demandProject);

        for (int i = 0; i < requestDto.getItem().size(); i++) {
            DemandItem demandItem = requestDto.getItem().get(i).toDemandItemEntity();
            demandItem.setDemandProject(demandProject);
            demandItemRepository.save(demandItem);
        }

        return demandProject.getId();
    }
}