package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateContactAcitivity extends Activity {

    private Button submitButton;
    private EditText businessNumberField, nameField, primaryBusinessField, addressField, provinceField;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        submitButton = (Button) findViewById(R.id.submitButton);
        businessNumberField = (EditText) findViewById(R.id.businessNumber);
        nameField = (EditText) findViewById(R.id.name);
        primaryBusinessField = (EditText) findViewById(R.id.primaryBusiness);
        addressField = (EditText) findViewById(R.id.address);
        provinceField = (EditText) findViewById(R.id.province);

    }

    public void submitInfoButton(View v) {
        //each entry needs a unique ID
        String uid = appState.firebaseReference.push().getKey();
        String businessNumber = businessNumberField.getText().toString();
        if(!checkBusinessNumber(businessNumber))
            Toast.makeText(this, "Business number requires 9-digit number", Toast.LENGTH_SHORT).show();
        String name = nameField.getText().toString();
        if(!checkName(name))
            Toast.makeText(this, "Name requires 2-48 characters", Toast.LENGTH_SHORT).show();
        String primaryBusiness = primaryBusinessField.getText().toString();
        if(!checkPrimaryBusiness(primaryBusiness))
            Toast.makeText(this, "Primary business requires, {Fisher, Distributor, Processor, Fish Monger}", Toast.LENGTH_SHORT).show();
        String address = addressField.getText().toString();
        if(!checkAddress(address))
            Toast.makeText(this, "Address requires <50 characters", Toast.LENGTH_SHORT).show();
        String province = provinceField.getText().toString();
        if(!checkProvince(province))
            Toast.makeText(this, "Province requires, {AB, BC, MB, NB, NL, NS, NT, NU, ON, PE, QC, SK, YT}", Toast.LENGTH_SHORT).show();
        if(checkBusinessNumber(businessNumber)&&checkName(name)&&checkPrimaryBusiness(primaryBusiness)&&checkAddress(address)&&checkProvince(province)){
            Contact person = new Contact(uid, businessNumber, name, primaryBusiness, address, province);

            appState.firebaseReference.child(uid).setValue(person);
        }
        finish();


    }
    //check if input business number satisfies business rules
    public boolean checkBusinessNumber(String bNumber){
        boolean satisfied = true;
        if(bNumber.length()!=9) {
            satisfied = false;
        }
        return satisfied;
    }

    //check if input name satisfies business rules
    public boolean checkName(String name){
        boolean satisfied = true;

        if(name.length()<2 || name.length()>48) {
            satisfied = false;
        }
        return satisfied;
    }

    //check if input primary business satisfies business rules
    public boolean checkPrimaryBusiness(String primaryBusiness){
        boolean satisfied = true;
        ArrayList<String> business = new ArrayList<String>();
        business.add("fisher");
        business.add("distributor");
        business.add("processor");
        business.add("fish monger");
        String p = primaryBusiness.toLowerCase();
        if(!business.contains(p)) {
            satisfied = false;
        }
        return satisfied;

    }

    //check if input address satisfies business rules
    public boolean checkAddress(String address){
        boolean satisfied = true;
        if(address.length()>50) {
            satisfied = false;
        }
        return  satisfied;
    }

    //check if input province satisfies business rules
    public boolean checkProvince(String province){
        boolean satisfied = true;
        ArrayList<String> provinces = new ArrayList<String>();
        provinces.add("AB");
        provinces.add("BC");
        provinces.add("MB");
        provinces.add("NB");
        provinces.add("NL");
        provinces.add("NS");
        provinces.add("NT");
        provinces.add("NU");
        provinces.add("ON");
        provinces.add("PE");
        provinces.add("QC");
        provinces.add("SK");
        provinces.add("YT");
        provinces.add("");
        String p = province.toUpperCase();
        if(!provinces.contains(p)) {
            satisfied = false;
        }
        return  satisfied;
    }

}
