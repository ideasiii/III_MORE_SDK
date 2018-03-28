package org.iii.more.eventlistener;


import org.json.JSONObject;

public abstract class EventListener
{
    public static interface Callback
    {
        public void onEvent(JSONObject jsonObject);
    }
}
