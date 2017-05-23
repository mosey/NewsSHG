package com.example.administrator.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.news.R;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class TabFragment_a extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_a, container, false);

        BridgeWebView bridgeWebView = (BridgeWebView)view.findViewById(R.id.bridgeWebView);
        initBridgeWebView(bridgeWebView);

        return view;
    }
    private void initBridgeWebView(BridgeWebView bridgeWebView){
        // 通过webview注册一个方法,此方法提供H5调用,获取远程数据之后json格式返回即可
        // 加载本地的H5页面
        bridgeWebView.loadUrl("file:///android_asset/refresh.html");
        bridgeWebView.getSettings().setJavaScriptEnabled(true);  // 默认开启
        //  注册一个方法,可以交给H5调用  webCallAndroid
        bridgeWebView.registerHandler("getServerData", new BridgeHandler() {

            @Override  // data： 从页面传入的数据
            public void handler(String keyword, final CallBackFunction function) {
                Log.i("jxy", "keyword:" + keyword);
                //RequestParams params = new RequestParams("http://www.jxy-edu.com/AjaxServlet");
                String url = "http://api.dagoogle.cn/news/get-news?tableNum=1&pagesize=10";
                RequestParams params = new RequestParams(url);
                params.addQueryStringParameter("keyword",keyword);
                params.addQueryStringParameter("currentPage","1");
                params.addQueryStringParameter("size","10");
                // 需要开启网络权限
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("jxy", "返回的数据为:" + result);
                        // 调用xutils3的http框架,访问远程数据并且返回结果
                        function.onCallBack(result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(Callback.CancelledException cex) {
                        Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFinished() {

                    }
                });

            }

        });
    }
}
