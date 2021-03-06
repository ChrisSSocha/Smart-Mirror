package technology.socha.chris.smartmirror.calendar.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import technology.socha.chris.smartmirror.calendar.domain.GoogleCalendar;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class GoogleCalendarService implements CalendarService {

    private final String applicationName;
    private final GoogleClientSecrets clientSecrets;
    private final NetHttpTransport httpTransport;
    private final FileDataStoreFactory dataStoreFactory;
    private final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    private static final List<String> CALENDAR_SCOPE = Arrays.asList(CalendarScopes.CALENDAR_READONLY);

    public GoogleCalendarService(String applicationName, File credentialsDirectory, GoogleClientSecrets clientSecrets) {
        this.applicationName = applicationName;
        this.clientSecrets = clientSecrets;

        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(credentialsDirectory);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Credential authorize() throws IOException {
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, jsonFactory, clientSecrets, CALENDAR_SCOPE)
                        .setDataStoreFactory(dataStoreFactory)
                        .setAccessType("offline")
                        .build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

        return credential;
    }

    public GoogleCalendar getCalendar() throws IOException {
        Credential credential = authorize();

        com.google.api.services.calendar.Calendar calendar = new com.google.api.services.calendar.Calendar.Builder(
                httpTransport, jsonFactory, credential)
                .setApplicationName(applicationName)
                .build();

        return new GoogleCalendar(calendar);
    }

}
