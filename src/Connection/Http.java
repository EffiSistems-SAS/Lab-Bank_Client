package Connection;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Http {

    private HttpClient htppClient;
    private HttpGet get;
    private HttpPost post;
    private HttpResponse response;
    private String resource, base_url;
    
    private static Http http;

    private Http() {
        htppClient = HttpClients.createDefault();
        get = null;
        post = null;
        base_url = "http://localhost:4000";
    }
    
    public static Http getInstance(){
        if(http==null){
            http = new Http();
        }
        return http;
    } 

    public String GET(String ruta) {

        get = new HttpGet(base_url + ruta);
        try {
            response = this.htppClient.execute(get);
            resource = EntityUtils.toString(this.response.getEntity());

        } catch (Exception e) {
        }
        return resource;

    }

    public void POST(String ruta, String data) {
        try {
            URI final_url = new URIBuilder(base_url + ruta).build();
            post = new HttpPost(final_url);
            post.setHeader("content-type", "application/json");
            StringEntity final_data = new StringEntity(data);
            post.setEntity(final_data);
            response = htppClient.execute(post);
            resource = EntityUtils.toString(response.getEntity());
        } catch (URISyntaxException ex) {
            System.out.println(ex.getMessage());

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

}
