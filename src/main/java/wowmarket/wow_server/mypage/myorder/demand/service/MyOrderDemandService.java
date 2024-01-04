package wowmarket.wow_server.mypage.myorder.demand.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.DemandDetail;
import wowmarket.wow_server.domain.DemandOrder;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.mypage.myorder.demand.dto.MyOrderDemandDetailResponseDto;
import wowmarket.wow_server.mypage.myorder.demand.dto.MyOrderDemandFormDto;
import wowmarket.wow_server.mypage.myorder.demand.dto.MyOrderDemandFormListResponseDto;
import wowmarket.wow_server.mypage.myorder.demand.dto.MyOrderDemandItemDto;
import wowmarket.wow_server.repository.DemandDetailRepository;
import wowmarket.wow_server.repository.DemandOrderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyOrderDemandService {
    private final DemandOrderRepository demandOrderRepository;
    private final DemandDetailRepository demandDetailRepository;

    @Transactional(readOnly = true)
    public MyOrderDemandFormListResponseDto findMyAllDemandOrderForm(Pageable pageable, User user){
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Page<DemandOrder> demandOrders = demandOrderRepository.findByUser_Id(user.getId(), pageable);
        Page<MyOrderDemandFormDto> demandOrderFormDtos = demandOrders.map(MyOrderDemandFormDto::new);
        MyOrderDemandFormListResponseDto responseDto = new MyOrderDemandFormListResponseDto(demandOrderFormDtos.getContent(),
                demandOrderFormDtos.getTotalPages(), demandOrderFormDtos.getNumber());
        return responseDto;
    }

    @Transactional(readOnly = true)
    public MyOrderDemandDetailResponseDto findMyOrderDemandDetail(Long demand_order_id){
        List<DemandDetail> demandDetails = demandDetailRepository.findByDemandOrder_Id(demand_order_id);
        List<MyOrderDemandItemDto> orderDemandItemDtos = demandDetails.stream().map(MyOrderDemandItemDto::new).collect(Collectors.toList());
        DemandOrder demandOrder = demandOrderRepository.findById(demand_order_id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 demand order id 입니다."));

        MyOrderDemandDetailResponseDto responseDto = new MyOrderDemandDetailResponseDto(orderDemandItemDtos, demandOrder);
        return responseDto;
    }

}
