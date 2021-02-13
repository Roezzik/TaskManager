package shared;

import shared.Connection;

public interface ConnectionListener {
    void connect(Connection connection);
    void receive(Connection connection, String string);
    void send(Connection connection, String string);
    void disconnect(Connection connection);
}
