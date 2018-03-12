package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DetailViewActivity extends Activity {

    private EditText businessNumberField, nameField, primaryBusinessField, addressField, provinceField;
    Contact receivedPersonInfo;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        appState = ((MyApplicationData)this.getApplicationContext());
        receivedPersonInfo = (Contact)getIntent().getSerializableExtra("Contact");

        businessNumberField = (EditText) findViewById(R.id.businessNumberDetail);
        nameField = (EditText) findViewById(R.id.nameDetail);
        primaryBusinessField = (EditText) findViewById(R.id.primaryBusinessDetail);
        addressField = (EditText) findViewById(R.id.addressDetail);
        provinceField = (EditText) findViewById(R.id.provinceDetail);

        if(receivedPersonInfo != null){
            businessNumberField.setText(receivedPersonInfo.businessNumber);
            nameField.setText(receivedPersonInfo.name);
            primaryBusinessField.setText(receivedPersonInfo.primaryBusiness);
            addressField.setText(receivedPersonInfo.address);
            provinceField.setText(receivedPersonInfo.province);
        }
    }

    public void updateContact(View v){
        CreateContactAcitivity create = new CreateContactAcitivity();

        String uid = receivedPersonInfo.uid;
        //new contact information
        String newBusinessNumber = businessNumberField.getText().toString();
        if(!create.checkBusinessNumber(newBusinessNumber))
            Toast.makeText(this, "Business number requires 9-digit number", Toast.LENGTH_SHORT).show();
        String newName = nameField.getText().toString();
        if(!create.checkName(newName))
            Toast.makeText(this, "Name requires 2-48 characters", Toast.LENGTH_SHORT).show();

        String newPrimaryBusiness = primaryBusinessField.getText().toString();
        if(!create.checkPrimaryBusiness(newPrimaryBusiness))
            Toast.makeText(this, "Primary business requires, {Fisher, Distributor, Processor, Fish Monger}", Toast.LENGTH_SHORT).show();

        String newAddress = addressField.getText().toString();
        if(!create.checkAddress(newAddress))
            Toast.makeText(this, "Address requires <50 characters", Toast.LENGTH_SHORT).show();

        String newProvince = provinceField.getText().toString();
        if(!create.checkProvince(newProvince))
            Toast.makeText(this, "Province requires, {AB, BC, MB, NB, NL, NS, NT, NU, ON, PE, QC, SK, YT}", Toast.LENGTH_SHORT).show();

        if(create.checkBusinessNumber(newBusinessNumber)&&create.checkName(newName)&&create.checkPrimaryBusiness(newPrimaryBusiness)&&create.checkAddress(newAddress)&&create.checkProvince(newProvince)) {
            //create a new contact object
            Contact newContact = new Contact(uid, newBusinessNumber, newName, newPrimaryBusiness, newAddress, newProvince);
            //update new business contact to firebase
            appState.firebaseReference.child(uid).setValue(newContact);
        }
        finish();
    }

    public void eraseContact(View v)
    {
        String uid = receivedPersonInfo.uid;
        appState.firebaseReference.child(uid).removeValue();
        finish();;
    }
}
