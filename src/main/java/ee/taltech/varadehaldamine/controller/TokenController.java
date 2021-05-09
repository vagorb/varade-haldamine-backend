package ee.taltech.varadehaldamine.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("token")
@RestController
public class TokenController {


    @PreAuthorize("hasRole('ROLE_admin')")
    public boolean getRole2() {
        return true;
    }

    @GetMapping
    public boolean getRole() {
        return getRole2();
    }
}
