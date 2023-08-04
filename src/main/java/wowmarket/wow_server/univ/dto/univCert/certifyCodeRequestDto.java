package wowmarket.wow_server.univ.dto.univCert;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class certifyCodeRequestDto {
    private String key; //univCertAPI
    private String univName;
    private String email; //univ_email;
    private int code;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
