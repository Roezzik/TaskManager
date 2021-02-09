package shared.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonManager {
    
    private static JsonManager instance;
    
    private JsonManager() {
    
    }
    
    public static JsonManager getInstance() {
        if (instance == null) {
            instance = new JsonManager();
        }
        return instance;
    }

//    public static void main(String[] args) throws IOException {
//
//        LocalDateTime localDateTime = LocalDateTime.now(Clock.systemUTC());
//        System.out.println("localDateTime - " + localDateTime);
//        Date notificationDate = Date.from(localDateTime.toInstant(ZoneOffset.UTC));
//        System.out.println("date - " + notificationDate);
//
//        Task    task    = new Task(1, "name", "desc", notificationDate, Status.SCHEDULED);
//        Command command = new Command(2, task);
//        System.out.println(command);
//
//        String resultJsonString = createJsonString(command);
//        System.out.println(resultJsonString);
//
//        Command result = parseJsonString(resultJsonString);
//        System.out.println(result.getContent());
//    }
    
    public String createJsonString(Object object) throws JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        StdDateFormat is ISO8601 since jackson 2.9
//        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        return mapper.writeValueAsString(object);
    }
    
    public Command parseJsonString(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, Command.class);
    }
    
}
