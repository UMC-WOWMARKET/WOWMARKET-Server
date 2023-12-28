package wowmarket.wow_server.demand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandProject;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DemandDto {
    private Long project_id;
    private String project_name;
    private String seller_name;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String thumbnail;
    private int achieved; //달성률 분자: 모든 주문상세의 '주문개수' 컬럼의 합
    private int goal; //달성률 분모: 상품 테이블에서 프로젝트 번호로 조회하여 해당 프로젝트에 들어있는 상품의 목표치의 합

    public DemandDto(DemandProject demandProject, int demandProject_total_count, int demandProject_total_goal) {
        this.project_id = demandProject.getId();
        this.project_name = demandProject.getProjectName();
        this.seller_name = demandProject.getSellerName();
        this.start_date = demandProject.getStartDate();
        this.end_date = demandProject.getEndDate();
        this.thumbnail = demandProject.getThumbnail();
        this.achieved = demandProject_total_count;
        this.goal = demandProject_total_goal;
    }
}
