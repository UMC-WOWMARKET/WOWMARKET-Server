package wowmarket.wow_server.domain;

import com.fasterxml.jackson.databind.annotation.EnumNaming;
import lombok.Getter;

import java.util.Arrays;


@Getter
public enum ReceiveType {
    DELIVERY(1L), PICKUP(2L), ALL(3L);

    private Long code;

    private ReceiveType(Long code) {
        this.code = code;
    }

    public static ReceiveType ofReceiveType(Long code){
        return Arrays.stream(ReceiveType.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수령방법입니다."));
    }
}
