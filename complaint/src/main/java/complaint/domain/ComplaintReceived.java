package complaint.domain;

import complaint.domain.*;
import complaint.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class ComplaintReceived extends AbstractEvent {

    private Long id;
    private String complainId;
    private String userId;
    private Object complainDetail;
}
