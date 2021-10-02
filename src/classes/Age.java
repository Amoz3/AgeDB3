package classes;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import static org.dreambot.api.methods.MethodProvider.*;

public class Age {

    private static String playerAge;

    public static String getAge() {
        // 1073741824 is player setting for if time played is visible or not, 0 = hidden 1073741824 = visible
        // widgits 712, c 2 , gc 98 (button) gc 100 (text)
        if (PlayerSettings.getConfig(3144) == 1073741824 || PlayerSettings.getConfig(3144) == -1073741824) { // if visible || the negative value is for when you have it set to not ask you & its visible

            greyCheck();
            WidgetChild age = Widgets.getWidget(712).getChild(2).getChild(100); // i forget how widgets work

            if (age.getText() != null) {
                String preProcessed = age.getText();
                playerAge = preProcessed.replace("<col=0dc10d>", "")
                        .replace("</col>","")
                        .replace("Time Played:","");

                // Time Played: <col=0dc10d>1 day, 11 hours</col>
            }
            return playerAge;
        }

        if (PlayerSettings.getConfig(3144) == 0 || PlayerSettings.getConfig(3144) == -2147483648) { // if its hidden, open up then rerun method || neg value is when its hidden and you have it set to not ask you
            if (Tabs.open(Tab.QUEST)) {
                greyCheck(); // this one makes it click a few times idk why and because this will only be done like once per acc i dont care
                WidgetChild ageButton = Widgets.getWidget(712).getChild(2).getChild(98);
                if (ageButton != null) {
                    if (ageButton.interact()) {
                        sleepUntil(Dialogues::areOptionsAvailable, 1200); // this could also be sleepUntil i guess
                    }
                    if (Dialogues.areOptionsAvailable()) {
                        Dialogues.chooseOption("Yes");
                        sleepUntil(() -> !Dialogues.inDialogue(), 10000);
                    }
                    getAge(); // recursive call because you've just opened the age text
                }
            } else {
                getAge(); // when mouse was far away and Tabs.open took a while it would return here, making it recursive ez fix, probably not the best fix
            }
        }
        return playerAge;
    }
    
    private static void greyCheck() { // ive made this a priv method so i dont have to repeat code in both checking and revealing sections of getAge()

        // 2 / 10 / 2021 when quest tab sub tab is anything other than gray icon null pointer is thrown here, as age button isnt there. reported by Bryno
        // this bug probably exists in the click to reveal part also
        // Varbit 8168 holds what sub tab the quest tab is in, we want grey tab which is value 0
        if (PlayerSettings.getBitValue(8168) != 0) {
            if (Tabs.open(Tab.QUEST)) {
                WidgetChild greyButton = Widgets.getChildWidget(629, 4);
                greyButton.interact();
                sleep(600);
                // ^ this lets widgets load before trying to get age widget child, you can lower this it will throw null pointer but still work
                // depending on ur frames it might still throw null pointer but that doesnt seem to break it
            }
            getAge();
        }
    }
}
