package com.eyesmart.testapplication.android;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Http请求工具类
 * Created by Tian on 2017-1-19 0019.
 */

public class HttpUtils {

    private static final int TIMEOUT = 8000;

    private static final int MESSAGE_SUCCESS = 0;
    private static final int MESSAGE_FAILURE = -1;

    public interface HttpCallBack {
        public String mResult = null;

        void onSuccess(String result);

        void onFailure(String msg);
    }

//**********************************************************************************************************

    /**
     * Get请求,运用HttpURLConnection
     *
     * @param urlStr
     * @param httpCallBack
     */
    public static void sendGetRequest(final String urlStr, final HttpCallBack httpCallBack) {
        if (urlStr == null || urlStr.isEmpty() || httpCallBack == null) {
            throw new NullPointerException();
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(urlStr);//URL的长度不能超过2048个字符
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");                       //请求方法，默认为get
                    conn.setConnectTimeout(TIMEOUT);                    //链接超时
                    conn.setReadTimeout(TIMEOUT);                       //读取超时
                    conn.setDoInput(true);                              //是否可输入，默认为true
                    conn.setDoOutput(false);                            //是否可输出，默认为false（POST请求时应设为true）
                    conn.setRequestProperty("accept", "*/*");           //添加请求头
                    conn.setRequestProperty("connection", "Keep-Alive");
                    //conn.setRequestProperty("action", NETWORK_GET);   //可自定义请求头

                    //conn.setUseCaches(false);                         //禁用网络缓存
                    conn.connect();                                     //不必显式调用，当调用conn.getInputStream()方法时内部也会自动调用connect方法
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        byte[] bytes = getBytesByInputStream(is);
                        String resultStr = new String(bytes, "UTF-8");
                        httpCallBack.onSuccess(resultStr);
                    } else {
                        httpCallBack.onFailure("ResponseCode: " + conn.getResponseCode());
                    }
                } catch (IOException e) {
                    httpCallBack.onFailure("Exception: " + e.toString());
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect(); // 释放资源
                    }
                }
            }
        }.start();
    }

    /**
     * Post请求,运用HttpURLConnection
     *
     * @param urlStr
     * @param requestStr
     * @param httpCallBack
     */
    public static void sendPostRequest(final String urlStr, final String requestStr, final HttpCallBack httpCallBack) {
        if (urlStr == null || urlStr.isEmpty() || requestStr == null || requestStr.isEmpty() || httpCallBack == null) {
            throw new NullPointerException();
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(urlStr);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(TIMEOUT);
                    conn.setReadTimeout(TIMEOUT);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("connection", "Keep-Alive");
                    //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("charset", "utf-8");

                    OutputStream os = conn.getOutputStream();           //获取conn的输出流
                    byte[] requestBody = requestStr.getBytes("UTF-8");
                    os.write(requestBody);                              //将请求体写入到conn的输出流中
                    os.flush();                                         //记得调用输出流的flush方法
                    os.close();                                         //关闭输出流
                    conn.connect();
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        byte[] bytes = getBytesByInputStream(is);
                        String resultStr = new String(bytes, "UTF-8");
                        httpCallBack.onSuccess(resultStr);
                    } else {
                        httpCallBack.onFailure("ResponseCode: " + conn.getResponseCode());
                    }
                } catch (IOException e) {
                    httpCallBack.onFailure("Exception: " + e.toString());
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect(); // 释放资源
                    }
                }
            }
        }.start();
    }

//**********************************************************************************************************

    public void testOkHttp() throws IOException {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {               //拦截器，添加，移除、转换请求头或响应头信息
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()      //统一请求头
                        .header("Authorization", "")
                        .addHeader("key", "value")
                        .build();
                return chain.proceed(request);
            }
        });
        client = client.newBuilder()
                .addInterceptor(new LoggingInterceptor())
                .cache(new Cache(new File(""), 10 * 1024 * 1024))   //设置缓存
                .build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "json");
        FormBody formBody = new FormBody.Builder()                  //POST提交键值对
                .add("key", "value")
                .build();
        Request request = new Request.Builder()
                .url("http://api.zhifangw.cn/")
                .get()                                              //GET请求（默认）
                .post(requestBody)                                  //POST请求
                .header("User-Agent", "OkHttp Headers.java")        //添加请求头（覆盖）
                .addHeader("Accept", "application/json; q=0.5")     //不覆盖
                .build();

        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {                       //异步请求
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                okhttp3.Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                System.out.println(response.body().string());
            }
        });
        okhttp3.Response response = call.execute();                 //同步请求

        call.cancel();                                              //必要时取消请求
    }

//**********************************************************************************************************

    public interface TestService {
        @GET("testBean/{id}")
        Call<ResponseBody> getTestBean(@Path("id") int id);

        @POST("testBean/1")
        @Headers("Cache-Control: max-age=640000")
        Call<ResponseBody> getTestBean2(@Body String body, @Query("sort") String sort);

        //@HTTP可替代HTTP请求方法  @HTTP(method = "POST", path = "testBean/1", hasBody = true)

        //{}中的表示待定参数,路径中的参数使用@Path(“XXX”)
        //@Headers(“”)请求头设置
        //@Query用于查询参数如同”?”作用
        //@QueryMap用于有多个查询参数
        //@Body请求体(主要是用于@POST方式)
        //@FormUrlEncoded表明请求体是一个form表单
        //@Field注明表单中的键,方法的参数就是值
        //@Multipart表明请求体是一个支持文件上传的form表单
        //@Streaming表明响应体的数据用流的形式返回（用于数据量比较大时）
    }

    public static void testRetrofit() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.zhifangw.cn/")     //baseUrl必须以"/"结尾
                .build();
        TestService testService = retrofit.create(TestService.class);
        Call<ResponseBody> call = testService.getTestBean(1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        Response<ResponseBody> response = call.execute();
        call.cancel();
    }

//**********************************************************************************************************

    public static void testVolley(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);   //请求队列,可以只有一个（对应Application）, 也可以是一个Activity对应一个网络请求队列
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, "http://api.zhifangw.cn/",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //JsonObjectRequest、JsonArrayRequest获取Json数据
        //ImageRequest加载图片
        queue.add(stringRequest);
    }

//**********************************************************************************************************

    /**
     * 从InputStream中读取数据，转换成byte数组，最后关闭InputStream
     *
     * @param is
     * @return
     * @throws IOException
     */
    private static byte[] getBytesByInputStream(InputStream is) throws IOException {
        byte[] bytes = null;
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        try {
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bis.close();
            bos.close();
        }
        return bytes;
    }

    //读取请求头
    private static String getReqeustHeader(HttpURLConnection conn) {
        //https://github.com/square/okhttp/blob/master/okhttp-urlconnection/src/main/java/okhttp3/internal/huc/HttpURLConnectionImpl.java#L236
        Map<String, List<String>> requestHeaderMap = conn.getRequestProperties();
        Iterator<String> requestHeaderIterator = requestHeaderMap.keySet().iterator();
        StringBuilder sbRequestHeader = new StringBuilder();
        while (requestHeaderIterator.hasNext()) {
            String requestHeaderKey = requestHeaderIterator.next();
            String requestHeaderValue = conn.getRequestProperty(requestHeaderKey);
            sbRequestHeader.append(requestHeaderKey);
            sbRequestHeader.append(":");
            sbRequestHeader.append(requestHeaderValue);
            sbRequestHeader.append("\n");
        }
        return sbRequestHeader.toString();
    }

    //读取响应头
    private static String getResponseHeader(HttpURLConnection conn) {
        Map<String, List<String>> responseHeaderMap = conn.getHeaderFields();
        int size = responseHeaderMap.size();
        StringBuilder sbResponseHeader = new StringBuilder();
        for (int i = 0; i < size; i++) {
            String responseHeaderKey = conn.getHeaderFieldKey(i);
            String responseHeaderValue = conn.getHeaderField(i);
            sbResponseHeader.append(responseHeaderKey);
            sbResponseHeader.append(":");
            sbResponseHeader.append(responseHeaderValue);
            sbResponseHeader.append("\n");
        }
        return sbResponseHeader.toString();
    }

    private static class LoggingInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Logger logger = Logger.getLogger("LoggingInterceptor");
            Request request = chain.request();

            long t1 = System.nanoTime();//请求发起的时间
            logger.info(String.format("发送请求 %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            okhttp3.Response response = chain.proceed(request);

            long t2 = System.nanoTime();//收到响应的时间

            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);

            logger.info(String.format("接收响应: [%s] %n状态码:[%s]%n返回json:【%s】 %.3fms%n%s",
                    response.request().url(),
                    response.code(),
                    responseBody.string(),
                    (t2 - t1) / 1e6d,
                    response.headers()));

            return response;
        }
    }
}