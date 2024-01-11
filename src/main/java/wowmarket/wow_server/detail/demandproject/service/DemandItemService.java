// 참여폼 작성시 상품명&가격&상품 이미지 받아오는 api

package wowmarket.wow_server.detail.demandproject.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.detail.demandproject.dto.DemandItemResponseDto;
import wowmarket.wow_server.detail.demandproject.dto.DemandQuestionResponseDto;
import wowmarket.wow_server.detail.demandproject.dto.DemandResponseDto;
import wowmarket.wow_server.detail.project.dto.ItemResponseDto;
import wowmarket.wow_server.detail.project.dto.OrderQuestionResponseDto;
import wowmarket.wow_server.detail.project.dto.OrderResponseDto;
import wowmarket.wow_server.domain.*;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DemandItemService {
    private final DemandItemRepository itemRepository;
    private final DemandProjectRepository demandProjectRepository;
    private final DemandQuestionRepository demandQuestionRepository;

    public DemandItemService(DemandItemRepository itemRepository, DemandProjectRepository demandProjectRepository, DemandQuestionRepository demandQuestionRepository) {
        this.itemRepository = itemRepository;
        this.demandProjectRepository = demandProjectRepository;
        this.demandQuestionRepository = demandQuestionRepository;
    }

    public DemandResponseDto getDemandItemInfo(Long demand_project_id){
        List<DemandItem> itemList = itemRepository.findDemandItemByDemandProject_Id(demand_project_id);
        List<DemandItemResponseDto> itemResponseDtoList =
                itemList.stream()
                        .map(DemandItemResponseDto::new)
                        .toList();

        List<DemandQuestion> demandQuestions = demandQuestionRepository.findByDemandProject_Id(demand_project_id);
        List<DemandQuestionResponseDto> demandQuestionResponseDtoList =
                demandQuestions.stream()
                        .map(DemandQuestionResponseDto::new)
                        .toList();
        return new DemandResponseDto(itemResponseDtoList, demandQuestionResponseDtoList);

    }

}