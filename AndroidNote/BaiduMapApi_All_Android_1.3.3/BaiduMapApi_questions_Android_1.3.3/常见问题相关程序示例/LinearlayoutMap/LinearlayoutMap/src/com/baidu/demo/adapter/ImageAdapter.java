package com.baidu.demo.adapter;

import android.content.Context;  
import android.view.View;  
import android.view.ViewGroup;  
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;  
import android.widget.GridView;  
import android.widget.ImageView;  

public class ImageAdapter extends BaseAdapter {  
    private Context mContext;   
    private ImageView[] imgItems;  
    private int[] selResId;
    private int[] picIds;
    private int[] highlightIds;
    private int focusId = -1;
    public ImageAdapter(Context c,int[] picIds, int[] selResId, int [] highlightIds) {   
        mContext = c;   
        this.selResId=selResId;
        this.picIds = picIds;
        this.highlightIds = highlightIds;
        imgItems=new ImageView[picIds.length];  
        for(int i=0;i<picIds.length;i++) {  
            imgItems[i] = new ImageView(mContext);   
            imgItems[i].setLayoutParams(new GridView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));//设置ImageView宽高    
            imgItems[i].setAdjustViewBounds(false);   
            imgItems[i].setImageResource(picIds[i]);   
        }  
    }   
   
    public int getCount() {   
        return imgItems.length;   
    }   
   
    public Object getItem(int position) {   
        return position;   
    }   
   
    public long getItemId(int position) {   
        return position;   
    }   
   
    /** 设置暂时选中的效果 */    
    public void setHighLight(int index) {
        if (index != focusId) {
            imgItems[index].setImageResource(highlightIds[index]);  //设置选中的样式
        }
    }
    
    /** 取消暂时选中的效果 */
    public void cancelHighLight(int index) {
        if (index != focusId) {
            imgItems[index].setImageResource(picIds[index]);  //恢复未选中的样式
        }
    }
    
    /**  
     * 设置选中的效果  
     */    
    public void setFocus(int index) {   
        if(index != focusId) {
            imgItems[index].setImageResource(selResId[index]);//设置选中的样式
            if (focusId != -1) {
                imgItems[focusId].setImageResource(picIds[focusId]);//恢复未选中的样式
            }
            focusId = index;
        }
    } 
    
    /**
     * 设置图片
     * @param index 位置索引
     * @param resid 资源ID
     */
    public void setImageResource(int index , int resid){
    	imgItems[index].setImageResource(resid);
    }
      
    public View getView(int position, View convertView, ViewGroup parent) {   
        ImageView imageView;   
        if (convertView == null) {   
            imageView=imgItems[position];  
        } else {   
            imageView = (ImageView) convertView;   
        }   
        return imageView;   
    }   
}   
