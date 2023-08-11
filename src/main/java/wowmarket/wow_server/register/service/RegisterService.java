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
public class RegisterService {

    private final ProjectRepository projectRepository;
    private final ItemRepository itemRepository;
    private final DemandProjectRepository demandProjectRepository;
    private final DemandItemRepository demandItemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    public Long registerProject(RegisterProjectDto requestDto, User user) throws Exception {
        Project project = requestDto.toEntity();

        //category 연관관계 설정
        project.setCategory(categoryRepository.findById(requestDto.getCategory_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "일치하는 카테고리가 없습니다")));
        //user 연관관계 설정
//        project.setUser(userRepository.findByEmail(SecurityUtil.getLoginUsername())
//                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다")));
        project.setUser(user);

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


    public Long registerDemand(RegisterDemandProjectDto requestDto, User user) throws Exception {
        DemandProject demandProject = requestDto.toEntity();

        //category 연관관계 설정
        demandProject.setCategory(categoryRepository.findById(requestDto.getCategory_id())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "일치하는 카테고리가 없습니다")));
        //user 연관관계 설정
        demandProject.setUser(userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다")));

        demandProjectRepository.save(demandProject);

        for (int i = 0; i < requestDto.getItem().size(); i++) {
            DemandItem demandItem = requestDto.getItem().get(i).toDemandItemEntity();
            demandItem.setDemandProject(demandProject);
            demandItemRepository.save(demandItem);
        }

        return demandProject.getId();
    }
}
