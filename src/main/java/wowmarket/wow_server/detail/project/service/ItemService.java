// 주문폼 작성시 상품명&가격&상품 이미지 받아오는 api

package wowmarket.wow_server.detail.project.service;
import jakarta.persistence.criteria.Order;
import org.springframework.stereotype.Service;
import wowmarket.wow_server.detail.project.dto.ItemResponseDto;
import wowmarket.wow_server.detail.project.dto.OrderQuestionResponseDto;
import wowmarket.wow_server.detail.project.dto.OrderResponseDto;
import wowmarket.wow_server.domain.Item;
import wowmarket.wow_server.domain.OrderQuestion;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.repository.ItemRepository;
import wowmarket.wow_server.repository.OrderQuestionRepository;
import wowmarket.wow_server.repository.ProjectRepository;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ProjectRepository projectRepository;
    private final OrderQuestionRepository orderQuestionRepository;

    public ItemService(ProjectRepository projectRepository, ItemRepository itemRepository, OrderQuestionRepository orderQuestionRepository) {

        this.itemRepository = itemRepository;
        this.projectRepository = projectRepository;
        this.orderQuestionRepository = orderQuestionRepository;
    }

//    public List<ItemResponseDto> getItemInfo(Long project_id){
//        List<Item> itemList = itemRepository.findByProject_Id(project_id);
//        return itemList.stream()  // DB 에서 조회한 List -> stream 으로 변환
//                .map(ItemResponseDto::new)  // stream 처리를 통해, Board 객체 -> BoardResponseDto 로 변환
//                .toList();
//    }

    public OrderResponseDto getItemInfo(Long project_id){
        Project project = projectRepository.findByProject_Id(project_id);
        List<Item> itemList = itemRepository.findByProject_Id(project_id);
        List<ItemResponseDto> itemResponseDtoList =
                itemList.stream()  // DB 에서 조회한 List -> stream 으로 변환
                .map(ItemResponseDto::new)  // stream 처리를 통해, Board 객체 -> BoardResponseDto 로 변환
                .toList();
        List<OrderQuestion> orderQuestions = orderQuestionRepository.findByProject_Id(project_id);
        List<OrderQuestionResponseDto> orderQuestionResponseDtoList =
                orderQuestions.stream()
                .map(OrderQuestionResponseDto::new)
                .toList();
        return new OrderResponseDto(project, itemResponseDtoList, orderQuestionResponseDtoList);

    }


}