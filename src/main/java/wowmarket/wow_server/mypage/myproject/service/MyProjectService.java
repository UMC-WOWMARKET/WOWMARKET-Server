package wowmarket.wow_server.mypage.myproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.*;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.mypage.myproject.dto.*;
import wowmarket.wow_server.repository.*;

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
    private final DemandProjectRepository demandProjectRepository;
    private final DemandItemRepository demandItemRepository;
    private final UserRepository userRepository;

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

    @Transactional(readOnly = true)
    public MyDemandResponseDto findAllMyDemandForm(Long seller_id, Pageable pageable){
        Page<DemandProject> demandProjects = demandProjectRepository.findDemandProjectByUser_Id(seller_id, pageable);
        Page<MyDemandDto> demandOrderDtos = demandProjects.map(MyDemandDto::new);
        MyDemandResponseDto responseDto = new MyDemandResponseDto(demandOrderDtos.getContent(), demandOrderDtos.getTotalPages(), demandOrderDtos.getNumber() + 1);
        return responseDto;
    }

    @Transactional
    public void updateMyDemandFormStatus(Long demand_project_id){
        DemandProject demandProject = demandProjectRepository.findById(demand_project_id).get();
        if (demandProject.is_end() == false)
            demandProject.set_end(true);
    }

    @Transactional(readOnly = true)
    public MyDemandDetailResponseDto findMyDemandFormDetail(Long demand_project_id){
        DemandProject project = demandProjectRepository.findById(demand_project_id).get();
        List<DemandItem> demandItems = demandItemRepository.findDemandItemByDemandProject_Id(demand_project_id);
        List<MyDemandItemDto> itemList = demandItems.stream().map(MyDemandItemDto::new).collect(Collectors.toList());
        MyDemandDetailResponseDto responseDto = new MyDemandDetailResponseDto(itemList, project);
        return responseDto;
    }

}
