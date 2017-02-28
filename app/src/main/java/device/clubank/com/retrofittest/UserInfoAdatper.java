package device.clubank.com.retrofittest;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.clubank.device.data.remote.model.MemberInfo;

import java.util.ArrayList;

/**
 * Created by fengyq on 2017/2/28.
 */
public class UserInfoAdatper extends RecyclerView.Adapter<UserInfoAdatper.MyHolde>{
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList data;




    public UserInfoAdatper(Context mContext, ArrayList data) {
        this.mContext = mContext;
        this.data = data;

        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyHolde onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyHolde holder = new MyHolde(inflater.inflate(R.layout.activity_user_item, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolde MyHolde, final int i) {
        MemberInfo info=(MemberInfo)data.get(i);
        MyHolde.header.setTag( R.id.header,info.avatar_url);
        Glide.with(mContext)
                .load(info.avatar_url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into( MyHolde.header);

        if(!TextUtils.isEmpty(info.language))
            MyHolde.repos.setText(info.language);
        else
            ((MainActivity)mContext).getRepos(i,info.repos_url);

        MyHolde.oftenLanguage.setText(mContext.getString(R.string.oftenLanguage)+info.oftenLanguage);
        MyHolde.uername.setText(info.login);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolde extends RecyclerView.ViewHolder {
        ImageView header;
        TextView uername,repos,oftenLanguage;

        public MyHolde(View itemView) {
            super(itemView);
            header = (ImageView) itemView.findViewById(R.id.header);
            uername = (TextView) itemView.findViewById(R.id.uername);
            repos=(TextView) itemView.findViewById(R.id.repos);
            oftenLanguage=(TextView) itemView.findViewById(R.id.oftenLanguage);
        }
    }
}
