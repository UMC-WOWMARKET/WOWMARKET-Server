// 참여폼 작성시 상품명&가격&상품 이미지 받아오는 api

package wowmarket.wow_server.detail.demandproject.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.demandproject.dto.DemandItemResponseDto;
import wowmarket.wow_server.domain.*;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DemandItemService {
    private final DemandItemRepository itemRepository;

    public DemandItemService(DemandItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<DemandItemResponseDto> getDemandItemInfo(Long demand_project_id){
        List<DemandItem> itemList = itemRepository.findDemandItemByDemandProject_Id(demand_project_id);
        return itemList.stream()  // DB 에서 조회한 List -> stream 으로 변환
                .map(DemandItemResponseDto::new)  // stream 처리를 통해, Board 객체 -> BoardResponseDto 로 변환
                .toList();
    }


}