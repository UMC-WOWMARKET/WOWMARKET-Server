package wowmarket.wow_server.mypage.myproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.mypage.myproject.dto.MySalesFormDto;
import wowmarket.wow_server.mypage.myproject.dto.MySalesFormListResponseDto;
import wowmarket.wow_server.repository.ProjectRepository;

@Service
@RequiredArgsConstructor
public class MyProjectService {

    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public MySalesFormListResponseDto findAllMySalesForm(Long user_id, Pageable pageable){
        Page<Project> projects = projectRepository.findByUser_Id(user_id, pageable);
        Page<MySalesFormDto> mySalesFormDtos = projects.map(MySalesFormDto::new);
        MySalesFormListResponseDto responseDto = new MySalesFormListResponseDto(mySalesFormDtos.getContent(),
                mySalesFormDtos.getTotalPages(), mySalesFormDtos.getNumber());
        return responseDto;
    }

}
