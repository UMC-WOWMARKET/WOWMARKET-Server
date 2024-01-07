package wowmarket.wow_server.mypage.myproject.MySalesProject.service;

import com.nimbusds.oauth2.sdk.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.domain.Category;
import wowmarket.wow_server.domain.Item;
import wowmarket.wow_server.domain.Project;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.global.jwt.SecurityUtil;
import wowmarket.wow_server.mypage.myproject.MySalesProject.dto.*;
import wowmarket.wow_server.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MySalesProjectService {
    private final ProjectRepository projectRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public MySalesListResponseDto findAllMySalesForm(Pageable pageable, User user){
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Page<Project> projects = projectRepository.findByUser_Id(user.getId(), pageable);
        Page<MySalesFormDto> mySalesFormDtos = projects.map(MySalesFormDto::new);
        MySalesListResponseDto responseDto = new MySalesListResponseDto(mySalesFormDtos.getContent(),
                mySalesFormDtos.getTotalPages(), mySalesFormDtos.getNumber());
        return responseDto;
    }

    @Transactional
    public ResponseEntity finishMySalesForm(Long project_id, User user){
        Project project = projectRepository.findById(project_id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
        if (user == null || project.getUser().getId() != user.getId()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (project.isEnd() == false)
            project.setEnd(true);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public MySalesDetailResponseDto findMySalesDetail(Long project_id){
        Project project = projectRepository.findById(project_id).get();
        List<Item> itemList = itemRepository.findByProject_Id(project_id);
        List<MySalesItemDto> itemDtos = itemList.stream().map(MySalesItemDto::new).collect(Collectors.toList());

        MySalesDetailResponseDto responseDto = new MySalesDetailResponseDto(project, itemDtos);
        return responseDto;
    }

    @Transactional
    public ResponseEntity modifyMySalesProject(Long projectId, MySalesProjectModifyRequestDto requestDto, User user){
        Project project = projectRepository.findById(projectId).orElseThrow(()->new IllegalArgumentException("해당 project id가 없습니다."));
        if (user == null || project.getUser().getId() != user.getId()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Category category = categoryRepository.findById(Long.valueOf(requestDto.getCategoryId())).orElseThrow(() -> new IllegalArgumentException("해당 category id가 없습니다."));
        project.modify(requestDto, category);
        projectRepository.save(project);
        return new ResponseEntity(HttpStatus.OK);
    }

}
