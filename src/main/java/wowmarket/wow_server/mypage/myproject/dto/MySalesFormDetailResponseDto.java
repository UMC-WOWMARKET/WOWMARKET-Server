package wowmarket.wow_server.mypage.myproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MySalesFormDetailResponseDto {
    private Long projectid;
    private String projectname;
    private String description;
//    private String mainimg;
    private List<MySalesItemDto> itemList;
    private LocalDate startdate;
    private LocalDate enddate;
    private String receive_type;
    private String bank;
    private String account;
    private String email;
    private String phone;
    private String category;

    public MySalesFormDetailResponseDto(Project project, List<MySalesItemDto> itemDtos){
        this.projectid = project.getId();
        this.projectname = project.getName();
        this.description = project.getDescription();
        this.itemList = itemDtos;
        this.startdate = project.getStart_date();
        this.enddate = project.getEnd_date();
        this.receive_type = project.getReceive_type();
        this.bank = project.getBank();
        this.account = project.getAccount();
        this.email = project.getInquired_email();
        this.phone = project.getInquired_phone();
        this.category = project.getCategory().getName();
    }
}
