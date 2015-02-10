package com.ly.lottery.view.manager;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ly.lottery.R;
import com.ly.lottery.view.SecondUI;

/**
 * Created by Administrator on 2015/2/10.
 */
public class TitleManager {

    private static TitleManager titleManager = new TitleManager();
    private RelativeLayout commonContainer;
    private RelativeLayout loginContainer;
    private RelativeLayout unLoginContainer;
    private ImageView goback;// 返回
    private ImageView help;// 帮助
    private ImageView login;// 登录
    private TextView titleContent;// 标题内容
    private TextView userInfo;// 用户信息

    private TitleManager(){}

    public static TitleManager getInstance()
    {
        return titleManager;
    }

    public void init(Activity activity){
        commonContainer = (RelativeLayout) activity.findViewById(R.id.ii_common_container);
        loginContainer = (RelativeLayout) activity.findViewById(R.id.ii_login_title);
        unLoginContainer = (RelativeLayout) activity.findViewById(R.id.ii_unlogin_title);

        goback = (ImageView) activity.findViewById(R.id.ii_title_goback);
        help = (ImageView) activity.findViewById(R.id.ii_title_help);
        login = (ImageView) activity.findViewById(R.id.ii_title_login);

        titleContent = (TextView) activity.findViewById(R.id.ii_title_content);
        userInfo = (TextView) activity.findViewById(R.id.ii_top_user_info);
        setListener();
    }

    private void setListener(){
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("返回键");
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("帮助");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("登陆");
                //changeUI需要修改，不能传递对象，但是明确目标
                MiddleManager.getInstance().changeUI(SecondUI.class);
            }
        });
    }

    private void initTitle(){
        commonContainer.setVisibility(View.GONE);
        loginContainer.setVisibility(View.GONE);
        unLoginContainer.setVisibility(View.GONE);
    }

    //显示通用标题
    public void showCommonContainer(){
        initTitle();
        commonContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 显示未登录的标题
     */
    public void showUnLoginTitle() {
        initTitle();
        unLoginContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 显示登陆的标题
     */
    public void showLoginTitle() {
        initTitle();
        loginContainer.setVisibility(View.VISIBLE);

    }

    public void changeTitle(String title){
        titleContent.setText(title);
    }
}
