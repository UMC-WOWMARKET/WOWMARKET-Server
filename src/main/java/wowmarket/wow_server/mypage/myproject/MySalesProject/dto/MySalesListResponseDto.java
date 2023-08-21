package wowmarket.wow_server.mypage.myproject.MySalesProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MySalesListResponseDto {
    private List<MySalesFormDto> list;
    private int totalpage;
    private int currentpage;

    public MySalesListResponseDto(List<MySalesFormDto> newDtos, int totalpage, int currentpage){
        this.list = newDtos;
        this.totalpage = totalpage;
        this.currentpage = currentpage;
    }

}
