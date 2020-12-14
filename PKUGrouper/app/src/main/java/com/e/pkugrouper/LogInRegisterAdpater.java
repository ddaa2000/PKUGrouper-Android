package com.e.pkugrouper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LogInRegisterAdpater extends FragmentPagerAdapter {
    final int pageCount = 2;
    private String pageTitles[] = {"登录","注册"};
    public LogInRegisterAdpater(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new LoginFragment();
        else if(position == 1)
            return new RegisterFragment();
        return new LoginFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
