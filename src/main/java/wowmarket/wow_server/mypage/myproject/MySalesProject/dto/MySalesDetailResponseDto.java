package wowmarket.wow_server.mypage.myproject.MySalesProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class MySalesDetailResponseDto {
    private Long projectId;
    private String projectname;
    private String description;
    private String thumbnail;
    private String category;
    private String image1;
    private String image2;
    private String image3;
    private List<MySalesItemDto> itemList;
    private LocalDate startdate;
    private LocalDate enddate;
    private String receive_type;
    private String seller_bank;
    private String seller_account;
    private String seller_account_name;
    private String seller_nickname;

    public MySalesDetailResponseDto(Project project, List<MySalesItemDto> itemDtos){
        this.projectId = project.getId();
        this.projectname = project.getName();
        this.description = project.getDescription();
        this.thumbnail = project.getThumbnail();
        this.image1 = project.getImage1();
        this.image2 = project.getImage2();
        this.image3 = project.getImage3();
        this.category = project.getCategory().getName();
        this.itemList = itemDtos;
        this.startdate = project.getStartDate();
        this.enddate = project.getEndDate();
        this.receive_type = project.getReceive_type();
        this.seller_bank = project.getBank();
        this.seller_account = project.getAccount();
        this.seller_account_name = project.getAccount_holder_name();
        this.seller_nickname = project.getNickname();
    }
}
