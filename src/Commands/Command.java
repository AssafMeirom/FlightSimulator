package Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface Command {

    void doCommand(String[] arguments);
}
