/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Component;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Roni
 */
@Component
public class AppUserManagerDB implements AppUserManagerDao {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public AppUserManagerDB() {
    }

    @Override
    public void add(AppUser user) {
        HttpPost post = new HttpPost("http://localhost:8080/users");

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("username", user.getUsername()));
        urlParameters.add(new BasicNameValuePair("password", user.getPassword()));
        urlParameters.add(new BasicNameValuePair("authorization", user.getAuthorization()));

        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void remove(AppUser user) {
        /*
        if (userRepo.existsByUsername(user.getUsername())) {
            userRepo.deleteByUsername(user.getUsername());
        }
         */
    }

    @Override
    public ArrayList<AppUser> getUsers() {
        HttpGet request = new HttpGet("http://localhost:8080/users");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }
        } catch (IOException ex) {
            Logger.getLogger(AppUserManagerDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    @Override
    public AppUser getUser(String username) {
        return new AppUser("Make", "Make", "Admin");
    }

    @Override
    public boolean checkLogin(String username, String password) {
        return true;
    }

}
