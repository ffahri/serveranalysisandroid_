package com.webischia.serveranalysis.Service;

// Dosyaya buradaki metotlardan yazdırıyoruz.

import com.webischia.serveranalysis.Models.Graphic;

public class SaveObjectsImpl implements SaveObjects {
    @Override
    public void saveGraphics() {
            //Java serialization ile yaz
    }

    @Override
    public Graphic loadGraphics(String name) {
            //Java deserialization ile oku graphic nesnesine aç ve döndür

        return null;
    }
}
