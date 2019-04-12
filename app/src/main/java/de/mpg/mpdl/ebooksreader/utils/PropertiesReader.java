package de.mpg.mpdl.ebooksreader.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private Context context;
    private Properties properties;

    public PropertiesReader(Context context) {
        this.context = context;
        properties = new Properties();
    }

    public Properties getEBooksProperties() {

    try {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("Ebooks.properties");

        properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
