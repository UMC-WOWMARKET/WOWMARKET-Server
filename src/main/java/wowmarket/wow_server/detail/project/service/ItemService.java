// 주문폼 작성시 상품명&가격&상품 이미지 받아오는 api

package wowmarket.wow_server.detail.project.service;
import org.springframework.stereotype.Service;
import wowmarket.wow_server.detail.project.dto.ItemResponseDto;
import wowmarket.wow_server.domain.Item;
import wowmarket.wow_server.repository.ItemRepository;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemResponseDto> getItemInfo(Long project_id){
        List<Item> itemList = itemRepository.findByProject_Id(project_id);
        return itemList.stream()  // DB 에서 조회한 List -> stream 으로 변환
                .map(ItemResponseDto::new)  // stream 처리를 통해, Board 객체 -> BoardResponseDto 로 변환
                .toList();
    }


}