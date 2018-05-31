package com.jarvanmo.kal.text;

import android.graphics.Color;
import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.URLSpan;

/**
 * Created by mo on 17-7-10.
 *
 * @author mo
 */

public class URLSpanWithoutLine extends URLSpan {

    private int color = Color.BLUE;

    public URLSpanWithoutLine(String url, int color) {
        this(url);
        this.color = color;
    }

    public URLSpanWithoutLine(String url) {
        super(url);
    }


    protected URLSpanWithoutLine(Parcel in) {
        super(in);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        ds.setColor(color);
    }


    public static final Creator<URLSpanWithoutLine> CREATOR = new Creator<URLSpanWithoutLine>() {
        @Override
        public URLSpanWithoutLine createFromParcel(Parcel source) {
            return new URLSpanWithoutLine(source);
        }

        @Override
        public URLSpanWithoutLine[] newArray(int size) {
            return new URLSpanWithoutLine[size];
        }
    };
}
