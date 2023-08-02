package wowmarket.wow_server.sale.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SaleDto {
    private String project_name;
    private String seller_name;
    private LocalDate start_date;
    private LocalDate end_date;
    //    private Blob img;
    private int achieved; //달성률 분자: 모든 주문상세의 '주문개수' 컬럼의 합
    private int goal; //달성률 분모: 상품 테이블에서 프로젝트 번호로 조회하여 해당 프로젝트에 들어있는 상품의 목표치의 합

    public SaleDto(Project project, int project_total_count, int project_total_goal) {
        this.project_name = project.getName();
        this.seller_name = project.getNickname();
        this.start_date = project.getStart_date();
        this.end_date = project.getEnd_date();
        this.achieved = project_total_count;
        this.goal = project_total_goal;
    }
}
