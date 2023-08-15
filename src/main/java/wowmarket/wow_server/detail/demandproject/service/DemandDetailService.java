package wowmarket.wow_server.detail.demandproject.service;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.demandproject.dto.DemandDetailRequestDto;
import wowmarket.wow_server.domain.*;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.*;

import java.util.List;

@Service
public class DemandDetailService {
    private final DemandItemRepository itemRepository;
    private final UserRepository userRepository;
    private final DemandDetailRepository demandDetailRepository;


    public DemandDetailService(DemandDetailRepository demandDetailRepository, DemandItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.demandDetailRepository = demandDetailRepository;
        this.userRepository = userRepository;
    }


    public DemandDetailRequestDto createDemandForm(Long demand_project_id, List<DemandDetailRequestDto> requestDto)
    {
        for (int i = 0; i < requestDto.size(); i++) {
            User user = userRepository.findByEmail(SecurityUtil.getLoginUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            //List<DemandItem> demandItem = itemRepository.findDemandItemByDemandProject_Id(demand_project_id);
            DemandItem demandItem = itemRepository.findDemandItemById(requestDto.get(i).getDemandItemId()); //프론트에서 demand_item_id 받아오기
            int count = requestDto.get(i).getCount();

            DemandDetail demandDetail = DemandDetail.builder()
                    .user(user) //User user
                    .demandItem(demandItem) //DemandItem demandItem
                    .count(count) //int Count
                    .build();
            demandDetailRepository.save(demandDetail);
        }

        return null;
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