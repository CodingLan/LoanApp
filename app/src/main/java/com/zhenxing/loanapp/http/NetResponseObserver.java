package com.zhenxing.loanapp.http;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IntDef;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.zhenxing.loanapp.R;
import com.zhenxing.loanapp.bean.BaseBean;
import com.zhenxing.loanapp.bean.BaseBeanInterface;
import com.zhenxing.loanapp.fragment.NetResponseHandler;
import com.zhenxing.loanapp.http.TBHttpConstant.TBHttpCode;
import com.zhenxing.loanapp.util.ToastUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Android on 2017/3/3.
 */

public class NetResponseObserver <T> implements Observer<T> {
    NetResponseHandler mPresenter;
    public static final int REQUEST_LOGIN = 9999;
    protected Disposable mDisposable = null;

    public NetResponseObserver(NetResponseHandler presenter) {
        mPresenter = presenter;
    }

    public boolean isCancelProgress() {
        return true;
    }

    public boolean isShowMessageDialog() {
        return true;
    }

    public boolean isShowProgress() {
        return true;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mPresenter != null) {
            mPresenter.addDisposable(d);
            mDisposable = d;
            if (isShowProgress()) {
                mPresenter.showProgressDialog();
            }
        }
    }

    @Override
    public void onNext(T value) {
        try {
            if (isCancelProgress()) {
                mPresenter.closeProgressDialog();
            }
            if (value instanceof BaseBeanInterface) {
                if (((BaseBeanInterface)value).isSuccess()) {
                    onSuccess(value);
                } else {
                    handlerError((BaseBean)value);
                }
            }
        } catch (Exception e) {
            Logger.e(e, "");
            //上报异常
        }
    }

    public void onSuccess(T value) {

    }

    public void handlerError(BaseBean baseBean) {

        handlerError(baseBean.getCode());
    }

    @Override
    public void onError(Throwable e) {
        Logger.t("other-http").e(e.toString());
        try {
            if (isCancelProgress()) {
                mPresenter.closeProgressDialog();
            }

            if (e instanceof HttpException) {
                HttpException httpException = (HttpException)e;
                int code = httpException.code();
                if (code == 500) {
                    BaseBean baseBean = new BaseBean();
                    baseBean.code = TBHttpCode.FAIL_SERVER;
                    baseBean.setShowMessage(TBHttpCode.getName(mPresenter.getContext(), baseBean.code));
                    handlerError(baseBean);
                    return;
                }
            }

            if (e instanceof CustomError) {
                if (((CustomError)e).getType() == CustomError.TYPE_NET_ERROR) {
                    BaseBean baseBean = new BaseBean();
                    baseBean.code = TBHttpCode.FAIL_NETWORK;
                    baseBean.setShowMessage(TBHttpCode.getName(mPresenter.getContext(), baseBean.code));
                    handlerError(baseBean);
                    // handlerError(TBHttpCode.FAIL_NETWORK);
                } else if (((CustomError)e).getType() == CustomError.TYPE_CUSTOM_ERROR) {
                    handlerError((BaseBean)((CustomError)e).obj);
                }
            } else {
                BaseBean baseBean = new BaseBean();
                baseBean.code = TBHttpCode.FAIL_NETWORK;
                baseBean.setShowMessage(TBHttpCode.getName(mPresenter.getContext(), baseBean.code));
                handlerError(baseBean);
                // handlerError(TBHttpCode.FAIL_NETWORK);
            }
        } catch (Exception e1) {
            Logger.e(e1, e1.getMessage());
            ToastUtil.showToast(getString(R.string.tb_error_unknown));
        }
    }

    @Override
    public void onComplete() {

    }

    @Deprecated
    public void handlerError(int code) {
        String codeStr = TBHttpCode.getName(mPresenter.getContext(), code);
        if (TextUtils.isEmpty(codeStr)) {
            //未知错误
            codeStr = TBHttpCode.getName(mPresenter.getContext(), TBHttpCode.FAIL_UNKNOWN);
        }
        switch (code) {
            case 110:
            case 105:
            case 500002:
            case 500001:
            case 910003:
                ToastUtil.showToast(codeStr);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tbex://login"));
                intent.putExtra("canBack", false);
                //                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                if (mPresenter.getAttachActivity() != null) {
                    mPresenter.getAttachActivity()
                              .startActivity(intent);
                } else if (mPresenter.getAttachFragment() != null) {
                    mPresenter.getAttachFragment()
                              .startActivity(intent);
                }
                return;
        }
        showMessage(codeStr);
    }

    public void showMessage(String msg) {
        if (isShowMessageDialog()) {
            ToastUtil.showToast(msg);
            //            mPresenter.showMessageDialog(msg);
        }
    }

    public String getString(int strId) {
        return mPresenter.getContext().getString(strId);
    }

    public NetResponseHandler getPresenter() {
        return mPresenter;
    }

    public static class CustomError extends RuntimeException {

        public static final int TYPE_NET_ERROR = 0;
        public static final int TYPE_CUSTOM_ERROR = 1;
        public static final int TYPE_IGNORE = 2;
        private int type;
        private BaseBeanInterface obj;

        public CustomError(BaseBeanInterface message, @ErrorType int type) {
            super(message.toString());
            this.obj = message;
            this.type = type;
        }

        @IntDef ( {TYPE_NET_ERROR, TYPE_CUSTOM_ERROR, TYPE_IGNORE} )
        @Retention ( RetentionPolicy.SOURCE )
        public @interface ErrorType {
        }

        public int getType() {
            return type;
        }

        public BaseBeanInterface getObject() {
            return obj;
        }
    }
}

