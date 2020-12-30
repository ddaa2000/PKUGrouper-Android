package com.e.pkugrouper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LogInRegisterAdpater extends FragmentStateAdapter {
    final int pageCount = 2;
    private String pageTitles[] = {"登录","注册"};
    public LogInRegisterAdpater(@Nullable FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0)
            return new LoginFragment();
        else if(position == 1)
            return new RegisterFragment();
        return new LoginFragment();
    }

    //@Override
    //public CharSequence getPageTitle(int position) {
    //    return pageTitles[position];
    //}

    @Override
    public int getItemCount() {
        return pageCount;
    }
}
