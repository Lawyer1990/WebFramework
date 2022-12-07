package driver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static configs.Config.PATH_TO_DOWNLOADED;
import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

@Log4j2
public class DriverInitialize {
    protected static final String DRIVER_CREATED = " --- {} DRIVER IS CREATED --- ";
    protected static final String DRIVER_INITIALIZED = " --- {} IS INITIALIZED --- ";
    private static final String IO_EXCEPTION = "IO Exception: ";
    private static final String JSON_EXCEPTION = "JsonProcessingException: ";
    private static final String SESSION_PATH = "%s/session/%s/chromium/send_command";

    public enum DriverType {
        DEFAULT
    }

    public enum Browser {
        CHROME("Chrome"),
        FIREFOX("Firefox"),
        REMOTE("Remote");

        private String browserType;

        Browser(String browserType) {
            this.browserType = browserType;
        }

        public String getValue() {
            return browserType;
        }
    }

    private static Map<String, Object> setParam() {
        Map<String, Object> commandParams = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        commandParams.put("cmd", "Page.setDownloadBehavior");
        params.put("behavior", "allow");
        params.put("downloadPath", System.getProperty("user.dir") + File.separator + PATH_TO_DOWNLOADED);
        commandParams.put("params", params);
        return commandParams;
    }

    protected void allowHeadlessDownload(ChromeDriverService driverService, SessionId sessionId) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        String command = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            command = objectMapper.writeValueAsString(setParam());
        } catch (JsonProcessingException e) {
            log.error(JSON_EXCEPTION, e);
        }
        String u = String.format(SESSION_PATH, driverService.getUrl().toString(), sessionId);
        HttpPost request = new HttpPost(u);
        request.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        try {
            request.setEntity(new StringEntity(command));
            httpClient.execute(request);
        } catch (IOException e) {
            log.error(IO_EXCEPTION, e);
        }
    }

    @Data
    public static class DriverSettings {
        private EventFiringWebDriver eventFiringWebDriver;
        private DriverType type;
    }
}
