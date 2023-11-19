package com.example.checkers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.Stack;
import java.util.Vector;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import pl.droidsonroids.gif.GifImageView;

import static com.example.checkers.R.color.black;

public class Board_2 extends AppCompatActivity implements View.OnClickListener {
    private final int Row = 8;
    private final int Col = 8;
    private static int[][] lBoard = new int[8][8];
    private static ImageButton[][] gBoard = new ImageButton[8][8];
    private final int player1 = 1;
    private final int player2 = 2;
    private final int player1_king = 3;
    private final int player2_king = 4;
    private final int computer = 2;
    private final int human = 1;
    private int Empty = 0;
    static int turn = 1;
    static int count = 0;
    int row2 = 0, col2 = 0, row1 = 0, col1 = 0,x,y;
    static int level = 3;
     static int time = 0;
    static int erore = 0;
    static int erore2 = 0;
    public static Stack<int[][]> undos = new Stack<int[][]>();
    public static Stack<int[][]> redo = new Stack<int[][]>();
    GifImageView gif;
    int numgif=0;
    TextView textView;
    MediaPlayer mp;
    MediaPlayer mp2;
    Button menu;
    Button back;
    Button restart;
    Button undo;
    Button redos;
    AdView mAdView;
    public Legal_moves bestMov = new Legal_moves(-1, -1, "null", -1, -1, "null", null,null);
    Vector<Legal_moves> moves10=null;
    Vector<Legal_moves> moves100=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_board_2);
        gif =(GifImageView)findViewById(R.id.gif);
        mp  = MediaPlayer.create(this, R.raw.maorr);
        mp2  = MediaPlayer.create(this, R.raw.victory);
        menu= findViewById(R.id.menu);
        menu.setOnClickListener(this);
        back= findViewById(R.id.back);
        back.setOnClickListener(this);
        restart= findViewById(R.id.restart);
        restart.setOnClickListener(this);
        undo= findViewById(R.id.undo);
        undo.setOnClickListener(this);
        redos= findViewById(R.id.redos);
        redos.setOnClickListener(this);
        textView=findViewById(R.id.textView);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-7979606640397319/5165661573");
        AdSize adSize = new AdSize(320, 50);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        initBoard(lBoard, gBoard);


    }


    @SuppressLint("ResourceAsColor")
    public void initBoard(int[][] lBoard, ImageButton[][] gBoard) {
          if(turn==human){
            textView.setText("The turn of the white");
            textView.setTextColor(Color.parseColor("#FFFFFF"));}
        else if(turn==computer) {
            textView.setText("The turn of the brown");
            textView.setTextColor(black);}
        int flag = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                lBoard[i][j] = Empty;
            }
        }

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {


                if ((i == 1) & (j == 7 | j == 5 | j == 3 | j == 1)) {

                    lBoard[i][j] = player2;
                } else if ((i == 0 | i == 2) & (j == 0 | j == 2 | j == 4 | j == 6)) {

                    lBoard[i][j] = player2;
                }
                if ((i == 6) & (j == 0 | j == 2 | j == 4 | j == 6)) {

                    lBoard[i][j] = player1;
                } else if ((i == 7 | i == 5) & (j == 7 | j == 5 | j == 3 | j == 1)) {

                    lBoard[i][j] = player1;

                }
            }


        }

//
        gBoard[0][0] = (ImageButton) findViewById(R.id.b0_0);
        gBoard[0][2] = (ImageButton) findViewById(R.id.b0_2);
        gBoard[0][4] = (ImageButton) findViewById(R.id.b0_4);
        gBoard[0][6] = (ImageButton) findViewById(R.id.b0_6);
        gBoard[1][1] = (ImageButton) findViewById(R.id.b1_1);
        gBoard[1][3] = (ImageButton) findViewById(R.id.b1_3);
        gBoard[1][5] = (ImageButton) findViewById(R.id.b1_5);
        gBoard[1][7] = (ImageButton) findViewById(R.id.b1_7);
        gBoard[2][0] = (ImageButton) findViewById(R.id.b2_0);
        gBoard[2][2] = (ImageButton) findViewById(R.id.b2_2);
        gBoard[2][4] = (ImageButton) findViewById(R.id.b2_4);
        gBoard[2][6] = (ImageButton) findViewById(R.id.b2_6);
        gBoard[3][1] = (ImageButton) findViewById(R.id.b3_1);
        gBoard[3][3] = (ImageButton) findViewById(R.id.b3_3);
        gBoard[3][5] = (ImageButton) findViewById(R.id.b3_5);
        gBoard[3][7] = (ImageButton) findViewById(R.id.b3_7);
        gBoard[4][0] = (ImageButton) findViewById(R.id.b4_0);
        gBoard[4][2] = (ImageButton) findViewById(R.id.b4_2);
        gBoard[4][4] = (ImageButton) findViewById(R.id.b4_4);
        gBoard[4][6] = (ImageButton) findViewById(R.id.b4_6);
        gBoard[5][1] = (ImageButton) findViewById(R.id.b5_1);
        gBoard[5][3] = (ImageButton) findViewById(R.id.b5_3);
        gBoard[5][5] = (ImageButton) findViewById(R.id.b5_5);
        gBoard[5][7] = (ImageButton) findViewById(R.id.b5_7);
        gBoard[6][0] = (ImageButton) findViewById(R.id.b6_0);
        gBoard[6][2] = (ImageButton) findViewById(R.id.b6_2);
        gBoard[6][4] = (ImageButton) findViewById(R.id.b6_4);
        gBoard[6][6] = (ImageButton) findViewById(R.id.b6_6);
        gBoard[7][1] = (ImageButton) findViewById(R.id.b7_1);
        gBoard[7][3] = (ImageButton) findViewById(R.id.b7_3);
        gBoard[7][5] = (ImageButton) findViewById(R.id.b7_5);
        gBoard[7][7] = (ImageButton) findViewById(R.id.b7_7);
        //
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i % 2 == 0 & j % 2 == 0) || (i % 2 != 0 & j % 2 != 0))
                    gBoard[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    @SuppressLint("ResourceAsColor")
    public void onClick(View v) {

        if(v==menu)
            open_menu();
        if(v==back)
            open_level();
        if (v==restart)
            restart();
        if (v==undo)
            undo(undos);
        if (v==redos)
            redo(redo);
        int i = 0, j = 0;
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                if ((i % 2 == 0 & j % 2 == 0) || (i % 2 != 0 & j % 2 != 0))
                    if (gBoard[i][j].getId() == v.getId()) {
                        if (count == 0 & lBoard[i][j]>0) {
                            row2 = i;
                            col2 = j;
                            count = 1;
                            Board_paint(lBoard,turn,row2,col2);
                            break;
                        }  if (count == 1 & lBoard[i][j]==0) {
                            Board_paint_reverse(moves100);
                            row1 = i;
                            col1 = j;
                            count = 2;
                            break;

                        }
                        if (count == 1 & lBoard[i][j]>0) {
                            Board_paint_reverse(moves100);
                            row2 = i;
                            col2 = j;
                            count = 1;
                            Board_paint(lBoard,turn,row2,col2);
                            break;

                        }

                    }

            }
        }

        printlbord(lBoard);
        if (turn != computer) {
            if ( count == 2) {
                doubel_eat( lBoard, row2, col2, row1, col1);
               // count = 0;
            }
            if ( count == 2 && lBoard[row2][col2]==player1_king) {
                doubel_eat_king( lBoard, row2, col2, row1, col1);
             //   count = 0;
            }
            Data m = legal_eat(lBoard, row2, col2, row1, col1);
            Data a = legal_king_eat(lBoard, row2, col2, row1, col1);
            if (m.getRow() != -1 & count == 2) {
                do_eat(gBoard, lBoard, row2, col2, row1, col1, m);
                count = 0;
            }
            if (a.getRow() != -1 & count == 2) {

                do_eat(gBoard, lBoard, row2, col2, row1, col1, a);
                count = 0;
            }
            if (count == 2 && !legal_move(lBoard, row2, col2, row1, col1) && !legal_move_king(lBoard, row2, col2, row1, col1)) {
                count = 0;
                System.out.println("maor" + row2 + "  " + col2);
                System.out.println("maor" + row1 + "  " + col1);
                System.out.println("--false--");
            }

            do_move(gBoard, lBoard, row2, col2, row1, col1);

            if(if_win(lBoard)==computer)
                win_messege_p2();
           else if (if_win(lBoard)==human)
                win_messege_p1();
           else if (if_draw())
               draw_Message();
        }
            if(turn==computer) {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                wait(time);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        computerMove3();
                                        count = 0;
                                        if(turn==human){
                                            textView.setText("The turn of the white");
                                            textView.setTextColor(Color.parseColor("#FFFFFF"));}
                                        else if(turn==computer) {
                                            textView.setText("The turn of the brown");
                                            textView.setTextColor(black);}
                                        if(if_win(lBoard)==computer)
                                            win_messege_p2();
                                        if (if_win(lBoard)==human)
                                            win_messege_p1();
                                        if (if_draw())
                                            draw_Message();
                                    }
                                });
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    };
                };
                thread.start();

            }

        mp.start();
        if(turn==human){
            textView.setText("The turn of the white");
            textView.setTextColor(Color.parseColor("#FFFFFF"));}
        else if(turn==computer) {
            textView.setText("The turn of the brown");
            textView.setTextColor(black);}
        numgif=grade_bord(lBoard);
        gifchange();
    }

    public void do_move(ImageButton[][] gBoard, int[][] lBoard, int row2, int col2, int row1, int col1) {
        if (count == 2) {
            undos.push(copy(lBoard));
            if (lBoard[row2][col2] == player1_king) {
                lBoard[row2][col2] = Empty;
                lBoard[row1][col1] = player1_king;
                gBoard[row1][col1].setImageResource(R.drawable.player_1_king);
                gBoard[row2][col2].setImageResource(black);
                turn = 3 - turn;
                count = 0;
                return;
            }
            if (lBoard[row2][col2] == player2_king) {
                lBoard[row2][col2] = Empty;
                lBoard[row1][col1] = player2_king;
                gBoard[row1][col1].setImageResource(R.drawable.player_2_king);
                gBoard[row2][col2].setImageResource(black);
                turn = 3 - turn;
                count = 0;
                return;
            }
            System.out.println("maor" + row2 + "  " + col2);
            System.out.println("TURN" + turn);
            if (turn == player2) {
                if (row1 == 7) {
                    gBoard[row1][col1].setImageResource(R.drawable.player_2_king);
                    lBoard[row1][col1] = player2_king;
                } else {
                    gBoard[row1][col1].setImageResource(R.drawable.player22);
                    lBoard[row1][col1] = player2;
                }
            } else {
                if (row1 == 0) {
                    gBoard[row1][col1].setImageResource(R.drawable.player_1_king);
                    lBoard[row1][col1] = player1_king;
                } else {
                    gBoard[row1][col1].setImageResource(R.drawable.player11);
                    lBoard[row1][col1] = player1;
                }
            }
            gBoard[row2][col2].setImageResource(black);
            lBoard[row2][col2] = Empty;
            turn = 3 - turn;
            count = 0;
        }

    }

    public Boolean legal_move(int[][] lBoard, int row2, int col2, int row1, int col1) {
        switch (turn) {
            case player2:
                if (lBoard[row2][col2] == turn) {
                    if (lBoard[row2][col2] == turn & lBoard[row2][col2] == player2 & (lBoard[row1][col1] == Empty)
                            & ((row2 + 1 == row1 & col2 + 1 == col1) | (row2 + 1 == row1 & col2 - 1 == col1)))
                        return true;
                }
                break;
            case player1:
                if (lBoard[row2][col2] == player1) {
                    if (lBoard[row2][col2] == player1 & (lBoard[row1][col1] == Empty)
                            & ((row2 - 1 == row1 & col2 - 1 == col1) | (row2 - 1 == row1 & col2 + 1 == col1)))
                        return true;
                }
                break;
            default:

                // code block
        }
        return false;
    }

    public void printlbord(int lBoard[][]) {
        System.out.print("");
        for (int i = 0; i < 8; i++) {
            System.out.print(i + " - ");
            for (int j = 0; j < 8; j++) {
                System.out.print(lBoard[i][j] + "  ");
                if (j == 7)
                    System.out.println();
            }
        }


    }

    public Data legal_eat(int[][] lBoard, int row2, int col2, int row1, int col1) {
        Data m = new Data(-1, -1);
        if (count != 2)
            return m;
        switch (turn) {
            case player2:
                if (lBoard[row2][col2] == turn & (row2 + 2 < 8) & (col2 + 2 < 8)) {
                    if ((lBoard[row1][col1] == Empty) & ((lBoard[row2 + 1][col2 + 1] == player1) | (lBoard[row2 + 1][col2 + 1] == player1_king))
                            & ((row2 + 2 == row1 & col2 + 2 == col1))) {
                        m.setRow(row2 + 1);
                        m.setCol(col2 + 1);
                        return m;
                    }
                }
                if (lBoard[row2][col2] == turn & (row2 + 2 < 8) & (col2 - 2 >= 0)) {
                    if ((lBoard[row1][col1] == Empty) & ((lBoard[row2 + 1][col2 - 1] == player1) | (lBoard[row2 + 1][col2 - 1] == player1_king))
                            & ((row2 + 2 == row1 & col2 - 2 == col1))) {
                        m.setRow(row2 + 1);
                        m.setCol(col2 - 1);
                        return m;
                    }
                }
                break;
            case player1:
                if (lBoard[row2][col2] == turn & (row2 - 2 > -1) & (col2 - 2 > -1)) {
                    if (lBoard[row2][col2] == player1 & (lBoard[row1][col1] == Empty) & ((lBoard[row2 - 1][col2 - 1] == player2) | (lBoard[row2 - 1][col2 - 1] == player2_king))
                            & ((row2 - 2 == row1 & col2 - 2 == col1))) {
                        m.setRow(row2 - 1);
                        m.setCol(col2 - 1);
                        return m;
                    }
                }
                if (lBoard[row2][col2] == turn & (row2 - 2 > -1) & (col2 + 2 < 8)) {
                    if (lBoard[row2][col2] == player1 & (lBoard[row1][col1] == Empty) & ((lBoard[row2 - 1][col2 + 1] == player2) | (lBoard[row2 - 1][col2 + 1] == player2_king))
                            & ((row2 - 2 == row1 & col2 + 2 == col1))) {
                        m.setRow(row2 - 1);
                        m.setCol(col2 + 1);
                        return m;
                    }
                }
                break;
            default:

                // code block
        }
        return m;
    }

    public void do_eat(ImageButton[][] gBoard, int[][] lBoard, int row2, int col2, int row1, int col1, Data m) {
        if (count == 2) {
            undos.push(copy(lBoard));
            if (lBoard[row2][col2] == player1_king) {
                lBoard[row2][col2] = Empty;
                lBoard[row1][col1] = player1_king;
                gBoard[row1][col1].setImageResource(R.drawable.player_1_king);
                gBoard[row2][col2].setImageResource(black);
                gBoard[m.getRow()][m.getCol()].setImageResource(black);
                lBoard[m.getRow()][m.getCol()] = Empty;
                turn = 3 - turn;
                count = 0;
                return;
            }
            if (lBoard[row2][col2] == player2_king) {
                lBoard[row2][col2] = Empty;
                lBoard[row1][col1] = player2_king;
                gBoard[row1][col1].setImageResource(R.drawable.player_2_king);
                gBoard[row2][col2].setImageResource(black);
                gBoard[m.getRow()][m.getCol()].setImageResource(black);
                lBoard[m.getRow()][m.getCol()] = Empty;
                turn = 3 - turn;
                count = 0;
                return;
            }
            System.out.println("maor" + row2 + "  " + col2);
            System.out.println("TURN" + turn);
            if (turn == player2) {
                if (row1 == 7) {
                    gBoard[row1][col1].setImageResource(R.drawable.player_2_king);
                    lBoard[row1][col1] = player2_king;
                } else {
                    gBoard[row1][col1].setImageResource(R.drawable.player22);
                    lBoard[row1][col1] = player2;
                }
            } else {
                if (row1 == 0) {
                    gBoard[row1][col1].setImageResource(R.drawable.player_1_king);
                    lBoard[row1][col1] = player1_king;
                } else {
                    gBoard[row1][col1].setImageResource(R.drawable.player11);
                    lBoard[row1][col1] = player1;
                }
            }
            gBoard[row2][col2].setImageResource(black);
            lBoard[row2][col2] = Empty;
            gBoard[m.getRow()][m.getCol()].setImageResource(black);
            lBoard[m.getRow()][m.getCol()] = Empty;
            turn = 3 - turn;
            count = 0;
        }
    }

    public Boolean legal_move_king(int[][] lBoard, int row2, int col2, int row1, int col1) {
        switch (turn + 2) {
            case player2_king:
                if (lBoard[row2][col2] == turn + 2) {
                    if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player2_king & (lBoard[row1][col1] == Empty)
                            & ((row2 + 1 == row1 & col2 + 1 == col1) | (row2 + 1 == row1 & col2 - 1 == col1)))
                        return true;
                }
                if (lBoard[row2][col2] == turn + 2) {
                    if (lBoard[row2][col2] == player2_king & (lBoard[row1][col1] == Empty)
                            & ((row2 - 1 == row1 & col2 - 1 == col1) | (row2 - 1 == row1 & col2 + 1 == col1)))
                        return true;
                }
                break;
            case player1_king:
                if (lBoard[row2][col2] == turn + 2) {
                    if (lBoard[row2][col2] == player1_king & (lBoard[row1][col1] == Empty)
                            & ((row2 + 1 == row1 & col2 + 1 == col1) | (row2 + 1 == row1 & col2 - 1 == col1)))
                        return true;
                }
                if (lBoard[row2][col2] == turn + 2) {
                    if (lBoard[row2][col2] == player1_king & (lBoard[row1][col1] == Empty)
                            & ((row2 - 1 == row1 & col2 - 1 == col1) | (row2 - 1 == row1 & col2 + 1 == col1)))
                        return true;
                }
                break;
            default:

                // code block
        }
        return false;
    }

    public Data legal_king_eat(int[][] lBoard, int row2, int col2, int row1, int col1) {
        Data m = new Data(-1, -1);
        if (count != 2)
            return m;
        switch (turn + 2) {
            case player2_king:
                if (lBoard[row2][col2] == turn + 2 & (row2 - 2 > -1) & (col2 - 2 > -1)) {
                    if (lBoard[row2][col2] == player2_king & (lBoard[row1][col1] == Empty) & ((lBoard[row2 - 1][col2 - 1] == player1) | (lBoard[row2 - 1][col2 - 1] == player1_king))
                            & ((row2 - 2 == row1 & col2 - 2 == col1))) {
                        m.setRow(row2 - 1);
                        m.setCol(col2 - 1);
                        return m;
                    }
                }
                if (lBoard[row2][col2] == turn + 2 & (row2 - 2 > -1) & (col2 + 2 < 8)) {
                    if (lBoard[row2][col2] == player2_king & (lBoard[row1][col1] == Empty) & ((lBoard[row2 - 1][col2 + 1] == player1) | (lBoard[row2 - 1][col2 + 1] == player1_king))
                            & ((row2 - 2 == row1 & col2 + 2 == col1))) {
                        m.setRow(row2 - 1);
                        m.setCol(col2 + 1);
                        return m;
                    }
                }
                if (lBoard[row2][col2] == player2_king & (row2 + 2 < 8) & (col2 + 2 < 8)) {
                    if ((lBoard[row1][col1] == Empty) & ((lBoard[row2 + 1][col2 + 1] == player1) | (lBoard[row2 + 1][col2 + 1] == player1_king))
                            & ((row2 + 2 == row1 & col2 + 2 == col1))) {
                        m.setRow(row2 + 1);
                        m.setCol(col2 + 1);
                        return m;
                    }
                }
                if (lBoard[row2][col2] == player2_king & (row2 + 2 < 8) & (col2 - 2 >= 0)) {
                    if ((lBoard[row1][col1] == Empty) & ((lBoard[row2 + 1][col2 - 1] == player1) | (lBoard[row2 + 1][col2 - 1] == player1_king))
                            & ((row2 + 2 == row1 & col2 - 2 == col1))) {
                        m.setRow(row2 + 1);
                        m.setCol(col2 - 1);
                        return m;
                    }
                }
                break;
            case player1_king:
                if (lBoard[row2][col2] == turn + 2 & (row2 - 2 > -1) & (col2 - 2 > -1)) {
                    if (lBoard[row2][col2] == player1_king & (lBoard[row1][col1] == Empty) & ((lBoard[row2 - 1][col2 - 1] == player2) | (lBoard[row2 - 1][col2 - 1] == player2_king))
                            & ((row2 - 2 == row1 & col2 - 2 == col1))) {
                        m.setRow(row2 - 1);
                        m.setCol(col2 - 1);
                        return m;
                    }
                }
                if (lBoard[row2][col2] == turn + 2 & (row2 - 2 > -1) & (col2 + 2 < 8)) {
                    if (lBoard[row2][col2] == player1_king & (lBoard[row1][col1] == Empty) & ((lBoard[row2 - 1][col2 + 1] == player2) | (lBoard[row2 - 1][col2 + 1] == player2_king))
                            & ((row2 - 2 == row1 & col2 + 2 == col1))) {
                        m.setRow(row2 - 1);
                        m.setCol(col2 + 1);
                        return m;
                    }
                }
                if (lBoard[row2][col2] == player1_king & (row2 + 2 < 8) & (col2 + 2 < 8)) {
                    if ((lBoard[row1][col1] == Empty) & ((lBoard[row2 + 1][col2 + 1] == player2) | (lBoard[row2 + 1][col2 + 1] == player2_king))
                            & ((row2 + 2 == row1 & col2 + 2 == col1))) {
                        m.setRow(row2 + 1);
                        m.setCol(col2 + 1);
                        return m;
                    }
                }
                if (lBoard[row2][col2] == player1_king & (row2 + 2 < 8) & (col2 - 2 >= 0)) {
                    if ((lBoard[row1][col1] == Empty) & ((lBoard[row2 + 1][col2 - 1] == player2) | (lBoard[row2 + 1][col2 - 1] == player2_king))
                            & ((row2 + 2 == row1 & col2 - 2 == col1))) {
                        m.setRow(row2 + 1);
                        m.setCol(col2 - 1);
                        return m;
                    }
                }
                break;
            default:

                // code block
        }
        return m;
    }

    public int grade_bord(int[][] lBoard) {
        int sum_human = 0;
        int sum_computer = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (lBoard[i][j] == player1)
                    sum_human += 10+(i-8)*-1;

                if (lBoard[i][j] == player2)
                    sum_computer += 10+i;

                if (lBoard[7][j] == player1)
                    sum_human += 1;

                if (lBoard[0][j] == player2)
                    sum_computer += 1;

                if (lBoard[i][j] == player1_king)
                    sum_human += 35;

                if (lBoard[i][j] == player2_king)
                    sum_computer += 35;


                if (lBoard[i][7] == player1)
                    sum_human += 1;

                if (lBoard[i][7] == player2)
                    sum_computer += 1;

                if (lBoard[i][7] == player1_king)
                    sum_human += 3;

                if (lBoard[i][7] == player2_king)
                    sum_computer += 3;


                if (lBoard[i][0] == player1)
                    sum_human += 1;

                if (lBoard[i][0] == player2)
                    sum_computer += 1;

                if (lBoard[i][0] == player1_king)
                    sum_human += 3;

                if (lBoard[i][0] == player2_king)
                    sum_computer += 3;

                if (lBoard[0][j] == player1_king)
                    sum_human += 2;

                if (lBoard[0][j] == player2_king)
                    sum_computer += 2;

                if (lBoard[7][j] == player1_king)
                    sum_human += 2;

                if (lBoard[7][j] == player2_king)
                    sum_computer += 2;

                if (i - 1 >= 0 & j - 1 >= 0)
                    if (lBoard[i][j] == player2 & lBoard[i - 1][j - 1] == player2)
                        sum_computer += 6;

                if (i - 1 >= 0 & j + 1 < 8)
                    if (lBoard[i][j] == player2 & lBoard[i - 1][j + 1] == player2)
                        sum_computer += 6;

                if (j - 1 >= 0 & i + 1 < 8)
                    if (lBoard[i][j] == player1 & lBoard[i + 1][j - 1] == player1)
                        sum_human += 6;
                if (i + 1 < 8 & j + 1 < 8)
                    if (lBoard[i][j] == player1 & lBoard[i + 1][j + 1] == player1)
                        sum_human += 6;

                if(p2_win(lBoard))
                    sum_computer += 500;

                if(p1_win(lBoard))
                    sum_human += 500;


            }
        }
        return (sum_computer) - (sum_human);
    }

    public int if_p2_win(int[][] lBoard) {
        int num1 = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (lBoard[i][j] == player1 || lBoard[i][j] == player1_king)
                    num1++;
            }
        }
        if (num1 == 1)
            return 1;
        if (num1 == 3)
            return 3;
        return 0;
    }

    public Boolean p2_win(int[][] lBoard) {
        int num1 = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (lBoard[i][j] == player1 || lBoard[i][j] == player1_king)
                    num1++;
            }
        }
        if (num1 == 0)
            return true;
        return false;
    }

    public Boolean p1_win(int[][] lBoard) {
        int num1 = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (lBoard[i][j] == player2 || lBoard[i][j] == player2_king)
                    num1++;
            }
        }
        if (num1 == 0)
            return true;
        return false;
    }

    public void copyBoard(int[][] DogmaBoard, int[][] lBoard) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                DogmaBoard[i][j] = lBoard[i][j];
            }
        }
    }

    public int negamax(int[][] tempBoard, int depth, int turn) {
        int best = Integer.MIN_VALUE;
        int val;
        Vector<Legal_moves> moves = new Vector<Legal_moves>();
        int[][] ezerBoard = new int[8][8];

        if (depth == 0 )
            return -grade_bord(tempBoard);

        moves = moves(tempBoard, turn, moves);

        for (int i = 0; i < moves.size(); i++) {
            copyBoard(ezerBoard, tempBoard);
            Legal_moves m = moves.get(i);
            make_move(ezerBoard, m, turn);
            val = -negamax(ezerBoard, depth - 1, 3 - turn);
            if (val > best) {
                best = val;
                if (depth == level) {
                    bestMov = m;
                }
            }

        }

        return (best);
    }

    protected   void all_legal_move(int[][] lBoard, Vector<Legal_moves> moves, int turn) {

        if (turn == computer) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                    if (i + 1 < 8 & j + 1 < 8) {
                        if (lBoard[i][j] == player2 & lBoard[i + 1][j + 1] == Empty) {
                            m.setLrow(i);
                            m.setLcol(j);
                            m.setTorow(i + 1);
                            m.setTocol(j + 1);
                            m.setType("mov");
                            m.setSign("++");
                            moves.add(m);
                        }
                    }

                    if (j - 1 >= 0 & i + 1 < 8) {
                        if (lBoard[i][j] == player2 & (lBoard[i + 1][j - 1] == Empty)) {
                            m.setLrow(i);
                            m.setLcol(j);
                            m.setTorow(i + 1);
                            m.setTocol(j - 1);
                            m.setType("mov");
                            m.setSign("+-");
                            moves.add(m);
                        }
                    }
                }
            }
        } else {  //תור בן אדם

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                    if (i - 1 >= 0 & j - 1 >= 0) {
                        if (lBoard[i][j] == player1 & lBoard[i - 1][j - 1] == Empty) {

                            m.setLrow(i);
                            m.setLcol(j);
                            m.setTorow(i - 1);
                            m.setTocol(j - 1);
                            m.setType("mov");
                            m.setSign("--");
                            moves.add(m);
                        }
                    }

                    if (i - 1 >= 0 & j + 1 < 8) {
                        if (lBoard[i][j] == player1 & (lBoard[i - 1][j + 1] == Empty)) {

                            m.setLrow(i);
                            m.setLcol(j);
                            m.setTorow(i - 1);
                            m.setTocol(j + 1);
                            m.setType("mov");
                            m.setSign("-+");
                            moves.add(m);
                        }
                    }
                }
            }

        }
    }

    public void all_legal_eat(int[][] lBoard, Vector<Legal_moves> moves, int turn) {
        if (turn == computer) {
            for (int row2 = 0; row2 < 8; row2++) {
                for (int col2 = 0; col2 < 8; col2++) {

                    if (lBoard[row2][col2] == turn & (row2 + 2 < 8) & (col2 + 2 < 8)) {
                        if ((lBoard[row2 + 2][col2 + 2] == Empty) & ((lBoard[row2 + 1][col2 + 1] == player1) | (lBoard[row2 + 1][col2 + 1] == player1_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 + 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat");
                            m.setSign("++");
                            m.setA(a);
                            moves.add(m);
                            erore2=0;
                            Doubleeating(lBoard,turn,row2+2,col2+2,row2,col2,a,moves);

                        }
                    }
                    if (lBoard[row2][col2] == turn & (row2 + 2 < 8) & (col2 - 2 >= 0)) {
                        if ((lBoard[row2 + 2][col2 - 2] == Empty) & ((lBoard[row2 + 1][col2 - 1] == player1) | (lBoard[row2 + 1][col2 - 1] == player1_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 - 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat");
                            m.setSign("+-");
                            m.setA(a);
                            moves.add(m);
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            erore2=0;
                            Doubleeating(lBoard,turn,row2+2,col2-2,row2,col2,a,moves);

                        }
                    }
                }
            }
        } else {
            for (int row2 = 0; row2 < 8; row2++) {
                for (int col2 = 0; col2 < 8; col2++) {
                    if (lBoard[row2][col2] == turn & (row2 - 2 > -1) & (col2 - 2 > -1)) {
                        if (lBoard[row2][col2] == player1 & (lBoard[row2 - 2][col2 - 2] == Empty) & ((lBoard[row2 - 1][col2 - 1] == player2) | (lBoard[row2 - 1][col2 - 1] == player2_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 - 1);
                            a.setCol(col2 - 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat");
                            m.setSign("--");
                            m.setA(a);
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            m.setEats(eats);
                            moves.add(m);
                            erore2=0;
                            Doubleeating(lBoard,turn,row2-2,col2-2,row2,col2,a,moves);

                        }
                    }
                    if (lBoard[row2][col2] == turn & (row2 - 2 > -1) & (col2 + 2 < 8)) {
                        if (lBoard[row2][col2] == player1 & (lBoard[row2 - 2][col2 + 2] == Empty) & ((lBoard[row2 - 1][col2 + 1] == player2) | (lBoard[row2 - 1][col2 + 1] == player2_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 - 1);
                            a.setCol(col2 + 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat");
                            m.setSign("-+");
                            m.setA(a);
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            m.setEats(eats);
                            moves.add(m);
                            erore2=0;
                            Doubleeating(lBoard,turn,row2-2,col2+2,row2,col2,a,moves);
                        }
                    }
                }
            }
        }
    }

    public void all_move_king(int[][] lBoard, Vector<Legal_moves> moves, int turn) {
        if (turn == computer) {
            for (int row2 = 0; row2 < Row; row2++) {
                for (int col2 = 0; col2 < Col; col2++) {

                    if (row2 + 1 < 8 & col2 + 1 < 8)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player2_king & (lBoard[row2 + 1][col2 + 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 1);
                            m.setTocol(col2 + 1);
                            m.setType("mov_king");
                            m.setSign("++");
                            moves.add(m);

                        }

                    if (row2 + 1 < 8 & col2 - 1 > -1)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player2_king & (lBoard[row2 + 1][col2 - 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 1);
                            m.setTocol(col2 - 1);
                            m.setType("mov_king");
                            m.setSign("+-");
                            moves.add(m);

                        }
                    if (row2 - 1 > -1 & col2 + 1 < 8)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player2_king & (lBoard[row2 - 1][col2 + 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 1);
                            m.setTocol(col2 + 1);
                            m.setType("mov_king");
                            m.setSign("-+");
                            moves.add(m);

                        }

                    if (row2 - 1 > -1 & col2 - 1 > -1)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player2_king & (lBoard[row2 - 1][col2 - 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 1);
                            m.setTocol(col2 - 1);
                            m.setType("mov_king");
                            m.setSign("--");
                            moves.add(m);
                        }
                }
            }
        } else {
            for (int row2 = 0; row2 < Row; row2++) {
                for (int col2 = 0; col2 < Col; col2++) {

                    if (row2 + 1 < 8 & col2 + 1 < 8)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player1_king & (lBoard[row2 + 1][col2 + 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 1);
                            m.setTocol(col2 + 1);
                            m.setType("mov_king");
                            m.setSign("++");
                            moves.add(m);

                        }

                    if (row2 + 1 < 8 & col2 - 1 > -1)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player1_king & (lBoard[row2 + 1][col2 - 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 1);
                            m.setTocol(col2 - 1);
                            m.setType("mov_king");
                            m.setSign("+-");
                            moves.add(m);

                        }
                    if (row2 - 1 > -1 & col2 + 1 < 8)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player1_king & (lBoard[row2 - 1][col2 + 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 1);
                            m.setTocol(col2 + 1);
                            m.setType("mov_king");
                            m.setSign("-+");
                            moves.add(m);

                        }

                    if (row2 - 1 > -1 & col2 - 1 > -1)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player1_king & (lBoard[row2 - 1][col2 - 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 1);
                            m.setTocol(col2 - 1);
                            m.setType("mov_king");
                            m.setSign("--");
                            moves.add(m);

                        }
                }
            }
        }

    }

    public void all_eat_king(int[][] lBoard, Vector<Legal_moves> moves, int turn){

        if(turn==computer){
            for (int row2 = 0; row2 < Row; row2++) {
                for (int col2 = 0; col2 < Col; col2++) {

                    if (lBoard[row2][col2] == turn + 2 & (row2 - 2 > -1) & (col2 - 2 > -1)) {
                        if (lBoard[row2][col2] == player2_king & (lBoard[row2 - 2][col2 - 2] == Empty) & ((lBoard[row2 - 1][col2 - 1] == player1) | (lBoard[row2 - 1][col2 - 1] == player1_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 - 1);
                            a.setCol(col2 - 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat_king");
                            m.setSign("--");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2-2,col2-2,row2,col2,a,moves);

                        }
                    }
                    if (lBoard[row2][col2] == turn + 2 & (row2 - 2 > -1) & (col2 + 2 < 8)) {
                        if (lBoard[row2][col2] == player2_king & (lBoard[row2 - 2][col2 + 2] == Empty) & ((lBoard[row2 - 1][col2 + 1] == player1) | (lBoard[row2 - 1][col2 + 1] == player1_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 - 1);
                            a.setCol(col2 + 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat_king");
                            m.setSign("-+");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2-2,col2+2,row2,col2,a,moves);
                        }
                    }
                    if (lBoard[row2][col2] == player2_king & (row2 + 2 < 8) & (col2 + 2 < 8)) {
                        if ((lBoard[row2 + 2][col2 + 2] == Empty) & ((lBoard[row2 + 1][col2 + 1] == player1) | (lBoard[row2 + 1][col2 + 1] == player1_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 + 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat_king");
                            m.setSign("++");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2+2,col2+2,row2,col2,a,moves);
                        }
                    }
                    if (lBoard[row2][col2] == player2_king & (row2 + 2 < 8) & (col2 - 2 >= 0)) {
                        if ((lBoard[row2 + 2][col2 - 2] == Empty) & ((lBoard[row2 + 1][col2 - 1] == player1) | (lBoard[row2 + 1][col2 - 1] == player1_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 - 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat_king");
                            m.setSign("+-");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2+2,col2-2,row2,col2,a,moves);
                        }
                    }
                }
            }
        }else{//need to fix
            for (int row2 = 0; row2 < Row; row2++) {
                for (int col2 = 0; col2 < Col; col2++) {

                    if (lBoard[row2][col2] == turn + 2 & (row2 - 2 > -1) & (col2 - 2 > -1))
                        if (lBoard[row2][col2] == player1_king & (lBoard[row2 - 2][col2 - 2] == Empty) & ((lBoard[row2 - 1][col2 - 1] == player2) | (lBoard[row2 - 1][col2 - 1] == player2_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 - 1);
                            a.setCol(col2 - 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat_king");
                            m.setSign("--");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2-2,col2-2,row2,col2,a,moves);                        }

                    if (lBoard[row2][col2] == turn + 2 & (row2 - 2 > -1) & (col2 + 2 < 8))
                        if (lBoard[row2][col2] == player1_king & (lBoard[row2 - 2][col2 + 2] == Empty) & ((lBoard[row2 - 1][col2 + 1] == player2) | (lBoard[row2 - 1][col2 + 1] == player2_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 - 1);
                            a.setCol(col2 + 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat_king");
                            m.setSign("-+");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2-2,col2+2,row2,col2,a,moves);
                        }

                    if (lBoard[row2][col2] == player1_king & (row2 + 2 < 8) & (col2 + 2 < 8))
                        if ((lBoard[row2 + 2][col2 + 2] == Empty) & ((lBoard[row2 + 1][col2 + 1] == player2) | (lBoard[row2 + 1][col2 + 1] == player2_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 + 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat_king");
                            m.setSign("++");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2+2,col2+2,row2,col2,a,moves);
                        }

                    if (lBoard[row2][col2] == player1_king & (row2 + 2 < 8) & (col2 - 2 >= 0))
                        if ((lBoard[row2 + 2][col2 - 2] == Empty) & ((lBoard[row2 + 1][col2 - 1] == player2) | (lBoard[row2 + 1][col2 - 1] == player2_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 - 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat_king");
                            m.setSign("+-");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2+2,col2-2,row2,col2,a,moves);
                        }
                }
            }
        }

    }

    protected   void all_legal_move_paint(int[][] lBoard, Vector<Legal_moves> moves, int turn,int i,int j) {

        if (turn == computer) {

                    Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                    if (i + 1 < 8 & j + 1 < 8) {
                        if (lBoard[i][j] == player2 & lBoard[i + 1][j + 1] == Empty) {
                            m.setLrow(i);
                            m.setLcol(j);
                            m.setTorow(i + 1);
                            m.setTocol(j + 1);
                            m.setType("mov");
                            m.setSign("++");
                            moves.add(m);
                        }
                    }

                    if (j - 1 >= 0 & i + 1 < 8) {
                        if (lBoard[i][j] == player2 & (lBoard[i + 1][j - 1] == Empty)) {
                            m.setLrow(i);
                            m.setLcol(j);
                            m.setTorow(i + 1);
                            m.setTocol(j - 1);
                            m.setType("mov");
                            m.setSign("+-");
                            moves.add(m);
                        }
                    }

        } else {  //תור בן אדם



                    if (i - 1 >= 0 & j - 1 >= 0) {
                        if (lBoard[i][j] == player1 & lBoard[i - 1][j - 1] == Empty) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(i);
                            m.setLcol(j);
                            m.setTorow(i - 1);
                            m.setTocol(j - 1);
                            m.setType("mov");
                            m.setSign("--");
                            moves.add(m);
                        }
                    }

                    if (i - 1 >= 0 & j + 1 < 8) {
                        if (lBoard[i][j] == player1 & (lBoard[i - 1][j + 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(i);
                            m.setLcol(j);
                            m.setTorow(i - 1);
                            m.setTocol(j + 1);
                            m.setType("mov");
                            m.setSign("-+");
                            moves.add(m);
                        }

            }

        }
    }

    public void all_legal_eat_paint(int[][] lBoard, Vector<Legal_moves> moves, int turn,int row2,int col2) {
        if (turn == computer) {


                    if (lBoard[row2][col2] == turn & (row2 + 2 < 8) & (col2 + 2 < 8)) {
                        if ((lBoard[row2 + 2][col2 + 2] == Empty) & ((lBoard[row2 + 1][col2 + 1] == player1) | (lBoard[row2 + 1][col2 + 1] == player1_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 + 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat");
                            m.setSign("++");
                            m.setA(a);
                            moves.add(m);
                            erore2=0;
                            Doubleeating(lBoard,turn,row2+2,col2+2,row2,col2,a,moves);

                        }
                    }
                    if (lBoard[row2][col2] == turn & (row2 + 2 < 8) & (col2 - 2 >= 0)) {
                        if ((lBoard[row2 + 2][col2 - 2] == Empty) & ((lBoard[row2 + 1][col2 - 1] == player1) | (lBoard[row2 + 1][col2 - 1] == player1_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 - 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat");
                            m.setSign("+-");
                            m.setA(a);
                            moves.add(m);
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            erore2=0;
                            Doubleeating(lBoard,turn,row2+2,col2-2,row2,col2,a,moves);

                        }
                    }

        } else {

                    if (lBoard[row2][col2] == turn & (row2 - 2 > -1) & (col2 - 2 > -1)) {
                        if (lBoard[row2][col2] == player1 & (lBoard[row2 - 2][col2 - 2] == Empty) & ((lBoard[row2 - 1][col2 - 1] == player2) | (lBoard[row2 - 1][col2 - 1] == player2_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 - 1);
                            a.setCol(col2 - 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat");
                            m.setSign("--");
                            m.setA(a);
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            m.setEats(eats);
                            moves.add(m);
                            erore2=0;
                            Doubleeating(lBoard,turn,row2-2,col2-2,row2,col2,a,moves);

                        }
                    }
                    if (lBoard[row2][col2] == turn & (row2 - 2 > -1) & (col2 + 2 < 8)) {
                        if (lBoard[row2][col2] == player1 & (lBoard[row2 - 2][col2 + 2] == Empty) & ((lBoard[row2 - 1][col2 + 1] == player2) | (lBoard[row2 - 1][col2 + 1] == player2_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 - 1);
                            a.setCol(col2 + 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat");
                            m.setSign("-+");
                            m.setA(a);
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            m.setEats(eats);
                            moves.add(m);
                            erore2=0;
                            Doubleeating(lBoard,turn,row2-2,col2+2,row2,col2,a,moves);
                        }
                    }

        }
    }

    public void all_move_king_paint(int[][] lBoard, Vector<Legal_moves> moves, int turn,int row2,int col2) {
        if (turn == computer) {

                    if (row2 + 1 < 8 & col2 + 1 < 8)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player2_king & (lBoard[row2 + 1][col2 + 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 1);
                            m.setTocol(col2 + 1);
                            m.setType("mov_king");
                            m.setSign("++");
                            moves.add(m);

                        }

                    if (row2 + 1 < 8 & col2 - 1 > -1)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player2_king & (lBoard[row2 + 1][col2 - 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 1);
                            m.setTocol(col2 - 1);
                            m.setType("mov_king");
                            m.setSign("+-");
                            moves.add(m);

                        }
                    if (row2 - 1 > -1 & col2 + 1 < 8)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player2_king & (lBoard[row2 - 1][col2 + 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 1);
                            m.setTocol(col2 + 1);
                            m.setType("mov_king");
                            m.setSign("-+");
                            moves.add(m);

                        }

                    if (row2 - 1 > -1 & col2 - 1 > -1)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player2_king & (lBoard[row2 - 1][col2 - 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 1);
                            m.setTocol(col2 - 1);
                            m.setType("mov_king");
                            m.setSign("--");
                            moves.add(m);
                        }

        } else {


                    if (row2 + 1 < 8 & col2 + 1 < 8)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player1_king & (lBoard[row2 + 1][col2 + 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 1);
                            m.setTocol(col2 + 1);
                            m.setType("mov_king");
                            m.setSign("++");
                            moves.add(m);

                        }

                    if (row2 + 1 < 8 & col2 - 1 > -1)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player1_king & (lBoard[row2 + 1][col2 - 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 1);
                            m.setTocol(col2 - 1);
                            m.setType("mov_king");
                            m.setSign("+-");
                            moves.add(m);

                        }
                    if (row2 - 1 > -1 & col2 + 1 < 8)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player1_king & (lBoard[row2 - 1][col2 + 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 1);
                            m.setTocol(col2 + 1);
                            m.setType("mov_king");
                            m.setSign("-+");
                            moves.add(m);

                        }

                    if (row2 - 1 > -1 & col2 - 1 > -1)
                        if (lBoard[row2][col2] == turn + 2 & lBoard[row2][col2] == player1_king & (lBoard[row2 - 1][col2 - 1] == Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 1);
                            m.setTocol(col2 - 1);
                            m.setType("mov_king");
                            m.setSign("--");
                            moves.add(m);

                        }

        }

    }

    public void all_eat_king_paint(int[][] lBoard, Vector<Legal_moves> moves, int turn,int row2,int col2){

        if(turn==computer){

                    if (lBoard[row2][col2] == turn + 2 & (row2 - 2 > -1) & (col2 - 2 > -1)) {
                        if (lBoard[row2][col2] == player2_king & (lBoard[row2 - 2][col2 - 2] == Empty) & ((lBoard[row2 - 1][col2 - 1] == player1) | (lBoard[row2 - 1][col2 - 1] == player1_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 - 1);
                            a.setCol(col2 - 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat_king");
                            m.setSign("--");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2-2,col2-2,row2,col2,a,moves);

                        }
                    }
                    if (lBoard[row2][col2] == turn + 2 & (row2 - 2 > -1) & (col2 + 2 < 8)) {
                        if (lBoard[row2][col2] == player2_king & (lBoard[row2 - 2][col2 + 2] == Empty) & ((lBoard[row2 - 1][col2 + 1] == player1) | (lBoard[row2 - 1][col2 + 1] == player1_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 - 1);
                            a.setCol(col2 + 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat_king");
                            m.setSign("-+");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2-2,col2+2,row2,col2,a,moves);
                        }
                    }
                    if (lBoard[row2][col2] == player2_king & (row2 + 2 < 8) & (col2 + 2 < 8)) {
                        if ((lBoard[row2 + 2][col2 + 2] == Empty) & ((lBoard[row2 + 1][col2 + 1] == player1) | (lBoard[row2 + 1][col2 + 1] == player1_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 + 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat_king");
                            m.setSign("++");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2+2,col2+2,row2,col2,a,moves);
                        }
                    }
                    if (lBoard[row2][col2] == player2_king & (row2 + 2 < 8) & (col2 - 2 >= 0)) {
                        if ((lBoard[row2 + 2][col2 - 2] == Empty) & ((lBoard[row2 + 1][col2 - 1] == player1) | (lBoard[row2 + 1][col2 - 1] == player1_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 - 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat_king");
                            m.setSign("+-");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2+2,col2-2,row2,col2,a,moves);
                        }
                    }

        }else{//need to fix

                    if (lBoard[row2][col2] == turn + 2 & (row2 - 2 > -1) & (col2 - 2 > -1))
                        if (lBoard[row2][col2] == player1_king & (lBoard[row2 - 2][col2 - 2] == Empty) & ((lBoard[row2 - 1][col2 - 1] == player2) | (lBoard[row2 - 1][col2 - 1] == player2_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 - 1);
                            a.setCol(col2 - 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat_king");
                            m.setSign("--");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2-2,col2-2,row2,col2,a,moves);                        }

                    if (lBoard[row2][col2] == turn + 2 & (row2 - 2 > -1) & (col2 + 2 < 8))
                        if (lBoard[row2][col2] == player1_king & (lBoard[row2 - 2][col2 + 2] == Empty) & ((lBoard[row2 - 1][col2 + 1] == player2) | (lBoard[row2 - 1][col2 + 1] == player2_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 - 1);
                            a.setCol(col2 + 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat_king");
                            m.setSign("-+");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2-2,col2+2,row2,col2,a,moves);
                        }

                    if (lBoard[row2][col2] == player1_king & (row2 + 2 < 8) & (col2 + 2 < 8))
                        if ((lBoard[row2 + 2][col2 + 2] == Empty) & ((lBoard[row2 + 1][col2 + 1] == player2) | (lBoard[row2 + 1][col2 + 1] == player2_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 + 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat_king");
                            m.setSign("++");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2+2,col2+2,row2,col2,a,moves);
                        }

                    if (lBoard[row2][col2] == player1_king & (row2 + 2 < 8) & (col2 - 2 >= 0))
                        if ((lBoard[row2 + 2][col2 - 2] == Empty) & ((lBoard[row2 + 1][col2 - 1] == player2) | (lBoard[row2 + 1][col2 - 1] == player2_king))) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 - 1);
                            m.setLrow(row2);
                            m.setLcol(col2);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat_king");
                            m.setSign("+-");
                            m.setA(a);
                            moves.add(m);
                            x=row2;y=col2;
                            erore=0;
                            Vector<Data>eats=new Vector<Data>();
                            eats.add(a);
                            Doubleeating_king(lBoard,turn,row2+2,col2-2,row2,col2,a,moves);
                        }

        }

    }


    public void doubel_eat(int[][]lBoard,int row2,int col2,int row1,int col1) {
        Legal_moves m ;
        Vector<Legal_moves> moves2 = new Vector<Legal_moves>();
        all_legal_eat(lBoard, moves2, human);
        for (int j = 0; j < moves2.size(); j++) {
            m=moves2.get(j);

            if (m.getTorow() == row1 & m.getTocol() == col1 & m.getLrow()==row2 & m.getLcol()==col2 & m.getType()=="eat_eat") {
                System.out.println("נכנס");
                undos.push(copy(lBoard));
                lBoard[m.getLrow()][m.getLcol()] = Empty;
                gBoard[m.getLrow()][m.getLcol()].setImageResource(black);
                if (m.getTorow() == 0) {
                    lBoard[m.getTorow()][m.getTocol()] = player1_king;
                    gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player_1_king);
                } else {
                    lBoard[m.getTorow()][m.getTocol()] = player1;
                    gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player11);
                }
                Vector<Data> q = m.getEats();
                for (int i = 0; i < q.size(); i++) {
                    lBoard[q.get(i).getRow()][q.get(i).getCol()] = Empty;
                    gBoard[q.get(i).getRow()][q.get(i).getCol()].setImageResource(black);
                }
                turn = 3 - turn;
                return;
            }
        }
    }

    public void Doubleeating(int [][] lBoard,int turn,int row2,int col2,int lrow,int lcol,Data s, Vector<Legal_moves> moves) {
    erore2++;
    if(erore2>8)
        return;
       int num;
       num=0;
        Vector<Data>eats=new Vector<Data>();
        eats.add(s);
        switch (turn) {
            case computer:

                    if (row2 + 2 < 8 & col2 + 2 < 8) {
                        if ((lBoard[row2 + 1][col2 + 1] == 3 - turn | lBoard[row2 + 1][col2 + 1] == 3 - turn + 2 )& (lBoard[row2 + 2][col2 + 2] ==Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 + 1);
                            m.setLrow(lrow);
                            m.setLcol(lcol);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat_eat");
                            m.setSign("++");
                            eats.add(a);
                            m.setEats(eats);
                            moves.add(m);
                            num++;
                        }
                    }
                if (row2 + 2 < 8 & col2 - 2 > -1) {
                    if ((lBoard[row2 + 1][col2 - 1] == 3 - turn | lBoard[row2 + 1][col2 - 1] == 3 - turn + 2) & (lBoard[row2 + 2][col2 - 2] ==Empty)) {
                        Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                        Data a = new Data(-1, -1);
                        if(num==1 )
                            eats.remove(eats.size()-1);
                        a.setRow(row2 + 1);
                        a.setCol(col2 - 1);
                        m.setLrow(lrow);
                        m.setLcol(lcol);
                        m.setTorow(row2 + 2);
                        m.setTocol(col2 - 2);
                        m.setType("eat_eat");
                        m.setSign("+-");
                        eats.add(a);
                        m.setEats(eats);
                        moves.add(m);
                        num++;
                    }
                }
                if(num==0)
                      return;
                if(num==1)
                    Doubleeating(lBoard,turn,moves.lastElement().getTorow(),moves.lastElement().getTocol(),lrow,lcol,s,moves);
                if(num==2){
                    int index=moves.size()-2;
                    Doubleeating(lBoard,turn,moves.lastElement().getTorow(),moves.lastElement().getTocol(),lrow,lcol,s,moves);
                    Doubleeating(lBoard,turn,moves.elementAt(index).getTorow(),moves.elementAt(index).getTocol(),lrow,lcol,s,moves);
                }
                break;
            case human:
                    if (row2 - 2 >-1 & col2 - 2 >-1) {
                        if ((lBoard[row2 - 1][col2 - 1] == 3 - turn | lBoard[row2 - 1][col2 - 1] == 3 - turn + 2) & (lBoard[row2 - 2][col2 - 2] ==Empty) ) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 - 1);
                            a.setCol(col2 - 1);
                            m.setLrow(lrow);
                            m.setLcol(lcol);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat_eat");
                            m.setSign("--");
                            eats.add(a);
                            m.setEats(eats);
                            moves.add(m);
                            num++;
                        }
                    }
                if (row2 - 2 >-1 & col2 + 2 <8) {
                    if ((lBoard[row2 - 1][col2 + 1] == 3 - turn | lBoard[row2 - 1][col2 + 1] == 3 - turn + 2) & (lBoard[row2 - 2][col2 + 2] ==Empty)) {
                        Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                        Data a = new Data(-1, -1);
                        if(num==1 )
                            eats.remove(eats.size()-1);
                        a.setRow(row2 - 1);
                        a.setCol(col2 + 1);
                        m.setLrow(lrow);
                        m.setLcol(lcol);
                        m.setTorow(row2 - 2);
                        m.setTocol(col2 + 2);
                        m.setType("eat_eat");
                        m.setSign("-+");
                        eats.add(a);
                        m.setEats(eats);
                        moves.add(m);
                        num++;
                    }
                }
                if(num==0)
                    return;
                if(num==1)
                    Doubleeating(lBoard,turn,moves.lastElement().getTorow(),moves.lastElement().getTocol(),lrow,lcol,s,moves);
                if(num==2){
                    int index=moves.size()-2;
                    Doubleeating(lBoard,turn,moves.lastElement().getTorow(),moves.lastElement().getTocol(),lrow,lcol,s,moves);
                    Doubleeating(lBoard,turn,moves.elementAt(index).getTorow(),moves.elementAt(index).getTocol(),lrow,lcol,s,moves);
                }
                break;

        }
    }

    public void Doubleeating_king(int [][] lBoard,int turn,int row2,int col2,int lrow,int lcol,Data s, Vector<Legal_moves> moves) {
        int num=0;
        boolean stop=false;
        erore++;
        if(erore>8)
            return;
        Vector<Data>eats=new Vector<Data>();
        eats.add(s);
                    if (row2 + 2 < 8 & col2 + 2 < 8) {
                        if ((lBoard[row2 + 1][col2 + 1] == 3 - turn | lBoard[row2 + 1][col2 + 1] == 3 - turn + 2) & (lBoard[row2 + 2][col2 + 2] ==Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            a.setRow(row2 + 1);
                            a.setCol(col2 + 1);
                            m.setLrow(lrow);
                            m.setLcol(lcol);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat_eat_king");
                            m.setSign("++");
                            eats.add(a);
                            m.setEats(eats);
                            moves.add(m);
                            stop=true;
                            num++;
                        }
                    }
                    if (row2 + 2 < 8 & col2 - 2 > -1) {
                        if ((lBoard[row2 + 1][col2 - 1] == 3 - turn | lBoard[row2 + 1][col2 - 1] == 3 - turn + 2) & (lBoard[row2 + 2][col2 - 2] ==Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            if(stop==true){
                                if(num==2){
                                    eats.remove(eats.size()-1); }
                            }
                            a.setRow(row2 + 1);
                            a.setCol(col2 - 1);
                            m.setLrow(lrow);
                            m.setLcol(lcol);
                            m.setTorow(row2 + 2);
                            m.setTocol(col2 - 2);
                            m.setType("eat_eat_king");
                            m.setSign("+-");
                            eats.add(a);
                            m.setEats(eats);
                            moves.add(m);
                            num++;
                            stop=true;
                        }
                    }

                        if (row2 - 2 > -1 & col2 - 2 > -1) {
                            if ((lBoard[row2 - 1][col2 - 1] == 3 - turn | lBoard[row2 - 1][col2 - 1] == 3 - turn + 2) & (lBoard[row2 - 2][col2 - 2] ==Empty)) {
                                Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                                Data a = new Data(-1, -1);
                                if(stop==true){
                                    if(num==2)
                                        eats.remove(eats.size()-1);
                                    if(num==3){
                                        eats.remove(eats.size()-1);
                                        eats.remove(eats.size()-2); }

                                }
                                a.setRow(row2 - 1);
                                a.setCol(col2 - 1);
                                m.setLrow(lrow);
                                m.setLcol(lcol);
                                m.setTorow(row2 - 2);
                                m.setTocol(col2 - 2);
                                m.setType("eat_eat_king");
                                m.setSign("--");
                                eats.add(a);
                                m.setEats(eats);
                                moves.add(m);
                                num++;
                                stop=true;
                            }
                        }
                    if (row2 - 2 > -1 & col2 + 2 < 8) {
                        if ((lBoard[row2 - 1][col2 + 1] == 3 - turn | lBoard[row2 - 1][col2 + 1] == 3 - turn + 2) & (lBoard[row2 - 2][col2 + 2] ==Empty)) {
                            Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                            Data a = new Data(-1, -1);
                            if(stop==true){
                                if(num==2)
                                    eats.remove(eats.size()-1);
                                if(num==3){
                                    eats.remove(eats.size()-1);
                                    eats.remove(eats.size()-2); }
                                if(num==4){
                                    eats.remove(eats.size()-1);
                                    eats.remove(eats.size()-2);
                                    eats.remove(eats.size()-3);}

                            }
                            a.setRow(row2 - 1);
                            a.setCol(col2 + 1);
                            m.setLrow(lrow);
                            m.setLcol(lcol);
                            m.setTorow(row2 - 2);
                            m.setTocol(col2 + 2);
                            m.setType("eat_eat_king");
                            m.setSign("-+");
                            eats.add(a);
                            m.setEats(eats);
                            moves.add(m);
                            num++;
                            stop=true;
                        }
                    }
                    stop=false;
                    if (num==0)
                        return;
                    if (num==1)
                        Doubleeating_king(lBoard, turn, moves.lastElement().getTorow(), moves.lastElement().getTocol(), lrow, lcol, s, moves);
                    if (num==2) {
                        int index1 = moves.size() - 2;
                        Doubleeating_king(lBoard, turn, moves.lastElement().getTorow(), moves.lastElement().getTocol(), lrow, lcol, s, moves);
                        Doubleeating_king(lBoard, turn, moves.elementAt(index1).getTorow(), moves.elementAt(index1).getTocol(), lrow, lcol, s, moves);
                    }
                    if (num==3) {
                        int index1 = moves.size() - 2;
                        int index2 = moves.size() - 3;
                        Doubleeating_king(lBoard, turn, moves.lastElement().getTorow(), moves.lastElement().getTocol(), lrow, lcol, s, moves);
                        Doubleeating_king(lBoard, turn, moves.elementAt(index1).getTorow(), moves.elementAt(index1).getTocol(), lrow, lcol, s, moves);
                        Doubleeating_king(lBoard, turn, moves.elementAt(index2).getTorow(), moves.elementAt(index2).getTocol(), lrow, lcol, s, moves);
                    }
                    if (num==4) {
                        int index1 = moves.size() - 2;
                        int index2 = moves.size() - 3;
                        int index3 = moves.size() - 4;
                        Doubleeating_king(lBoard, turn, moves.lastElement().getTorow(), moves.lastElement().getTocol(), lrow, lcol, s, moves);
                        Doubleeating_king(lBoard, turn, moves.elementAt(index1).getTorow(), moves.elementAt(index1).getTocol(), lrow, lcol, s, moves);
                        Doubleeating_king(lBoard, turn, moves.elementAt(index2).getTorow(), moves.elementAt(index2).getTocol(), lrow, lcol, s, moves);
                        Doubleeating_king(lBoard, turn, moves.elementAt(index3).getTorow(), moves.elementAt(index3).getTocol(), lrow, lcol, s, moves);
                    }


    }


    public void doubel_eat_king(int[][]lBoard,int row2,int col2,int row1,int col1) {
        Legal_moves m = new Legal_moves(row2, col2, "NULL", -1, -1, "NULL", null, null);
        Vector<Data> eats = new Vector<Data>();
        Vector<Legal_moves> moves2 = new Vector<Legal_moves>();
        all_eat_king(lBoard,moves2,human);
        for (int j = 0; j < moves2.size(); j++) {
            m=moves2.get(j);
            if (m.getTorow() == row1 & m.getTocol() == col1 & m.getLrow()==row2 & m.getLcol()==col2 & m.getType()=="eat_eat_king") {
            System.out.println("נכנס");
            undos.push(copy(lBoard));
            lBoard[m.getLrow()][m.getLcol()] = Empty;
            gBoard[m.getLrow()][m.getLcol()].setImageResource(black);
            lBoard[m.getTorow()][m.getTocol()] = player1_king;
            gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player_1_king);
            Vector<Data> q = m.getEats();
            for (int i = 0; i < q.size(); i++) {
                lBoard[q.get(i).getRow()][q.get(i).getCol()] = Empty;
                gBoard[q.get(i).getRow()][q.get(i).getCol()].setImageResource(black);
            }
            turn = 3 - turn;
            return;
        }
    }
    }

    public void make_move(int[][] DogmaBoard, Legal_moves m, int turn) {

        int typs = -1;
        String mov = "mov";
        String eatt = "eat";
        String mov_king = "mov_king";
        String eat_king = "eat_king";
        String eat_eat = "eat_eat";
        String eat_eat_king = "eat_eat_king";
        if (m.getType().equals(mov))
            typs = 0;
        else if (m.getType().equals(eatt))
            typs = 1;
        else if (m.getType().equals(mov_king))
            typs = 2;
        else if (m.getType().equals(eat_king))
            typs = 3;
        else if (m.getType().equals(eat_eat))
            typs = 4;
        else if (m.getType().equals(eat_eat_king))
            typs = 5;
        Data a;
        if (turn == computer) {

            switch (typs) {
                case 0:
                    DogmaBoard[m.getLrow()][m.getLcol()] = Empty;
                    DogmaBoard[m.getTorow()][m.getTocol()] = player2;
                    if (m.getTorow() == 7)
                        DogmaBoard[m.getTorow()][m.getTocol()] = player2_king;
                    break;
                case 1:
                    DogmaBoard[m.getLrow()][m.getLcol()] = Empty;
                    DogmaBoard[m.getTorow()][m.getTocol()] = player2;
                    if (m.getTorow() == 7)
                        DogmaBoard[m.getTorow()][m.getTocol()] = player2_king;
                    a = m.getA();
                    DogmaBoard[a.getRow()][a.getCol()] = Empty;


                    break;
                case 2:

                    DogmaBoard[m.getLrow()][m.getLcol()] = Empty;
                    DogmaBoard[m.getTorow()][m.getTocol()] = player2_king;

                    break;
                case 3:

                    DogmaBoard[m.getLrow()][m.getLcol()] = Empty;
                    DogmaBoard[m.getTorow()][m.getTocol()] = player2_king;
                    a = m.getA();
                    DogmaBoard[a.getRow()][a.getCol()] = Empty;


                    break;
                case 4:
                    DogmaBoard[m.getLrow()][m.getLcol()] = Empty;
                    if (m.getTorow() == 7)
                        DogmaBoard[m.getTorow()][m.getTocol()] = player2_king;
                    DogmaBoard[m.getTorow()][m.getTocol()] = player2;
                  Vector<Data> q=m.getEats();
                    for (int i = 0; i < q.size(); i++) {
                        DogmaBoard[q.get(i).getRow()][q.get(i).getCol()]=Empty;
                    }


                    break;
                case 5:
                    DogmaBoard[m.getLrow()][m.getLcol()] = Empty;
                    DogmaBoard[m.getTorow()][m.getTocol()] = player2_king;
                    Vector<Data> p=m.getEats();
                    for (int i = 0; i < p.size(); i++) {
                        DogmaBoard[p.get(i).getRow()][p.get(i).getCol()]=Empty;
                    }


                    break;
                case -1:

                    break;
                default:

            }

        } else if (turn == human) {

            switch (typs) {
                case 0:
                    DogmaBoard[m.getLrow()][m.getLcol()] = Empty;
                    DogmaBoard[m.getTorow()][m.getTocol()] = player1;
                    if (m.getTorow() == 0)
                        DogmaBoard[m.getTorow()][m.getTocol()] = player1_king;

                    break;
                case 1:
                    DogmaBoard[m.getLrow()][m.getLcol()] = Empty;
                    DogmaBoard[m.getTorow()][m.getTocol()] = player1;
                    if (m.getTorow() == 0)
                        DogmaBoard[m.getTorow()][m.getTocol()] = player1_king;
                    a = m.getA();
                    DogmaBoard[a.getRow()][a.getCol()] = Empty;

                    break;
                case 2:

                    DogmaBoard[m.getLrow()][m.getLcol()] = Empty;
                    DogmaBoard[m.getTorow()][m.getTocol()] = player1_king;

                    break;
                case 3:
                    DogmaBoard[m.getLrow()][m.getLcol()] = Empty;
                    DogmaBoard[m.getTorow()][m.getTocol()] = player1_king;
                    a = m.getA();
                    DogmaBoard[a.getRow()][a.getCol()] = Empty;


                    break;
                case 4:
                    DogmaBoard[m.getLrow()][m.getLcol()] = Empty;
                    if (m.getTorow() == 0)
                        DogmaBoard[m.getTorow()][m.getTocol()] = player1_king;
                    DogmaBoard[m.getTorow()][m.getTocol()] = player1;
                  Vector<Data> q=m.getEats();
                    for (int i = 0; i < q.size(); i++) {
                        DogmaBoard[q.get(i).getRow()][q.get(i).getCol()]=Empty;
                    }


                    break;
                case 5:
                    DogmaBoard[m.getLrow()][m.getLcol()] = Empty;
                        DogmaBoard[m.getTorow()][m.getTocol()] = player1_king;
                    Vector<Data> p=m.getEats();
                    for (int i = 0; i < p.size(); i++) {
                        DogmaBoard[p.get(i).getRow()][p.get(i).getCol()]=Empty;
                    }


                    break;
                case -1:

                    break;
                default:

            }

        }

    }

    public Vector<Legal_moves> moves(int[][] tempBoard, int turn, Vector<Legal_moves> moves) {
        moves.removeAllElements();
        all_legal_move(tempBoard, moves, turn);
        all_legal_eat(tempBoard, moves, turn);
        all_move_king(tempBoard, moves, turn);
        all_eat_king(tempBoard, moves, turn);
        moves10=moves;
        return moves;
    }
    public Vector<Legal_moves> moves_paint(int[][] tempBoard, int turn, Vector<Legal_moves> moves,int i,int j) {
        moves.removeAllElements();
        all_legal_move_paint(tempBoard, moves, turn,i,j);
        all_legal_eat_paint(tempBoard, moves, turn,i,j);
        all_move_king_paint(tempBoard, moves, turn,i,j);
        all_eat_king_paint(tempBoard, moves, turn,i,j);
        moves100=moves;
        return moves;
    }

    public void Board_paint(int[][] board, int turn, int i, int j){
        Vector<Legal_moves> moves = new Vector<Legal_moves>();
        moves_paint(lBoard,human,moves,i,j);
        if(moves.size()==0)
        System.out.println("000000000000");
        for ( i = 0; i < moves.size(); i++) {
            System.out.println(moves.get(i).getTorow()+"-----"+moves.get(i).getTocol());
            gBoard[moves.get(i).getTorow()][moves.get(i).getTocol()].setImageResource(R.drawable.paint);
        }

    }
    public void Board_paint_reverse(Vector<Legal_moves> moves){
        if(moves==null)
            return;
        for ( int i = 0; i < moves.size(); i++) {
            System.out.println("777777777");
            System.out.println(moves.get(i).getTorow()+"-----"+moves.get(i).getTocol());
            gBoard[moves.get(i).getTorow()][moves.get(i).getTocol()].setImageResource(black);
        }
        moves100.removeAllElements();
    }

    public  void computerMove3() {//עושה את המהלך של האלגוריתם negamax
        undos.push(copy(lBoard));
        Legal_moves m ;
        int index = 0, typs = -1, num;
        String mov = "mov";
        String eat = "eat";
        String mov_king = "mov_king";
        String eat_king = "eat_king";
        String eat_eat = "eat_eat";
        String eat_eat_king = "eat_eat_king";
        Data a;
        if (turn == computer) {
            System.out.println("נכנס");
            if(if_p2_win(lBoard)==1)
                level=1;
            else if(if_p2_win(lBoard)==3)
                level=3;
            numgif= negamax(lBoard, level, computer);
            m = bestMov;
            System.out.println("maorr"+m.getLrow()+"  "+m.getLcol());
            if (m.getLrow() == -1)
                return;
            if (m.getType().equals(mov))
                typs = 0;
            if (m.getType().equals(eat))
                typs = 1;
            if (m.getType().equals(mov_king))
                typs = 2;
            if (m.getType().equals(eat_king))
                typs = 3;
            if (m.getType().equals(eat_eat))
                typs = 4;
            if (m.getType().equals(eat_eat_king))
                typs = 5;
            System.out.println(typs);
            switch (typs) {
                case 0:

                    lBoard[m.getLrow()][m.getLcol()] = Empty;
                    gBoard[m.getLrow()][m.getLcol()].setImageResource(black);
                    if (m.getTorow() != 7) {
                        lBoard[m.getTorow()][m.getTocol()] = computer;
                        gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player22);
                    } else {
                        lBoard[m.getTorow()][m.getTocol()] = player2_king;
                        gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player_2_king);
                    }
                    turn = 3 - turn;
                    break;
                case 1:
                    gBoard[m.getLrow()][m.getLcol()].setImageResource(black);
                    lBoard[m.getLrow()][m.getLcol()] = Empty;
                    if (m.getTorow() == 7) {
                        lBoard[m.getTorow()][m.getTocol()] = player2_king;
                        gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player_2_king);
                    } else {
                        lBoard[m.getTorow()][m.getTocol()] = computer;
                        gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player22);
                    }
                    a = m.getA();
                    lBoard[a.getRow()][a.getCol()] = Empty;
                    gBoard[a.getRow()][a.getCol()].setImageResource(black);
                    turn = 3 - turn;
                    break;
                case 2:

                    lBoard[m.getLrow()][m.getLcol()] = Empty;
                    gBoard[m.getLrow()][m.getLcol()].setImageResource(black);
                    lBoard[m.getTorow()][m.getTocol()] = player2_king;
                    gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player_2_king);
                    turn = 3 - turn;

                    break;
                case 3:
                    lBoard[m.getLrow()][m.getLcol()]=Empty;
                    gBoard[m.getLrow()][m.getLcol()].setImageResource(black);
                    lBoard[m.getTorow()][m.getTocol()]=player2_king;
                    gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player_2_king);
                    a = m.getA();
                    lBoard[a.getRow()][a.getCol()] = Empty;
                    gBoard[a.getRow()][a.getCol()].setImageResource(black);
                    turn=3-turn;
                    break;
                case 4:
                    lBoard[m.getLrow()][m.getLcol()]=Empty;
                    gBoard[m.getLrow()][m.getLcol()].setImageResource(black);
                    if(m.getTorow()==7) {
                        lBoard[m.getTorow()][m.getTocol()]=player2_king;
                        gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player_2_king);
                    }
                    else {
                        lBoard[m.getTorow()][m.getTocol()]=player2;
                        gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player22);
                    }
                  Vector<Data> q=m.getEats();
                    for (int i = 0; i < q.size(); i++) {
                        lBoard[q.get(i).getRow()][q.get(i).getCol()]=Empty;
                        gBoard[q.get(i).getRow()][q.get(i).getCol()].setImageResource(black);
                    }

                    turn=3-turn;

                    break;
                case 5:
                    lBoard[m.getLrow()][m.getLcol()]=Empty;
                    gBoard[m.getLrow()][m.getLcol()].setImageResource(black);
                        lBoard[m.getTorow()][m.getTocol()]=player2_king;
                        gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player_2_king);
                    Vector<Data> p=m.getEats();
                    for (int i = 0; i < p.size(); i++) {
                        lBoard[p.get(i).getRow()][p.get(i).getCol()]=Empty;
                        gBoard[p.get(i).getRow()][p.get(i).getCol()].setImageResource(black);
                    }

                    turn=3-turn;

                    break;
                case -1:

                    break;
                default:

            }
        }
        System.out.println("סיים");
        mp.start();
    }

    private void open_menu() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    private void open_level() {
        Intent intent=new Intent(this,level.class);
        startActivity(intent);
    }
    public void restart(){

        int flag=0;
        for (int i = 0; i < Row; i++) {
            for (int j = 0; j < Col; j++) {
                lBoard[i][j]=Empty;
            }
        }
        for (int i = 0; i < Row; i++) {
            for (int j = 0; j < Col; j++) {
                if(i%2==0 & (j == 0 | j == 2 | j == 4 | j == 6))
                    gBoard[i][j].setImageResource(black);
                if(i%2!=0 & (j == 7 | j == 5 | j == 3 | j == 1))
                    gBoard[i][j].setImageResource(black);

                if ((i == 1) & (j == 7 | j == 5 | j == 3 | j == 1)) {
                    gBoard[i][j].setImageResource(R.drawable.player22);
                    lBoard[i][j] = player2;
                } else if ((i == 0 | i == 2) & (j == 0 | j == 2 | j == 4 | j == 6)) {
                    gBoard[i][j].setImageResource(R.drawable.player22);
                    lBoard[i][j] = player2;
                }
                if ((i == 6) & (j == 0 | j == 2 | j == 4 | j == 6)) {
                    gBoard[i][j].setImageResource(R.drawable.player11);
                    lBoard[i][j] = player1;
                } else if ((i == 7 | i == 5) & (j == 7 | j == 5 | j == 3 | j == 1)) {
                    gBoard[i][j].setImageResource(R.drawable.player11);
                    lBoard[i][j] = player1;

                }
            }


        }
        turn=player1;
        moves10.removeAllElements();
    }
    public  void redo(Stack<int[][]> redo){
        if (redo.isEmpty())
            return;
        System.out.println("undo");
        int [][]Dboard=new int [8][8];

        copyBoard(Dboard,redo.pop());
        for (int i = 0; i < Row; i++) {
            for (int j = 0; j < Col; j++) {
                lBoard[i][j]=Dboard[i][j];
            }
        }
        for (int i = 0; i < Row; i++) {
            for (int j = 0; j < Col; j++) {
                if(i%2==0 & (j == 0 | j == 2 | j == 4 | j == 6))
                    gBoard[i][j].setImageResource(black);
                if(i%2!=0 & (j == 7 | j == 5 | j == 3 | j == 1))
                    gBoard[i][j].setImageResource(black);
            }
        }
        for (int i = 0; i < Row; i++) {
            for (int j = 0; j < Col; j++) {
                if(lBoard[i][j]==player1)
                    gBoard[i][j].setImageResource(R.drawable.player11);

                if(lBoard[i][j]==player1_king)
                    gBoard[i][j].setImageResource(R.drawable.player_1_king);

                if(lBoard[i][j]==player2)
                    gBoard[i][j].setImageResource(R.drawable.player22);

                if(lBoard[i][j]==player2_king)
                    gBoard[i][j].setImageResource(R.drawable.player_2_king);

            }
        }
     //   turn=3-turn;
    }
    public  void undo(Stack<int[][]> undos){
        if (undos.isEmpty())
            return;
        System.out.println("undo");
        int [][]Dboard=new int [8][8];
        redo.push(copy(lBoard));
        copyBoard(Dboard,undos.pop());
        for (int i = 0; i < Row; i++) {
            for (int j = 0; j < Col; j++) {
                lBoard[i][j]=Dboard[i][j];
            }
        }
        for (int i = 0; i < Row; i++) {
            for (int j = 0; j < Col; j++) {
                if(i%2==0 & (j == 0 | j == 2 | j == 4 | j == 6))
                    gBoard[i][j].setImageResource(black);
                if(i%2!=0 & (j == 7 | j == 5 | j == 3 | j == 1))
                    gBoard[i][j].setImageResource(black);
            }
        }
        for (int i = 0; i < Row; i++) {
            for (int j = 0; j < Col; j++) {
                if(lBoard[i][j]==player1)
                    gBoard[i][j].setImageResource(R.drawable.player11);

                if(lBoard[i][j]==player1_king)
                    gBoard[i][j].setImageResource(R.drawable.player_1_king);

                if(lBoard[i][j]==player2)
                    gBoard[i][j].setImageResource(R.drawable.player22);

                if(lBoard[i][j]==player2_king)
                    gBoard[i][j].setImageResource(R.drawable.player_2_king);

            }
        }
//turn=3-turn;
    }

    public int[][] copy(int b[][]) {
        int a[][]=new int[Row][Col];
        for (int i = 0; i < Row; i++) {
            for (int j = 0; j <Col; j++) {
                a[i][j]=b[i][j];
            }
        }
        return a;
    }
    public void win_messege_p1(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Board_2.this);
        builder.setMessage("The white player won");
        builder.setTitle("Victory");
        mp2.start();
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel();

                restart();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void win_messege_p2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Board_2.this);
        builder.setMessage("The computer won");
        builder.setTitle("Victory");
        mp2.start();
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel();
                restart();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public int if_win(int lBoard[][]) {
        int num1 = 0, num2 = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (lBoard[i][j] == player1 || lBoard[i][j] == player1_king)
                    num1++;
                if (lBoard[i][j] == player2 || lBoard[i][j] == player2_king)
                    num2++;

            }
        }
        if (num1 == 0)
            return player2;
        if (num2 == 0)
            return player1;

        return 0;
    }
    public boolean if_draw(){
        Vector<Legal_moves> moves = new Vector<Legal_moves>();
        moves = moves(lBoard, 3-turn, moves);
        if(moves.isEmpty())
            return true;
        return false;
 }
    public void draw_Message(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Board_2.this);
        builder.setMessage("This game is a draw");
        builder.setTitle("Message");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel();
                restart();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void gifchange(){
        if(numgif>5)
            gif.setImageResource(R.drawable.imogiheppy);

        if(numgif<5&numgif>=0)
           gif.setImageResource(R.drawable.imogi);
        if(numgif>-5 &numgif<0)
            gif.setImageResource(R.drawable.imogiboom);

        if( numgif>-15 &numgif<-5)
            gif.setImageResource(R.drawable.imogithink);
        if( numgif>-30 &numgif<-15)
            gif.setImageResource(R.drawable.imogiangry);
        if(numgif<-30 )
            gif.setImageResource(R.drawable.imogicraime);



    }
}