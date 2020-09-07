import java.net.HttpURLConnection;
import java.net.URL;

public class FreeSMSAPI {

    private final String user;
    private final String pwd;

    private final String[][] http_code =
            {{"200", "SMS envoyé !"},
            {"400", "Un des paramètres est manquant."},
            {"402", "Trop de SMS !"},
            {"403", "User, PWD invalide ou le service n'est pas activé."},
            {"500", "Erreur serveur Free !"}};

    public FreeSMSAPI(String user, String pwd) {
        this.user = user;
        this.pwd = pwd;
    }

    public String sendMessage(String msg) throws Exception{
        //Percent Encoding
        msg = msg.replaceAll(" ", "%20");

        String url = "https://smsapi.free-mobile.fr/sendmsg?user=" + this.user + "&pass=" + this.pwd + "&msg=" + msg;


        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

        httpClient.setRequestMethod("GET");
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");

        String responseCode = String.valueOf(httpClient.getResponseCode());

        for (String[] strings : http_code) {
            if (strings[0].equals(responseCode)) {
                return strings[1];
            }
        }

        return "Code non enregistré !";
    }

}
