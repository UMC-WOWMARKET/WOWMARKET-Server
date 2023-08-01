package wowmarket.wow_server.mypage.myproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.domain.Item;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.mypage.myproject.dto.MySalesFormDetailResponseDto;
import wowmarket.wow_server.mypage.myproject.dto.MySalesFormDto;
import wowmarket.wow_server.mypage.myproject.dto.MySalesFormListResponseDto;
import wowmarket.wow_server.mypage.myproject.dto.MySalesItemDto;
import wowmarket.wow_server.repository.ItemRepository;
import wowmarket.wow_server.repository.ProjectRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyProjectService {

    private final ProjectRepository projectRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public MySalesFormListResponseDto findAllMySalesForm(Long user_id, Pageable pageable){
        Page<Project> projects = projectRepository.findByUser_Id(user_id, pageable);
        Page<MySalesFormDto> mySalesFormDtos = projects.map(MySalesFormDto::new);
        MySalesFormListResponseDto responseDto = new MySalesFormListResponseDto(mySalesFormDtos.getContent(),
                mySalesFormDtos.getTotalPages(), mySalesFormDtos.getNumber());
        return responseDto;
    }

    @Transactional
    public void finishMySalesForm(Long project_id){
        Project project = projectRepository.findById(project_id).get();
        if (project.is_end() == false)
            project.set_end(true);
    }

    @Transactional(readOnly = true)
    public MySalesFormDetailResponseDto findMySalesDetail(Long project_id){
        Project project = projectRepository.findById(project_id).get();
        List<Item> itemList = itemRepository.findByProject_Id(project_id);
        List<MySalesItemDto> itemDtos = itemList.stream().map(MySalesItemDto::new).collect(Collectors.toList());

        MySalesFormDetailResponseDto responseDto = new MySalesFormDetailResponseDto(project, itemDtos);
        return responseDto;
    }

}
