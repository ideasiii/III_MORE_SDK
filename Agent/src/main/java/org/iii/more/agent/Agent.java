/****************************************************************
 * Created by Garbage Manufacturer (GM) in 2018/3/27
 * This is a garbage project.
 * The project contains many modules.
 * So all modules are junk.
 * Do not use these rubbish.
 * It will crash your system!
 ***************************************************************/

package org.iii.more.agent;

import org.iii.more.common.Config;
import org.iii.more.common.Logs;
import org.iii.more.common.Type;
import org.iii.more.restapiclient.HttpConfig;
import org.iii.more.restapiclient.Response;
import org.iii.more.restapiclient.RestApiClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;


public abstract class Agent
{
    private static RestApiClient restApiClient = new RestApiClient();
    private static SdkData sdkData = new SdkData();
    private static String smAppId = null;
    
    public static synchronized void syncData(final String strAppId)
    {
        smAppId = strAppId;
        restApiClient.setResponseListener(responseListener);
        Logs.showTrace("[Agent] syncData Start");
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("app_id", strAppId);
        Response response = new Response();
        int nResponse_id = restApiClient.HttpPost(Config.CONF_SDK_SYNC_URL, HttpConfig.HTTP_DATA_TYPE.X_WWW_FORM, param, response);
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
                                
                                if (!jsonData.isNull("sdks") && !jsonData.isNull("app_id"))
                                {
                                    String strAppId = jsonData.getString("app_id");
                                    if (0 == strAppId.compareTo(smAppId))
                                    {
                                        JSONArray jsonSdks = jsonData.getJSONArray("sdks");
                                        for (int i = 0; i < jsonSdks.length(); ++i)
                                        {
                                            JSONObject jsonSdk = jsonSdks.getJSONObject(i);
                                            sdkData.addItem(jsonSdk.getString("id"), jsonSdk.getString("name"), jsonSdk.getString("version"), jsonSdk.getBoolean("authorization"));
                                        }
                                        sdkData.printData();
                                    }
                                }
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
    
    public static boolean isAuth(final String strSdkId)
    {
        if (null != sdkData && 0 < sdkData.size())
        {
            SdkData.SdkItem sdkItem = new SdkData.SdkItem();
            if (Type.SUCCESS == sdkData.getSdkItem(strSdkId, sdkItem))
            {
                Logs.showTrace("[Agent] isAuth SDK id: " + sdkItem.ID + " isAuth: " + sdkItem.isAuthorization);
                return true;
            }
        }
        Logs.showTrace("[Agent] isAuth invalid SDK Data");
        return false;
    }
}
