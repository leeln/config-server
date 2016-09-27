package org.leeln.config.server.impl;

import com.netflix.hystrix.HystrixThreadPool;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.leeln.config.server.UserService;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Subscriber;

/**
 * @author liuzhenyuan
 * @version Last modified 16/9/5
 */
@Service
@DefaultProperties(
        commandProperties = {
                @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "THREAD")
        },
        threadPoolProperties = {
                @HystrixProperty(name = HystrixPropertiesManager.CORE_SIZE, value = "50")
        }
)
public class UserServiceImpl implements UserService {


    @Override
    @HystrixCommand(fallbackMethod = "getABack", commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "1000")
    })
    public Observable<String> getA() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    System.out.println("getA:" + Thread.currentThread().getName());
                    Thread.sleep(100);
                    subscriber.onNext("A");
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getABack(Throwable throwable) {
        System.out.println(throwable);
        return "a";
    }

    @Override
    @HystrixCommand(fallbackMethod = "getBBack", commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "1000")
    })
    public Observable<String> getB() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    System.out.println("getB:" + Thread.currentThread().getName());
                    Thread.sleep(200);

                    subscriber.onNext("B");
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getBBack(Throwable throwable) {

        System.out.println(throwable);
        return "b";
    }

    @Override
    @HystrixCommand(fallbackMethod = "getCBack", commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "1000")
    })
    public Observable<String> getC() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    System.out.println("getC:" + Thread.currentThread().getName());
                    Thread.sleep(300);

                    subscriber.onNext("C");
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public String a() {
        System.out.println("getA:" + Thread.currentThread().getName());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "A";
    }

    @Override
    public String b() {
        System.out.println("getB:" + Thread.currentThread().getName());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "B";
    }

    @Override
    public String c() {
        System.out.println("getC:" + Thread.currentThread().getName());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "C";
    }

    private String getCBack(Throwable throwable) {
        System.out.println(throwable);
        return "c";
    }

}
