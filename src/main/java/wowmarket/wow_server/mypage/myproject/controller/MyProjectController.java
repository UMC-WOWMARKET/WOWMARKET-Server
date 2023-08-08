package wowmarket.wow_server.mypage.myproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wowmarket.wow_server.mypage.myproject.dto.*;
import wowmarket.wow_server.mypage.myproject.service.MyProjectService;

@RestController
@RequestMapping("/wowmarket/mypage/myproject")
@RequiredArgsConstructor
public class MyProjectController {
    private final MyProjectService myProjectService;

    @GetMapping("/order")
    public MySalesOrderListResponseDto getMySalesOrderForms(@RequestParam(value = "page", defaultValue = "1", required = false)int page){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return myProjectService.findMySalesOrderForms(pageable);
    }

    @PutMapping("/{user_id}/myproject/order/{order_id}")
    public ResponseEntity updateMySalesOrderFormStatus(@PathVariable Long user_id, @PathVariable Long order_id, @RequestBody MySalesOrderStatusRequestDto requestDto){
        myProjectService.updateMySalesOrderStatus(order_id, requestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{user_id}/myproject/order/detail/{order_id}")
    public MySalesOrderDetailResponseDto getMySalesOrderDetail(@PathVariable Long user_id, @PathVariable Long order_id){
        return myProjectService.findMySalesOrderDetail(order_id);
    }

    @DeleteMapping("/{user_id}/myproject/order/detail/{order_id}")
    public ResponseEntity deleteMySalesOrderForm(@PathVariable Long user_id, @PathVariable Long order_id){
        myProjectService.deleteMySalesOrder(order_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{user_id}/myproject/demand")
    public MyDemandResponseDto getMyDemandList(@PathVariable Long user_id, @RequestParam(value = "page", defaultValue = "1", required = false)int page){
        Pageable pageable = PageRequest.of(page - 1, 10);
        return myProjectService.findAllMyDemandForm(user_id, pageable);
    }

    @PutMapping("/{user_id}/myproject/demand/{demand_project_id}")
    public ResponseEntity updateMyDemandFormStatus(@PathVariable Long user_id, @PathVariable Long demand_project_id){
        myProjectService.updateMyDemandFormStatus(demand_project_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{user_id}/myproject/demand/detail/{demand_project_id}")
    public MyDemandDetailResponseDto getMyDemandDetailForm(@PathVariable Long user_id, @PathVariable Long demand_project_id){
        return myProjectService.findMyDemandFormDetail(demand_project_id);
    }

}
