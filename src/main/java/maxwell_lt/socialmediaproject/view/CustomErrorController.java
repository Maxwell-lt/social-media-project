package maxwell_lt.socialmediaproject.view;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public ModelAndView errorPage(HttpServletRequest httpRequest) {
        ModelAndView page = new ModelAndView("error");
        int errorCode = (int) httpRequest.getAttribute("javax.servlet.error.status_code");
        String msg = "";

        switch (errorCode) {
            case 400:
                msg = "Bad Request";
                break;
            case 401:
                msg = "Unauthorized";
                break;
            case 403:
                msg = "Forbidden";
                break;
            case 404:
                msg = "Not Found";
                break;
            case 500:
                msg = "Internal Server Error";
                break;
            default:
                msg = "Something bad happened";
        }
        page.addObject("errorcode", errorCode);
        page.addObject("errormsg", msg);
        return page;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}


