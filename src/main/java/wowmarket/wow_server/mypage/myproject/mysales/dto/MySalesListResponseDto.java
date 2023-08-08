package wowmarket.wow_server.mypage.myproject.mysales.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.mypage.myproject.dto.MySalesFormDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class MySalesListResponseDto {
    private List<MySalesFormDto> myprojectList;
    private int totalpage;
    private int currentpage;

    public MySalesListResponseDto(List<MySalesFormDto> newDtos, int totalpage, int currentpage){
        this.myprojectList = newDtos;
        this.totalpage = totalpage;
        this.currentpage = currentpage;
    }

}
