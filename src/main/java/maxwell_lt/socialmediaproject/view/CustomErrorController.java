package maxwell_lt.socialmediaproject.view;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.RequestDispatcher.*;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public ModelAndView errorPage(HttpServletRequest httpRequest) {
        ModelAndView page = new ModelAndView("error");
        int errorCode = (int) httpRequest.getAttribute(ERROR_STATUS_CODE);
        String message = (String) httpRequest.getAttribute(ERROR_MESSAGE);

        if (message.equals("")) {
            switch (errorCode) {
                case 400:
                    message = "Bad Request";
                    break;
                case 401:
                    message = "Unauthorized";
                    break;
                case 403:
                    message = "Forbidden";
                    break;
                case 404:
                    message = "Not Found";
                    break;
                case 500:
                    message = "Internal Server Error";
                    break;
                default:
                    message = "Something bad happened";
            }
        }
        page.addObject("errorcode", errorCode);
        page.addObject("errormsg", message);
        return page;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}


