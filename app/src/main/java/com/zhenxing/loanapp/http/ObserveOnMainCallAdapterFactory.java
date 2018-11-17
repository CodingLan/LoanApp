package com.zhenxing.loanapp.http;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * 主线程回调
 *
 * @see(https://github.com/square/retrofit/blob/master/samples/src/main/java/com/example/retrofit/RxJavaObserveOnMainThread.java) .
 * Created by xtdhwl on 04/01/2018.
 */

public class ObserveOnMainCallAdapterFactory extends CallAdapter.Factory {

    public ObserveOnMainCallAdapterFactory() {

    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != Observable.class) {
            return null; // Ignore non-Observable types.
        }

        // Look up the next call adapter which would otherwise be used if this one was not present.
        //noinspection unchecked returnType checked above to be Observable.
        final CallAdapter<Object, Observable<?>> delegate =
            (CallAdapter<Object, Observable<?>>)retrofit.nextCallAdapter(this, returnType,
                annotations);

        return new CallAdapter<Object, Object>() {
            @Override
            public Object adapt(Call<Object> call) {
                // Delegate to get the normal Observable...
                Observable<?> o = delegate.adapt(call);
                // ...and change it to send notifications to the observer on the specified scheduler.
                o.observeOn(AndroidSchedulers.mainThread());
                o.subscribeOn(Schedulers.io());
                return o;
            }

            @Override
            public Type responseType() {
                return delegate.responseType();
            }
        };
    }
}
