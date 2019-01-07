package com.android.io.github.vaidehighime.www.signavv001;
import android.content.Context;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class CoordinateFactory {

    private final ArrayList<Coordinates> allCoordinates=new ArrayList<>();

    public void pushCoordinates(Coordinates e) {
        this.allCoordinates.add(e);
    }

    public File saveToFile(Context context){
        StringBuilder gcode = new StringBuilder();
        for (int i = 0; i < this.allCoordinates.size(); i++) {
            gcode.append("X").append(this.allCoordinates.get(i).getX()).append(" ").append("Y").append(this.allCoordinates.get(i).getY()).append("\n");
        }
        File file;
        file = new File(context.getCacheDir(),"generated_gcode_file.gcode");
        FileWriter writer;
        try {
                writer = new FileWriter(file);
                writer.write(gcode.toString());
                writer.close();
            } catch (IOException e) {
            e.printStackTrace();
        }

        return (file);
    }
}
