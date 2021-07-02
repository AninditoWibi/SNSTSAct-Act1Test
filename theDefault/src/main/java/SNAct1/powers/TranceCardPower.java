package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static SNAct1.SNAct1Mod.makePowerPath;
import static SNAct1.util.Wiz.atb;

public class TranceCardPower extends AbstractPower {
    public static final String POWER_ID = SNAct1Mod.makeID(TranceCardPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private final int DAMAGE_OUTGOING_MODIFIER = 100;
    private final int DAMAGE_REDUCTION_MODIFIER = 50;

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("TrancePower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("TrancePower32.png"));

    public TranceCardPower(AbstractCreature owner, int duration) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = duration;
        updateDescription();

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DAMAGE_OUTGOING_MODIFIER + DESCRIPTIONS[3] + DAMAGE_REDUCTION_MODIFIER + DESCRIPTIONS[4];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + DAMAGE_OUTGOING_MODIFIER + DESCRIPTIONS[3] + DAMAGE_REDUCTION_MODIFIER + DESCRIPTIONS[4];
        }
    }

    public void atEndOfRound() {
        if (this.amount == 0) {
            atb(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            atb(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.NORMAL) {
            return damage;
        }
        return damage * (1 + ((float)DAMAGE_OUTGOING_MODIFIER / 100f));
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.NORMAL) {
            return damage;
        }
        return damage * (((float)DAMAGE_REDUCTION_MODIFIER / 100f));
    }

}
