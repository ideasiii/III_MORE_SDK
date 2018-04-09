package org.iii.more.agent;

import org.iii.more.common.Logs;
import org.iii.more.common.Type;

import java.util.HashMap;

/**
 * Created by Jugo on 2018/3/26
 */

public class SdkData
{
    public static class SdkItem
    {
        public String ID;
        public String Name;
        public String Version;
        public boolean isAuthorization;
        
        public SdkItem()
        {
            isAuthorization = false;
        }
        
        public SdkItem(String strId, String strName, String strVersion, boolean bAuth)
        {
            ID = strId;
            Name = strName;
            Version = strVersion;
            isAuthorization = bAuth;
        }
        
        public String toString()
        {
            return (ID + ',' + Name + ',' + Version + ',' + isAuthorization);
        }
        
    }
    
    private HashMap<String, SdkItem> sdkItems = null;
    
    public SdkData()
    {
        sdkItems = new HashMap<String, SdkItem>();
    }
    
    public void addItem(String strId, SdkItem item)
    {
        if (null == sdkItems)
        {
            return;
        }
        sdkItems.put(strId, item);
    }
    
    public void addItem(String strId, String strName, String strVersion, boolean bAuth)
    {
        if (null == sdkItems)
        {
            return;
        }
        
        sdkItems.put(strId, new SdkItem(strId, strName, strVersion, bAuth));
    }
    
    public void printData()
    {
        for (Object key : sdkItems.keySet())
        {
            Logs.showTrace(key + " : " + sdkItems.get((String) key).toString());
        }
    }
    
    public int size()
    {
        return sdkItems.size();
    }
    
    public int getSdkItem(final String strSdkId, SdkItem sdkItem)
    {
        SdkItem item = sdkItems.get(strSdkId);
        if (null != item)
        {
            sdkItem.ID = item.ID;
            sdkItem.Name = item.Name;
            sdkItem.isAuthorization = item.isAuthorization;
            sdkItem.Version = item.Version;
            return Type.SUCCESS;
        }
        
        Logs.showTrace("[SdkData] getSdkItem SDK id: " + strSdkId + " is invalid");
        return Type.FAIL;
    }
    
}
