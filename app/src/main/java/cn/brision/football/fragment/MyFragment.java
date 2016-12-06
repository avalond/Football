package cn.brision.football.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.example.zane.easyimageprovider.builder.ImageProviderBuilder;
import com.example.zane.easyimageprovider.builder.core.EasyImage;
import com.example.zane.easyimageprovider.provider.listener.OnGetImageListener;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.activity.logins.LoginActivity;
import cn.brision.football.activity.logins.LogoutActivity;
import cn.brision.football.activity.my.MyFollowsActivity;
import cn.brision.football.activity.my.MyYuyueActivity;
import cn.brision.football.data.MatchAction;
import cn.brision.football.leancloud.MyUser;
import cn.brision.football.model.BaseInfo;
import cn.brision.football.utils.Const;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.PreferencesManager;
import cn.brision.football.utils.StringReplaceUtils;
import cn.brision.football.utils.ToastUtil;
import cn.brision.football.view.roundedimageview.RoundedImageView;

/**
 * Created by wangchengcheng on 16/8/2.
 * 我的页面
 */
public class MyFragment extends BaseFragment {

    @Bind(R.id.uers_name)
    TextView userName;
    @Bind(R.id.user_logo)
    RoundedImageView userLogo;

    private static final int FOLLOWS_REQUEST_CODE = 0;
    private static final int YUYUE_REQUEST_CODE = 1;
    private EasyImage easyImage;
    private MyLoginStatusReceiver mReceiver;
    private MatchAction matchAction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my);
        onRegisterReceiver();
        matchAction = dataManger.getMatchAction();
    }

    private boolean isLoging() {
        PreferencesManager pm = PreferencesManager.getInstance(getActivity());
        String accessToken = pm.get("Access_token", "");

        return accessToken.length() > 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initView() {
        if (isLoging()) {
            MyUser currentUser = (MyUser) MyUser.getCurrentUser();
            String nick = currentUser.getNickName();
            String user_number = PreferencesManager.getInstance(getActivity()).get("user_number");
            userName.setText(!TextUtils.isEmpty(nick) ? nick :StringReplaceUtils.replacePhoneNumber(user_number));
            String avatar = currentUser.getAvatar();
            if (!TextUtils.isEmpty(avatar))
                GlideUtils.get(getActivity()).getImage(avatar, userLogo, R.anim.fade_in);
            else
                userLogo.setImageResource(R.mipmap.user);
        } else {
            userName.setText("点击登录");
            userLogo.setImageResource(R.mipmap.user);
        }
    }

    @OnClick(R.id.my_setting)
    public void settingClick(View view) {
        Intent intent = new Intent(getActivity(), LogoutActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.my_follows)
    public void myFollowsClick(View view) {
        if (!isLoging()) {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), FOLLOWS_REQUEST_CODE);
        } else {
            Intent intent = new Intent(getActivity(), MyFollowsActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.my_yuyue)
    public void myYuyueClick(View view) {
        if (!isLoging()) {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), YUYUE_REQUEST_CODE);
        } else {
            Intent intent = new Intent(getActivity(), MyYuyueActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.user_rl)
    public void myLogoClick(View view) {

        if (!isLoging()) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        // 使用不带theme的构造器，获得的dialog边框距离屏幕仍有几毫米的缝隙。
        // Dialog dialog = new Dialog(getActivity());
        final Dialog dialog = new Dialog(getActivity(), R.style.personalInformation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // must be called before set content
        View v = View.inflate(getActivity(), R.layout.dialog_person_informention, null);
        dialog.setContentView(v);
        TextView tvspeace = (TextView) v.findViewById(R.id.tv_speace);
        TextView tvdismiss = (TextView) v.findViewById(R.id.tv_dismiss);
        TextView photograph = (TextView) v.findViewById(R.id.tv_photograph);
        TextView name = (TextView) v.findViewById(R.id.tv_name);
        TextView photoAlbums = (TextView) v.findViewById(R.id.tv_photo_albums);
        LinearLayout ll = (LinearLayout) v.findViewById(R.id.ll_photo);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM);

        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        //p.height = (int) (d.getHeight() * 0.33); // 高度设置为屏幕的0.6
        p.verticalMargin = 0.02f;
        p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.65
        p.alpha = 1.0f;
        dialogWindow.setAttributes(p);
        dialogWindow.setAttributes(lp);

        photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCamera();
                easyImage.execute();
                dialog.cancel();
            }
        });
        tvdismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        photoAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAlbum();
                easyImage.execute();
                dialog.cancel();
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_information_nickname, null);
                TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
                final EditText etMessage = (EditText) view.findViewById(R.id.tv_message);
                final ImageView ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
                TextView btnDelete = (TextView) view.findViewById(R.id.btn_delete);
                TextView notDelete = (TextView) view.findViewById(R.id.btn_not_delete);
                tvTitle.setText("昵称");
                etMessage.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        ivDelete.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                showDefault(userName.getText().toString(), etMessage);
                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etMessage.setText("");
                        ivDelete.setVisibility(View.GONE);
                    }
                });
                btnDelete.setText("取消");
                notDelete.setText("完成");
                builder.setView(view);
                final AlertDialog create = builder.create();
                create.setCanceledOnTouchOutside(false);
                WindowManager.LayoutParams lp = create.getWindow().getAttributes();
                lp.alpha = 1.0f;
                create.getWindow().setAttributes(lp);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        create.dismiss();
                        ivDelete.setVisibility(View.GONE);
                    }
                });
                notDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String etNickname = etMessage.getText().toString().trim();
                        if (!TextUtils.isEmpty(etNickname)) {
                            create.cancel();
                            //tvUpdata.setTextColor(Color.BLACK);
                            saveNick(etNickname);
                            Map<String, String> stringStringMap = new HashMap<String, String>();
                            stringStringMap.put("nick", etNickname);
                            updateProfile(stringStringMap);
                        } else {

                            ToastUtil.showToastDefault(getActivity(), "请输入昵称");
                        }
                    }
                });

                create.show();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    private void showDefault(String text, EditText et) {

        if (!TextUtils.isEmpty(text)) {
            et.setText(text);
        }

    }

    @Override
    protected void getOkhttpData() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            boolean isLogin = data.getBooleanExtra("isLogin", false);
            switch (requestCode) {
                case FOLLOWS_REQUEST_CODE:
                    if (isLogin) {
                        Intent intent = new Intent(getActivity(), MyFollowsActivity.class);
                        startActivity(intent);
                    }
                    break;

                case YUYUE_REQUEST_CODE:
                    if (isLogin) {
                        Intent intent = new Intent(getActivity(), MyYuyueActivity.class);
                        startActivity(intent);
                    }
                    break;

                default:
                    easyImage.onActivityResult(requestCode, resultCode, data);
                    break;
            }

        } else {
            if (requestCode != FOLLOWS_REQUEST_CODE && requestCode != YUYUE_REQUEST_CODE)
                easyImage.onActivityResult(requestCode, resultCode, data);
        }

    }


    /**
     * 相册中获取图片
     */
    private void initAlbum() {
        easyImage = EasyImage.creat(new ImageProviderBuilder().with(this).useAlbum().useCrop(500, 500)
                .setGetImageListener("uri", new OnGetImageListener() {
                    @Override
                    public void getDataBack(Object o) {
                        Uri uri = (Uri) o;
                        userLogo.setImageBitmap(BitmapFactory.decodeFile(uri.getPath()));
                        upPhoto(uri);
                    }
                }));
    }

    /**
     * 相机中获取图片
     */
    private void initCamera() {
        easyImage = EasyImage.creat(new ImageProviderBuilder().with(this).useCamera().useCrop(500, 500)
                .setGetImageListener("uri", new OnGetImageListener() {
                    @Override
                    public void getDataBack(Object o) {
                        Uri uri = (Uri) o;
                        Log.d("getDataBack", uri.toString());
                        userLogo.setImageBitmap(BitmapFactory.decodeFile(uri.getPath()));
                        upPhoto(uri);
                    }
                }));
    }

    private void upPhoto(final Uri uri) {
        try {
            final AVFile file = AVFile.withAbsoluteLocalPath("LeanCloud.png", uri.getPath());
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    Log.d("imageFiles", file.getUrl());//返回一个唯一的 Url 地址
                    Map<String, String> stringStringMap = new HashMap<String, String>();
                    stringStringMap.put("headImage", file.getUrl());
                    updateProfile(stringStringMap);
                    savePhoto(file.getUrl());
                }
            });
        } catch (FileNotFoundException e) {
            String message = e.getMessage();
            Log.d("message", message);
            e.printStackTrace();
        }
    }

    private void savePhoto(final String url) {
        MyUser.getCurrentUser().put("avatar", url);
        MyUser.getCurrentUser().saveEventually(new SaveCallback() {
            @Override
            public void done(AVException e) {
                GlideUtils.get(getActivity()).getImage(url, userLogo, R.anim.fade_in);
            }
        });
    }

    private void saveNick(final String nick) {
        MyUser.getCurrentUser().put("nick", nick);
        MyUser.getCurrentUser().saveEventually(new SaveCallback() {
            @Override
            public void done(AVException e) {
                userName.setText(nick);
            }
        });
    }

    private void updateProfile(Map<String, String> map) {
        matchAction.updateProfile(map, new MatchAction.UpdateProfileListener() {
            @Override
            public void updateProfileData(BaseInfo mBaseInfo) {
                if (!(mBaseInfo != null && mBaseInfo.getStatus() == 200))
                    ToastUtil.showToastOnce(getActivity(), "操作失败");
            }
        });
    }

    class MyLoginStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Const.LOGIN_STATE_CHANGE)) {
                initView();
            }
        }
    }

    private void onRegisterReceiver() {
        if (mReceiver == null) {
            mReceiver = new MyLoginStatusReceiver();
            IntentFilter wxIntentFilter = new IntentFilter();
            wxIntentFilter.addAction(Const.LOGIN_STATE_CHANGE);
            mContext.registerReceiver(mReceiver, wxIntentFilter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(mReceiver);
    }
}
