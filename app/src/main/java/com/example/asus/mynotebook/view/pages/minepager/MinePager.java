package com.example.asus.mynotebook.view.pages.minepager;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.model.GuidePage;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bumptech.glide.Glide;
import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.presenter.minepager.MyLogin;
import com.example.asus.mynotebook.presenter.minepager.UpdatePwd;
import com.example.asus.mynotebook.utils.GuidFloat;
import com.example.asus.mynotebook.view.activity.NoteDetails;
import com.example.asus.mynotebook.view.activity.UpdateIcon;
import com.example.asus.mynotebook.view.activity.WriteNoteActivity;
import com.example.asus.mynotebook.view.interfaces.BasePager;

import org.litepal.tablemanager.Connector;

import java.io.File;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import shem.com.materiallogin.MaterialLoginView;


/**
 * Created by asus on 2018/1/20.
 */

public class MinePager extends BasePager {
    //我的页面

    private final FragmentManager mFragmentManager;

    private boolean bool_initView ;
    private CircleImageView circleImageView;
    private MaterialLoginView ml_login;
    private TextView accountName;
    private LinearLayout update_pwd;
    private LinearLayout login;
    private LinearLayout manager_login;
    private LinearLayout update_icon;
    private View dismissLogin;
    private LinearLayout loginExit;
    private final View view;
    private View dealuser;


    public MinePager(Activity activity, FragmentManager mFragmentManager) {
        super(activity);
        this.mFragmentManager = mFragmentManager;
        bool_initView = false;
        view = View.inflate(mactivity, R.layout.pager_mine, null);
        frame_Content_Layout.addView(view);
        initView(view);
    }

    @Override
    public void initData() {
        System.gc();

        initLogIn(view);
        update_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flags.currentAccount == -1) {

                    new SVProgressHUD(mactivity).showErrorWithStatus("未登录!", SVProgressHUD.SVProgressHUDMaskType.Clear);

                } else {

                    UpdatePwd.showDialog(mactivity, mFragmentManager);
                }
            }
        });

        GuidFloat.addGuide(mactivity,"GuidLogin",login,R.layout.view_guid_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyLogin(view).mlogin(mactivity);
            }
        });
        manager_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyLogin(view).managerlogin(mactivity);
            }
        });
        update_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flags.currentAccount != -1) {
                    mactivity.startActivity(new Intent(mactivity, UpdateIcon.class));
                } else {
                    new SVProgressHUD(mactivity).showErrorWithStatus("未登录，请先登录", SVProgressHUD.SVProgressHUDMaskType.Clear);
                }
            }
        });
        loginExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flags.currentAccount == -1){
                    new SVProgressHUD(mactivity).showErrorWithStatus("未登录!!", SVProgressHUD.SVProgressHUDMaskType.Clear);

                }else {
                    Flags.currentAccount = -1;
                    Flags.CURRENT_STATUS = 0;
                    Flags.USER = null;
                    circleImageView.setImageDrawable(mactivity.getResources().getDrawable(R.drawable.personal_image,null));
                    accountName.setText("");
                    new SVProgressHUD(mactivity).showSuccessWithStatus("已退出!", SVProgressHUD.SVProgressHUDMaskType.Clear);
                }
            }
        });
        dealuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flags.CURRENT_STATUS != 0){
                Intent intent = new Intent(mactivity, NoteDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("key","user");
                intent.putExtras(bundle);
                mactivity.startActivity(intent);
                } else {
                    new SVProgressHUD(mactivity).showErrorWithStatus("不是管理员不能查看!", SVProgressHUD.SVProgressHUDMaskType.Clear);

                }

            }
        });

        Bundle extras = mactivity.getIntent().getExtras();
        if (extras != null) {
            String icon = extras.getString("icon");
            Glide.with(mactivity).load(Uri.fromFile(new File(icon))).into(circleImageView);
        }
    }

    private void initView(View view) {
        Log.d("MinePager","初始化View");
        circleImageView = view.findViewById(R.id.circleImageView);
        accountName = view.findViewById(R.id.tv_accountName);
        ml_login = view.findViewById(R.id.ml_login);
        update_pwd = view.findViewById(R.id.update_pwd);
        login = view.findViewById(R.id.login_pwd);
        manager_login = view.findViewById(R.id.login_manager);
        update_icon = view.findViewById(R.id.login_updateicon);
        dismissLogin = view.findViewById(R.id.ib_dismisslogin);
        loginExit = view.findViewById(R.id.login_exit);
        dismissLogin.setVisibility(View.INVISIBLE);
        dealuser = view.findViewById(R.id.login_dealuser);
    }

    private void initLogIn(View view) {
        SQLiteDatabase db = Connector.getDatabase();
        new MyLogin(view).login(mactivity);
    }


}
