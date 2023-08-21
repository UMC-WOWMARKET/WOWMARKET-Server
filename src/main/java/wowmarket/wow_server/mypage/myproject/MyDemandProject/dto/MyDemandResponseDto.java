package wowmarket.wow_server.mypage.myproject.MyDemandProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyDemandResponseDto {
    private List<MyDemandDto> list;
    private int currentpage;
    private int totalpage;

    public MyDemandResponseDto(List<MyDemandDto> newDto, int totalpage, int currentpage){
        this.list = newDto;
        this.totalpage = totalpage;
        this.currentpage = currentpage;
    }
}
