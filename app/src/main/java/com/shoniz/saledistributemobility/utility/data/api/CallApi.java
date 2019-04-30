package com.shoniz.saledistributemobility.utility.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class CallApi<T> {

    private String baseUrl;

    public CallApi(String url){
        this.baseUrl = url;
    }

    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build();
//    private static OkHttpClient getInstance() {
//        return okHttpClient;
//    }

    public Response Post(String route, Object object) throws IOException  {

        Gson gson = new Gson();
        String MIME_JSON = "application/json";
        RequestBody body = RequestBody.create(MediaType.parse(MIME_JSON), gson.toJson(object));

        Request request = new Request.Builder()
                .url(baseUrl + route)
                .post(body)
                .build();

        return okHttpClient.newCall(request).execute();

    }
   /* String Post2(Context context, String route, Object object) throws IOException {
        HttpURLConnection httpCon=null;
        OutputStream os=null;
        BufferedReader br=null;
        try {

            httpCon = (HttpURLConnection) ((new URL(baseUrl + route).openConnection()));
            httpCon.setDoOutput(true);
            httpCon.setRequestProperty("Content-Type", MIME_JSON);
            httpCon.setRequestProperty("Accept", MIME_JSON);
            httpCon.setRequestMethod("POST");
            httpCon.setReadTimeout( 600000 *//*milliseconds*//* );
            httpCon.setConnectTimeout( 15000 *//* milliseconds *//* );
            httpCon.connect();
            os = httpCon.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            Gson gson = new Gson();
            writer.write(gson.toJson(object));
            writer.flush();
            writer.close();
            int responseCode=httpCon.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(httpCon.getInputStream(),"UTF-8"));
                String line ;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                return  sb.toString();
            }
            else {
                 throw new ApiNetworkException(context,httpCon);
            }

        } finally {
            if(httpCon!=null)
               httpCon.disconnect();

            if(os!=null)
                os.close();
            if(br!=null)
                br.close();
        }

    }*/
    public T Get(String route, Object object) throws IOException {
        String MIME_JSON = "application/json";
        RequestBody body = RequestBody.create(MediaType.parse(MIME_JSON), new Gson().toJson(object));
        Request request = new Request.Builder()
                .url(baseUrl +route)
                .post(body)
                .build();
        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();
        Response r =  okHttpClient.newCall(request).execute();
        Type listType = new TypeToken<T>(){}.getType();
        return gson.fromJson(r.body().charStream(), listType);
    }
}
