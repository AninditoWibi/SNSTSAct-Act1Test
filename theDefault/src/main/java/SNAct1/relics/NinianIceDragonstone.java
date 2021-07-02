package SNAct1.relics;

import SNAct1.SNAct1Mod;
import SNAct1.cards.enemyStatusCards.StatusFrostbitten;
import SNAct1.util.TexLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static SNAct1.SNAct1Mod.makeID;
import static SNAct1.util.Wiz.atb;
import static SNAct1.util.Wiz.shuffleIn;

public class NinianIceDragonstone extends CustomRelic {
    public static final String ID = makeID(NinianIceDragonstone.class.getSimpleName());
    private static String pathToImg = SNAct1Mod.makeRelicPath("NinianIceDragonstone.png");
    private static String pathToImgOutline = SNAct1Mod.makeRelicOutlinePath("NinianIceDragonstone.png");

    private static final Texture IMG = TexLoader.getTexture(pathToImg);
    private static final Texture OUTLINE = TexLoader.getTexture(pathToImgOutline);

    public NinianIceDragonstone() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
        refreshTips();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        flash();
        atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        shuffleIn(new StatusFrostbitten(), 1);
    }

    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
    }

    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }

    public void refreshTips() {
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
        this.tips.add(new CardPowerTip(new StatusFrostbitten()));
    }

    public AbstractRelic makeCopy() {
        return new NinianIceDragonstone();
    }
}
