package wowmarket.wow_server.detail.project.notice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoticePageResponseDto {
    private List<NoticeResponseDto> noticeLists;
    private Long user_id;
    private Long seller_id;

    public NoticePageResponseDto(List<NoticeResponseDto> newDtos, Long user_id, Long seller_id){
        this.noticeLists = newDtos;
        this.user_id = user_id;
        this.seller_id = seller_id;
    }

    public NoticePageResponseDto(List<NoticeResponseDto> newDtos){
        this.noticeLists = newDtos;
    }
}
