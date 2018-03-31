package org.iii.more.agent;

import org.iii.more.common.Logs;
import org.iii.more.restapiclient.Config;
import org.iii.more.restapiclient.Response;
import org.iii.more.restapiclient.RestApiClient;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * Created by Jugo on 2018/3/27
 */
public abstract class Agent
{
    private static RestApiClient restApiClient = new RestApiClient();
    private static SdkData sdkData = new SdkData();
    private static final String mstrURL = "http://192.168.0.105/api/sdkSync.jsp";
    
    public static synchronized void syncData()
    {
        restApiClient.setResponseListener(responseListener);
        Logs.showTrace("[Agent] syncData Start");
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("1", "a");
        Response response = new Response();
        int nResponse_id = restApiClient.HttpPost(mstrURL, Config.HTTP_DATA_TYPE.X_WWW_FORM, param, response);
        Logs.showTrace("[Agent] https response id: " + nResponse_id);
    }
    
    private static RestApiClient.ResponseListener responseListener = new RestApiClient.ResponseListener()
    {
        @Override
        public void onResponse(JSONObject jsonObject)
        {
            try
            {
                if (null != jsonObject)
                {
                    if (!jsonObject.isNull("code"))
                    {
                        if (HttpURLConnection.HTTP_OK == jsonObject.getInt("code"))
                        {
                            if (!jsonObject.isNull("data"))
                            {
                                JSONObject jsonData = new JSONObject(jsonObject.getString("data"));
                                Logs.showTrace("[Agent] onResponse Data: " + jsonData);
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Logs.showError("[Agent] responseListener Exception: " + e.getMessage());
            }
        }
    };
}
