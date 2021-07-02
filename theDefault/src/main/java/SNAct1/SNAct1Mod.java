package SNAct1;

import SNAct1.CustomIntent.MassAttackIntent;
import SNAct1.cards.enemyStatusCards.StatusFrostbitten;
import SNAct1.cards.playerSpecialCards.CervantesSpecial;
import SNAct1.cards.playerSpecialCards.KefkaChoice;
import SNAct1.cards.playerSpecialCards.TerraChoice;
import SNAct1.cards.playerSpecialCards.TerraKefkaSpecial;
import SNAct1.monsters.*;
import SNAct1.variables.SecondMagicNumber;
import SNAct1.relics.*;
import SNAct1.variables.ThirdMagicNumber;
import actlikeit.RazIntent.CustomIntent;
import basemod.*;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import SNAct1.cards.*;
import SNAct1.util.IDCheckDontTouchPls;
import SNAct1.util.TextureLoader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 4 places.
// I comment those places below, under the place where you set your ID.

// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault", and change to "yourmodname" rather than "thedefault".
// You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories, and press alt+c to make the replace case sensitive (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class SNAct1Mod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        AddAudioSubscriber,
        PostBattleSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(SNAct1Mod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties SNAct1DefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "SN Act 1 - Interdimensional Rifts";
    private static final String AUTHOR = "SolarNougat"; // And pretty soon - You!
    private static final String DESCRIPTION = "Test file for SN's act 1 mod.";

    // =============== INPUT TEXTURE LOCATION =================

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    /*
    public static class Enums {
        @SpireEnum(name = "SNBOSSCARDS") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor EGO;
        @SpireEnum(name = "SNBOSSCARDS")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    } */

    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = getModID() + "Resources/images/Badge.png";


    // =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String makeBossVoiceLinePath(String resourcePath) {
        return getModID() + "Resources/audio/bossvoice/" + resourcePath;
    }

    public static String makeMusicPath(String resourcePath) {
        return getModID() + "Resources/audio/music/" + resourcePath;
    }

    public static String makeMonsterPath(String resourcePath) {
        return getModID() + "Resources/images/monsters/" + resourcePath;
    }

    public static String makeUIPath(String resourcePath) {
        return getModID() + "Resources/images/ui/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return getModID() + "Resources/images/" + resourcePath;
    }

    // =============== /INPUT TEXTURE LOCATION/ =================


    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    public SNAct1Mod() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        setModID("SNAct1");
        logger.info("Done subscribing");

        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        SNAct1DefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("SNAct1Mod", "SNAct1Config", SNAct1DefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");

    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = SNAct1Mod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = SNAct1Mod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = SNAct1Mod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO

    // ====== YOU CAN EDIT AGAIN ======

    public static void initialize() {
        SNAct1Mod snact1mod = new SNAct1Mod();
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================

    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        //custom Intent for terra, kefka, ninian
        CustomIntent.add(new MassAttackIntent());

        // if or when the act is done, transfer these to addBoss calls instead of addMonster calls
        BaseMod.addMonster(BossNinian.ID, "Boss-NinianEliwood", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new BossEliwood(-550.0F, 0.0F),
                        new BossNinian(200.0F, 0.0F)
                }));
        BaseMod.addMonster(BossCervantes.ID, "Boss-Cervantes", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new BossCervantes(50.0F, 0.0F)
                }));
        BaseMod.addMonster(BossKefka.ID, "Boss-TerraKefka", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new BossKefka(70.0F, 0.0F),
                        new BossTerra(-400.0F, 0.0F)
                }));


        logger.info("Done loading badge Image and mod options");
    }

    // =============== / POST-INITIALIZE/ =================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        // boss relics
        BaseMod.addRelic(new CervantesAcheronAndNirvana(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(CervantesAcheronAndNirvana.ID);
        BaseMod.addRelic(new NinianIceDragonstone(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(NinianIceDragonstone.ID);
        BaseMod.addRelic(new TerraEsperkinsMagicite(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(TerraEsperkinsMagicite.ID);
        // charspecific relics
        BaseMod.addRelic(new IroncladSacredFlame(), RelicType.RED);
        UnlockTracker.markRelicAsSeen(IroncladSacredFlame.ID);
        BaseMod.addRelic(new WatcherHymnal(), RelicType.PURPLE);
        UnlockTracker.markRelicAsSeen(WatcherHymnal.ID);
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new ThirdMagicNumber());

        logger.info("Adding cards");
        /*
        // enemy special cards
        BaseMod.addCard(new CervantesSpecial());
        UnlockTracker.markCardAsSeen(CervantesSpecial.ID);
        BaseMod.addCard(new TerraKefkaSpecial());
        UnlockTracker.markCardAsSeen(TerraKefkaSpecial.ID);
        BaseMod.addCard(new TerraChoice());
        UnlockTracker.markCardAsSeen(TerraChoice.ID);
        BaseMod.addCard(new KefkaChoice());
        UnlockTracker.markCardAsSeen(KefkaChoice.ID);
        // mod-added statuses
        BaseMod.addCard(new StatusFrostbitten());
        UnlockTracker.markCardAsSeen(StatusFrostbitten.ID);
        */

        new AutoAdd("SNAct1")
                .packageFilter(AbstractSNActCard.class)
                .setDefaultSeen(true)
                .cards();

        logger.info("Done adding cards!");
    }

    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/Card-Strings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/Power-Strings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/Relic-Strings.json");

        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Event-Strings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/eng/UI-Strings.json");

        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                getModID() + "Resources/localization/eng/Monster-Strings.json");

    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================    



    @Override
    public void receiveAddAudio() {

        // voice - cervantes
        BaseMod.addAudio(makeID("CervantesOpening"), makeBossVoiceLinePath("cervantes-opening.wav"));
        BaseMod.addAudio(makeID("CervantesDC"), makeBossVoiceLinePath("cervantes-dreadcharge.wav"));
        BaseMod.addAudio(makeID("CervantesDCB"), makeBossVoiceLinePath("cervantes-geodaray.wav"));
        BaseMod.addAudio(makeID("Cervantes4A+B"), makeBossVoiceLinePath("cervantes-culverin.wav"));
        BaseMod.addAudio(makeID("CervantesIGDR"), makeBossVoiceLinePath("cervantes-igdr.wav"));
        BaseMod.addAudio(makeID("CervantesDSB"), makeBossVoiceLinePath("cervantes-sixties.wav"));
        BaseMod.addAudio(makeID("CervantesSC"), makeBossVoiceLinePath("cervantes-soulcharge.wav"));
        BaseMod.addAudio(makeID("Cervantes44A+G"), makeBossVoiceLinePath("cervantes-phantasm.wav"));
        BaseMod.addAudio(makeID("CervantesCE"), makeBossVoiceLinePath("cervantes-ultimate.wav"));
        BaseMod.addAudio(makeID("CervantesDeath"), makeBossVoiceLinePath("cervantes-death.wav"));
        // voice - eliwood
        BaseMod.addAudio(makeID("EliwoodOpening"), makeBossVoiceLinePath("eliwood-opening.wav"));
        BaseMod.addAudio(makeID("EliwoodAttacking"), makeBossVoiceLinePath("eliwood-deathblow.wav"));
        BaseMod.addAudio(makeID("EliwoodDefending"), makeBossVoiceLinePath("eliwood-massblock.wav"));
        BaseMod.addAudio(makeID("EliwoodDebuff"), makeBossVoiceLinePath("eliwood-chillattack.wav"));
        BaseMod.addAudio(makeID("EliwoodBuff"), makeBossVoiceLinePath("eliwood-visions.wav"));
        BaseMod.addAudio(makeID("EliwoodPlayerDies"), makeBossVoiceLinePath("eliwood-playerdeath.wav"));
        BaseMod.addAudio(makeID("EliwoodDeath"), makeBossVoiceLinePath("eliwood-death.wav"));
        // voice - kefka
        BaseMod.addAudio(makeID("KefkaOpening"), makeBossVoiceLinePath("kefka-opening.wav"));
        BaseMod.addAudio(makeID("KefkaFire"), makeBossVoiceLinePath("kefka-fragrantfire.wav"));
        BaseMod.addAudio(makeID("KefkaThundaga"), makeBossVoiceLinePath("kefka-zaptrap.wav"));
        BaseMod.addAudio(makeID("KefkaLaughing"), makeBossVoiceLinePath("kefka-laughter.wav"));
        BaseMod.addAudio(makeID("KefkaHyperdrive"), makeBossVoiceLinePath("kefka-hyperdrive.wav"));
        BaseMod.addAudio(makeID("KefkaHavocWing"), makeBossVoiceLinePath("kefka-havocwing.wav"));
        BaseMod.addAudio(makeID("KefkaDeath"), makeBossVoiceLinePath("kefka-death.wav"));
        BaseMod.addAudio(makeID("KefkaFlee"), makeBossVoiceLinePath("kefka-flee.wav"));
        // voice - ninian
        BaseMod.addAudio(makeID("NinianOpening"), makeBossVoiceLinePath("ninian-opening.wav"));
        BaseMod.addAudio(makeID("NinianAttacking"), makeBossVoiceLinePath("ninian-attack-edited.wav"));
        BaseMod.addAudio(makeID("NinianDefending"), makeBossVoiceLinePath("ninian-defense-edited.wav"));
        BaseMod.addAudio(makeID("NinianUltimate"), makeBossVoiceLinePath("ninian-ultimate-edited.wav"));
        BaseMod.addAudio(makeID("NinianDeath"), makeBossVoiceLinePath("ninian-death.wav"));
        // voice - terra
        BaseMod.addAudio(makeID("TerraOpening"), makeBossVoiceLinePath("terra-opening.wav"));
        BaseMod.addAudio(makeID("TerraFiraga"), makeBossVoiceLinePath("terra-firaga.wav"));
        BaseMod.addAudio(makeID("TerraBlizzard"), makeBossVoiceLinePath("terra-blizzard.wav"));
        BaseMod.addAudio(makeID("TerraRasp"), makeBossVoiceLinePath("terra-rasp.wav"));
        BaseMod.addAudio(makeID("TerraTornado"), makeBossVoiceLinePath("terra-tornado.wav"));
        BaseMod.addAudio(makeID("TerraFlood"), makeBossVoiceLinePath("terra-flood.wav"));
        BaseMod.addAudio(makeID("TerraDeath"), makeBossVoiceLinePath("terra-death.wav"));
        BaseMod.addAudio(makeID("TerraFlee"), makeBossVoiceLinePath("terra-flee.wav"));
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {

    }

}
