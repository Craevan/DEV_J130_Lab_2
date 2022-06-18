package ru.avalon.utils;

import java.io.*;
import java.util.Collection;
import java.util.Properties;

public class CredentialsInitializer {

    private final Properties properties = new Properties();
    private final File file = new File("src/ru/avalon/resources/Credentials.properties");

    public CredentialsInitializer() {
    }

    public void run() throws IOException {
        if (checkCredentialsFile()) {
            ConsoleHelper.writeMessage("Файл настроек обнаружен.");

            while (true) {
                ConsoleHelper.writeMessage("Подключиться с настройками из файла? [Y]/[N]:");
                String userAnswer = ConsoleHelper.readLine();
                if (userAnswer.equalsIgnoreCase("Y")) {
                    break;
                } else if (userAnswer.equalsIgnoreCase("N")) {
                    createCredentials();
                    break;
                }
            }
        } else {
            ConsoleHelper.writeMessage("Обнаружены проблемы с файлом настроек");
            ConsoleHelper.writeMessage("Необходимо ввести данные для подключения к БД заново");
            createCredentials();
        }
    }

    private void createCredentials() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             FileOutputStream fos = new FileOutputStream(file)) {
            ConsoleHelper.writeMessage("Введите адрес БД:");
            String inputValue = reader.readLine();
            String urlPrefix = "db.host";
            properties.setProperty(urlPrefix, inputValue);
            ConsoleHelper.writeMessage("Введите логин:");
            inputValue = reader.readLine();
            String loginPrefix = "db.login";
            properties.setProperty(loginPrefix, inputValue);
            ConsoleHelper.writeMessage("Введите пароль:");
            inputValue = reader.readLine();
            String passPrefix = "db.password";
            properties.setProperty(passPrefix, inputValue);
            properties.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkCredentialsFile() throws IOException {
        if (!file.exists() || !file.canRead()) {
            return false;
        }
        try (FileReader fReader = new FileReader(file)) {
            properties.load(fReader);
            if (properties.isEmpty()) {
                return false;
            }
            if (properties.size() != 3) {
                return false;
            }
            if (!properties.containsKey("db.password") ||
                    !properties.containsKey("db.login") ||
                    !properties.containsKey("db.host")) {
                return false;
            }
            Collection<Object> values = properties.values();
            if (values.stream().anyMatch(b -> String.valueOf(b).isEmpty())) {
                return false;
            }
        }
        return true;
    }
}
