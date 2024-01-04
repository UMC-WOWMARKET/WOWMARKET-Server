package wowmarket.wow_server.mypage.myorder.demand.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.DemandOrder;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.mypage.myorder.demand.dto.MyOrderDemandFormDto;
import wowmarket.wow_server.mypage.myorder.demand.dto.MyOrderDemandFormListResponseDto;
import wowmarket.wow_server.repository.DemandDetailRepository;
import wowmarket.wow_server.repository.DemandOrderRepository;

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

}
