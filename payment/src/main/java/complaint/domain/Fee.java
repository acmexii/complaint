package complaint.domain;

import complaint.PaymentApplication;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Fee_table")
@Data
//<<< DDD / Aggregate Root
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String applicationNumber;

    private Long charge;

    private String userId;

    @PostPersist
    public void onPostPersist() {}

    public static FeeRepository repository() {
        FeeRepository feeRepository = PaymentApplication.applicationContext.getBean(
            FeeRepository.class
        );
        return feeRepository;
    }

    //<<< Clean Arch / Port Method
    public void pay(PayCommand payCommand) {
        //implement business logic here:

        complaint.external.GetInfoQuery getInfoQuery = new complaint.external.GetInfoQuery();
        // getInfoQuery.set??()
        User user = FeeApplication.applicationContext
            .getBean(complaint.external.UserService.class)
            .userInfo(getInfoQuery);

        PaymentCompleted paymentCompleted = new PaymentCompleted(this);
        paymentCompleted.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
