package complaint.external;

import java.util.Date;
import lombok.Data;

@Data
public class Fee {

    private Long id;
    private String applicationNumber;
    private Long charge;
    private String userId;
}
