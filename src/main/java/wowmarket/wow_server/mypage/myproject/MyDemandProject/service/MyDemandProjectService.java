package wowmarket.wow_server.mypage.myproject.MyDemandProject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.DemandItem;
import wowmarket.wow_server.domain.DemandProject;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandDetailResponseDto;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandDto;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandItemDto;
import wowmarket.wow_server.mypage.myproject.MyDemandProject.dto.MyDemandResponseDto;
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
    private final UserRepository userRepository;

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

}
