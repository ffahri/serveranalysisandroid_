package com.webischia.serveranalysis.Service;

// Dosyaya buradaki metotlardan yazdırıyoruz.

import com.webischia.serveranalysis.Models.Graphic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveObjectsImpl implements SaveObjects {
    @Override
    public void saveGraphics(Graphic graphObj) {
            //Java serialization ile yaz

        try {
            //grafik ismine göre kayıt yapılıyor. Aynı isimde iki grafik olamaz.
            FileOutputStream outFile = new FileOutputStream("/server_analysis/"+graphObj.getName()+".ser");
            ObjectOutputStream out = new ObjectOutputStream(outFile);
            out.writeObject(graphObj); //objeyi yazdırdık
            out.close();
            outFile.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public Graphic loadGraphics(String name) {
            //Java deserialization ile oku graphic nesnesine aç ve döndür
        Graphic temp;
        try {
            FileInputStream fileIn = new FileInputStream("/server_analysis/"+name+".ser"); //bu isimi nereden okuyacağız
            ObjectInputStream in = new ObjectInputStream(fileIn);                               // ayrı bir text gibi birşeye kayıt ?
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
        return null;
    }
}
