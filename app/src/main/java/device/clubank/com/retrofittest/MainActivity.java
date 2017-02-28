package device.clubank.com.retrofittest;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.clubank.device.common.utlis.MyCallback;
import com.clubank.device.data.remote.ApiResponse;
import com.clubank.device.data.remote.ApiService;
import com.clubank.device.data.remote.model.BaseConfig;
import com.clubank.device.data.remote.model.MemberInfo;
import com.clubank.device.data.remote.model.ReposInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Response;
/**
 * Created by fengyq on 2017/2/28.
 */

public class MainActivity extends BaseActivity {


    private ApiService apiservice;
    private UserInfoAdatper adapter;
    private  ArrayList memberData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiservice = getService();

    }



    /**
     * 搜索功能
     */
    public void searchUser(View v) {
        hideSoftKeyboard();
        String inputUser = ((EditText) findViewById(R.id.inputUser)).getText().toString();
        if (TextUtils.isEmpty(inputUser))
            Toast.makeText(this, getString(R.string.search_hit), Toast.LENGTH_SHORT).show();
        else
            refreshData(inputUser);


    }

    /**
     *编程偏好
     */
    public void  getRepos(int index,String url){
     apiservice.getRepos(url+"?"+BaseConfig.oAuth).enqueue(new MyCallback<ArrayList<ReposInfo>>(this,
                "getRepos"+","+index,false));

    }


    /**
     * 搜索
     * @param inputUser
     */
    public void refreshData(String inputUser) {
        apiservice.searchUser(BaseConfig.SEARCH_URL_START + inputUser +
                BaseConfig.SEARCH_URL_END+"&"+BaseConfig.oAuth).enqueue(new MyCallback<ApiResponse<ArrayList<MemberInfo>>>(this,
                "searchUser", true));

    }

    /**
     * 成功
     */
    @Override
    public void onSuc(String op, Response response) {
        super.onSuc(op, response);

        if (op.equals("searchUser")) {
            ApiResponse<ArrayList<MemberInfo>> apiResponse = (ApiResponse<ArrayList<MemberInfo>>) response.body();
            if(apiResponse.total_count==0){
                Toast.makeText(this, getString(R.string.not_data), Toast.LENGTH_SHORT).show();
                return;
            }
            memberData= apiResponse.getitems();
            showMemberInfo();


        } else {//getRepos
            ArrayList<ReposInfo> data = (ArrayList<ReposInfo>)
                    response.body();
            //op.split(",")[1] 当前加载编程偏好的用户index
            showRepos(data,Integer.parseInt(op.split(",")[1]));
        }


    }


    /**
     * 失败
     * @param op
     * @param message
     */
    @Override
    public void onFail(String op, String message) {
        super.onFail(op, message);
        Toast.makeText(this, getString(R.string.other_error), Toast.LENGTH_SHORT).show();


    }


    public void showMemberInfo() {

        RecyclerView reclist = (RecyclerView) findViewById(R.id.list);
        adapter = new UserInfoAdatper(this, memberData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        reclist.setLayoutManager(layoutManager);
        reclist.setAdapter(adapter);

    }

    //整理数据结构，通知list更新偏好语言
    public void showRepos(ArrayList data,int index){
        StringBuffer buf=new StringBuffer(data.size());
        for (int i=0;i<data.size();i++){
            ReposInfo rinfo=  (ReposInfo) data.get(i);
            if(!TextUtils.isEmpty(rinfo.language)){
                buf.append(rinfo.language+",");
            }

        }
        String laguages=buf.toString().substring(0, buf.length() - 1);
        MemberInfo  info    =(MemberInfo)memberData.get(index);
        info.language=laguages;
        info.oftenLanguage=oftenLanguage(laguages.split(","));
        memberData.set(index,info);
        adapter.notifyDataSetChanged();
    }

    //整理使用频繁语言
    public String oftenLanguage(String[] laguages){
        String oftenLanguage="";
        int sortingCount=0;//比较使用次数

        Map<String,Integer>map = new HashMap<String,Integer>();
        for (int i = 0; i < laguages.length; i++) {
            if (map.containsKey(laguages[i])) {
                map.put(laguages[i], map.get(laguages[i]) + 1);
            } else {
                map.put(laguages[i],1);
            }

        }

        Iterator<Map.Entry<String, Integer>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,Integer> entry =  iter.next();
            if (entry.getValue()>sortingCount){
                sortingCount=entry.getValue();
                oftenLanguage=entry.getKey();
            }

        }
            return oftenLanguage;
    }



}
