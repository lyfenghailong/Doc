/*
 * Project: ROVI
 * 
 * Copyright (C) 2011, TOSHIBA Corporation.
 * 
 *  This file defined a program view for CoverView.
 * 
 * @version 1.0.2
 * @author Feng Hailong
 * @date   2012/12/04
 */

package jp.co.toshiba.ome.android.JPMediaGuide.cover;

import jp.co.toshiba.ome.android.JPMediaGuide.R;
import jp.co.toshiba.ome.android.JPMediaGuide.cache.IAsyncView;
import jp.co.toshiba.ome.android.JPMediaGuide.constant.UIConstant;
import jp.co.toshiba.ome.android.JPMediaGuide.data.CoverDataManager;
import jp.co.toshiba.ome.android.JPMediaGuide.data.ResouceManager;
import jp.co.toshiba.ome.android.JPMediaGuide.data.bean.CoverItemInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;

/**
 * This View is to show the program.
 * 
 * @author Feng Hailong
 * 
 * @date 2012/12/04
 */
public class ProgramView3 extends View implements IAsyncView
{
    /**
     * The margin to left of the widgets.
     */
    private int      m_marginLeft      = 10;
    
    /**
     * The margin to top of the widgets.
     */
    private int      m_marginTop       = 10;
    
    /**
     * The margin to right of the widgets.
     */
    private int      m_marginRight     = 0;
    
    /**
     * The width of this view.
     */
    private int      m_layout_width    = 200;
    
    /**
     * The height of this view.
     */
    private int      m_layout_height   = 242;
    
    /**
     * The width of the image view.
     */
    private int      m_image_with      = 0;
    
    /**
     * The height of the image view.
     */
    private int      m_image_height    = 130;
    
    /**
     * The text size.
     */
    private int      m_textSize        = 18;
    
    /**
     * The spacing of widgets 
     */
    private int      m_spacing           = 5;
    
    /**
     * The title.
     */
    private String   m_title           = null;
    
    /**
     * The channel title.
     */
    private String   m_channelTitle    = null;
    
    /**
     * The void type.
     */
    private String   m_vodType         = null;
    
    /**
     * The airing time.
     */
    private String   m_airingTime      = null;
    
    /**
     * The Drawable of video recording icon.
     */
    private Drawable m_videoIcon       = null;
    
    /**
     * The Drawable of audio recording icon.
     */
    private Drawable m_audioIcon       = null;
    
    /**
     * The Drawable of view back ground.
     */
    private Drawable m_background      = null;
    
    /**
     * The bitmap for the image view.
     */
    private Drawable   m_programIcon   = null;
    
    /**
     * The rect of audio recording.
     */
    private Rect     m_arRect          = null;
    
    /**
     * The rect of audio recording.
     */
    private Rect     m_vrRect          = null;
    
    /**
     * The rect of back ground.
     */
    private Rect     m_backgroundRect  = null;
    
    /**
     * The rect of image view.
     */
    private Rect     m_imageViewRect   = null;
    
    /**
     * The paint which use to draw text.
     */
    private Paint    m_textPaint       = null;
    
    /**
     * Show the bottom line or not.
     */
    boolean          m_isShowBottomLine = false;
    
    /**
     * The constructor.
     * 
     * @param context
     * 
     * @param attrs
     * 
     * @param defStyle
     */
    public ProgramView3(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initialize();
    }
    
    /**
     * The constructor.
     * 
     * @param context
     * 
     * @param attrs
     */
    public ProgramView3(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize();
    }
    
    /**
     * The constructor.
     * 
     * @param context
     */
    public ProgramView3(Context context)
    {
        super(context);
        initialize();
    }
    
    /**
     * Initialize the data.
     */
    private void initialize()
    {
        Resources resources = this.getResources();
        
        m_layout_width = (int) resources.getDimension(R.dimen.shelf_program_width_lan);
        m_layout_height = (int) resources.getDimension(R.dimen.shelf_program_height);
        m_image_with = m_layout_width - m_marginLeft - m_marginRight;
        m_textSize = (int) resources.getDimension(R.dimen.shelf_program_textsize);
        this.setLayoutParams(new AbsListView.LayoutParams(m_layout_width,m_layout_height));
        
        m_textPaint = new Paint();
        m_textPaint.setColor(Color.WHITE);
        m_textPaint.setTextAlign(Align.LEFT);
        m_textPaint.setTextSize(18);
        m_textPaint.setAntiAlias(true);
        
        m_videoIcon = resources.getDrawable(R.drawable.icon_rec_n);
        m_audioIcon = resources.getDrawable(R.drawable.icon_reserve_n);
        m_background = new ColorDrawable(android.R.color.transparent);
        
        m_vrRect = new Rect(m_marginLeft, m_marginTop, m_marginLeft + 20, m_marginTop + 20);
        m_arRect = new Rect(m_marginLeft, m_marginTop + 20, m_marginLeft + 20, m_marginTop + 40);
        m_backgroundRect = new Rect(0, 0, m_layout_width, m_layout_height);
        m_imageViewRect = new Rect(m_marginLeft, m_marginTop + m_textSize * 2
                + m_spacing * 2, m_layout_width - m_marginRight,
                m_marginTop + m_textSize * 2 + m_spacing * 2 + m_image_height);
    }
    
    /**
     * Called to draw this view.
     */
    @Override
    protected void onDraw(Canvas canvas)
    {
        drawBackground(canvas);
        
        drawArIcon(canvas);
        
        drawVrIcon(canvas);
        
        drawImageView(canvas);
        
        drawTitle(canvas);
        
        drawVodTypeTitle(canvas);
        
        drawChannelTitle(canvas);
        
        drawAiringTimeTitle(canvas);
        
        drawBottomLine(canvas);
    }
    
    /**
     * Set the data to the view.
     * 
     * @param info
     */
    public void setData(CoverItemInfo info ,Configuration configuration)
    {
        m_imageViewRect = new Rect(m_marginLeft, m_marginTop + m_textSize * 2
                + m_spacing * 2, m_layout_width - m_marginLeft, m_marginTop
                + m_textSize * 2 + m_spacing * 2 + m_image_height);
        
        m_programIcon = null;
        m_videoIcon = null;
        m_audioIcon = null;
        
        m_title = info.getTitle();
        m_channelTitle = info.getVendorDisplayString();
        if (info.getkeyWord() == CoverDataManager.VOD_KEY)
        {
            m_airingTime = null;
        }
        else
        {
            m_airingTime   = info.getDisplayedAiringTime();
        }
        setItemVodType(info.getHeadendID());
        
        ResouceManager resMgr = ResouceManager.getInstance();

        if (info.getVideoRecorded())
        {
            m_videoIcon = info.isVideoRecording() ? resMgr.getRecordNormalDrawable() : resMgr.getRecordDisableDrawable();
        }
        
        if (info.getAudioRecorded())
        {
            m_audioIcon = info.isAudioRecording() ? resMgr.getRemindNormalDrawable() : resMgr.getRecordDisableDrawable();
        }
        
        m_isShowBottomLine = info.getIsShowBottonLine();
        
        Resources resources = this.getResources();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            m_layout_width = (int) resources.getDimension(R.dimen.shelf_program_width_lan);
        }
        else
        {
            m_layout_width = (int) resources.getDimension(R.dimen.shelf_program_width_por);
        }
        
        LayoutParams params = getLayoutParams();
        params.width = m_layout_width;
        setLayoutParams(params);
        m_backgroundRect = new Rect(0, 0, m_layout_width, m_layout_height);
        m_image_with = m_layout_width - m_marginLeft - m_marginRight;
        m_imageViewRect = new Rect(m_marginLeft, m_marginTop + m_textSize * 2
                + m_spacing * 2, m_image_with + m_marginLeft - m_marginRight,
                m_marginTop + m_textSize * 2 + m_spacing * 2 + m_image_height);
       
        
        invalidate();
    }
    
    /**
     * The drawable.
     */
    private Drawable m_drawable = null;
    
    /**
     * Called it to set the bitmap.
     */
    @Override
    public void setImageBitmap(Bitmap bitmap)
    {
        if (null != bitmap)
        {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            resizeRect(m_imageViewRect, width, height);
            m_programIcon = new BitmapDrawable(bitmap);
        }
        
        super.invalidate();
    }
    
    @Override
    public void setImageDrawable(Drawable drawable)
    {
        
    }
    
    @Override
    public void setAsyncDrawable(Drawable drawable)
    {
        m_drawable = drawable;
    }
    
    @Override
    public Drawable getAsyncDrawable()
    {
        return m_drawable;
    }
    
    /**
     * Draw the back ground.
     * 
     * @param canvas
     */
    private void drawBackground(Canvas canvas)
    {
        m_background.setBounds(m_backgroundRect);
        m_background.draw(canvas);
    }
    
    /**
     * Draw the audio recording icon.
     * 
     * @param canvas
     */
    private void drawArIcon(Canvas canvas)
    {
        if (null != m_audioIcon)
        {
            m_audioIcon.setBounds(m_arRect);
            m_audioIcon.draw(canvas);
        }
    }
    
    /**
     * Draw the video recording icon.
     * 
     * @param canvas
     */
    private void drawVrIcon(Canvas canvas)
    {
        if (null != m_videoIcon)
        {
            m_videoIcon.setBounds(m_vrRect);
            m_videoIcon.draw(canvas);
        }
    }
    
    /**
     * Draw the image view.
     * 
     * @param canvas
     */
    private void drawImageView(Canvas canvas)
    {
        if (null != m_programIcon)
        {
            m_programIcon.setBounds(m_imageViewRect);
            m_programIcon.draw(canvas);
        }
    }
    
    /**
     * Draw the title.
     * 
     * @param canvas
     */
    private void drawTitle(Canvas canvas)
    {
        int width = m_layout_width - m_marginLeft - m_marginRight - 20 - m_spacing;
        int textWidth = Math.round(m_textPaint.measureText(m_title));
        if (textWidth <= width)
        {
            canvas.drawText(m_title, m_marginLeft + 20 + m_spacing, m_marginTop
                    + m_textSize, m_textPaint);
        }
        else
        {
            int index = m_textPaint.breakText(m_title, 0, m_title.length(),
                    true, width, null);
            canvas.drawText(m_title.substring(0, index - 1), m_marginLeft + 20
                    + m_spacing, m_marginTop + m_textSize, m_textPaint);
            drawSingleLineText(canvas, m_title.substring(index - 1), width,
                    m_marginLeft + 20 + m_spacing, m_marginTop + m_textSize * 2
                            + m_spacing);
        }
    }
    
    /**
     * Draw the title.
     * 
     * @param canvas
     */
    private void drawChannelTitle(Canvas canvas)
    {
        int width = m_layout_width - m_marginLeft - m_marginRight;
        drawSingleLineText(canvas, m_channelTitle, width, m_marginLeft,
                m_marginTop + m_textSize * 3 + m_spacing * 3 + m_image_height);
    }
    
    /**
     * Draw the title.
     * 
     * @param canvas
     */
    private void drawVodTypeTitle(Canvas canvas)
    {
        int width = m_layout_width - m_marginLeft - m_marginRight;
        drawSingleLineText(canvas, m_vodType, width, m_marginLeft, m_marginTop
                + m_textSize * 3 + m_spacing * 3 + m_image_height);
    }
    
    /**
     * Draw the title.
     * 
     * @param canvas
     */
    private void drawAiringTimeTitle(Canvas canvas)
    {
        int width = m_layout_width - m_marginLeft - m_marginRight;
        m_textPaint.setTextSize(13);
        drawSingleLineText(canvas, m_airingTime, width, m_marginLeft,
                m_marginTop + m_textSize * 4 + m_spacing * 4 + m_image_height);
        m_textPaint.setTextSize(m_textSize);
    }
    
    /**
     * Draw the text in one line.
     * 
     * @param canvas
     * 
     * @param str
     * 
     * @param width
     * 
     * @param x
     * 
     * @param y
     */
    private void drawSingleLineText(Canvas canvas, String str, int width,
            float x, float y)
    {
        if (TextUtils.isEmpty(str))
        {
            return;
        }
        
        Paint textPaint = m_textPaint;
        int appandWith = Math.round(m_textPaint.measureText("..."));
        int textWidth = (int) textPaint.measureText(str);
        
        if (textWidth > width)
        {
            int index = textPaint.breakText(str, 0, str.length() - 1, true,
                    width - appandWith, null);
            String drawText = (str.substring(0, index) + "...");
            canvas.drawText(drawText, 0, drawText.length(), x, y, m_textPaint);
        }
        else
        {
            canvas.drawText(str, 0, str.length(), x, y, m_textPaint);
        }
    }
    
    /**
     * The line paint.
     */
    private Paint m_linePaint = new Paint();
    
    /**
     * Draw the bottom line.
     * 
     * @param canvas
     */
    private void drawBottomLine(Canvas canvas)
    {
        if (m_isShowBottomLine)
        {
            m_linePaint.setColor(Color.WHITE);
            m_linePaint.setStrokeWidth(2);
            canvas.drawLine(0, m_layout_height-2, m_layout_width, m_layout_height - 2, m_linePaint);
        }
    }
    
    /**
     * Set the void type.
     *
     * @param headenedId
     */
    private void setItemVodType(int headenedId)
    {
        m_vodType = null;
        
        if (headenedId == Integer.parseInt(UIConstant.VOD_HEADEND_TSUTAYA_TV))
        {
            m_vodType = this.getResources().getString(R.string.filtertype_tsutayatv);
        }
        else if (headenedId == Integer.parseInt(UIConstant.VOD_HEADEND_UNEXT))
        {
            m_vodType = this.getResources().getString(R.string.filtertype_unext);
        }
    }
    
    /**
     * Resize the rect according to the width and height of the bitmap. 
     * Its like "centerInside" of ImageView. But the rect's width must
     * be longer than his height.
     * 
     * @param rect
     * 
     * @param width The width of the bitmap.
     * 
     * @param height The height of the bitmap.
     */
    private void resizeRect(Rect rect, float width, float height)
    {
        float maxWidth = rect.right - rect.left;
        float maxHeight = rect.bottom - rect.top;
        
        if (maxWidth < width && maxHeight < height)
        {
            float ration = width / height;
            float rectRation = maxWidth / maxHeight;
            if (ration > rectRation)
            {
                width = maxWidth;
                height = ration * width;
            }
            else
            {
                width = width * maxHeight / height;
                height = maxHeight;
            }
        }
        
        int hSpacing = (int) ((maxWidth - width) / 2);
        int vSpacing = (int) ((maxHeight - height) / 2);
        m_imageViewRect.left = m_imageViewRect.left + hSpacing;
        m_imageViewRect.top = m_imageViewRect.top + vSpacing;
        m_imageViewRect.right = m_imageViewRect.right - hSpacing;
        m_imageViewRect.bottom = m_imageViewRect.bottom - vSpacing;
    }
}

