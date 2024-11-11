package complaint.domain;

import complaint.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "complainments",
    path = "complainments"
)
public interface ComplainmentRepository
    extends PagingAndSortingRepository<Complainment, Long> {}
