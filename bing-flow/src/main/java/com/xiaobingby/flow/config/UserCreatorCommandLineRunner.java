package com.xiaobingby.flow.config;

import org.flowable.idm.api.IdmIdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author :xie
 * Email: 1487471733@qq.com
 * Date: 2019/9/20
 * Time: 17:56
 * Describe:
 */
@Component
public class UserCreatorCommandLineRunner implements CommandLineRunner {
    @Autowired
    private   IdmIdentityService idmIdentityService;

    @Override
    public void run(String... args) throws Exception {

    }
}
