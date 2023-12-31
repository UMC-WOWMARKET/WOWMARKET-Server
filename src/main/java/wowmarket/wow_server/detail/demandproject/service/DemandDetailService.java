package wowmarket.wow_server.detail.demandproject.service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.demandproject.dto.DemandDetailRequestDto;
import wowmarket.wow_server.detail.demandproject.dto.DemandFormRequestDto;
import wowmarket.wow_server.domain.*;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.*;

import java.util.List;
import java.util.Objects;

@Service
public class DemandDetailService {
    private final DemandItemRepository itemRepository;
    private final UserRepository userRepository;
    private final DemandDetailRepository demandDetailRepository;
    private final DemandProjectRepository demandProjectRepository;

    private final DemandItemRepository demandItemRepository;
    private final DemandOrderRepository demandOrderRepository;


    public DemandDetailService(DemandDetailRepository demandDetailRepository, DemandItemRepository itemRepository, UserRepository userRepository, DemandProjectRepository demandProjectRepository, DemandItemRepository demandItemRepository, DemandOrderRepository demandOrderRepository) {
        this.itemRepository = itemRepository;
        this.demandDetailRepository = demandDetailRepository;
        this.userRepository = userRepository;
        this.demandProjectRepository = demandProjectRepository;
        this.demandItemRepository= demandItemRepository;
        this.demandOrderRepository=demandOrderRepository;
    }


    public ResponseEntity createDemandForm(Long demand_project_id, DemandFormRequestDto requestDto)
    {
        DemandProject demandProject = demandProjectRepository.findByDemandProject_Id(demand_project_id);
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        System.out.print("here");
        //* 사용자의 대학교가 수요조사 프로젝트의 대학교와 일치하지 않으면 -> 추후 수정될 조건 *
        if (!Objects.equals(user.getUniv(), demandProject.getUser().getUniv()))
        {
            //에러 반환
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        //이미 수요조사폼을 작성한 사용자의 경우, 작성 못하게하는 로직
        if (demandOrderRepository.existsByUser_Id(user.getId()))
        {
            return new ResponseEntity(HttpStatus.FORBIDDEN); //403: Forbidden

        }

        DemandOrder demandOrder = DemandOrder.builder()
                .user(user)
                .demandProject(demandProject)
                .build();
        demandOrderRepository.save(demandOrder);

        for (int i = 0; i < requestDto.getDemandDetailRequestDtoList().size(); i++) {
            DemandItem item = demandItemRepository.findDemandItemById(requestDto.getDemandDetailRequestDtoList().get(i).getDemandItemId());
            int count = requestDto.getDemandDetailRequestDtoList().get(i).getCount();
            DemandDetail demandDetail = DemandDetail.builder()
                    .demandOrder(demandOrder)
                    .demandItem(item)
                    .count(count)
                    .build();
            demandDetailRepository.save(demandDetail);
        }

        //참여인원 업데이트
        demandProjectRepository.updateParticipantNumber(demand_project_id);

        return new ResponseEntity(HttpStatus.OK);

    }
}