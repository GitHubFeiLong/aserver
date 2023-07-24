package com.zhy.authentication.server.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({})
class BaseUserServiceTest {

    @Test
    void testPassword() {
        String encode = new BCryptPasswordEncoder().encode("123456");
        System.out.println("encode = " + encode);

        String replace = UUID.randomUUID().toString().replace("-", "");
        System.out.println("replace = " + replace);
    }
}
