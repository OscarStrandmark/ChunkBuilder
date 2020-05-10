package Logic;

import UI.Window;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {

    private Window window;

    private static final boolean ENABLE_DEBUG = true;

    //IDs
    private static final int AIR            = 0;
    private static final int ROCK           = 1;
    private static final int SPIKES         = 2;
    private static final int ARROWTRAPLEFT  = 3;
    private static final int ARROWTRAPRIGHT = 4;
    private static final int ENTRANCE       = 5;
    private static final int EXIT           = 6;

    public void setWindow(Window w){
        window = w;
    }

    public void saveChunk(JLabel[][] matrix, boolean up, boolean down, boolean left, boolean right, boolean entrance, boolean exit) throws IOException {

        //Translate JLabel matrix to tileID matrix
        int[][] tileMatrix = new int[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (matrix[row][col].getIcon().toString().contains("Air.png")){
                    tileMatrix[row][col] = AIR;
                }
                if (matrix[row][col].getIcon().toString().contains("Rock.png")){
                    tileMatrix[row][col] = ROCK;
                }
                if (matrix[row][col].getIcon().toString().contains("Spikes.png")){
                    tileMatrix[row][col] = SPIKES;
                }
                if (matrix[row][col].getIcon().toString().contains("ArrowtrapLeft.png")){
                    tileMatrix[row][col] = ARROWTRAPLEFT;
                }
                if (matrix[row][col].getIcon().toString().contains("ArrowtrapRight.png")){
                    tileMatrix[row][col] = ARROWTRAPRIGHT;
                }
                if (matrix[row][col].getIcon().toString().contains("Entrance.png")){
                    tileMatrix[row][col] = ENTRANCE;
                }
                if (matrix[row][col].getIcon().toString().contains("Exit.png")){
                    tileMatrix[row][col] = EXIT;
                }
            }
        }

        if(ENABLE_DEBUG){
            //Debug print
            System.out.println();
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    System.out.print(tileMatrix[row][col] + " ");
                }
                System.out.println();
            }
        }

        String fileName = "";

        //Add bits to filename depending on the properties
        if(up){ fileName += "1"; } else { fileName += "0"; }
        if(down){ fileName += "1"; } else { fileName += "0"; }
        if(left){ fileName += "1"; } else { fileName += "0"; }
        if(right){ fileName += "1"; } else { fileName += "0"; }
        if(entrance){ fileName += "1"; } else { fileName += "0"; }
        if(exit){ fileName += "1"; } else { fileName += "0"; }

        fileName += "_" + JOptionPane.showInputDialog("Enter name/description for created chunk");
        System.out.println(fileName);

        String dataString = "";

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                dataString += tileMatrix[row][col];
            }
        }


        File file = new File("C:\\dev\\GameChunks\\" + fileName + ".txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(dataString);
        bw.flush();
        bw.close();
    }
}
