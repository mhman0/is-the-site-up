package io.javasb.isthesiteup.controllers;
//when creating a new class I was able to type the package name into the create class file 
//which automatically create a package

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * Create a string Url, then make a http connection / get request and see if I get a successful response
 * Notes:
 * 1. io
 */

@RestController //Marked as a Rest controller via suggested text
public class UrlCheckController {
    //These constants will be the response messages that I see
    private final String SITE_IS_UP = "Site is up!";
    private final String SITE_IS_DOWN = "Site is down!";
    private final String INCORRECT_URL = "Url is incorrect!";



    // Need a get method
    @GetMapping("/check")//checking if the Url is up or not
    public String getUrlStatusMessage(@RequestParam String url){//need to ping and identify if the site is up or not
        String returnMessage = "";//returns and empty string so that I can assign one of the contants to it
        // Surrounded with try catch because I don't want to throw and error if there's a malformedUrlException
        // Instead, it would be better to show a message noting that url is incorrect
        try {
            URL urlobj = new URL(url);//creating a url object /constructor that takes in a string
            HttpURLConnection conn = (HttpURLConnection) urlobj.openConnection();// opens a new connection 
            conn.setRequestMethod("GET");
            conn.connect();
            /*
             * Checking the response code after connection exists
             * created an interger that allows me to see if the response code is
             * between 200 and 299.
             * 
             * If not equal to 2 or 3 then it's none of the 200 or 300 response codes then likely the site is down
             */
            int responseCodeCategory = conn.getResponseCode() / 100;
            if (responseCodeCategory != 2 || responseCodeCategory != 3) {
                returnMessage = SITE_IS_DOWN;
            } else {
                returnMessage = SITE_IS_UP;
            }

        } catch (MalformedURLException e) {
            returnMessage = INCORRECT_URL;
        } catch (IOException e) {// ioexception means that the site is down, so I will return a site down message
            returnMessage = SITE_IS_DOWN;
        }

        return returnMessage;//you check the return message by 
    }

}
