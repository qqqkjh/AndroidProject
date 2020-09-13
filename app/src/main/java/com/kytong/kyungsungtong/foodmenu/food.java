package com.kytong.kyungsungtong.foodmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kytong.kyungsungtong.R;

import java.io.IOException;

public class food extends AppCompatActivity {

    private Button food2_move;

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

//       다음날 음식페이지 이동
        food2_move = findViewById(R.id.food2_move);
        food2_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent food2 = new Intent(food.this,food2.class);
                startActivity(food2);
            }
        });



        result = (TextView) findViewById(R.id.result);

        final StringBuilder builder = new StringBuilder();




        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Document doc = Jsoup.connect("https://bds.bablabs.com/restaurants/MjIyOTA1MDI1?campus_id=nbZgab6ors").get();

                    Elements element = doc.select("div.card");

                    int cnt = 0;
                    int ck = 0;
                    int flag = 0;
                    String day = "";
                    String test = "";
                    for (Element e1 : element.select("div.date-title")) {
                        if (cnt > 0) {
                            break;
                        }
                        builder.append(e1.text() + "\n");
                        cnt++;
                        day +=e1;
                    }


                    Elements element3 = doc.select("div.label-wrapper");

                    if (day.contains("토")){
                        for(Element e1 : element.select("span")){
                            if(e1.text().contains("아침")&&test.contains("점심")){
                                break;
                            }
                            test +=e1;
                            if(e1.text().contains("점심")) {
                                builder.append(e1.text() + "\n");
                                builder.append("====================================\n");
                            }
                            if(e1.text().contains("점심")){
                                builder.append("기숙사생 식단\n");
                                Elements element2 = doc.select("div.card-body");
                                for (Element e2 : element.select("div.card-text")) {
                                    String tmp = "";
                                    tmp = tmp + e2;
                                    if (tmp.contains(">")) {
                                        flag++;
                                        if (flag > 1) {
                                            builder.append(e2.text() + "\n");
                                            builder.append("====================================\n");
                                            flag = 0;
                                            break;
                                        }
                                    }

                                }
                            }
                        }
                    }
                     else {
                        for (Element e1 : element.select("span")) {
                            if (e1.text().contains("아침") && test.contains("종일") || e1.text().contains("점심") || test.contains("종일 ")) {
                                break;
                            }
                            test += e1;
                            builder.append(e1.text() + "\n");
                            builder.append("====================================\n");
                            if (e1.text().contains("아침")) {
                                builder.append("기숙사생 식단 ");
                                Elements element2 = doc.select("div.card-body");
                                for (Element e2 : element.select("div.card-text")) {
                                    String tmp = "";
                                    tmp = tmp + e2;
                                    if (tmp.contains(">")) {
                                        builder.append(e2.text() + "\n");
                                        flag++;
                                        if (flag > 1) {
                                            builder.append("====================================\n");
                                            flag = 0;
                                            break;
                                        }
                                    }

                                }

                            }

                            if (e1.text().contains("종일")) {
                                builder.append("학생식사 메뉴\n");
                                ck++;
                                Elements element2 = doc.select("div.card-body");
                                for (Element e2 : element.select("div.card-text")) {
                                    String tmp = "";
                                    tmp = tmp + e2;
                                    if (flag > 1 && tmp.contains(">")) {
                                        builder.append(e2.text() + "\n");
                                        builder.append("=====================================\n");
                                        flag = 0;
                                        break;
                                    }
                                    flag++;
                                }

                            }
                            if (e1.text().contains("종일") && ck > 0) {
                                builder.append("교직원 식단\n");
                                Elements element2 = doc.select("div.card-body");
                                for (Element e2 : element.select("div.card-text")) {
                                    String tmp = "";
                                    tmp = tmp + e2;
                                    if (flag > 2 && tmp.contains(">")) {
                                        builder.append(e2.text() + "\n");
                                        builder.append("=====================================\n");
                                        flag = 0;
                                        break;
                                    }
                                    flag++;
                                }

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