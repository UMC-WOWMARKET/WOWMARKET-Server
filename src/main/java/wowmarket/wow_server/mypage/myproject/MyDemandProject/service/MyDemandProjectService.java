package wowmarket.wow_server.mypage.myproject.MyDemandProject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.*;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.*;
import wowmarket.wow_server.repository.CategoryRepository;
import wowmarket.wow_server.repository.DemandItemRepository;
import wowmarket.wow_server.repository.DemandProjectRepository;
import wowmarket.wow_server.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyDemandProjectService {
    private final DemandProjectRepository demandProjectRepository;
    private final DemandItemRepository demandItemRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public MyDemandResponseDto findAllMyDemandForm(Pageable pageable, User user){
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Page<DemandProject> demandProjects = demandProjectRepository.findDemandProjectByUser_Id(user.getId(), pageable);
        Page<MyDemandDto> demandOrderDtos = demandProjects.map(MyDemandDto::new);
        MyDemandResponseDto responseDto = new MyDemandResponseDto(demandOrderDtos.getContent(), demandOrderDtos.getTotalPages(), demandOrderDtos.getNumber());
        return responseDto;
    }

    @Transactional
    public ResponseEntity updateMyDemandFormStatus(Long demand_project_id){
        DemandProject demandProject = demandProjectRepository.findById(demand_project_id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        if (demandProject.isEnd() == false)
            demandProject.setEnd(true);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public MyDemandDetailResponseDto findMyDemandFormDetail(Long demand_project_id, User user){
        DemandProject project = demandProjectRepository.findById(demand_project_id).get();
        if (user == null || project.getUser().getId() != user.getId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        List<DemandItem> demandItems = demandItemRepository.findDemandItemByDemandProject_Id(demand_project_id);
        List<MyDemandItemDto> itemList = demandItems.stream().map(MyDemandItemDto::new).collect(Collectors.toList());
        MyDemandDetailResponseDto responseDto = new MyDemandDetailResponseDto(itemList, project);
        return responseDto;
    }

    @Transactional
    public ResponseEntity modifyMyDemandProject(Long projectId, MyDemandProjectModifyRequestDto requestDto, User user){
        DemandProject demandProject = demandProjectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("해당 demand project id가 없습니다."));
        if (user == null || user.getId() != demandProject.getUser().getId()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("해당 category id가 없습니다."));
        demandProject.modify(requestDto, category);

        for(int i=0;i<requestDto.getItemList().size();i++){
            MyDemandItemDto itemDto = requestDto.getItemList().get(i);
            DemandItem demandItem = demandItemRepository.findDemandItemById(itemDto.getItemId());
            demandItem.modify(itemDto);
            demandItemRepository.save(demandItem);
        }

        demandProjectRepository.save(demandProject);
        return new ResponseEntity(HttpStatus.OK);

    }

}
