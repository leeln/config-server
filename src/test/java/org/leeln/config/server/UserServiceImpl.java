package org.leeln.config.server;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Subscriber;

/**
 * @author liuzhenyuan
 * @version Last modified 16/9/5
 */
@Service
public class UserServiceImpl implements UserService {


    @Override
    @HystrixCommand(fallbackMethod = "getABack",
            commandProperties = {
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "THREAD")
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

    public String getABack() {
        return "a";
    }

    @Override
    @HystrixCommand(fallbackMethod = "getBBack",
            commandProperties = {
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "THREAD")
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

    public String getBBack() {
        return "b";
    }

    @Override
    @HystrixCommand(fallbackMethod = "getCBack",
            commandProperties = {
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "THREAD")
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

    public String getCBack() {
        return "c";
    }

}
