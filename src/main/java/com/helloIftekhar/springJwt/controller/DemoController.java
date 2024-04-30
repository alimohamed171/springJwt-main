package com.helloIftekhar.springJwt.controller;

import com.helloIftekhar.springJwt.DES;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from secured url");
    }

    @GetMapping("/admin_only")
    public ResponseEntity<String> adminOnly() {
        String responseData = DES.tripleDESEncrypt("ALI mohamed" );
        String response = DES.tripleDESDecrypt(responseData);
      //  return ResponseEntity.ok(responseData);
      return ResponseEntity.ok(response+"\n" + responseData );

    }

}
