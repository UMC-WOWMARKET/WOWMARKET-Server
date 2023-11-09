package wowmarket.wow_server.register.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.*;
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
    private final CategoryRepository categoryRepository;


    public Long registerProject(RegisterProjectDto requestDto, User user) throws Exception {
        Project project = requestDto.toEntity();

        //category 연관관계 설정
        project.setCategory(categoryRepository.findById(requestDto.getCategory_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "일치하는 카테고리가 없습니다")));
        //user 연관관계 설정
        if(user != null) project.setUser(user);
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다"); //유저 없을 시 400 에러 반환

        // 소속 학생만 구매 가능하도록 설정했지만 판매자의 학교 인증이 안 됐을 경우
        if(!project.isSellToAll() && !user.isUniv_check())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "학교 인증이 확인되지 않았습니다.");

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
        if(user != null) demandProject.setUser(user);
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다"); //유저 없을 시 400 에러 반환

        demandProjectRepository.save(demandProject);

        for (int i = 0; i < requestDto.getItem().size(); i++) {
            DemandItem demandItem = requestDto.getItem().get(i).toDemandItemEntity();
            demandItem.setDemandProject(demandProject);
            demandItemRepository.save(demandItem);
        }

        return demandProject.getId();
    }
}
