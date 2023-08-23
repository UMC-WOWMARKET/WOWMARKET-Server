package wowmarket.wow_server.detail.demandproject.service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.demandproject.dto.DemandDetailRequestDto;
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


    public DemandDetailService(DemandDetailRepository demandDetailRepository, DemandItemRepository itemRepository, UserRepository userRepository, DemandProjectRepository demandProjectRepository, DemandItemRepository demandItemRepository) {
        this.itemRepository = itemRepository;
        this.demandDetailRepository = demandDetailRepository;
        this.userRepository = userRepository;
        this.demandProjectRepository = demandProjectRepository;
        this.demandItemRepository= demandItemRepository;
    }


    public ResponseEntity createDemandForm(Long demand_project_id, List<DemandDetailRequestDto> requestDto)
    {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        DemandProject demandProject = demandProjectRepository.findByDemandProject_Id(demand_project_id);

        //사용자의 대학교가 수요조사 프로젝트의 대학교와 일치하지 않으면
        if (!Objects.equals(user.getUniv(), demandProject.getUser().getUniv()))
        {
            //에러 반환
            return new ResponseEntity(HttpStatus.BAD_REQUEST); //400
        }


        List<DemandItem> demandItems= demandItemRepository.findDemandItemByDemandProject_Id(demand_project_id);
        //이미 수요조사폼을 작성한 사용자의 경우
        for (int i = 0; i < demandItems.size(); i++) {
            if (demandDetailRepository.existsByUserIdAndDemandItemId(user.getId(), demandItems.get(i).getId()))
            {
                return new ResponseEntity(HttpStatus.FORBIDDEN); //403: Forbidden
            }
        }

        for (int i = 0; i < requestDto.size(); i++) {
            DemandItem demandItem = itemRepository.findDemandItemById(requestDto.get(i).getDemandItemId()); //프론트에서 demand_item_id 받아오기
            int count = requestDto.get(i).getCount();

            DemandDetail demandDetail = DemandDetail.builder()
                    .user(user) //User user
                    .demandItem(demandItem) //DemandItem demandItem
                    .count(count) //int Count
                    .build();
            demandDetailRepository.save(demandDetail);
        }

        //참여인원 업데이트
        demandProjectRepository.updateParticipantNumber(demand_project_id);

        return new ResponseEntity(HttpStatus.OK);
    }

//    public DemandDetailRequestDto createDemandForm2(Long demand_project_id, DemandDetailListRequestDto requestDto)
//    {
//        for (int i = 0; i < requestDto.getDemandDetailRequestDtoList().size(); i++) {
//            User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
//            //List<DemandItem> demandItem = itemRepository.findDemandItemByDemandProject_Id(demand_project_id);
//            DemandItem demandItem = itemRepository.findDemandItemById(requestDto.getDemandDetailRequestDtoList().get(i).getDemandItemId()); //프론트에서 demand_item_id 받아오기
//            int count = requestDto.getDemandDetailRequestDtoList().get(i).getCount();
//
//            DemandDetail demandDetail = DemandDetail.builder()
//                    .user(user) //User user
//                    .demandItem(demandItem) //DemandItem demandItem
//                    .count(count) //int Count
//                    .build();
//            demandDetailRepository.save(demandDetail);
//        }
//
//        return null;
//    }
}