package org.iii.more.agent;

import org.iii.more.common.Logs;

import java.util.HashMap;

/**
 * Created by Jugo on 2018/3/26
 */

public class SdkData
{
    private class Item
    {
        public String ID;
        public String Name;
        public String Version;
        public boolean isAuthorization;
        
        public Item()
        {
            isAuthorization = false;
        }
        
        public Item(String strId, String strName, String strVersion, boolean bAuth)
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
    
    private HashMap<String, Item> sdkItems = null;
    
    public SdkData()
    {
        sdkItems = new HashMap<String, Item>();
    }
    
    public void addItem(String strId, Item item)
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
        
        sdkItems.put(strId, new Item(strId, strName, strVersion, bAuth));
    }
    
    public void printData()
    {
        for (Object key : sdkItems.keySet())
        {
            Logs.showTrace(key + " : " + sdkItems.get((String) key).toString());
        }
    }
}
