package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

import static SNAct1.SNAct1Mod.makePowerPath;
import static SNAct1.util.Wiz.atb;

public class ArdentAspectPower extends AbstractPower {
    public static final String POWER_ID = SNAct1Mod.makeID(ArdentAspectPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("ArdentAspectPower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("ArdentAspectPower32.png"));

    public ArdentAspectPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    public void atStartOfTurnPostDraw() {
        AbstractCreature p = AbstractDungeon.player;
        for (AbstractPower po : p.powers) {
            if (po.ID.equals(StrengthPower.POWER_ID)) {
                if (po.amount > 0) {
                    atb(new ApplyPowerAction(p, p, new StrengthPower(p, po.amount)));
                    atb(new ApplyPowerAction(p, p, new LoseStrengthPower(p, po.amount)));
                }
            }
            if (po.ID.equals(DexterityPower.POWER_ID)) {
                if (po.amount > 0) {
                    atb(new ApplyPowerAction(p, p, new DexterityPower(p, po.amount)));
                    atb(new ApplyPowerAction(p, p, new LoseDexterityPower(p, po.amount)));
                }
            }
        }
    }

}
