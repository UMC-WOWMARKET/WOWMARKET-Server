package wowmarket.wow_server.admin.adminAccount.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class AdminResponseDto {
    private List<AdminDto> adminList;

    public AdminResponseDto(List<AdminDto> adminList){
        this.adminList = adminList;
    }
}
