package wowmarket.wow_server.admin.permission.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PermissionProjectsRequest {
    private List<Long> projectIds;
}
