package com.indyzalab.quest;


import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AndroidUtils
{
    private static Typeface jsNoklaeTypeFace;
    private static Typeface robotoTypeFace;
    private static Typeface robotoTypeFaceLight;
    private static Typeface robotoTypeFaceThin;

    public static void setNoklaeFont (Context context, View view)
    {
        if (jsNoklaeTypeFace == null)
        {
            jsNoklaeTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/JS_Noklae.ttf");
        }
        setFont(view, jsNoklaeTypeFace);
    }


    public static void setRobotoFont (Context context, View view)
    {
        if (robotoTypeFace == null)
        {
            robotoTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Regular.ttf");
        }
        setFont(view, robotoTypeFace);
    }
    
    
    public static void setRobotoLightFont (Context context, View view)
    {
        if (robotoTypeFaceLight == null)
        {
            robotoTypeFaceLight = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Light.ttf");
        }
        setFont(view, robotoTypeFaceLight);
    }
    public static void setRobotoThinFont (Context context, View view)
    {
        if (robotoTypeFaceThin == null)
        {
            robotoTypeFaceThin = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Thin.ttf");
        }
        setFont(view, robotoTypeFaceThin);
    }
    private static void setFont (View view, Typeface robotoTypeFace)
    {
        if (view instanceof ViewGroup)
        {
            for (int i = 0; i < ((ViewGroup)view).getChildCount(); i++)
            {
                setFont(((ViewGroup)view).getChildAt(i), robotoTypeFace);
            }
        }
        else if (view instanceof TextView)
        {
            ((TextView) view).setTypeface(robotoTypeFace);
        }
    }
    
}
