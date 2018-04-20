package com.webischia.serveranalysis.Service;

// Dosyaya buradaki metotlardan yazdırıyoruz.

import android.content.Context;
import android.util.Log;

import com.webischia.serveranalysis.Controls.SaveControl;
import com.webischia.serveranalysis.Models.Graphic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SaveServiceImpl implements SaveService {

    SaveControl saveControl;
    static Context context;

    public SaveServiceImpl(SaveControl saveControl, Context context) {
        this.saveControl = saveControl;
        this.context = context;
    }

    @Override
    public void saveGraphics(Graphic graphObj, String username, String token, String serverIP) {
        //Java serialization ile yaz
        try {
            //grafik ismine göre kayıt yapılıyor. Aynı isimde iki grafik olamaz.
            File file = new File(context.getFilesDir(), graphObj.getName() + ".ser");
            if (file.exists()) {
                saveControl.saveError(context);
            } else {
                file.createNewFile();
                FileOutputStream outFile = context.openFileOutput(graphObj.getName() + ".ser", Context.MODE_PRIVATE);
                ObjectOutputStream out = new ObjectOutputStream(outFile);
                out.writeObject(graphObj); //objeyi yazdırdık
                out.close();
                outFile.close();
                Log.d("SAVE_GRAPH", "SUCCESS");
                saveNames(graphObj.getName(), username);
                saveControl.successSave(graphObj.getName(), context, username, token, serverIP);
            }
        } catch (IOException i) {
            i.printStackTrace();
            Log.d("SAVE_GRAPH", "NOPE");

        }
    }

    @Override
    public Graphic loadGraphics(String name) {
        //Java deserialization ile oku graphic nesnesine aç ve döndür
        Graphic temp;

        try {
            FileInputStream fileIn = context.openFileInput(name + ".ser"); //bu isimi nereden okuyacağız
            ObjectInputStream in = new ObjectInputStream(fileIn);             // ayrı bir text gibi birşeye kayıt ?
            temp = (Graphic) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Graphic not found");
            c.printStackTrace();
            return null;
        }
        return temp;
    }

    @Override
    public void saveNames(String name, String username) {

        File file = new File(context.getFilesDir(), username + ".dat");
        int id;
        if (!file.exists()) {
            id = Context.MODE_PRIVATE;
            try {
                file.createNewFile();
            } catch (Exception a) {
                a.printStackTrace();
            }
        } else
            id = Context.MODE_APPEND;

        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(username + ".dat", id);
            outputStream.write("\n".getBytes());
            outputStream.write(name.getBytes());//newline added
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void loadNames(String username, String token, String serverIP) {

        ArrayList graphicList = null;
        File file = new File(context.getFilesDir(), username + ".dat");
        Log.d("SAVE_S_IMP ", username); //

        FileInputStream fis;
        if (file.exists()) {
            try {
                fis = new FileInputStream(file);
                graphicList = new ArrayList<Graphic>();
                BufferedReader in
                        = new BufferedReader(new InputStreamReader(fis));
                //= new BufferedReader(new FileReader(path));

                while (in.ready()) {
                    String line = in.readLine();
                    Log.d("Line = ", line); //
                    if (line.length() > 0)
                        graphicList.add(loadGraphics(line));

                }
                fis.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("Size = ", "" + graphicList.size());
            saveControl.loadGraphs(graphicList, context, username, token, serverIP);
            //return graphicList;
        } else
            Log.d("YOK", "YOOOK");

    }

    @Override
    public void removeGraphic(Graphic graphObj, String username, String token, String serverIP) {


        try {
            File file = new File(context.getFilesDir(), graphObj.getName() + ".ser");
            if(file.exists())
                if(file.delete()) {
                    File file2 = new File(context.getFilesDir(), username + ".dat");
                    File tempFile = new File(context.getFilesDir(),"temp.dat");
                    tempFile.createNewFile();
                    int id;

                     //////////////////
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
                        FileOutputStream outputStream = context.openFileOutput("temp.dat", Context.MODE_PRIVATE);
                        String currentLine;

                        while((currentLine = reader.readLine()) != null) {
                            // trim newline when comparing with lineToRemove
                            if(currentLine.equals(graphObj.getName())) continue;
                            outputStream.write("\n".getBytes());
                            outputStream.write(currentLine.getBytes());
                        }
                        outputStream.close();
                        reader.close();
                        tempFile.renameTo(file2);

                        /////////
                        saveControl.successRemove(context, username, token, serverIP);

                }
        } catch (Exception i) {
            i.printStackTrace();

        }

    }

    @Override
    public void updateGraphic(Graphic graphObj, String username, String token, String serverIP) {
        try {
            //grafik ismine göre kayıt yapılıyor. Aynı isimde iki grafik olamaz.
            File file2 = new File(context.getFilesDir(), graphObj.getName() + ".ser");
            file2.delete();
            try {
                //grafik ismine göre kayıt yapılıyor. Aynı isimde iki grafik olamaz.
                File file = new File(context.getFilesDir(), graphObj.getName() + ".ser");
                if (file.exists()) {
                    saveControl.saveError(context);
                } else {
                    file.createNewFile();
                    FileOutputStream outFile = context.openFileOutput(graphObj.getName() + ".ser", Context.MODE_PRIVATE);
                    ObjectOutputStream out = new ObjectOutputStream(outFile);
                    out.writeObject(graphObj); //objeyi yazdırdık
                    out.close();
                    outFile.close();
                    Log.d("SAVE_GRAPH", "SUCCESS");
                    //saveNames(graphObj.getName(), username);
                    saveControl.successSave(graphObj.getName(), context, username, token, serverIP);
                }
            } catch (IOException i) {
                i.printStackTrace();
                Log.d("SAVE_GRAPH", "NOPE");

            }

        } catch (Exception i) {
            i.printStackTrace();
            Log.d("UPDATE.GRAPH", "NOPE");

        }
    }
}
