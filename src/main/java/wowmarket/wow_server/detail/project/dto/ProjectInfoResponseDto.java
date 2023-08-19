package wowmarket.wow_server.detail.project.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.Project;

import java.time.LocalDate;

@Getter
public class ProjectInfoResponseDto {
    private String thumbnail; //대표이미지
    private String category; //카테고리
    private String name; //프로젝트 이름
    private String univ; //판매자의 대학교
    private String nickname; //프로젝트 담당자명
    private String description; //프로젝트 설명
    private LocalDate start_date; //시작날짜
    private LocalDate end_date; //종료날짜
    private int participant_number; //참여인원
    private int achieved; //달성률 분자: 모든 주문상세의 '주문개수' 컬럼의 합
    private int goal; //달성률 분모: 상품 테이블에서 프로젝트 번호로 조회하여 해당 프로젝트에 들어있는 상품의 목표치의 합


    public ProjectInfoResponseDto(Project project, int achieved, int goal) {
        this.thumbnail = project.getThumbnail(); //대표이미지
        this.category = project.getCategory().getName(); //카테고리
        this.name = project.getName(); //프로젝트 이름
        this.univ = project.getUser().getUniv(); //판매자의 대학교
        this.nickname = project.getNickname(); //프로젝트 담당자명
        this.description = project.getDescription(); //프로젝트 설명
        this.start_date = project.getStartDate(); //시작날짜
        this.end_date = project.getEndDate(); //종료날짜
        this.participant_number = project.getParticipant_number(); //참여인원
        this.achieved = achieved;
        this.goal = goal;
    }
}