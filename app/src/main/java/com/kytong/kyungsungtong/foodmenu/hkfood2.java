package com.kytong.kyungsungtong.foodmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kytong.kyungsungtong.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class hkfood2 extends AppCompatActivity {

    private Button hkfood3_move;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hkfood2);

        result = (TextView) findViewById(R.id.result);

        final StringBuilder builder = new StringBuilder();

        hkfood3_move = findViewById(R.id.hkfood3_move);
        hkfood3_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hkfood3 = new Intent(hkfood2.this,hkfood3.class);
                startActivity(hkfood3);
            }
        });




        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Document doc = Jsoup.connect("https://bds.bablabs.com/restaurants/MjIyOTM2NTE2?campus_id=nbZgab6ors").get();

                    Elements element = doc.select("div.card");

                    int cnt=0;
                    int ck=0;
                    int flag=0;
                    String day = "";
                    String test = "";
                    for(Element e1 : element.select("div.date-title")){
                        if (cnt > 0) {
                            builder.append(e1.text() + "\n");
                            break;
                        }
                        cnt++;
                    }

                    Elements element3 = doc.select("div.label-wrapper");
                    for (Element e1 : element.select("span")) {
                        if (e1.text().contains("종일") && test.contains("종일")) {
                            break;
                        }
                        test += e1;
                        builder.append(e1.text() + "\n");
                        builder.append("====================================\n");
                        if (e1.text().contains("종일")) {
                            builder.append("학생 식단 \n");
                            Elements element2 = doc.select("div.card-body");
                            for (Element e2 : element.select("div.card-text")) {
                                String tmp = "";
                                tmp = tmp + e2;
                                if (tmp.contains(">")) {
                                    flag++;
                                    if (flag > 3) {
                                        builder.append(e2.text() + "\n");
                                        builder.append("====================================\n");
                                        flag = 0;
                                        break;
                                    }
                                }

                            }

                        }

                        if (e1.text().contains("종일")) {
                            builder.append("교직원 식단\n");
                            ck++;
                            Elements element2 = doc.select("div.card-body");
                            for (Element e2 : element.select("div.card-text")) {
                                String tmp = "";
                                tmp = tmp + e2;
                                if (flag > 3 && tmp.contains(">")) {
                                    builder.append(e2.text() + "\n");
                                    builder.append("====================================\n");
                                    flag = 0;
                                    break;
                                }
                                flag++;
                            }

                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });

            }
        }).start();


    }

}



