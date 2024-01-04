package wowmarket.wow_server.sale.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SaleDto {
    private Long projectId;
    private String sellerName;
    private String projectName;
    private LocalDateTime endDate;
    private String thumbnail;
    private int achieved; //달성률 분자: 모든 주문상세의 '주문개수' 컬럼의 합
    private int goal; //달성률 분모: 상품 테이블에서 프로젝트 번호로 조회하여 해당 프로젝트에 들어있는 상품의 목표치의 합

    public SaleDto(Project project, int projectTotalCount, int projectTotalGoal) {
        this.projectId = project.getId();
        this.sellerName = project.getSellerName();
        this.projectName = project.getProjectName();
        this.endDate = project.getEndDate();
        this.thumbnail = project.getThumbnail();
        this.achieved = projectTotalCount;
        this.goal = projectTotalGoal;
    }
}
