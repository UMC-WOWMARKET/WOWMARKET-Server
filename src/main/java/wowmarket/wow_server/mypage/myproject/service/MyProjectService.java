package wowmarket.wow_server.mypage.myproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.*;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.mypage.myproject.dto.*;
import wowmarket.wow_server.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyProjectService {

    private final DemandProjectRepository demandProjectRepository;
    private final DemandItemRepository demandItemRepository;

  
    public MyDemandResponseDto findAllMyDemandForm(Long seller_id, Pageable pageable){
        Page<DemandProject> demandProjects = demandProjectRepository.findDemandProjectByUser_Id(seller_id, pageable);
        Page<MyDemandDto> demandOrderDtos = demandProjects.map(MyDemandDto::new);
        MyDemandResponseDto responseDto = new MyDemandResponseDto(demandOrderDtos.getContent(), demandOrderDtos.getTotalPages(), demandOrderDtos.getNumber() + 1);
        return responseDto;
    }

    @Transactional
    public void updateMyDemandFormStatus(Long demand_project_id){
        DemandProject demandProject = demandProjectRepository.findById(demand_project_id).get();
        if (demandProject.isEnd() == false)
            demandProject.setEnd(true);
    }

    @Transactional(readOnly = true)
    public MyDemandDetailResponseDto findMyDemandFormDetail(Long demand_project_id){
        DemandProject project = demandProjectRepository.findById(demand_project_id).get();
        List<DemandItem> demandItems = demandItemRepository.findDemandItemByDemandProject_Id(demand_project_id);
        List<MyDemandItemDto> itemList = demandItems.stream().map(MyDemandItemDto::new).collect(Collectors.toList());
        MyDemandDetailResponseDto responseDto = new MyDemandDetailResponseDto(itemList, project);
        return responseDto;
    }

}
