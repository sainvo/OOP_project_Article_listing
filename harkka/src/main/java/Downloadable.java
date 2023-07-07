//package com.sainvo.oop.harkka.java;

//import java.net.URL;

/**interface to set apart sources that are available online aka downloadable 
 * versus sources that are not (physical books) 
 * Why? If the resource happened to be something like a blog etc. with content that is not final/static but
 * is updated. ( Don't know if this is necessary for this project but anyway) 
 * */

public interface Downloadable {
    boolean hasValidURL(String url);
}