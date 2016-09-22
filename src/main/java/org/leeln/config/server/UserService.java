package org.leeln.config.server;

import rx.Observable;

/**
 * @author liuzhenyuan
 * @version Last modified 16/9/5
 */
public interface UserService {

    Observable<String> getA();

    Observable<String> getB();

    Observable<String> getC();


    String a();
    String b();
    String c();
}
