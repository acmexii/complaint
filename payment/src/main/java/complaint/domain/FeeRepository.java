package complaint.domain;

import complaint.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "fees", path = "fees")
public interface FeeRepository extends PagingAndSortingRepository<Fee, Long> {}
