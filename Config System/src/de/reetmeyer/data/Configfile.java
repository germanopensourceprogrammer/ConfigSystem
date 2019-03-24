package de.reetmeyer.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Configfile {

    public String version;

    public HashMap<String, String> values = new HashMap<>();

    public String path;

    public Configfile(String file) {
        path = file;
        try {
            String[] lines = Files.readAllLines(Paths.get(file)).toArray(new String[0]);
            ArrayList<String> list = new ArrayList<>();
            for (String line : lines) {
                if (line.contains(":")) list.add(line);
            }
            lines = list.toArray(new String[0]);

            version = lines[0].replace("Version:", "");
            for (int i = 0; i < lines.length; i++) {
                System.out.println(lines[i]);
            }
            for (String line :
                    lines) {
                if (line.contains(":")) values.put(line.split(":")[0], line.split(":")[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void save () {
        String[] lines = new String[values.size()];
        String[] keys = this.values.keySet().toArray(new String[0]);
        String[] values = this.values.values().toArray(new String[0]);
        for (int i = 0; i < keys.length; i++) {
            lines[i] = keys[i] + ":" + values[i];
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (String line :
                    lines) {
                writer.append(line+ "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Configfile cf = new Configfile("E:\\config.gospcf");
        System.out.println(cf.values.get("Version"));
        cf.values.replace("Serverport", "334334");
        cf.save();
    }

}
