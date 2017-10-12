package com.epicodus.boggle;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private GridView gridView;
    private ArrayList<String> userAnswers = new ArrayList<>();
    private String[] vowels = {"a", "e", "o", "u", "i"};
    private String[] consonants = {"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n",
            "p", "q", "r", "s", "t", "v", "w", "x", "y", "z"};
    private ArrayList<String> lettersList = new ArrayList<>();
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MAX_INPUT_LENGTH = 8;
    private static final int MIN_INPUT_LENGTH = 3;

    @Bind(R.id.submit_button) Button mSubmitButton;
    @Bind(R.id.edit_text_user_input) EditText mWordEditText;
    @Bind(R.id.show_list_button) Button mShowListButton;
    @Bind(R.id.grid_view) GridView mGridView;
    @Bind(R.id.timer_text) TextView mTimerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        int[] numArray = selectNumbersSumToEight();
        lettersList = selectLetters(numArray[0], numArray[1]);

        mGridView.setAdapter(new LettersAdapter(this, lettersList, R.layout.letter_activity));

        mSubmitButton.setOnClickListener(this);
        mShowListButton.setOnClickListener(this);

        CountDownTimer timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerTextView.setText(getText(R.string.seconds_left).toString() + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                mTimerTextView.setText(getText(R.string.timer_done).toString());
                mSubmitButton.setEnabled(false);
            }
        };
        timer.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_button:
                String userInputWord = mWordEditText.getText().toString();
                checkUserInput(userInputWord);
                break;
            case R.id.show_list_button:
                Intent intent = new Intent(MainActivity.this, WordActivity.class);
                intent.putStringArrayListExtra("words", userAnswers);
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }

    private ArrayList<String> selectLetters(int k, int m) {
        int vowelsInt;
        int consonantsInt;
        ArrayList<String> letters = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            vowelsInt = (int) Math.floor(Math.random() * (vowels.length - 1));
            letters.add(vowels[vowelsInt]);
        }

        for (int j = 0; j < m; j++) {
            consonantsInt = (int) Math.floor(Math.random() * (consonants.length - 1));
            letters.add(consonants[consonantsInt]);
        }
        return letters;
    }

    private int[] selectNumbersSumToEight() {
        int[] returnArray = new int[2];
        int[] numOne = {2, 3, 4, 5, 6, 7, 8};
        int[] numTwo = {6, 5, 4, 3, 2, 1, 0};

        int randomNumber = (int) Math.floor(Math.random() * (MAX_INPUT_LENGTH - 1));
        returnArray[0] = numOne[randomNumber];
        returnArray[1] = numTwo[randomNumber];
        return returnArray;
    }

    private void checkUserInput(String word) {
        if (word.length() < MIN_INPUT_LENGTH) {
            mWordEditText.setError("Enter at least 3 characters");
        } else if (word.length() > MAX_INPUT_LENGTH) {
            mWordEditText.setError("Word is tooo long!");
        } else if(compareStrings(word, lettersList)) {
            userAnswers.add(word);
            mWordEditText.getText().clear();
        } else {
            mWordEditText.setError("Wrong letters input. Try again");
        }
    }

    private boolean compareStrings(String word, ArrayList<String> lettersList) {
        String[] userArray = word.split("(?!^)");
        int count = 0;
        ArrayList<String> copyListString = new ArrayList<>(lettersList);
        for (String i : userArray) {
            if(copyListString.remove(i)) {
                count++;
            }
        }
        return count == userArray.length;
    }
}


