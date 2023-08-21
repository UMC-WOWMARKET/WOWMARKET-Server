package wowmarket.wow_server.detail.demandproject.dto;

import lombok.Getter;
import wowmarket.wow_server.domain.DemandProject;

import java.time.LocalDate;

@Getter
public class DemandProjectInfoResponseDto {
    private String thumbnail; //대표이미지
    private String category; //카테고리
    private String name; //프로젝트 이름
    private String univ; //판매자의 대학교
    private String nickname; //프로젝트 담당자명
    private String description; //프로젝트 설명
    private LocalDate start_date; //시작날짜
    private LocalDate end_date; //종료날짜
    private int participant_number; //참여인원
    private int achieved; //달성률 분자
    private int goal; //달성률 분모

    public DemandProjectInfoResponseDto(DemandProject project, int achieved, int goal) {
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