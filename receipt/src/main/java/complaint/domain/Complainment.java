package complaint.domain;

import complaint.ReceiptApplication;
import complaint.domain.ComplaintReceived;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Complainment_table")
@Data
//<<< DDD / Aggregate Root
public class Complainment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String complainId;

    private String userId;

    @Embedded
    private ComplainmentDetail complainDetail;

    @PostPersist
    public void onPostPersist() {
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        complaint.external.PayCommand payCommand = new complaint.external.PayCommand();
        // mappings goes here
        ReceiptApplication.applicationContext
            .getBean(complaint.external.FeeService.class)
            .pay(/* get???(), */payCommand);

        ComplaintReceived complaintReceived = new ComplaintReceived(this);
        complaintReceived.publishAfterCommit();
    }

    public static ComplainmentRepository repository() {
        ComplainmentRepository complainmentRepository = ReceiptApplication.applicationContext.getBean(
            ComplainmentRepository.class
        );
        return complainmentRepository;
    }
}
//>>> DDD / Aggregate Root
