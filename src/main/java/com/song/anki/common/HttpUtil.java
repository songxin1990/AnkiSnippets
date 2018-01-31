package com.song.anki.common;

import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 要设置他妈的超时时间我草。
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static final OkHttpClient client = new OkHttpClient();
    public static String post(String url, String content)
            throws ClientProtocolException, IOException {
        return post(url, "application/json", content);
    }

    public static String post(String url, String contentType, String content)
            throws ClientProtocolException, IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", contentType);
        httppost.setEntity(new InputStreamEntity(IOUtils.toInputStream(content, "UTF-8")));

        // 设置超时时间
        RequestConfig requestConfig =
                RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).build();// 设置请求和传输超时时间
        httppost.setConfig(requestConfig);

        // 执行httppost
        CloseableHttpResponse response = httpClient.execute(httppost);

        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        String respContent = null;
        if (entity != null) {
            respContent = IOUtils.toString(entity.getContent(), "UTF-8");
        }
        if (statusCode != 200) {
            logger.error("HTTP POST fail,statusCode=>" + statusCode + ",content=>" + respContent);
            return null;
        }
        return respContent;
    }

    public static String post2(String url,String content) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type","text/json" );

        BasicHttpEntity entity = new BasicHttpEntity();
        entity.setContent(IOUtils.toInputStream(content, "UTF-8"));
        httpPost.setEntity(entity);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response2 = httpclient.execute(httpPost);
        return IOUtils.toString(response2.getEntity().getContent(), "UTF-8");
    }

    public static  String okhttpPost(String url,String content) throws IOException {
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, content);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String get(String url, Map<String, String> params)
            throws ClientProtocolException, IOException {
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            sb.append("&").append(key).append("=").append(val);
        }
        return get(sb.toString());
    }

    public static String get(String url) throws ClientProtocolException, IOException {
        HttpGet get = new HttpGet(url);

        get.addHeader("Content-Type", "application/json");
        CloseableHttpClient httpclient = HttpClients.createDefault();

        CloseableHttpResponse response = httpclient.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        String respContent = null;
        if (entity != null) {
            respContent = IOUtils.toString(entity.getContent(), "UTF-8");
        }
        if (statusCode != 200) {
            logger.error("HTTP GET fail,statusCode=>" + statusCode + ",content=>" + respContent);
            return null;
        }
        return respContent;
    }

    public static HttpResponse postForm(String url, Map<String, String> nameValuePair) throws IOException {
        HttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost();

        int timeout = 10 * 1000;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
        httpPost.setConfig(requestConfig);

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setURI(URI.create(url));

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : nameValuePair.entrySet()) {
            String name = entry.getKey();
            String val = entry.getValue();
            nvps.add(new BasicNameValuePair(name, val));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        HttpResponse response = client.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        String resp = IOUtils.toString(response.getEntity().getContent());
        if (statusCode != 200) {
            throw new RuntimeException("postForm error,statusCode:" + statusCode + ",resp:" + resp);
        }
        return response;
    }

    public static void main(String[] args) throws IOException {
        //String url = "https://500px.com/photo/3446133/evolution-by-szilvia-pap-kutasi";
        //String url = "https://500px.com/photo/388736/oo-by-besim-mazhiqi";

//    String url = "https://500px.com/popular";
        String url = "https://www.lagou.com/jobs/3713477.html";
//   String respContent =  get(url);
//   System.out.println(respContent);

//    Request Header:
//    Request URL:https://www.lagou.com/jobs/positionAjax.json?city=%E6%B7%B1%E5%9C%B3&needAddtionalResult=false
//    Request Method:POST
//    Status Code:200 OK
//    Remote Address:106.75.72.62:443
        String url2 = "https://www.lagou.com/jobs/positionAjax.json?city=%E6%B7%B1%E5%9C%B3&needAddtionalResult=false";
        String resp2 = post(url2, "");
        System.out.println(resp2);

    }
}
