package com.example.android.newsapp;

/**
 * Created by bjoern on 14.02.17.
 * Version:
 *
 * @author <a href="mailto:mail@bjoern.cologne">Bjoern Gam</a>
 * @link <a href="http://bjoern.cologne">Webpage </a>
 * <p>
 * Description: The class for our news content
 */

final public class News
{
    /** Tag for log messages */
    private static final String LOG_TAG = News.class.getName();

    //private variables of the class

    private String mwebPublicationDate;     //contains the information when the article was published
    private String mwebTitle;               //contains the title of the article
    private String mwebUrl;                 //contains the HTTP URL of the complete article
    private String msectionName;            //contrain the section where article was published

    /**
     * Default constructor
     * @param webPublicationDate            // when was the article published
     * @param webTitle                      // what is the title of the article
     * @param webUrl                        // the URL of the complete article
     * @param sectionName                   // the name of the section
     */

    public News (String webTitle, String webPublicationDate, String webUrl, String sectionName ){
        mwebPublicationDate = webPublicationDate;
        mwebTitle = webTitle;
        mwebUrl = webUrl;
        msectionName = sectionName;
    }

    // our basic get methodes

    public String getMwebPublicationDate() { return "Published Date:  "+ mwebPublicationDate+ " ";}
    public String getMwebTitle() {return mwebTitle;}
    public String getMwebUrl() {return mwebUrl;}
    public String getMsectionName() {return "Section:  "+ msectionName;}

    // useful for debugging overloading ot toString()

    @Override
    public String toString() {
        return mwebTitle + " " + mwebPublicationDate + " " + msectionName +" " + mwebUrl;
    }
}
