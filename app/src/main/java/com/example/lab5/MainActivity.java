package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    int row = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editRow = findViewById(R.id.row);

        Button button = findViewById(R.id.button_buld);
        Button button1 = findViewById(R.id.button_calculation);

        TableLayout tableLayout = findViewById(R.id.table_input);

        TextView r = findViewById(R.id.text_R);
        TextView e = findViewById(R.id.text_E);

        CheckBox checkBox = findViewById(R.id.checkBoxBold);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    row =Integer.parseInt(editRow.getText().toString());
                }catch (Exception e){
                    row = 1;
                }

                 createTable(tableLayout , row);
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ArrayList<Integer>> arrList = readTable(row);
                int[][] arr = incidenceToAdjacency(arrList , row , !checkBox.isChecked());

                print(arr);

                r.setText("Структурная изыбточность R = " + getR(arr));
                e.setText("E^2 = " + e(arr));


            }
        });
    }

    private double getR(int[][] arr){
        int n = arr.length-1;
        return sumVertices(arr) * 0.5/ n - 1.0;
    }

    private double e(int arr[][]){

        double countE = sumVertices(arr)/2;

        double n = arr.length;

        double e2 = 0;
        for(int i = 0; i < n; i++){
            int degreev =0;
            for(int j = 0; j <n;j++)
                degreev +=arr[i][j];
            e2 += degreev*degreev;
        }

        e2 -= 4 * countE * countE / n;

        return e2;

    }

    private int sumVertices(int[][] arr){

        int count = 0;
        for(int[] row : arr){
            for(int cell : row ){
                if(cell == 1){
                    count+=cell;
                }
            }
        }
        return count;
    }

    private void createTable(TableLayout tableLayout , int row){
        tableLayout.removeAllViews();


        for(int i = 0; i < row; i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextSize(18);
            textView.setText((i + 1) + "  ");

            EditText editText = new EditText(this);
            editText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            editText.setHint("         ");
            editText.setId(i *100);
//            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            tableRow.addView(textView);
            tableRow.addView(editText);
            tableLayout.addView(tableRow);
        }
    }

    private ArrayList<ArrayList<Integer>> readTable(int row){
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();

        for(int i =0 ; i < row;i++){
            String text = ((EditText) findViewById(i *100)).getText().toString();

            ArrayList<Integer> list = new ArrayList<>();
            for(String num : text.split(" ")){
                try {
                    int n = Integer.parseInt(num)-1;
                    if(n < row)
                        list.add(n);
                }catch (Exception e ){

                }
            }
            arr.add(list);
        }

        return arr;
    }

    private int[][] incidenceToAdjacency(ArrayList<ArrayList<Integer>> incidence , int verticesCount , boolean isOriented){
        int[][] arr = new int[verticesCount][verticesCount];

        for(int i =0 ; i < incidence.size(); i++){
            for(int cell : incidence.get(i)){
                arr[i][cell] = 1;
                if(isOriented)
                arr[cell][i] = 1;
            }
        }

        return arr;
    }


    static void print(int[][] arr){
        for (int[] ints : arr) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(ints[j]);
            }
            System.out.println();
        }
    }
}