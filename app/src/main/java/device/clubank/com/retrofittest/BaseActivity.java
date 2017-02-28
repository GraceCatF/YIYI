package device.clubank.com.retrofittest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.clubank.device.common.utlis.RetrofitClient;
import com.clubank.device.data.remote.ApiService;

import retrofit2.Response;

/**
 * Created by fengyq on 2017/2/28
 */
public class BaseActivity<T> extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    public void onSuc(String op, Response<T> response) {

    }

    public void onFail(String op, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }


    public ApiService getService() {
        return RetrofitClient.getInstance(this).apiService;
    }

    //隐藏软键盘
    public void hideSoftKeyboard() {
        if (this.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (this.getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
