package com.example.administrator.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.model.News;
import com.example.administrator.news.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Calendar;
/**
 *  Fragment需要添加xml布局文件
 */
@ContentView(R.layout.time_page)
public class TimePageFragment extends Fragment {

    @ViewInject(R.id.listview)  // 需要添加listItem
    private ListView listview;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("jxy",this.getClass() + "---->1: onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("jxy",this.getClass() + "---->2: onCreate");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("jxy",this.getClass() + "---->3: onCreateView");
        return x.view().inject(this,inflater,null);
    }

    @Override  // 布局创建完毕,一般此方法用来初始化数据
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("jxy",this.getClass() + "---->4: onViewCreated");
        TextView tv_curDate = (TextView) view.findViewById(R.id.curDate);

        Calendar now = Calendar.getInstance();
        String strMonth = "0" + (now.get(Calendar.MONTH)+1);
        strMonth = strMonth.substring(strMonth.length()-2,strMonth.length());
        String strDay = "0" + now.get(Calendar.DAY_OF_MONTH);
        strDay = strDay.substring(strDay.length()-2,strDay.length());
        tv_curDate.setText(strMonth + "月" + strDay + "日 " + now.get(Calendar.YEAR)+ "年");

        // 给listView添加数据(创建适配器),不能直接添加,listview.addView();
        // 1: 此处应该先发送http请求,如果进入: onSuccess,则说明数据已经获取
        RequestParams params = new RequestParams("http://hiwbs101083.jsp.jspee.com.cn/NewServlet");
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                Log.i("jxy","result="+result);
                // 2 通过Gson构建数据,Gson 把string转化为model /map
                final List<News> nList = new Gson().fromJson(result,new TypeToken<List<News>>(){
                }.getType());
                // 给ListView添加适配器
                listview.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return nList.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return nList.get(position);
                    }

                    @Override
                    public long getItemId(int position) {
                        return 0;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        News news = (News) getItem(position);
                        View item = null;
                        if(convertView!=null){
                            item = convertView;
                        }else{
                            item = View.inflate(TimePageFragment.this.getActivity(),R.layout.time_list_item,null);
                        }

                        // 3: 根据for循环,对item里面的view控件进行赋值(ImagerView需要单独获取)
                        TextView txt_time = (TextView) item.findViewById(R.id.time);
                        TextView txt_title = (TextView) item.findViewById(R.id.title);
                        ImageView iv = (ImageView)item.findViewById(R.id.iv_item);
                        txt_time.setText(news.getTime());
                        txt_title.setText(news.getTitle());
                        txt_title.setTag(news.getContentUrl());
                        // 设置加载图片的参数
                        ImageOptions options = new ImageOptions.Builder()
// 是否忽略GIF格式的图片
                                .setIgnoreGif(false)
// 图片缩放模式
                                .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
// 下载中显示的图片
                                .setLoadingDrawableId(R.mipmap.index)
// 下载失败显示的图片
                                .setFailureDrawableId(R.mipmap.index)
// 得到ImageOptions对象
                                .build();
                        x.image().bind(iv,news.getImgUrl(),options,new Callback.CommonCallback<Drawable>() {
                            @Override
                            public void onSuccess(Drawable arg0) {
                                Log.i("jxy", "onSuccess........");
                            }

                            @Override
                            public void onFinished() {
                                Log.i("jxy", "onFinished........");
                            }

                            @Override
                            public void onError(Throwable arg0, boolean arg1) {

                                Log.i("jxy", "onError........");
                            }

                            @Override
                            public void onCancelled(Callback.CancelledException arg0) {
                                Log.i("jxy", "onCancelled........");
                            }
                        });
                        //给item注册事件
                        item.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v) {
                                Toast.makeText(x.app(), v.findViewById(R.id.title).getTag().toString(), Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(v.findViewById(R.id.title).getTag().toString())));
                            }
                        });

                        return item;
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("jxy",this.getClass() + "---->5: onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("jxy",this.getClass() + "---->6: onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("jxy",this.getClass() + "---->7: onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("jxy",this.getClass() + "---->8: onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("jxy",this.getClass() + "---->9: onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("jxy",this.getClass() + "---->10: onDestroy");
    }


}
