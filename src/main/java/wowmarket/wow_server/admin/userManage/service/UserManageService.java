package wowmarket.wow_server.admin.userManage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wowmarket.wow_server.admin.userManage.dto.UserManageDto;
import wowmarket.wow_server.admin.userManage.dto.UserSearchCond;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManageService {
    private final UserRepository userRepository;

    public List<UserManageDto> getUserList(UserSearchCond cond, User user){
        // 관리자만 조회 가능
        if (!user.getRole().toString().equals("ROLE_ADMIN")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 동적 쿼리로 조건 따라 유저 검색
        List<User> userList = userRepository.getUserList(cond.getEmail(), cond.getName(), cond.getUniv());

        // Dto로 필요 데이터만 담아 리턴
        return userList.stream()
                .map(UserManageDto::from)
                .collect(Collectors.toList());
    }
}
