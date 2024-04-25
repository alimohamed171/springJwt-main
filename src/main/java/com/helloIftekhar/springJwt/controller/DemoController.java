package com.helloIftekhar.springJwt.controller;

import com.helloIftekhar.springJwt.AES;
import com.helloIftekhar.springJwt.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.helloIftekhar.springJwt.Converter.convertToString;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from secured url");
    }

    @GetMapping("/admin_only")
    public ResponseEntity<String> adminOnly() {
        String responseData = AES.tripleDESEncrypt("ALI mohamed" );
        String response = AES.tripleDESDecrypt(responseData);
      //  return ResponseEntity.ok(responseData);
      return ResponseEntity.ok(response+"\n" + responseData );

    }

}
