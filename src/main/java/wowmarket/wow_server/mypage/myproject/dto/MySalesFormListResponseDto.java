package wowmarket.wow_server.mypage.myproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MySalesFormListResponseDto {
    private List<MySalesFormDto> myprojectList;
    private int totalpage;
    private int currentpage;

    public MySalesFormListResponseDto(List<MySalesFormDto> newDtos, int totalpage, int currentpage){
        this.myprojectList = newDtos;
        this.totalpage = totalpage;
        this.currentpage = currentpage;
    }

}
