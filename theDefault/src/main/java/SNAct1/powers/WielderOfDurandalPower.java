package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static SNAct1.SNAct1Mod.makePowerPath;

public class WielderOfDurandalPower extends AbstractPower {
    public static final String POWER_ID = SNAct1Mod.makeID(WielderOfDurandalPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("WielderOfDurandalPower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("WielderOfDurandalPower32.png"));

    public WielderOfDurandalPower(AbstractCreature owner, int durandalDamageBonus) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = durandalDamageBonus;
        updateDescription();

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }
    // dummy visible power for eliwood
    // actual damage bonus is applied in InvisNinianWeaknessPower
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
