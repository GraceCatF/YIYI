package com.clubank.device.data.remote;


import com.clubank.device.data.remote.model.MemberInfo;
import com.clubank.device.data.remote.model.ReposInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by fengyq on 2017/2/28
 */
public interface ApiService {


    @GET
    Call<ApiResponse<ArrayList<MemberInfo>>> searchUser(@Url String url);


    @GET
    Call<ArrayList<ReposInfo>> getRepos(@Url String url);




}
