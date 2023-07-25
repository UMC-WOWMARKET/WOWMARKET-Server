package wowmarket.wow_server.mypage.myorder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.domain.Order;
import wowmarket.wow_server.mypage.myorder.dto.MyOrderFormListResponseDto;
import wowmarket.wow_server.mypage.myorder.dto.MyOrderFormResponseDto;
import wowmarket.wow_server.repository.OrderDetailRepository;
import wowmarket.wow_server.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class MyOrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Transactional(readOnly = true)
    public MyOrderFormListResponseDto findAllMyOrderForm(Pageable pageable){
        Page<Order> orders = orderRepository.findAll(pageable);
        Page<MyOrderFormResponseDto> orderformDtos = orders.map(MyOrderFormResponseDto::new);
        MyOrderFormListResponseDto responseDto = new MyOrderFormListResponseDto(orderformDtos.getContent(),
                orderformDtos.getTotalPages(), orderformDtos.getNumber());
        return responseDto;
    }
}
