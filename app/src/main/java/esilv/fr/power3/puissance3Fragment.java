package esilv.fr.power3;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class puissance3Fragment extends Fragment {

    // temp attribs
    public int numberOfPlayer;


    private TableLayout gameGrid;
    private ImageView currentPlayerIMG;
    private Game game;
    private static final int GRID_WIDTH = 7; // nb of column
    private static final int GRID_HEIGTH = 6; // nb of lines
    private SparseArray<CustomImageView> gridMap;
    private ImageView[] playersImageViewTable;
    private List<List<Integer>> columnList;
    private List<List<Integer>> linesList;
    private List<List<Integer>> globalList; // tha holds all lines/ colum/ row to check for win
    private int currentPlayer = 2;  // goes from 0 to 3


/*
 *  DISPLAY AREA
 */


    public puissance3Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_puissance3, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gameGrid = (TableLayout) getActivity().findViewById(R.id.gameGrid);
        currentPlayerIMG = (ImageView)getActivity().findViewById(R.id.currentPlayerImg);
        gridMap = new SparseArray<>();
        columnList = new ArrayList<>();
        linesList = new ArrayList<>();
        globalList = new ArrayList<>();
        playersImageViewTable = new ImageView[4];
        game = new Game();
        createGameGrid();
        bluidColumnLists();

//
        Log.d(">>20>>>", ((CustomImageView) gridMap.get(20)).getPosition() + "");

    }

    public void createGameGrid() {

        int counter =0;
        for(int i = 0; i < GRID_HEIGTH; i++){
            // to keep indexs of lines
            List<Integer> lines = new ArrayList<>();

            TableRow row = new TableRow(getActivity());
            for (int j = 0; j < GRID_WIDTH; j++){
                final CustomImageView image = new CustomImageView(getActivity(), counter);
                //implementing a listener
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        game.itemClicked(image.getPosition());
                    }
                });
                lines.add(counter);
                row.addView(image);
                // saving it in the map
                gridMap.put(counter, image);
                counter++;
            }
            globalList.add(lines);
            gameGrid.addView(row);
        }

        TableRow row = new TableRow(getActivity());
        row.setMinimumHeight(80);
        gameGrid.addView(row);



        row = new TableRow(getActivity());
        row.setGravity(Gravity.CENTER_HORIZONTAL);
        ImageView img = new ImageView(getActivity());
        img.setImageResource(R.drawable.power50bl);
        row.addView(img);
        playersImageViewTable[0] = img;

        img = new ImageView(getActivity());
        img.setImageResource(R.drawable.power50empty);
        row.addView(img);
        playersImageViewTable[1] = img;

        img = new ImageView(getActivity());
        img.setImageResource(R.drawable.power50empty);
        row.addView(img);
        playersImageViewTable[2] = img;

        img = new ImageView(getActivity());
        img.setImageResource(R.drawable.power50empty);
        row.addView(img);
        playersImageViewTable[3] = img;
        gameGrid.addView(row);


    }

    public void bluidColumnLists(){

        columnList.add(Arrays.asList(0, 7, 14, 21, 28, 35));
        columnList.add(Arrays.asList(1, 8, 15, 22, 29, 36));
        columnList.add(Arrays.asList(2, 9, 16, 23, 30, 37));
        columnList.add(Arrays.asList(3, 10, 17, 24, 31, 38));
        columnList.add(Arrays.asList(4, 11, 18, 25, 32, 39));
        columnList.add(Arrays.asList(5, 12, 19, 26, 33, 40));
        columnList.add(Arrays.asList(6, 13, 20, 27, 34, 41));

        for(List<Integer> list : columnList) {
            Collections.reverse(list);
            globalList.add(list);
        }


        // adding diagonales '/'
        globalList.add(Arrays.asList(14, 8, 2));
        globalList.add(Arrays.asList(21, 15, 9, 3));
        globalList.add(Arrays.asList(28, 22, 16, 10, 4));
        globalList.add(Arrays.asList(35, 29, 23, 17, 11, 5));
        globalList.add(Arrays.asList(36, 30, 24, 18, 12, 6));
        globalList.add(Arrays.asList(37, 31, 25, 19, 13));
        globalList.add(Arrays.asList(38, 32, 26, 20));
        globalList.add(Arrays.asList(39, 33, 27));

        // adding diagonales '\'
        globalList.add(Arrays.asList(4, 12, 20));
        globalList.add(Arrays.asList(3, 11, 19, 27));
        globalList.add(Arrays.asList(2, 10, 18, 26, 34));
        globalList.add(Arrays.asList(1, 9, 17, 25, 33, 41));
        globalList.add(Arrays.asList(0, 8, 16, 24, 32, 40));
        globalList.add(Arrays.asList(7, 15, 23, 31, 39));
        globalList.add(Arrays.asList(14, 22, 30, 38));
        globalList.add(Arrays.asList(21, 29, 37));


//        for(List<Integer> list : globalList) {
//            String display = "> ";
//            for(int pos : list){
//                display += pos + " ";
//            }
//            Log.d("global ", display);
//        }

    }


    protected class CustomImageView extends ImageView{

        private int position;
        private int playerID;

        public CustomImageView(Context context) {
            super(context);
            this.playerID = 0;
            changeColor(playerID);
        }

        public CustomImageView(Context context, int position) {
            super(context);
            this.playerID = 0;
            this.position = position;
            changeColor(playerID);
        }


        public int getPosition() {
            return position;
        }


        public int getPlayerID() {
            return playerID;
        }

        public void setPlayerID(int playerID) {
            this.playerID = playerID;
            changeColor(playerID);
        }

        public void changeColor(int playerID) {
            switch(playerID){
                case 1:
                    setImageResource(R.drawable.power50bl);
                    break;
                case 2:
                    setImageResource(R.drawable.power50or);
                    break;
                case 3:
                    setImageResource(R.drawable.power50gy);
                    break;
                case 4:
                    setImageResource(R.drawable.power50re);
                    break;
                default:
                    setImageResource(R.drawable.power50empty);
                    break;
            }
        }
    }



/*
 *  GAME AREA
 */
    protected class Game {

        private int numberOfPlayers = 3;
        private int currentPlayer = 1;

        public Game() {
        }

        public void itemClicked(int position){
            boolean success = setCoinToColumnLower(position);
            if(success) {
                changePlayer();
                boolean win = checkForWin();
                if(!win) {
                  //  Toast.makeText(getActivity(), "player n#" + currentPlayer , Toast.LENGTH_SHORT).show();
                }
            }else{

                Toast.makeText(getActivity(), "select another cell" , Toast.LENGTH_SHORT).show();
            }
        }


        public int changePlayer() {

            switch (currentPlayer) {
                case 1:
                    currentPlayer = 2;
                    playersImageViewTable[0].setImageResource(R.drawable.power50empty);
                    playersImageViewTable[1].setImageResource(R.drawable.power50or);
                    break;
                case 2:
                    if(numberOfPlayers == 2) {
                        currentPlayer = 1;

                        playersImageViewTable[1].setImageResource(R.drawable.power50empty);
                        playersImageViewTable[0].setImageResource(R.drawable.power50bl);
                    }
                    else {
                        currentPlayer = 3;
                        playersImageViewTable[1].setImageResource(R.drawable.power50empty);
                        playersImageViewTable[2].setImageResource(R.drawable.power50gy);
                    }
                    break;
                case 3:
                    if(numberOfPlayers == 3) {
                        currentPlayer = 1;
                        playersImageViewTable[2].setImageResource(R.drawable.power50empty);
                        playersImageViewTable[0].setImageResource(R.drawable.power50bl);
                    }
                    else {
                        currentPlayer = 4;
                        playersImageViewTable[2].setImageResource(R.drawable.power50empty);
                        playersImageViewTable[3].setImageResource(R.drawable.power50re);
                    }
                    break;
                case 4:
                    currentPlayer = 1;
                    playersImageViewTable[3].setImageResource(R.drawable.power50empty);
                    playersImageViewTable[0].setImageResource(R.drawable.power50bl);
                    break;
                default:
                    currentPlayer = 0;
                    Log.d("errorrr", "currentplayer 0");
                    break;
            }
            return currentPlayer;
        }
        public int changePlayer(int newPlayer){
            currentPlayer = newPlayer;
            return currentPlayer;
        }

        // return the position to put the coin
        public boolean setCoinToColumnLower(int position){

            for(List<Integer> currList : columnList){ // for each column
                if(currList.contains(position)){
                    for(int pos : currList) {
                        if(gridMap.get(pos).getPlayerID() == 0) { // if empty cell
                            gridMap.get(pos).setPlayerID(currentPlayer);
                            Log.d("game //  player :", currentPlayer + " at pos: " + gridMap.get(pos).getPosition() + " new state cell : " + gridMap.get(pos).getPlayerID());
                            return true;
                        }
                    }
                }
            }

            return false;
        }


        public boolean checkForWin() {

            // pour chaque ligne
            for(List<Integer> list : globalList) {


                int[] playersCounter = {  0 , 0, 0, 0 };  // from index 0 to 3
                for(int pos : list){
                    int cellPlayerID = gridMap.get(pos).getPlayerID();

                    switch(cellPlayerID){
                        case 0:
                            break;
                        case 1:
                            playersCounter[0]++;
                            playersCounter[1] = 0;
                            playersCounter[2] = 0;
                            playersCounter[3] = 0;
                            break;
                        case 2:
                            playersCounter[0] = 0;
                            playersCounter[1]++;
                            playersCounter[2] = 0;
                            playersCounter[3] = 0;
                            break;
                        case 3:
                            playersCounter[0] = 0;
                            playersCounter[1] = 0;
                            playersCounter[2]++;
                            playersCounter[3] = 0;
                            break;
                        case 4:
                            playersCounter[0] = 0;
                            playersCounter[1] = 0;
                            playersCounter[2] = 0;
                            playersCounter[3]++;
                            break;
                    }
                }

                for(int i=0; i < 4; i++) {
                    if (playersCounter[i] >= 3) {
                        Log.d("GAME OVER", "WIINNNNIIINNNG :)");
                        Toast.makeText(getActivity(), "FIN : Le joueur :"  + i+1 + " remporte la partie ! " , Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
            }




            return false;
        }

    }




}
