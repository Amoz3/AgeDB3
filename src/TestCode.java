import classes.Age;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import java.sql.Timestamp;


@ScriptManifest(name = "Testcode",
        description = "My script description!",
        author = "",
        version = 1.0, category = Category.WOODCUTTING,
        image = "")

public class TestCode extends AbstractScript {

    @Override
    public void onStart() {

    }


    @Override
    public int onLoop() {
        log(PlayerSettings.getConfig(3144));

        log(Age.getAge());

        return 3000;
    }

}

