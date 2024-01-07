package wowmarket.wow_server.mypage.myorder.sales.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.OrderDetail;
import wowmarket.wow_server.domain.Orders;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.mypage.myorder.sales.dto.MyOrderSalesDetailItemDto;
import wowmarket.wow_server.mypage.myorder.sales.dto.MyOrderSalesDetailResponseDto;
import wowmarket.wow_server.mypage.myorder.sales.dto.MyOrderFormListResponseDto;
import wowmarket.wow_server.mypage.myorder.sales.dto.MyOrderSalesResponseDto;
import wowmarket.wow_server.repository.OrderDetailRepository;
import wowmarket.wow_server.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyOrderSalesService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Transactional(readOnly = true)
    public MyOrderFormListResponseDto findAllMyOrderForm(Pageable pageable, User user){
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Page<Orders> orders = orderRepository.findByUser_Id(user.getId(), pageable);
        Page<MyOrderSalesResponseDto> orderformDtos = orders.map(MyOrderSalesResponseDto::new);
        MyOrderFormListResponseDto responseDto = new MyOrderFormListResponseDto(orderformDtos.getContent(),
                orderformDtos.getTotalPages(), orderformDtos.getNumber());
        return responseDto;
    }

    @Transactional(readOnly = true)
    public MyOrderSalesDetailResponseDto findMyOrderFormDetail(Long order_id, User user){
        List<OrderDetail> ordersDetails = orderDetailRepository.findByOrders_Id(order_id);
        List<MyOrderSalesDetailItemDto> orderFormDetailDtos = ordersDetails.stream().map(MyOrderSalesDetailItemDto::new).collect(Collectors.toList());
        Orders orders = orderRepository.findById(order_id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        if (user == null || user.getId() != orders.getUser().getId()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String address = orders.getAddress();
        if (orders.getProject().getReceive_type().equals("pickup"))
            address = orders.getProject().getReceive_address();
        MyOrderSalesDetailResponseDto responseDto = new MyOrderSalesDetailResponseDto(orderFormDetailDtos, orders, address);
        return responseDto;
    }

    @Transactional
    public ResponseEntity deleteMyOrderFormDetail(Long order_id){
        Orders orders = orderRepository.findById(order_id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        if (orders.getIsDel() == 0)
            orders.setIsDel(1);
        return new ResponseEntity(HttpStatus.OK);
    }
}
