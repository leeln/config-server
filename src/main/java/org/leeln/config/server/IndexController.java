package org.leeln.config.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.functions.Func3;

/**
 * @author liuzhenyuan
 * @version Last modified 16/9/22
 */
@RestController
@RequestMapping
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping
    public String index() {

        return Observable.zip(
                userService.getA(),
                userService.getB(),
                userService.getC(),
                new Func3<String, String, String, String>() {
                    @Override
                    public String call(String s, String s2, String s3) {
                        return s + s2 + s3;
                    }
                }).toBlocking().single();
    }

    @RequestMapping("/1")
    public String index2() {

        return userService.a() + userService.b() + userService.c();
    }
}
