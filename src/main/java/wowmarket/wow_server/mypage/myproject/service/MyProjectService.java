package wowmarket.wow_server.mypage.myproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.domain.Item;
import wowmarket.wow_server.domain.OrderDetail;
import wowmarket.wow_server.domain.Orders;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.mypage.myproject.dto.*;
import wowmarket.wow_server.repository.ItemRepository;
import wowmarket.wow_server.repository.OrderDetailRepository;
import wowmarket.wow_server.repository.OrderRepository;
import wowmarket.wow_server.repository.ProjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyProjectService {

    private final ProjectRepository projectRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Transactional(readOnly = true)
    public MySalesFormListResponseDto findAllMySalesForm(Long user_id, Pageable pageable){
        Page<Project> projects = projectRepository.findByUser_Id(user_id, pageable);
        Page<MySalesFormDto> mySalesFormDtos = projects.map(MySalesFormDto::new);
        MySalesFormListResponseDto responseDto = new MySalesFormListResponseDto(mySalesFormDtos.getContent(),
                mySalesFormDtos.getTotalPages(), mySalesFormDtos.getNumber());
        return responseDto;
    }

    @Transactional
    public void finishMySalesForm(Long project_id){
        Project project = projectRepository.findById(project_id).get();
        if (project.is_end() == false)
            project.set_end(true);
    }

    @Transactional(readOnly = true)
    public MySalesFormDetailResponseDto findMySalesDetail(Long project_id){
        Project project = projectRepository.findById(project_id).get();
        List<Item> itemList = itemRepository.findByProject_Id(project_id);
        List<MySalesItemDto> itemDtos = itemList.stream().map(MySalesItemDto::new).collect(Collectors.toList());

        MySalesFormDetailResponseDto responseDto = new MySalesFormDetailResponseDto(project, itemDtos);
        return responseDto;
    }

    @Transactional(readOnly = true)
    public MySalesOrderListResponseDto findMySalesOrderForms(Long user_id, Pageable pageable){
        List<Project> projects = projectRepository.findByUser_Id(user_id); //해당 유저가 판매하는 모든 프로젝트 가져오기
        List<MySalesOrderDto> orderDtoList = new ArrayList<>();
        for(Project p : projects){
            List<Orders> orders = orderRepository.findByProject_Id(p.getId());
            List<MySalesOrderDto> orderDtos = orders.stream().map(MySalesOrderDto::new).collect(Collectors.toList());
            orderDtoList.addAll(orderDtos);
        }

        int start = Math.min(Math.max(pageable.getPageSize() * (pageable.getPageNumber()), 0), orderDtoList.size());
        int end = Math.min(start + pageable.getPageSize(), orderDtoList.size());
        Page<MySalesOrderDto> orderDtoPage = new PageImpl<>(orderDtoList.subList(start,end), pageable, orderDtoList.size());
        MySalesOrderListResponseDto responseDto = new MySalesOrderListResponseDto(orderDtoPage.getContent(), orderDtoPage.getTotalPages(), orderDtoPage.getNumber() + 1);
        return responseDto;
    }

    @Transactional
    public void updateMySalesOrderStatus(Long order_id, MySalesOrderStatusRequestDto requestDto){
        Orders orders = orderRepository.findById(order_id).get();
        orders.setOrder_status(requestDto.getOrder_status());
    }

    @Transactional
    public MySalesOrderDetailResponseDto findMySalesOrderDetail(Long order_id){
        Orders orders = orderRepository.findById(order_id).get();
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrders_Id(order_id);
        List<MySalesOrderDetailDto> orderDetailDtos = orderDetails.stream().map(MySalesOrderDetailDto::new).collect(Collectors.toList());
        MySalesOrderDetailResponseDto responseDto = new MySalesOrderDetailResponseDto(orderDetailDtos, orders);
        return responseDto;
    }

    @Transactional
    public void deleteMySalesOrder(Long order_id){
        Orders orders = orderRepository.findById(order_id).get();
        if (orders.isDel() == false)
            orders.setDel(true);
    }

}
