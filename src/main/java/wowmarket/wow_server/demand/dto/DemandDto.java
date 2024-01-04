package wowmarket.wow_server.demand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.DemandProject;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DemandDto {
    private Long projectId;
    private String sellerName;
    private String projectName;
    private LocalDateTime endDate;
    private String thumbnail;
    private int achieved; //달성률 분자: 모든 주문상세의 '주문개수' 컬럼의 합
    private int goal; //달성률 분모: 상품 테이블에서 프로젝트 번호로 조회하여 해당 프로젝트에 들어있는 상품의 목표치의 합

    public DemandDto(DemandProject demandProject, int demandProjectTotalCount, int demandProjectTotalGoal) {
        this.projectId = demandProject.getId();
        this.sellerName = demandProject.getSellerName();
        this.projectName = demandProject.getProjectName();
        this.endDate = demandProject.getEndDate();
        this.thumbnail = demandProject.getThumbnail();
        this.achieved = demandProjectTotalCount;
        this.goal = demandProjectTotalGoal;
    }
}
