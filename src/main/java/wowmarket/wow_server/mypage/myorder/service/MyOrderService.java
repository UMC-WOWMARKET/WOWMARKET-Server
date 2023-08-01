package wowmarket.wow_server.mypage.myorder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.domain.OrderDetail;
import wowmarket.wow_server.domain.Orders;
import wowmarket.wow_server.mypage.myorder.dto.*;
import wowmarket.wow_server.repository.OrderDetailRepository;
import wowmarket.wow_server.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyOrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Transactional(readOnly = true)
    public MyOrderFormListResponseDto findAllMyOrderForm(Long user_id, Pageable pageable){
        Page<Orders> orders = orderRepository.findByUser_Id(user_id, pageable);
        Page<MyOrderFormResponseDto> orderformDtos = orders.map(MyOrderFormResponseDto::new);
        MyOrderFormListResponseDto responseDto = new MyOrderFormListResponseDto(orderformDtos.getContent(),
                orderformDtos.getTotalPages(), orderformDtos.getNumber());
        return responseDto;
    }

    @Transactional(readOnly = true)
    public MyOrderFormDetailResponseDto findMyOrderFormDetail(Long order_id){
        List<OrderDetail> ordersDetails = orderDetailRepository.findByOrders_Id(order_id);

        List<MyOrderFormDetailDto> orderFormDetailDtos = ordersDetails.stream().map(MyOrderFormDetailDto::new).collect(Collectors.toList());
        Orders orders = orderRepository.findById(order_id).get();
        MyOrderFormDetailResponseDto responseDto = new MyOrderFormDetailResponseDto(orderFormDetailDtos, orders.getReceiver(), orders.getAddress(),
        orders.getZipcode(), orders.getDelivery_msg(), orders.getPhone(), orders.getBank(), orders.getAccount(), orders.getDepositor(), orders.getDepositTime());
        return responseDto;
    }

    @Transactional
    public void updateMyOrderFormDetail(Long order_id, MyOrderFormDetailUpdateRequestDto requestDto){
        List<MyOrderFormItemListRequestDto> itemList = requestDto.getItemList();
        for(MyOrderFormItemListRequestDto i : itemList){
            OrderDetail orderDetail = orderDetailRepository.findByOrders_IdAndItem_Id(order_id, i.getItemId());
            orderDetail.updateOrderDetail(i);
        }
        Orders orders = orderRepository.findById(order_id).get();
        orders.updateOrderForm(requestDto);
    }

    @Transactional
    public void deleteMyOrderFormDetail(Long order_id){
        Orders orders = orderRepository.findById(order_id).get();
        if (orders.isDel() == false)
            orders.setDel(true);
    }
}
