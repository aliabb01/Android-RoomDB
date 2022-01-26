package ali.app.graphicsdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView personsListTextView;
    private Button button;
    private EditText firstNameText, lastNameText, phoneNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppActivity.getDatabase();
        personsListTextView = (TextView) findViewById(R.id.txt_list);
        firstNameText = (EditText) findViewById(R.id.firstNameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);
        phoneNumberText = (EditText) findViewById(R.id.phoneNumberText);
        button = (Button) findViewById(R.id.button);

        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_name, 0, 0, 0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = firstNameText.getText().toString().trim();
                String surname = lastNameText.getText().toString().trim();
                String phoneNumber = phoneNumberText.getText().toString().trim();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) || TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getApplicationContext(), "Name/Surname/Phone Number should not be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Person person = new Person();
                    person.setName(name);
                    person.setSurname(surname);
                    person.setPhoneNumber(phoneNumber);

                    db.personDAO().insert(person);
                    Toast.makeText(getApplicationContext(), "Saved successfully!", Toast.LENGTH_SHORT).show();

                    firstNameText.setText("");
                    lastNameText.setText("");
                    phoneNumberText.setText("");

                    firstNameText.requestFocus();
                    getPersonList();
                }
            }
        });
    }

    private void getPersonList() {
        personsListTextView.setText("");
        List<Person> personList = db.personDAO().getAllPersons();

        for(Person person : personList) {
            personsListTextView.append(person.getName() + " " +
                    person.getSurname() + " : " + person.getPhoneNumber());
            personsListTextView.append("\n");
        }
    }
}