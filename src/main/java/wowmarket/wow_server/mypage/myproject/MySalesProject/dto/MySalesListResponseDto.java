package wowmarket.wow_server.mypage.myproject.MySalesProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MySalesListResponseDto {
    private List<MySalesFormDto> list;
    private int totalPage;
    private int currentPage;

    public MySalesListResponseDto(List<MySalesFormDto> newDtos, int totalpage, int currentpage){
        this.list = newDtos;
        this.totalPage = totalpage;
        this.currentPage = currentpage;
    }

}
