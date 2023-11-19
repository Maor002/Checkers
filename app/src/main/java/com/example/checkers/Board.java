package com.example.checkers;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Stack;
import java.util.Vector;
import java.util.Stack;

import static com.example.checkers.R.color.black;
import static com.example.checkers.R.color.white;

public class Board extends AppCompatActivity implements View.OnClickListener {
    private static int Row=8;
    private static int Col=8;
    private static int[][] lBoard=new int [Row][Col];
    private static ImageButton[][] gBoard=new ImageButton[Row][Col];
    private final int player1=1;
    private final int player2=2;
    private final int player1_king=3;
    private final int player2_king=4;
    private int Empty=0;
    static
    int turn=1;
    private final int computer = 2;
    private final int human = 1;
    static  int count=0;
    int row2=0,col2=0,row1=0,col1=0,x,y;
    static int num_of_eat=0;
    public static Stack<int[][]> undos = new Stack<int[][]>();
    MediaPlayer mp;
    MediaPlayer mp2;
    AdView mAdView;
   private Button menu1;
    private Button restart;
    TextView textView;
    static int erore = 0;
    static int erore2 = 0;
    Vector<Legal_moves> moves100=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        mp  = MediaPlayer.create(this, R.raw.maorr);
        mp2  = MediaPlayer.create(this, R.raw.victory);
        menu1= findViewById(R.id.menu1);
        menu1.setOnClickListener(this);
        restart= findViewById(R.id.restart);
        textView= findViewById(R.id.textView);
        restart.setOnClickListener(this);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-7979606640397319/6779245702");
        AdSize adSize = new AdSize(320, 50);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        initBoard(lBoard,gBoard);

    }
    @SuppressLint("ResourceAsColor")
    public void initBoard(int[][]lBoard, ImageButton[][]gBoard){

        int flag=0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                lBoard[i][j]=Empty;
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

        gBoard[0][0]= (ImageButton)findViewById(R.id.b0_0);
        gBoard[0][2]=(ImageButton)findViewById(R.id.b0_2);
        gBoard[0][4]=(ImageButton)findViewById(R.id.b0_4);
        gBoard[0][6]=(ImageButton)findViewById(R.id.b0_6);
        gBoard[1][1]=(ImageButton)findViewById(R.id.b1_1);
        gBoard[1][3]=(ImageButton)findViewById(R.id.b1_3);
        gBoard[1][5]=(ImageButton)findViewById(R.id.b1_5);
        gBoard[1][7]=(ImageButton)findViewById(R.id.b1_7);
        gBoard[2][0]=(ImageButton)findViewById(R.id.b2_0);
        gBoard[2][2]=(ImageButton)findViewById(R.id.b2_2);
        gBoard[2][4]=(ImageButton)findViewById(R.id.b2_4);
        gBoard[2][6]=(ImageButton)findViewById(R.id.b2_6);
        gBoard[3][1]=(ImageButton)findViewById(R.id.b3_1);
        gBoard[3][3]=(ImageButton)findViewById(R.id.b3_3);
        gBoard[3][5]=(ImageButton)findViewById(R.id.b3_5);
        gBoard[3][7]=(ImageButton)findViewById(R.id.b3_7);
        gBoard[4][0]=(ImageButton)findViewById(R.id.b4_0);
        gBoard[4][2]=(ImageButton)findViewById(R.id.b4_2);
        gBoard[4][4]=(ImageButton)findViewById(R.id.b4_4);
        gBoard[4][6]=(ImageButton)findViewById(R.id.b4_6);
        gBoard[5][1]=(ImageButton)findViewById(R.id.b5_1);
        gBoard[5][3]=(ImageButton)findViewById(R.id.b5_3);
        gBoard[5][5]=(ImageButton)findViewById(R.id.b5_5);
        gBoard[5][7]=(ImageButton)findViewById(R.id.b5_7);
        gBoard[6][0]=(ImageButton)findViewById(R.id.b6_0);
        gBoard[6][2]=(ImageButton)findViewById(R.id.b6_2);
        gBoard[6][4]=(ImageButton)findViewById(R.id.b6_4);
        gBoard[6][6]=(ImageButton)findViewById(R.id.b6_6);
        gBoard[7][1]=(ImageButton)findViewById(R.id.b7_1);
        gBoard[7][3]=(ImageButton)findViewById(R.id.b7_3);
        gBoard[7][5]=(ImageButton)findViewById(R.id.b7_5);
        gBoard[7][7]=(ImageButton)findViewById(R.id.b7_7);
        //
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if((i%2==0 & j%2==0) ||(i%2!=0 & j%2!=0))
                    gBoard[i][j].setOnClickListener(this);
            }
        }
        if(turn==human){
            textView.setText("The turn of the white");
            textView.setTextColor(Color.parseColor("#FFFFFF"));}
        else if(turn==computer) {
            textView.setText("The turn of the brown");
            textView.setTextColor(black);}
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
if(v==menu1)
    open_menu();
if(v==restart)
    restart();
        int i=0,j = 0;
        for ( i = 0; i < 8; i++) {
            for ( j = 0; j < 8; j++) {
                if((i%2==0 & j%2==0) ||(i%2!=0 & j%2!=0))
                    if(gBoard[i][j].getId()==v.getId()){
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
        //maor();
        printlbord(lBoard);


        Data m=legal_eat(lBoard, row2, col2, row1, col1);
        Data a=legal_king_eat(lBoard, row2, col2, row1, col1);
        if (m.getRow() != -1 & count == 2) {

            do_eat(gBoard,lBoard, row2, col2, row1, col1,m);
            count = 0;
        }
        if (a.getRow() != -1 & count == 2) {
            do_eat(gBoard,lBoard, row2, col2, row1, col1,a);
            count = 0;
        }
        if(count==2){
            doubel_eat(lBoard,row2,col2,row1,col1);
        }
        if ( count == 2 ) {
            doubel_eat_king( lBoard, row2, col2, row1, col1);
            //count = 0;
        }
        if (count == 2 && !legal_move(lBoard, row2, col2, row1, col1)&& !legal_move_king(lBoard, row2, col2, row1, col1)) {
            count = 0;
            System.out.println("maor" + row2 + "  " + col2);
            System.out.println("maor" + row1 + "  " + col1);
            System.out.println("--false--");
        }
        do_move(gBoard,lBoard,row2,col2,row1,col1);
        if(if_win(lBoard)==computer)
            win_messege_p2();
        if (if_win(lBoard)==human)
            win_messege_p1();
        mp.start();
        if(turn==human){
            textView.setText("The turn of the white");
            textView.setTextColor(Color.parseColor("#FFFFFF"));}
        else if(turn==computer) {
            textView.setText("The turn of the brown");
            textView.setTextColor(black);}
    }
    public void do_move(ImageButton[][]gBoard,int[][]lBoard,int row2,int col2,int row1,int col1){
        if(count==2 ) {
            if(lBoard[row2][col2]==player1_king  ){
                lBoard[row2][col2]=Empty;
                lBoard[row1][col1]=player1_king;
                gBoard[row1][col1].setImageResource(R.drawable.player_1_king);
                gBoard[row2][col2].setImageResource(R.color.black);
                turn=3-turn;
                count=0;
                return;
            }
            if(lBoard[row2][col2]==player2_king  ){
                lBoard[row2][col2]=Empty;
                lBoard[row1][col1]=player2_king;
                gBoard[row1][col1].setImageResource(R.drawable.player_2_king);
                gBoard[row2][col2].setImageResource(R.color.black);
                turn=3-turn;
                count=0;
                return;
            }
            System.out.println("maor" + row2 + "  " + col2);
            System.out.println("TURN" + turn);
            if(turn==player2){
                if(row1==7) {
                    gBoard[row1][col1].setImageResource(R.drawable.player_2_king);
                    lBoard[row1][col1]=player2_king;
                }
                else  {
                    gBoard[row1][col1].setImageResource(R.drawable.player22);
                    lBoard[row1][col1]=player2;
                }
            }
            else {
                if(row1==0){
                    gBoard[row1][col1].setImageResource(R.drawable.player_1_king);
                    lBoard[row1][col1]=player1_king;
                }
                else {
                    gBoard[row1][col1].setImageResource(R.drawable.player11);
                    lBoard[row1][col1]=player1;
                }
            }
            gBoard[row2][col2].setImageResource(R.color.black);
            lBoard[row2][col2]=Empty;
            turn=3-turn;
            count=0;
        }
    }
    public Boolean legal_move(int[][]lBoard,int row2,int col2,int row1,int col1) {
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
                    if ( lBoard[row2][col2] == player1 & (lBoard[row1][col1] == Empty)
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

        for (int i = 0; i < 8; i++) {
            System.out.print(i+" - ");
            for (int j = 0; j < 8; j++) {
                System.out.print(lBoard[i][j] + "  ");
                if (j == 7)
                    System.out.println();
            }
        }


    }
    public Data legal_eat(int[][]lBoard,int row2,int col2,int row1,int col1){
        Data m=new Data(-1,-1);
        if(count!=2)
            return m;
        switch (turn) {
            case player2:
                if (lBoard[row2][col2] == turn &(row2+2<8)&(col2+2<8)) {
                    if ((lBoard[row1][col1] == Empty)&((lBoard[row2+1][col2+1] == player1)|(lBoard[row2+1][col2+1] == player1_king))
                            & ((row2 + 2 == row1 & col2 + 2 == col1) )){
                        m.setRow(row2+1);
                        m.setCol(col2+1);
                        return m;
                    }}
                if (lBoard[row2][col2] == turn &(row2+2<8)&(col2-2>=0)) {
                    if ((lBoard[row1][col1] == Empty)&((lBoard[row2+1][col2-1] == player1)|(lBoard[row2+1][col2-1] == player1_king))
                            & ((row2 + 2 == row1 & col2 - 2 == col1) )) {
                        m.setRow(row2+1);
                        m.setCol(col2-1);

                        return m;
                    }
                }
                break;
            case player1:
                if (lBoard[row2][col2] == turn & (row2-2>-1)&(col2-2>-1)) {
                    if ( lBoard[row2][col2] == player1 & (lBoard[row1][col1] == Empty) &((lBoard[row2-1][col2-1] == player2)|(lBoard[row2-1][col2-1] == player2_king))
                            & ((row2 - 2 == row1 & col2 - 2 == col1) )) {
                        m.setRow(row2-1);
                        m.setCol(col2-1);

                        return m;
                    }
                }
                if (lBoard[row2][col2] == turn & (row2-2>-1)&(col2+2<8)) {
                    if ( lBoard[row2][col2] == player1 & (lBoard[row1][col1] == Empty) &((lBoard[row2-1][col2+1] == player2)|(lBoard[row2-1][col2+1] == player2_king))
                            & ((row2 - 2 == row1 & col2 + 2 == col1) )) {
                        m.setRow(row2-1);
                        m.setCol(col2+1);
                       ;
                        return m;
                    }
                }
                break;
            default:

                // code block
        }
        return m;
    }
    public void do_eat(ImageButton[][]gBoard,int[][]lBoard,int row2,int col2,int row1,int col1,Data m){
        if(count==2 ) {
            if(lBoard[row2][col2]==player1_king  ){
                lBoard[row2][col2]=Empty;
                lBoard[row1][col1]=player1_king;
                gBoard[row1][col1].setImageResource(R.drawable.player_1_king);
                gBoard[row2][col2].setImageResource(R.color.black);
                gBoard[m.getRow()][m.getCol()].setImageResource(R.color.black);
                lBoard[m.getRow()][m.getCol()]=Empty;
                turn=3-turn;
                count=0;
                return;
            }
            if(lBoard[row2][col2]==player2_king  ){
                lBoard[row2][col2]=Empty;
                lBoard[row1][col1]=player2_king;
                gBoard[row1][col1].setImageResource(R.drawable.player_2_king);
                gBoard[row2][col2].setImageResource(R.color.black);
                gBoard[m.getRow()][m.getCol()].setImageResource(R.color.black);
                lBoard[m.getRow()][m.getCol()]=Empty;
                turn=3-turn;
                count=0;
                return;
            }
            System.out.println("maor" + row2 + "  " + col2);
            System.out.println("TURN" + turn);
            if(turn==player2){
                if(row1==7) {
                    gBoard[row1][col1].setImageResource(R.drawable.player_2_king);
                    lBoard[row1][col1]=player2_king;
                }
                else  {
                    gBoard[row1][col1].setImageResource(R.drawable.player22);
                    lBoard[row1][col1]=player2;
                }
            }
            else {
                if(row1==0){
                    gBoard[row1][col1].setImageResource(R.drawable.player_1_king);
                    lBoard[row1][col1]=player1_king;
                }
                else {
                    gBoard[row1][col1].setImageResource(R.drawable.player11);
                    lBoard[row1][col1]=player1;
                }
            }
            gBoard[row2][col2].setImageResource(R.color.black);
            lBoard[row2][col2]=Empty;
            gBoard[m.getRow()][m.getCol()].setImageResource(R.color.black);
            lBoard[m.getRow()][m.getCol()]=Empty;
            turn=3-turn;
            count=0;
        }
    }
    public Boolean legal_move_king(int[][]lBoard,int row2,int col2,int row1,int col1){
        switch (turn+2) {
            case player2_king:
                if (lBoard[row2][col2] == turn+2) {
                    if (lBoard[row2][col2] == turn+2 & lBoard[row2][col2] == player2_king & (lBoard[row1][col1] == Empty)
                            & ((row2 + 1 == row1 & col2 + 1 == col1) | (row2 + 1 == row1 & col2 - 1 == col1)))
                        return true;
                }
                if (lBoard[row2][col2] == turn+2) {
                    if ( lBoard[row2][col2] == player2_king & (lBoard[row1][col1] == Empty)
                            & ((row2 - 1 == row1 & col2 - 1 == col1) | (row2 - 1 == row1 & col2 + 1 == col1)))
                        return true;
                }
                break;
            case player1_king:
                if (lBoard[row2][col2] == turn+2) {
                    if ( lBoard[row2][col2] == player1_king & (lBoard[row1][col1] == Empty)
                            & ((row2 + 1 == row1 & col2 + 1 == col1) | (row2 + 1 == row1 & col2 - 1 == col1)))
                        return true;
                }
                if (lBoard[row2][col2] == turn+2) {
                    if ( lBoard[row2][col2] == player1_king & (lBoard[row1][col1] == Empty)
                            & ((row2 - 1 == row1 & col2 - 1 == col1) | (row2 - 1 == row1 & col2 + 1 == col1)))
                        return true;
                }
                break;
            default:

                // code block
        }
        return false;
    }
    public Data legal_king_eat(int[][]lBoard,int row2,int col2,int row1,int col1){
        Data m=new Data(-1,-1);
        if(count!=2)
            return m;
        switch (turn+2) {
            case player2_king:
                if (lBoard[row2][col2] == turn+2 & (row2-2>-1)&(col2-2>-1)) {
                    if ( lBoard[row2][col2] == player2_king & (lBoard[row1][col1] == Empty) &((lBoard[row2-1][col2-1] == player1)|(lBoard[row2-1][col2-1] == player1_king))
                            & ((row2 - 2 == row1 & col2 - 2 == col1) )) {
                        m.setRow(row2-1);
                        m.setCol(col2-1);
                        return m;
                    }
                }
                if (lBoard[row2][col2] == turn+2 & (row2-2>-1)&(col2+2<8)) {
                    if ( lBoard[row2][col2] == player2_king & (lBoard[row1][col1] == Empty) &((lBoard[row2-1][col2+1] == player1)|(lBoard[row2-1][col2+1] == player1_king))
                            & ((row2 - 2 == row1 & col2 + 2 == col1) )) {
                        m.setRow(row2-1);
                        m.setCol(col2+1);
                        return m;
                    }
                }
                if (lBoard[row2][col2] == player2_king &(row2+2<8)&(col2+2<8)) {
                    if ((lBoard[row1][col1] == Empty)&((lBoard[row2+1][col2+1] == player1)|(lBoard[row2+1][col2+1] == player1_king))
                            & ((row2 + 2 == row1 & col2 + 2 == col1) )){
                        m.setRow(row2+1);
                        m.setCol(col2+1);
                        return m;
                    }}
                if (lBoard[row2][col2] == player2_king &(row2+2<8)&(col2-2>=0)) {
                    if ((lBoard[row1][col1] == Empty)&((lBoard[row2+1][col2-1] == player1)|(lBoard[row2+1][col2-1] == player1_king))
                            & ((row2 + 2 == row1 & col2 - 2 == col1) )) {
                        m.setRow(row2+1);
                        m.setCol(col2-1);
                        return m;
                    }
                }
                break;
            case player1_king:
                if (lBoard[row2][col2] == turn+2 & (row2-2>-1)&(col2-2>-1)) {
                    if ( lBoard[row2][col2] == player1_king & (lBoard[row1][col1] == Empty) &((lBoard[row2-1][col2-1] == player2)|(lBoard[row2-1][col2-1] == player2_king))
                            & ((row2 - 2 == row1 & col2 - 2 == col1) )) {
                        m.setRow(row2-1);
                        m.setCol(col2-1);
                        return m;
                    }
                }
                if (lBoard[row2][col2] == turn+2 & (row2-2>-1)&(col2+2<8)) {
                    if ( lBoard[row2][col2] == player1_king & (lBoard[row1][col1] == Empty) &((lBoard[row2-1][col2+1] == player2)|(lBoard[row2-1][col2+1] == player2_king))
                            & ((row2 - 2 == row1 & col2 + 2 == col1) )) {
                        m.setRow(row2-1);
                        m.setCol(col2+1);
                        return m;
                    }
                }
                if (lBoard[row2][col2] == player1_king &(row2+2<8)&(col2+2<8)) {
                    if ((lBoard[row1][col1] == Empty)&((lBoard[row2+1][col2+1] == player2)|(lBoard[row2+1][col2+1] == player2_king))
                            & ((row2 + 2 == row1 & col2 + 2 == col1) )){
                        m.setRow(row2+1);
                        m.setCol(col2+1);
                        return m;
                    }}
                if (lBoard[row2][col2] == player1_king &(row2+2<8)&(col2-2>=0)) {
                    if ((lBoard[row1][col1] == Empty)&((lBoard[row2+1][col2-1] == player2)|(lBoard[row2+1][col2-1] == player2_king))
                            & ((row2 + 2 == row1 & col2 - 2 == col1) )) {
                        m.setRow(row2+1);
                        m.setCol(col2-1);
                        return m;
                    }
                }
                break;
            default:

                // code block
        }
        return m;
    }

    private void open_menu() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void restart(){
        int flag=0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                lBoard[i][j]=Empty;
            }
        }
        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {
             if(i%2==0 & (j == 0 | j == 2 | j == 4 | j == 6))
                 gBoard[i][j].setImageResource(R.color.black);
                if(i%2!=0 & (j == 7 | j == 5 | j == 3 | j == 1))
                    gBoard[i][j].setImageResource(R.color.black);

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
        }else{
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
    public void Doubleeating(int [][] lBoard,int turn,int row2,int col2,int lrow,int lcol,Data s, Vector<Legal_moves> moves) {
        erore2++;

        if(erore2>8)
            return;
        boolean pp=false,pm=false;
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
                        pp=true;
                    }
                }
                if (row2 + 2 < 8 & col2 - 2 > -1) {
                    if ((lBoard[row2 + 1][col2 - 1] == 3 - turn | lBoard[row2 + 1][col2 - 1] == 3 - turn + 2) & (lBoard[row2 + 2][col2 - 2] ==Empty)) {
                        Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                        Data a = new Data(-1, -1);
                        if(pp )
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
                        pm=true;
                    }
                }
                if(pp==false & pm==false)
                    return;
                if((pp==true & pm==false) |(pp==false & pm==true))
                    Doubleeating(lBoard,turn,moves.lastElement().getTorow(),moves.lastElement().getTocol(),lrow,lcol,s,moves);
                if(pp==true & pm==true){
                    int index=moves.size()-2;
                    Doubleeating(lBoard,turn,moves.lastElement().getTorow(),moves.lastElement().getTocol(),lrow,lcol,s,moves);
                    eats.removeAllElements();
                    Doubleeating(lBoard,turn,moves.elementAt(index).getTorow(),moves.elementAt(index).getTocol(),lrow,lcol,s,moves);
                }
                break;
            case human:
                if (row2 - 2 >-1 & col2 - 2 >-1) {
                    if ((lBoard[row2 - 1][col2 - 1] == 3 - turn | lBoard[row2 - 1][col2 - 1] == 3 - turn + 2) & (lBoard[row2 - 2][col2 - 2] ==Empty)) {
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
                        pp=true;
                    }
                }
                if (row2 - 2 >-1 & col2 + 2 <8) {
                    if ((lBoard[row2 - 1][col2 + 1] == 3 - turn | lBoard[row2 - 1][col2 + 1] == 3 - turn + 2) & (lBoard[row2 - 2][col2 + 2] ==Empty)) {
                        Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
                        Data a = new Data(-1, -1);
                        if(pp )
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
                        pm=true;
                    }
                }
                if(pp==false & pm==false)
                    return;
                if((pp==true & pm==false) |(pp==false & pm==true))
                    Doubleeating(lBoard,turn,moves.lastElement().getTorow(),moves.lastElement().getTocol(),lrow,lcol,s,moves);
                if(pp==true & pm==true){
                    int index=moves.size()-2;
                    Doubleeating(lBoard,turn,moves.lastElement().getTorow(),moves.lastElement().getTocol(),lrow,lcol,s,moves);
                    eats.removeAllElements();
                    Doubleeating(lBoard,turn,moves.elementAt(index).getTorow(),moves.elementAt(index).getTocol(),lrow,lcol,s,moves);
                }
                break;

        }
    }
    public void doubel_eat_king(int[][]lBoard,int row2,int col2,int row1,int col1) {
        Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
        Vector<Data> eats = new Vector<Data>();
        Vector<Legal_moves> moves2 = new Vector<Legal_moves>();
        all_eat_king(lBoard,moves2,turn);
        for (int j = 0; j < moves2.size(); j++) {
            m=moves2.get(j);
            if (m.getTorow() == row1 & m.getTocol() == col1 & m.getLrow()==row2 & m.getLcol()==col2 & m.getType()=="eat_eat_king") {
                System.out.println("נכנס");
                if (turn == player1) {
                    lBoard[m.getLrow()][m.getLcol()] = Empty;
                    gBoard[m.getLrow()][m.getLcol()].setImageResource(R.color.black);
                    lBoard[m.getTorow()][m.getTocol()] = player1_king;
                    gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player_1_king);
                    Vector<Data> q = m.getEats();
                    if (q == null) {
                        System.out.println("אין ווקטור");
                        turn = 3 - turn;
                        return;
                    }
                    for (int i = 0; i < q.size(); i++) {
                        lBoard[q.get(i).getRow()][q.get(i).getCol()] = Empty;
                        gBoard[q.get(i).getRow()][q.get(i).getCol()].setImageResource(R.color.black);
                    }
                    turn = 3 - turn;
                    return;
                } else {
                    lBoard[m.getLrow()][m.getLcol()] = Empty;
                    gBoard[m.getLrow()][m.getLcol()].setImageResource(R.color.black);
                    lBoard[m.getTorow()][m.getTocol()] = player2_king;
                    gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player_2_king);
                    Vector<Data> q = m.getEats();
                    if (q == null) {
                        System.out.println("אין ווקטור");
                        turn = 3 - turn;
                        return;
                    }
                    for (int i = 0; i < q.size(); i++) {

                        lBoard[q.get(i).getRow()][q.get(i).getCol()] = Empty;
                        gBoard[q.get(i).getRow()][q.get(i).getCol()].setImageResource(R.color.black);
                    }
                    turn = 3 - turn;
                    return;
                }
            }
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
                stop=true;
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
                num++;
            }
        }
        if (row2 + 2 < 8 & col2 - 2 > -1) {
            if ((lBoard[row2 + 1][col2 - 1] == 3 - turn | lBoard[row2 + 1][col2 - 1] == 3 - turn + 2) & (lBoard[row2 + 2][col2 - 2] ==Empty)) {
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
                    if(num==4){
                        eats.remove(eats.size()-1);
                        eats.remove(eats.size()-2);
                        eats.remove(eats.size()-3);}

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
        int index1,index2,index3;
        if (num==0)
            return;
        if (num==1)
            Doubleeating_king(lBoard, turn, moves.lastElement().getTorow(), moves.lastElement().getTocol(), lrow, lcol, s, moves);
        if (num==2) {
             index1 = moves.size() - 2;
            Doubleeating_king(lBoard, turn, moves.lastElement().getTorow(), moves.lastElement().getTocol(), lrow, lcol, s, moves);
            Doubleeating_king(lBoard, turn, moves.elementAt(index1).getTorow(), moves.elementAt(index1).getTocol(), lrow, lcol, s, moves);
        }
        if (num==3) {
             index1 = moves.size() - 2;
             index2 = moves.size() - 3;
            Doubleeating_king(lBoard, turn, moves.lastElement().getTorow(), moves.lastElement().getTocol(), lrow, lcol, s, moves);
            Doubleeating_king(lBoard, turn, moves.elementAt(index1).getTorow(), moves.elementAt(index1).getTocol(), lrow, lcol, s, moves);
            Doubleeating_king(lBoard, turn, moves.elementAt(index2).getTorow(), moves.elementAt(index2).getTocol(), lrow, lcol, s, moves);
        }
        if (num==4) {
             index1 = moves.size() - 2;
             index2 = moves.size() - 3;
             index3 = moves.size() - 4;
            Doubleeating_king(lBoard, turn, moves.lastElement().getTorow(), moves.lastElement().getTocol(), lrow, lcol, s, moves);
            Doubleeating_king(lBoard, turn, moves.elementAt(index1).getTorow(), moves.elementAt(index1).getTocol(), lrow, lcol, s, moves);
            Doubleeating_king(lBoard, turn, moves.elementAt(index2).getTorow(), moves.elementAt(index2).getTocol(), lrow, lcol, s, moves);
            Doubleeating_king(lBoard, turn, moves.elementAt(index3).getTorow(), moves.elementAt(index3).getTocol(), lrow, lcol, s, moves);
        }


    }

    public void doubel_eat(int[][]lBoard,int row2,int col2,int row1,int col1) {
        Legal_moves m ;
        Vector<Legal_moves> moves2 = new Vector<Legal_moves>();
        all_legal_eat(lBoard, moves2, turn);
        for (int j = 0; j < moves2.size(); j++) {
            m=moves2.get(j);

            if (m.getTorow() == row1 & m.getTocol() == col1 & m.getLrow()==row2 & m.getLcol()==col2 & m.getType()=="eat_eat") {
                System.out.println("נכנס");
                if(turn==player1) {
                    lBoard[m.getLrow()][m.getLcol()] = Empty;
                    gBoard[m.getLrow()][m.getLcol()].setImageResource(R.color.black);
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
                        gBoard[q.get(i).getRow()][q.get(i).getCol()].setImageResource(R.color.black);
                    }
                    turn = 3 - turn;
                    return;
                }else { lBoard[m.getLrow()][m.getLcol()] = Empty;
                    gBoard[m.getLrow()][m.getLcol()].setImageResource(R.color.black);
                    if (m.getTorow() == 7) {
                        lBoard[m.getTorow()][m.getTocol()] = player2_king;
                        gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player_2_king);
                    } else {
                        lBoard[m.getTorow()][m.getTocol()] = player2;
                        gBoard[m.getTorow()][m.getTocol()].setImageResource(R.drawable.player22);
                    }
                    Vector<Data> q = m.getEats();
                    for (int i = 0; i < q.size(); i++) {
                        lBoard[q.get(i).getRow()][q.get(i).getCol()] = Empty;
                        gBoard[q.get(i).getRow()][q.get(i).getCol()].setImageResource(R.color.black);
                    }
                    turn = 3 - turn;
                    return;
                }
            }
        }
    }

    public void undo(Stack<int[][]> undos){
        if (undos.isEmpty())
            return;
        int [][]Dboard=new int [8][8];

        copyBoard(Dboard,undos.pop());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                lBoard[i][j]=Dboard[i][j];
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(i%2==0 & (j == 0 | j == 2 | j == 4 | j == 6))
                    gBoard[i][j].setImageResource(R.color.black);
                if(i%2!=0 & (j == 7 | j == 5 | j == 3 | j == 1))
                    gBoard[i][j].setImageResource(R.color.black);
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
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

    }
    public void copyBoard(int a[][],int b[][]) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j <8; j++) {
                a[i][j]=b[i][j];
            }
        }
    }
    public int[][] copy(int b[][]) {
        int a[][]=new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j <8; j++) {
                a[i][j]=b[i][j];
            }
        }
        return a;
    }
    public void win_messege_p1(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Board.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Board.this);
        builder.setMessage("The brown player won");
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
 //       moves = moves(lBoard, turn, moves);
        if(moves.isEmpty())
            return true;
        return false;
    }
    public void draw_Message(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Board.this);
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
    public void Board_paint(int[][] board, int turn, int i, int j){
        Vector<Legal_moves> moves = new Vector<Legal_moves>();
        moves_paint(lBoard,turn,moves,i,j);
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
        moves100=null;
    }
    protected   void all_legal_move_paint(int[][] lBoard, Vector<Legal_moves> moves, int turn,int i,int j) {

        if (turn == computer) {


            if (i + 1 < 8 & j + 1 < 8) {
                if (lBoard[i][j] == player2 & lBoard[i + 1][j + 1] == Empty) {
                    Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
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
                    Legal_moves m = new Legal_moves(-1, -1, "NULL", -1, -1, "NULL", null,null);
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

    public Vector<Legal_moves> moves_paint(int[][] tempBoard, int turn, Vector<Legal_moves> moves,int i,int j) {
        moves.removeAllElements();
        all_legal_move_paint(tempBoard, moves, turn,i,j);
        all_legal_eat_paint(tempBoard, moves, turn,i,j);
        all_move_king_paint(tempBoard, moves, turn,i,j);
        all_eat_king_paint(tempBoard, moves, turn,i,j);
        moves100=moves;
        return moves;
    }
}