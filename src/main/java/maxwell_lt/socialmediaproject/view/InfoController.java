package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.utilities.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.*;

@Controller
public class InfoController {

    private UserUtil userUtil;

    @Autowired
    public InfoController(UserUtil userUtil) {
        this.userUtil = userUtil;
    }

    @GetMapping(value = "/info/likes", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, Integer> getUserLikes() {
        User user = userUtil.getCurrentUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
        Map<String, Integer> response = new HashMap<>();
        response.put("likes", user.getCurrentLikes().intValue());
        return response;
    }
}
