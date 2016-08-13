package me.majiajie.pagerbottomtabstrip;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

/**
 * 开放的导航按钮构建入口
 */
public class TabItemBuilder
{
    private TabItem mTabItem;

    private Context mContext;
    private TabItemBuild mTabItemBuild;
    public TabItemBuilder(@NotNull Context context){
        mContext = context;
    }

    public TabItemBuild create()
    {
        mTabItem = new TabItem(mContext);
        mTabItemBuild = mTabItem.builder(this);
        return mTabItemBuild;
    }
    public TabItemBuild getTabItemBuild(){
        return mTabItemBuild;
    }

    protected TabItem getTabItem()
    {
        return mTabItem;
    }
}
