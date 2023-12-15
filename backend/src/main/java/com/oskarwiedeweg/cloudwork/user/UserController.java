package com.oskarwiedeweg.cloudwork.user;

import com.oskarwiedeweg.cloudwork.user.dto.Setup2FADto;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Data
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/2fa/setup")
    @PreAuthorize("isAuthenticated()")
    public Setup2FADto setup2FA(@AuthenticationPrincipal Long userId) {
        return userService.setup2FA(userId);
    }

    @PostMapping("/2fa/verify/{code}")
    @PreAuthorize("isAuthenticated()")
    public Map<String, Boolean> verify2FA(@AuthenticationPrincipal Long userId, @PathVariable("code") Long code, @RequestParam("tempToken") String tempToken) {
        return Map.of("valid", userService.setup2FA(userId, code, tempToken));
    }

}
