package com.example.raw_and_rare.api.user;
import com.example.raw_and_rare.dto.user.UserDto;
import com.example.raw_and_rare.dto.user.UserDto.*;
import com.example.raw_and_rare.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;
    @GetMapping
    public String test() {
        return "test";
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<UserUpdateResponse> userUpdate(@PathVariable Long userId, @RequestBody UserUpdateRequest request){
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }
}
