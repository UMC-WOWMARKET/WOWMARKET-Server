package wowmarket.wow_server.admin.adminAccount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wowmarket.wow_server.admin.adminAccount.dto.ChangeRoleRequestDto;
import wowmarket.wow_server.domain.Role;
import wowmarket.wow_server.domain.User;
import wowmarket.wow_server.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AdminAccountManagementService {
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity giveAdminRole(ChangeRoleRequestDto requestDto, User user){
//        if (!user.getRole().equals("ROLE_ADMIN")){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        } 관리자만 변경 가능하도록 (그 전에 admin페이지는 관리자만 접근 가능하도록 설정 필요)
        User reqUser = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 email 입니다."));
        Role roleAdmin = Role.ROLE_ADMIN;
        reqUser.updateUserRole(roleAdmin);
        userRepository.save(reqUser);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity giveUserRole(ChangeRoleRequestDto requestDto, User user){
//        if (!user.getRole().equals("ROLE_ADMIN")){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        } 관리자만 변경 가능하도록 (그 전에 admin페이지는 관리자만 접근 가능하도록 설정 필요)
        User reqUser = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 email 입니다."));
        Role roleUser = Role.ROLE_USER;
        reqUser.updateUserRole(roleUser);
        userRepository.save(reqUser);

        return new ResponseEntity(HttpStatus.OK);
    }
}
