package dc.bot.https;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Call {
    private static final Logger LOGGER = LoggerFactory.getLogger(Call.class);

    public static void main(String args[]) throws InterruptedException {
        CallProperties cp = new CallProperties();
        cp.setHttps(true);
        cp.setMethod(CallProperties.Method.GET);
        cp.setUri("https://pokeapi.co/api/v2/pokemon/ditto");
        cp.setTimeOut(30);
        try {
            Call.request(cp);
        } catch (IOException  e) {
            LOGGER.error("Error en el formato de la peticion: "+e.getMessage()+"\nFull error:"+e);
        } catch (URISyntaxException e) {
            LOGGER.error("Error en el formato de la uri: "+e.getMessage()+"\nFull error:"+e);
        } catch (ExecutionException e) {
            LOGGER.error("Error en la ejecucion generica de la peticion web: "+e.getMessage()+"\nFull error:"+e);
        }
        LOGGER.debug("comenzando espera");
        //Thread.sleep(30000);
    }

    public static String request(CallProperties cp) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        int error;
        URI uri = new URI(cp.getUri());
        HttpResponse response;
        String responseStr;
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(cp.getTimeOut()))
                .build();
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(cp.getTimeOut()));
        setHeaders(request, cp.getProps());
        switch (cp.getMethod()){
            case GET:
                response=doGet(cp, client, request).get();
                break;
            case POST:
                response=doPost(cp, client, request).get();
                break;
            default:
                response=null;
        }

        responseStr=response.body().toString();
        LOGGER.info("Respuesta: \n"+ responseStr);
        error=response.statusCode();
        return responseStr;
    }

    private static CompletableFuture<HttpResponse> doGet(CallProperties cp, HttpClient client, HttpRequest.Builder requestB)  {
        HttpRequest request;
        request=requestB.build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(stringHttpResponse -> stringHttpResponse);
    }
    private static CompletableFuture<HttpResponse> doPost(CallProperties cp, HttpClient client, HttpRequest.Builder requestB) {
        HttpRequest request;
        request=requestB
                .POST(HttpRequest.BodyPublishers.ofString(cp.getBody()))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(stringHttpResponse -> stringHttpResponse);
    }
    private static void setHeaders(HttpRequest.Builder httpRequest, Map<String, String> headers){

        for (Map.Entry<String, String> header : headers.entrySet()) {
            httpRequest.setHeader(header.getValue(),header.getKey());
        }
    }
}
