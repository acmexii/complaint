package complaint.infra;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import complaint.domain.User;
import complaint.domain.UserRepository;
import complaint.domain.GetInfoQuery;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;


    

    @GetMapping(path = "/users/search/findByUserInfo/")
    public User userInfo, GetInfoQuery getInfoQuery) {
        return userRepository.findByuserInfo(getInfoQuery.getId());
    }

}