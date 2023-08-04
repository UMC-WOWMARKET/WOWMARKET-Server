package wowmarket.wow_server.mypage.myproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyDemandOrderResponseDto {
    private List<MyDemandOrderDto> demandList;
    private int currentpage;
    private int totalpage;

    public MyDemandOrderResponseDto(List<MyDemandOrderDto> newDto, int totalpage, int currentpage){
        this.demandList = newDto;
        this.totalpage = totalpage;
        this.currentpage = currentpage;
    }
}
