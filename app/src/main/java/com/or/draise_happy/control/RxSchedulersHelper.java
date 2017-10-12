package com.or.draise_happy.control;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RxSchedulersHelper 线程汇总
 * Created by dawabos on 2016/8/15.
 * Email dawabo@163.com
 *
 * _apiService.login(mobile, verifyCode)
 *          .subscribeOn(Schedulers.io())
 *          .observeOn(AndroidSchedulers.mainThread())
 *          //......
 *          compose
 *  _apiService.login(mobile, verifyCode)
 *          .compose(RxSchedulersHelper.io_main())
 *          //......
 */
public class RxSchedulersHelper {
    public static <T> Observable.Transformer<T, T> io_main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
