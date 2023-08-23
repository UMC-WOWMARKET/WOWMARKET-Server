package wowmarket.wow_server.mypage.myorder.service;

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
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.mypage.myorder.dto.*;
import wowmarket.wow_server.repository.OrderDetailRepository;
import wowmarket.wow_server.repository.OrderRepository;
import wowmarket.wow_server.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyOrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public MyOrderFormListResponseDto findAllMyOrderForm(Pageable pageable, User user){
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Page<Orders> orders = orderRepository.findByUser_Id(user.getId(), pageable);
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
        String address = orders.getAddress();
        if (orders.getProject().getReceive_type().equals("pickup"))
            address = orders.getProject().getReceive_address();
        MyOrderFormDetailResponseDto responseDto = new MyOrderFormDetailResponseDto(orderFormDetailDtos, orders, address);
        return responseDto;
    }

    @Transactional
    public ResponseEntity deleteMyOrderFormDetail(Long order_id){
        Orders orders = orderRepository.findById(order_id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        if (orders.isDel() == false)
            orders.setDel(true);
        return new ResponseEntity(HttpStatus.OK);
    }
}
