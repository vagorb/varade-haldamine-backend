package ee.taltech.varadehaldamine.controller;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * This controller is used to avoid Whitelabel error in frontend.
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
class AngularRedirectErrorController extends AbstractErrorController {

    private final ErrorAttributes errorAttributes;

    public AngularRedirectErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String redirectToAngular(ServletWebRequest request) {
        Map<String, Object> errorAttrs = errorAttributes.getErrorAttributes(request, false);
        String path = (String) errorAttrs.get("path");
        return "redirect:http://localhost:4200";
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }

        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> body = errorAttributes.getErrorAttributes(webRequest, false);
        return new ResponseEntity<>(body, status);
    }

    @Override
    public String getErrorPath() {
        return null;
    }

}
