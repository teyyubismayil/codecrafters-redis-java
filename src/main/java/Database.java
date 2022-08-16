import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Database {

    private static Database INSTANCE = null;

    private final Map<String, Item> storage;

    private Database() {
        storage = new ConcurrentHashMap<>();
    }

    public static synchronized Database getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

    public String get(String key) {
        Item item = storage.get(key);
        if (item == null) {
            return null;
        }
        if (item.getExpireAt() != null &&
                item.getExpireAt().isBefore(LocalDateTime.now())) {
            return null;
        }
        return item.getValue();
    }

    public void set(String key, String value, Long px) {
        LocalDateTime expireAt = px != null
                ? LocalDateTime.now().plus(px, ChronoUnit.MILLIS)
                : null;
        storage.put(key, new Item(value, expireAt));
    }

    private static class Item {

        private final String value;
        private final LocalDateTime expireAt;

        public Item(String value, LocalDateTime expireAt) {
            this.value = value;
            this.expireAt = expireAt;
        }

        public String getValue() {
            return value;
        }

        public LocalDateTime getExpireAt() {
            return expireAt;
        }
    }
}
