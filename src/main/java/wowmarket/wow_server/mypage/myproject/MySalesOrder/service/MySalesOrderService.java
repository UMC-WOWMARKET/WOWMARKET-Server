package wowmarket.wow_server.mypage.myproject.MySalesOrder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.OrderDetail;
import wowmarket.wow_server.domain.Orders;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.mypage.myproject.MySalesOrder.dto.*;
import wowmarket.wow_server.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MySalesOrderService {
    private final ProjectRepository projectRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public MySalesOrderListResponseDto findMySalesOrderForms(Pageable pageable, User user){
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        List<Project> projects = projectRepository.findByUser_Id(user.getId()); //해당 유저가 판매하는 모든 프로젝트 가져오기
        List<MySalesOrderDto> orderDtoList = new ArrayList<>();
        for(Project p : projects){
            List<Orders> orders = orderRepository.findByProject_Id(p.getId());
            List<MySalesOrderDto> orderDtos = orders.stream().map(MySalesOrderDto::new).collect(Collectors.toList());
            orderDtoList.addAll(orderDtos);
        }

        int start = Math.min(Math.max(pageable.getPageSize() * (pageable.getPageNumber()), 0), orderDtoList.size());
        int end = Math.min(start + pageable.getPageSize(), orderDtoList.size());
        Page<MySalesOrderDto> orderDtoPage = new PageImpl<>(orderDtoList.subList(start,end), pageable, orderDtoList.size());
        MySalesOrderListResponseDto responseDto = new MySalesOrderListResponseDto(orderDtoPage.getContent(), orderDtoPage.getTotalPages(), orderDtoPage.getNumber());
        return responseDto;
    }

    @Transactional
    public ResponseEntity updateMySalesOrderStatus(Long order_id, MySalesOrderStatusRequestDto requestDto){
        Orders orders = orderRepository.findById(order_id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        orders.setOrder_status(requestDto.getOrder_status());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    public MySalesOrderDetailResponseDto findMySalesOrderDetail(Long order_id){
        Orders orders = orderRepository.findById(order_id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrders_Id(order_id);
        String address = orders.getAddress();
        if (orders.getProject().getReceive_type() == "pickup")
            address = orders.getProject().getReceive_address();
        List<MySalesOrderDetailDto> orderDetailDtos = orderDetails.stream().map(MySalesOrderDetailDto::new).collect(Collectors.toList());
        MySalesOrderDetailResponseDto responseDto = new MySalesOrderDetailResponseDto(orderDetailDtos, orders, address);
        return responseDto;
    }

    @Transactional
    public ResponseEntity deleteMySalesOrder(Long order_id){
        Orders orders = orderRepository.findById(order_id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));;
        if (orders.isDel() == false)
            orders.setDel(true);
        return new ResponseEntity(HttpStatus.OK);
    }
}
