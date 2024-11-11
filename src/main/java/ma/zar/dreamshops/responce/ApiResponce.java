package ma.zar.dreamshops.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponce {
    private String message;
    private Object data;
}
