package wowmarket.wow_server.mypage.myinfo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetLikeProjectDto {
    private int totalPage;
    private int currentPage;
    private List<LikedProjectDto> projectList;

    @Builder
    public GetLikeProjectDto(int totalPage, int currentPage, List<LikedProjectDto> projectList) {
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.projectList = projectList;
    }
}
