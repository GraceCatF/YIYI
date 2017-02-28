package com.clubank.device.common.utlis;


import android.app.ProgressDialog;
import android.widget.Toast;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import device.clubank.com.retrofittest.BaseActivity;
import device.clubank.com.retrofittest.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fengyq on 2017/2/28
 */
public  class MyCallback<T> implements Callback<T> {
  private  BaseActivity a;
    private   String op;//接口名称
    private  ProgressDialog wd;
    private  boolean showWait;

    public MyCallback(BaseActivity a,String op,boolean showWait){
        this.a=a;
        this.op=op;
        this.showWait=showWait;
        if(showWait){
            wd = new ProgressDialog(a);
            wd.setCanceledOnTouchOutside(false);
            wd.show();
        }

    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(showWait) {
            wd.dismiss();
        }
        if (response.isSuccessful()) {
            if(null!=response.body()){
                a.onSuc(op,response);
            }


        } else {

            onFailure(call, new RuntimeException("response error detail=" + response.raw().toString()));
        }

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if(showWait) {
            wd.dismiss();
        }
        int msgId = 0;
        if (t instanceof UnknownHostException
                || t instanceof SocketException
                || t instanceof ConnectException
                ) {
            msgId = R.string.network_problem;
        } else if (t instanceof SocketTimeoutException) {
            msgId = R.string.network_timeout;
        } else if (t instanceof IOException) {
            msgId = R.string.server_error;

        }
        if (msgId != 0)

            Toast.makeText(a, a.getString(msgId), Toast.LENGTH_SHORT).show();

            // onFail("ExceptionCode" + msgId);
        else
            a.onFail(op, t.getMessage());


    }




}
