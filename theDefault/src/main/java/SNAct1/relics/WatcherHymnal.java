package SNAct1.relics;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static SNAct1.util.Wiz.atb;

public class WatcherHymnal extends CustomRelic {

    public static final String ID = SNAct1Mod.makeID(WatcherHymnal.class.getSimpleName());
    private static String pathToImg = SNAct1Mod.makeRelicPath("WatcherHymnal.png");
    private static String pathToImgOutline = SNAct1Mod.makeRelicOutlinePath("WatcherHymnal.png");

    private static final Texture IMG = TexLoader.getTexture(pathToImg);
    private static final Texture OUTLINE = TexLoader.getTexture(pathToImgOutline);

    static final int CARD_CREATION_LIMIT = 4;
    static final int STRENGTH_GAIN = 2;

    public WatcherHymnal() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    public void atBattleStart() {
        counter = 0;
    }

    // also see FindCardGenWatcherHymnal.java
    public void onCreateCard() {
        counter++;
        if (this.counter % CARD_CREATION_LIMIT == 0) {
            flash();
            this.counter = 0;
            atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STRENGTH_GAIN), STRENGTH_GAIN));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CARD_CREATION_LIMIT + DESCRIPTIONS[1] + STRENGTH_GAIN + DESCRIPTIONS[2];
    }
    @Override
    public AbstractRelic makeCopy() {
        return new WatcherHymnal();
    }

}
