package com.jzbwlkj.hengyangdata.retrofit;

import com.jzbwlkj.hengyangdata.ui.bean.AboutBean;
import com.jzbwlkj.hengyangdata.ui.bean.CheckAlonePwdBean;
import com.jzbwlkj.hengyangdata.ui.bean.LoginBean;
import com.jzbwlkj.hengyangdata.ui.bean.MessageDetailBean;
import com.jzbwlkj.hengyangdata.ui.bean.MessageListBean;
import com.jzbwlkj.hengyangdata.ui.bean.NewsDetailBean;
import com.jzbwlkj.hengyangdata.ui.bean.NewsListBean;
import com.jzbwlkj.hengyangdata.ui.bean.OrderBean;
import com.jzbwlkj.hengyangdata.ui.bean.OrderDetailBean;
import com.jzbwlkj.hengyangdata.ui.bean.SlideDetailBean;
import com.jzbwlkj.hengyangdata.ui.bean.SlideListBean;
import com.jzbwlkj.hengyangdata.ui.bean.UserBean;
import com.jzbwlkj.hengyangdata.ui.bean.WaitOrderBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by admin on 2017/3/27.
 */

public interface Api {

    //获取验证码
    @FormUrlEncoded
    @POST("/api/public/sendsms")
    Observable<HttpResult<CommonBean>> sendsms(@Field("mobile") String mobile,
                                               @Field("type") String password);

    //注册
    @FormUrlEncoded
    @POST("/api/public/register")
    Observable<HttpResult<CommonBean>> register(@Field("mobile") String mobile,
                                                @Field("password") String password,
                                                @Field("code") String code,
                                                @Field("duli_pass") String duli_pass);

    //忘记密码
    @FormUrlEncoded
    @POST("/api/public/forgetpwd")
    Observable<HttpResult<CommonBean>> forgetpwd(@Field("mobile") String mobile,
                                                 @Field("password") String password,
                                                 @Field("code") String code);

    //修改密码
    @FormUrlEncoded
    @POST("/api/public/modifypwd")
    Observable<HttpResult<CommonBean>> modifypwd(@Field("token") String token,
                                                 @Field("mobile") String mobile,
                                                 @Field("password") String password,
                                                 @Field("oldpassword") String oldpassword);

    //修改独立密码
    @FormUrlEncoded
    @POST("/api/public/modifydulipwd")
    Observable<HttpResult<CommonBean>> modifydulipwd(@Field("token") String token,
                                                     @Field("mobile") String mobile,
                                                     @Field("olddulipass") String olddulipass,
                                                     @Field("duli_pass") String duli_pass);

    //忘记独立密码
    @FormUrlEncoded
    @POST("/api/public/forgetdulipwd")
    Observable<HttpResult<CommonBean>> forgetdulipwd(@Field("mobile") String mobile,
                                                     @Field("code") String code,
                                                     @Field("dulipass") String duli_pass);

    //登录
    @FormUrlEncoded
    @POST("/api/public/login")
    Observable<HttpResult<LoginBean>> login(@Field("mobile") String mobile,
                                            @Field("password") String password);

    //获取用户信息
    @FormUrlEncoded
    @POST("/api/user/getUserInfo")
    Observable<HttpResult<UserBean>> getUserInfo(@Field("token") String token);

    //首页轮播图列表
    @FormUrlEncoded
    @POST("/api/message/slideList")
    Observable<HttpResult<List<SlideListBean>>> slideList(@Field("token") String token);

    //轮播图详情
    @FormUrlEncoded
    @POST("/api/message/slideDetail")
    Observable<HttpResult<SlideDetailBean>> slideDetail(@Field("id") String id);

    //付费信息列表
    @FormUrlEncoded
    @POST("/api/message/messageList")
    Observable<HttpResult<List<MessageListBean>>> messageList(@Field("token") String token,@Field("page") int page);

    //付费信息详情
    @FormUrlEncoded
    @POST("/api/message/messageDetail")
    Observable<HttpResult<MessageDetailBean>> messageDetail(@Field("token") String token, @Field("id") String id);

    //关于我们
    @FormUrlEncoded
    @POST("/api/public/getAbout")
    Observable<HttpResult<AboutBean>> getAbout(@Field("token") String token);

    //提交订单
    @FormUrlEncoded
    @POST("/api/order/createOrder")
    Observable<HttpResult<OrderBean>> createOrder(@Field("token") String token, @Field("msg_id") String msg_id, @Field("paytype") String paytype);

    //获取消息列表
    @FormUrlEncoded
    @POST("/api/news/index")
    Observable<HttpResult<NewsListBean>> newsIndex(@Field("token") String token);

    //消息详情
    @FormUrlEncoded
    @POST("/api/news/detail")
    Observable<HttpResult<NewsDetailBean>> newsDetail(@Field("token") String token, @Field("id") String id);

    //清空消息
    @FormUrlEncoded
    @POST("/api/news/empty_news")
    Observable<HttpResult<CommonBean>> clearNews(@Field("token") String token);

    //搜索
    @FormUrlEncoded
    @POST("/api/message/serch")
    Observable<HttpResult<List<MessageListBean>>> search(@Field("key") String key);

    //订单列表
    @FormUrlEncoded
    @POST("/api/order/orderList")
    Observable<HttpResult<List<WaitOrderBean>>> waitOrderList(@Field("token") String token, @Field("type") String type);

    //订单详情
    @FormUrlEncoded
    @POST("/api/order/orderDetails")
    Observable<HttpResult<OrderDetailBean>> orderDetails(@Field("token") String token, @Field("id") String id, @Field("duli_pass") String duli_pass);

    //订单详情
    @FormUrlEncoded
    @POST("/api/order/orderDetails")
    Observable<HttpResult<CheckAlonePwdBean>> checkAlonePwd(@Field("token") String token, @Field("id") String id, @Field("duli_pass") String duli_pass);
}