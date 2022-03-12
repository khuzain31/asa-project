package id.asalife.api.controllers;

import id.asalife.api.domains.models.DeviceInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeController {
    @GetMapping("")
    public ResponseEntity<DeviceInfo> getDeviceInfo(@RequestHeader("User-Agent") String userAgent, HttpServletRequest request) {
        return ResponseEntity.ok(new DeviceInfo(userAgent, request.getRemoteAddr()));
    }
}
