package com.lgp.myapinterface;

import com.lgp.myapiclientsdk.client.ApiClient;
import com.lgp.myapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MyapiInterfaceApplicationTests {

    @Resource
    private ApiClient apiClient;

    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("lgp");

        String nameByGet = apiClient.getNameByGet(user.getUsername());
        System.out.println(nameByGet);

        String nameByPost = apiClient.getNameByPost(user.getUsername());
        System.out.println(nameByPost);

        String usernameByPost = apiClient.getUsernameByPost(user);
        System.out.println(usernameByPost);
    }

}
