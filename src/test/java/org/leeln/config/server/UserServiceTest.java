package org.leeln.config.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * @author liuzhenyuan
 * @version Last modified 16/9/5
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getSync() throws Exception {


        long start = System.currentTimeMillis();

        String c = Observable.zip(
                userService.getA(),
                userService.getB(),
                userService.getC(),
                new Func3<String, String, String, String>() {
                    @Override
                    public String call(String s, String s2, String s3) {
                        return s + s2 + s3;
                    }
                }).toBlocking().single();

        long end = System.currentTimeMillis();

        System.out.println(c);
        System.out.println(end - start);
    }

    @Test
    public void getAsync() throws Exception {

        long start = System.currentTimeMillis();

        String c = Observable.zip(
                userService.getA().subscribeOn(Schedulers.io()),
                userService.getB().subscribeOn(Schedulers.io()),
                userService.getC().subscribeOn(Schedulers.io()),
                new Func3<String, String, String, String>() {
                    @Override
                    public String call(String s, String s2, String s3) {
                        return s + s2 + s3;
                    }
                }).toBlocking().single();


        long end = System.currentTimeMillis();

        System.out.println(c);
        System.out.println(end - start);
    }

    @Test
    public void getSync01() throws Exception {
        getSync();

        getSync();

        getSync();
    }

    @Test
    public void getAsync01() throws Exception {
        getAsync();
        getAsync();
        getAsync();
    }

}