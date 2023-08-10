package wowmarket.wow_server.mypage.myproject.MySalesProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class MySalesDetailResponseDto {
    private Long projectid;
    private String projectname;
    private String description;
//    private String mainimg;
    private String category;
    private List<MySalesItemDto> itemList;
    private LocalDate startdate;
    private LocalDate enddate;
    private String receive_type;
    private String seller_bank;
    private String seller_account;
    private String seller_account_name;
    private String seller_nickname;

    public MySalesDetailResponseDto(Project project, List<MySalesItemDto> itemDtos){
        this.projectid = project.getId();
        this.projectname = project.getName();
        this.description = project.getDescription();
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