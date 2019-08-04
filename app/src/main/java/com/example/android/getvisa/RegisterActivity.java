package com.example.android.getvisa;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText loginId,createPassword,confirmPassword,empCode,projectCode;
    String  loginId1="",createPassword1="",confirmPassword1="",empCode1="",projectCode1;
    String userIdC,empCodeC;
    Button register;

    private ViewPager signupFormPager ;    //pager object
    RegisterFormPagerAdapter rPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //View pager object
        signupFormPager = (ViewPager) findViewById(R.id.signupFormPager);
        rPagerAdapter = new RegisterFormPagerAdapter(getSupportFragmentManager());
        signupFormPager.setAdapter(rPagerAdapter);
        signupFormPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        signupFormPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if(position==0)
                    {
                        nextButton3Action();
                    }
                    if(position==1)
                    {
                        validateLoginDetails("employee");
                        nextRegisterEmpAction();
                    }
                    if(position==2)
                    {
                        validateLoginDetails("manager");
                        nextRegisterManagerAction();
                    }
                    if(position==3)
                    {
                        doneButtonAction();
                    }
            }

            @Override
            public void onPageSelected(int position) {
                if(position==0)
                {
                    nextButton3Action();
                }
                if(position==1)
                {
                    nextRegisterEmpAction();

                }
                if(position==2)
                {
                    nextRegisterManagerAction();

                }
                if(position==3)
                {
                    doneButtonAction();
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void validateLoginDetails(final String who){
        if(who.equals("employee")) {
            loginId = (EditText) findViewById(R.id.loginIdEmp);
            createPassword = (EditText) findViewById(R.id.createPassEmp);
            confirmPassword = (EditText) findViewById(R.id.confirmPassEmp);
            empCode = (EditText) findViewById(R.id.emp_codeEmp);
        }
        else {
            loginId = (EditText) findViewById(R.id.loginIdMan);
            createPassword = (EditText) findViewById(R.id.createPassMan);
            confirmPassword = (EditText) findViewById(R.id.confirmPassMan);
            empCode = (EditText) findViewById(R.id.emp_codeMan);
            projectCode = (EditText) findViewById(R.id.project_codeMan);
        }
        loginId1 = loginId.getText().toString();
        createPassword1 = createPassword.getText().toString();
        confirmPassword1 = confirmPassword.getText().toString();
        loginId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loginId1 = loginId.getText().toString();
                if(who.equals("employee")) {
                    new CheckUserId().execute(loginId1,"employee");
                }
                else{
                    new CheckUserId().execute(loginId1,"manager");

                }
            }
        });

        createPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                createPassword1 = createPassword.getText().toString();
                if(createPassword1.length()<8)
                {
                    createPassword.setError("Too short, must be atleast 8 characters long");
                }
                else
                {
                    createPassword.setError(null);
                }

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                confirmPassword1 = confirmPassword.getText().toString();
                createPassword1 = createPassword.getText().toString();
                if(!(confirmPassword1.equals(createPassword1)))
                {
                   confirmPassword.setError("Passwords don't match");
                }
                else
                {
                    confirmPassword.setError(null);

                }
            }
        });
        empCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                empCode1 = empCode.getText().toString();
                if(who.equals("employee")) {
                    new CheckEmpCode().execute(empCode1,"employee");
                }
                else{
                    new CheckEmpCode().execute(empCode1,"manager");
                }
            }
        });

        if(who.equals("manager")){
            projectCode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    projectCode1 = projectCode.getText().toString();
                    new CheckProjectCode().execute(projectCode1);
                }
            });
        }


    }




    public void nextRegisterEmpAction(){

        loginId = (EditText) findViewById(R.id.loginIdEmp);
        createPassword = (EditText) findViewById(R.id.createPassEmp);
        confirmPassword = (EditText) findViewById(R.id.confirmPassEmp);
        empCode = (EditText) findViewById(R.id.emp_codeEmp);

        register = (Button) findViewById(R.id.register_emp);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginId1 = loginId.getText().toString();
                createPassword1 = createPassword.getText().toString();
                confirmPassword1 = confirmPassword.getText().toString();
                empCode1 = empCode.getText().toString();
                int flag=0;
                if(loginId1.equals(""))
                {
                    loginId.setError("Login id field cannot be empty");

                }

                if(createPassword1.equals(""))
                {
                    createPassword.setError("Create password field cannot be empty");

                }
                if(confirmPassword1.equals(""))
                {
                    confirmPassword.setError("Confirm password field cannot be empty");

                }
                if(empCode1.equals(""))
                {
                    empCode.setError("Employee code field cannot be empty");

                }

                if(loginId.getError()==null && createPassword.getError()==null && confirmPassword.getError()==null &&empCode.getError()==null)
                {
                    new AddingUserAndSendingVerificationMail().execute(loginId1,createPassword1,empCode1,"no","first time");
                    signupFormPager.setCurrentItem(3);
                }
            }
        });


    }


    public void nextRegisterManagerAction(){
        loginId = (EditText) findViewById(R.id.loginIdEmp);
        createPassword = (EditText) findViewById(R.id.createPassEmp);
        confirmPassword = (EditText) findViewById(R.id.confirmPassEmp);
        empCode = (EditText) findViewById(R.id.emp_codeEmp);
        projectCode = (EditText) findViewById(R.id.project_codeMan);
        register = (Button) findViewById(R.id.register_man);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginId1 = loginId.getText().toString();
                createPassword1 = createPassword.getText().toString();
                confirmPassword1 = confirmPassword.getText().toString();
                empCode1 = empCode.getText().toString();
                projectCode1 = projectCode.getText().toString();
                int flag=0;
                if(loginId1.equals(""))
                {
                    loginId.setError("Login id field cannot be empty");

                }

                if(createPassword1.equals(""))
                {
                    createPassword.setError("Create password field cannot be empty");

                }
                if(confirmPassword1.equals(""))
                {
                    confirmPassword.setError("Confirm password field cannot be empty");

                }
                if(empCode1.equals(""))
                {
                    empCode.setError("Employee code field cannot be empty");

                }

                if(loginId.getError()==null && createPassword.getError()==null && confirmPassword.getError()==null &&empCode.getError()==null &&projectCode.getError()==null)
                {
                    new AddingManagerAndSendingVerificationMail().execute(loginId1,createPassword1,empCode1,projectCode1,"no");
                    signupFormPager.setCurrentItem(3);
                }
            }
        });
    }


    public void nextButton3Action(){
        register = (Button) findViewById(R.id.nextBtn3);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupFormPager.setCurrentItem(1);
            }
        });
    }
    public void doneButtonAction(){

    }

    class CheckUserId extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String LOGIN_URL = "http://192.168.0.101/user_id_check.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        private static final String TAG_USER_ID = "user_id";


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", args[0]);
                params.put("who", args[1]);

                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "GET", params);

                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(final JSONObject json) {

             int success = 0;
             String message = "";

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (json != null) {

                        try {

                            if(json.getString(TAG_MESSAGE).equals("true"))
                                loginId.setError("Login id not available");
                            else
                                loginId.setError(null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }
            });




        }

    }


    class CheckEmpCode extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String LOGIN_URL = "http://192.168.0.101/emp_code_check.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        private static final String TAG_EXIST = "already";


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                params.put("emp_code", args[0]);
                params.put("who", args[1]);

                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "GET", params);

                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(final JSONObject json) {

            int success = 0;
            String message = "";



            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (json != null) {

                        try {

                            if(json.getString(TAG_MESSAGE).equals("false")) {
                                empCode.setError("Invalid employee code");
                            }
                            else if(json.getString(TAG_EXIST).equals("present")){
                                empCode.setError("Employee code already exists");
                            }
                            else
                                empCode.setError(null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }
            });
        }

    }


    class CheckProjectCode extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String LOGIN_URL = "http://192.168.0.101/project_code_check.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        private static final String TAG_EXIST = "already";


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                params.put("project_code", args[0]);


                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "GET", params);

                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(final JSONObject json) {

            int success = 0;
            String message = "";


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (json != null) {

                        try {

                            if (json.getString(TAG_MESSAGE).equals("false")) {
                                projectCode.setError("Invalid employee code");
                            } else if (json.getString(TAG_EXIST).equals("present")) {
                                projectCode.setError("Employee code already exists");
                            } else
                                projectCode.setError(null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }
            });
        }

    }
class AddingUserAndSendingVerificationMail  extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();
        DbCheckActivity verificationMail = new DbCheckActivity();

        private String empEmail,msgBody,userIdForMail="",enUserId;
        private ProgressDialog pDialog;

        private static final String LOGIN_URL = "http://192.168.0.101/add_user_send_mail.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        private static final String TAG_ENUSERID =  "encrypted_user_id";
        private static final String TAG_EMPEMAIL = "user_email";



    @Override
        protected void onPreExecute() {

        }
        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", args[0]);
                params.put("password", args[1]);
                params.put("emp_code", args[2]);
                params.put("active", args[3]);
                params.put("login_status", args[4]);

                userIdForMail = args[0];
                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(final JSONObject json) {

                    if (json != null) {

                        try {

                            if(json.getInt(TAG_SUCCESS)==1) {
                                enUserId= json.getString(TAG_ENUSERID);
                                empEmail = json.getString(TAG_EMPEMAIL);
                                msgBody = "Hello "+userIdForMail+", \nYou have succesfully registered for GetVisa.\nPlease verify your email address and activate your account by clicking the following verification link :\n"
                                +"http://http://127.0.0.1/verify_user_mail.php?user_id="+enUserId+"\n\n\nThank You!";
                                verificationMail.sendMail(empEmail, "Email Verification", msgBody);

                            }
                            else
                              Toast.makeText(RegisterActivity.this,json.getInt(TAG_MESSAGE),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }

        }
    class AddingManagerAndSendingVerificationMail  extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();
        DbCheckActivity verificationMail = new DbCheckActivity();

        private String empEmail,msgBody,userIdForMail="",enUserId;
        private ProgressDialog pDialog;

        private static final String LOGIN_URL = "http://192.168.0.101/add_man_user_send_mail.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        private static final String TAG_ENUSERID =  "encrypted_user_id";
        private static final String TAG_EMPEMAIL = "user_email";



        @Override
        protected void onPreExecute() {

        }
        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", args[0]);
                params.put("password", args[1]);
                params.put("emp_code", args[2]);
                params.put("project_code", args[3]);
                params.put("active", args[4]);

                userIdForMail = args[0];
                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(final JSONObject json) {

            if (json != null) {

                try {

                    if(json.getInt(TAG_SUCCESS)==1) {
                        enUserId= json.getString(TAG_ENUSERID);
                        empEmail = json.getString(TAG_EMPEMAIL);
                        msgBody = "Hello "+userIdForMail+", \nYou have succesfully registered for GetVisa.\nPlease verify your email address and activate your account by clicking the following verification link :\n"
                                +"http://http://127.0.0.1/verify_man_user_mail.php?user_id="+enUserId+"\n\n\nThank You!";
                        verificationMail.sendMail(empEmail, "Email Verification", msgBody);

                    }
                    else
                        Toast.makeText(RegisterActivity.this,json.getInt(TAG_MESSAGE),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

    }


}



class RegisterFormPagerAdapter extends FragmentPagerAdapter {
    static  int progressOfPage=1;

    public RegisterFormPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if(position==0)
        {
            fragment = new ThirdSignupFragment();
        }
        if(position==1)
        {
            fragment = new FirstSignupFragment();

        }
        if(position==2)
        {
            fragment = new SecondSignupFragment();

        }
        if(position==3)
        {
            fragment = new FourthSignupFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 4;    // total number of pages in signup form
    }
}
