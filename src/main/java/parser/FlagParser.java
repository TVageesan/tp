package parser;

import java.util.HashMap;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FlagParser {
    private static final Logger logger = Logger.getLogger(FlagParser.class.getName());

    private final Map<String, String> parsedFlags = new HashMap<>();
    private final Map<String, String> aliasMap = new HashMap<>();

    public FlagParser(String argumentString) {
        initializeAliasMap();
        if (argumentString != null && !argumentString.trim().isEmpty()) {
            parse(argumentString);
        }
    }

    private void initializeAliasMap() {
        aliasMap.put("/progIndex", "/p");
        aliasMap.put("/programme", "/p");

        aliasMap.put("/day", "/d");
        aliasMap.put("/date", "/t");

        aliasMap.put("/name", "/n");
        aliasMap.put("/exercise", "/e");
        aliasMap.put("/set", "/s");
        aliasMap.put("/rep", "/r");
        aliasMap.put("/weight", "/w");

        aliasMap.put("/createEx", "/a");
        aliasMap.put("/updateEx", "/u");
        aliasMap.put("/removeEx", "/x");
        aliasMap.put("/createDay", "/ad");
        aliasMap.put("/removeDay", "/xd");
    }

    private void parse(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        String[] args = argumentString.trim().split(" (?=/)");
        for (String arg : args) {
            String[] argParts = arg.split(" ", 2);
            String flag = argParts[0].trim();
            String value = "";

            if (argParts.length > 1) {
                value = argParts[1].trim();
            }

            if (value.isEmpty()) {
                throw new IllegalArgumentException("Argument " + flag + " is empty. Please provide a valid value.");
            } else {
                flag = resolveAlias(flag);
                logger.log(Level.INFO, "Parsed flag: {0} with value: {1}", new Object[]{flag, value});
                parsedFlags.put(flag, value);
            }
        }
    }

    private String resolveAlias(String flag) {
        if (aliasMap.containsKey(flag)) {
            return aliasMap.get(flag);
        }
        return flag;
    }

    public String getFlagValue(String flag) {
        assert flag != null && !flag.isEmpty() : "Flag must not be null or empty";

        flag = resolveAlias(flag);
        String value = parsedFlags.get(flag);

        logger.log(Level.INFO, "Successfully retrieved value for flag {0}: {1}", new Object[]{flag, value});
        return value;
    }

    public boolean hasFlag(String flag) {
        assert flag != null && !flag.isEmpty() : "Flag must not be null or empty";

        flag = resolveAlias(flag);
        boolean hasFlag = parsedFlags.containsKey(flag);

        logger.log(Level.INFO, "Flag {0} presence: {1}", new Object[]{flag, hasFlag});
        return hasFlag;
    }

    public void validateRequiredFlags(FlagParser flagParser, String[] requiredFlags) {
        assert requiredFlags != null : "Required flags string must not be null";

        for (String flag : requiredFlags) {
            flag = resolveAlias(flag);
            if (!flagParser.hasFlag(flag)) {
                throw new IllegalArgumentException("Required flag: " + flag + "is missing. Please provide the flag.");
            }
        }
    }
}
