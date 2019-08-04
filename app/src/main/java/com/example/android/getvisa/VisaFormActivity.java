package com.example.android.getvisa;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

/*
An Activity for Filling Visa Form and Submitting it.
 */
public class VisaFormActivity extends AppCompatActivity {
    private ImageButton passIssueDatePicker, passExpiryDatePicker,reqFromDatePicker,reqToDatePicker;
    private EditText projectCode,passNumber,passIssueLocation,passIssueDate,passExpiryDate;
    private EditText visaCountry,visaEntry,reqFromDate,reqToDate;
    String passNumber1,passIssueLocation1,passIssueDate1,passExpiryDate1,projectCode1;
    String visaCountry1,visaEntry1,reqFromDate1,reqToDate1;
    private TextView errorDisplay;
    private int day_x,month_x,year_x,flag=0;
    static final int DIALOG_ID=0;
    private Button nextBtn,submitButton;


    private static final String empCode="EA1234"; //emp code of logged in emp.

    private ViewPager visaFormPager ;    //pager object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa_form);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        visaFormPager = (ViewPager) findViewById(R.id.visaFormPager);
        visaFormPager.setAdapter(new VisaFormPagerAdapter(getSupportFragmentManager()));
        visaFormPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position==0)
                {

                    nextButtonAction(); //action defining for next button
                    showAndInitializeDialogDate();//shows dialog and sets date when button is clicked
                }
                if (position==1)
                {

                    submitButtonAction(); //action defining for next button
                    showAndInitializeDialogVisaDate();//shows dialog and sets date when button is clicked
                }
            }

            @Override
            public void onPageSelected(int position) {
                 if (position==0)
                 {

                     nextButtonAction(); //action defining for next button
                     showAndInitializeDialogDate();//shows dialog and sets date when button is clicked
                 }
                if (position==1)
                {

                    submitButtonAction(); //action defining for next button
                    showAndInitializeDialogVisaDate();//shows dialog and sets date when button is clicked
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        Calendar cal = Calendar.getInstance();       //taking todays date
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);


    }

    private void submitButtonAction() {
        submitButton = (Button) findViewById(R.id.submitBtn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                visaCountry = (EditText) findViewById(R.id.visaCountry);
                visaEntry = (EditText) findViewById(R.id.visaEntry);
                reqFromDate = (EditText) findViewById(R.id.reqFromDate);
                reqToDate = (EditText) findViewById(R.id.reqToDate);


                visaCountry1 = visaCountry.getText().toString();
                visaEntry1 = visaEntry.getText().toString();
                reqFromDate1 = reqFromDate.getText().toString();
                reqToDate1 = reqToDate.getText().toString();
                if (visaCountry1.equals("") || visaEntry1.equals("") || reqFromDate1.equals("") || reqToDate1.equals("") ) {
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(VisaFormActivity.this);
                    a_builder.setMessage("Please fill all the required fields!")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Required Fields Missing");
                    alert.show();
                } else {

                    AlertDialog.Builder a_builder = new AlertDialog.Builder(VisaFormActivity.this);
                    a_builder.setMessage("You won't be able to make any changes later.Submit now ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new NewVisaApplication().execute(empCode,projectCode1,passNumber1,passIssueLocation1,passIssueDate1,passExpiryDate1,visaCountry1,visaEntry1,reqFromDate1,reqToDate1,"1");

                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }) ;
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Submit Confirmation");
                    alert.show();
                }
            }
        });
    }

    public void nextButtonAction(){

        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                projectCode = (EditText) findViewById(R.id.projectCode);
                passNumber = (EditText) findViewById(R.id.passportNumber);
                passIssueLocation = (EditText) findViewById(R.id.passLocation);
                passIssueDate = (EditText) findViewById(R.id.passIssueDate);
                passExpiryDate = (EditText) findViewById(R.id.passExpiryDate);


                projectCode1 = projectCode.getText().toString();
                 passNumber1 = passNumber.getText().toString();
                 passIssueLocation1= passIssueLocation.getText().toString();
                 passIssueDate1 = passIssueDate.getText().toString();
                 passExpiryDate1 = passExpiryDate.getText().toString();
                if(projectCode1.equals("")||passExpiryDate1.equals("")||passIssueDate1.equals("") || passNumber1.equals("") ||passIssueLocation1.equals("")) {
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(VisaFormActivity.this);
                    a_builder.setMessage("Please fill all the required fields!")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Required Fields Missing");
                    alert.show();
                }
                else {

                    int i = visaFormPager.getCurrentItem();
                    visaFormPager.setCurrentItem(i + 1);
                }
            }
        });
    }

    public void showAndInitializeDialogDate(){
        // following are objects from first form fragment
        passIssueDatePicker = (ImageButton) findViewById(R.id.datePickerIssue);
        passExpiryDatePicker = (ImageButton) findViewById(R.id.datePickerExpiry);
        passIssueDate = (EditText) findViewById(R.id.passIssueDate);
        passExpiryDate = (EditText) findViewById(R.id.passExpiryDate);
        flag=0;
        passIssueDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                showDialog(DIALOG_ID);


            }
        });
        flag=0;
        passExpiryDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=2;
                showDialog(DIALOG_ID);


            }
        });

    }
    public void showAndInitializeDialogVisaDate(){
        // following are objects from second form fragment
        reqFromDatePicker = (ImageButton) findViewById(R.id.reqFromDatePicker);
        reqToDatePicker = (ImageButton) findViewById(R.id.reqToDatePicker);
        reqFromDate = (EditText) findViewById(R.id.reqFromDate);
        reqToDate = (EditText) findViewById(R.id.reqToDate);
        flag=0;
        reqFromDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=3;
                showDialog(DIALOG_ID);


            }
        });
        flag=0;
        reqToDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=4;
                showDialog(DIALOG_ID);


            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID)
            return new DatePickerDialog(this,dPickerListener,year_x,month_x,day_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view,int year,int monthOfYear,int dayOfMonth){
                year_x=year;
                month_x=monthOfYear;
                day_x=dayOfMonth;
                month_x++;
                String date = day_x+"/"+month_x+"/"+year_x;
                if(flag==1)
                    passIssueDate.setText(date);
                if(flag==2)
                    passExpiryDate.setText(date);
                if(flag==3)
                    reqFromDate.setText(date);
                if(flag==4)
                    reqToDate.setText(date);
        }
    };

    /**
     * Background Async Task to Insert visa app
     */
    class NewVisaApplication extends AsyncTask<String, String, JSONObject> {

        private static final String TAG_SUCCESS = "success";
        private ProgressDialog pDialog;
        private static final String INSERT_EMPLOYEE_URL = "http://192.168.0.101/visa_app_add.php"; //ip should be your system's ip , do ifconfig and check
        private static final String TAG_MESSAGE = "message";
        JSONParser jsonParser = new JSONParser();

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(VisaFormActivity.this);
            pDialog.setMessage("Submitting Application...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Inserting employee
         */
        @Override
        protected JSONObject doInBackground(String... args) {



            try {
                // Building Parameters
                HashMap<String, String> params = new HashMap<>();

                params.put("emp_code",args[0]);
                params.put("project_code", args[1]);
                params.put("pass_no",args[2]);
                params.put("pass_issue_place", args[3]);
                params.put("pass_issue_date",args[4]);
                params.put("pass_expiry_date", args[5]);
                params.put("visa_country", args[6]);
                params.put("visa_entry", args[7]);
                params.put("req_from_date", args[8]);
                params.put("req_to_date", args[9]);
                params.put("visa_app_status", args[10]);



                // getting JSON Object
                // Note that insert visa app url accepts POST method
                Log.d("request", "starting");
                JSONObject json = jsonParser.makeHttpRequest(INSERT_EMPLOYEE_URL, "POST", params);

                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(JSONObject json) {
            // dismiss the dialog once done

            int success = 0;
            String message = "";

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (json != null) {
                Toast.makeText(VisaFormActivity.this, json.toString(), Toast.LENGTH_LONG).show();

                try {
                    success = json.getInt(TAG_SUCCESS);
                    message = json.getString(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (success == 1) {
                Log.d("Success!", message);
            }else{
                Log.d("Failure", message);
            }
        }

    }

}


class VisaFormPagerAdapter extends FragmentPagerAdapter{

    public VisaFormPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if(position==0)
        {
            fragment = new FirstFormFragment();
        }
        if(position==1)
        {
            fragment = new SecondFormFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;    // total number of pages in visa form
    }
}

