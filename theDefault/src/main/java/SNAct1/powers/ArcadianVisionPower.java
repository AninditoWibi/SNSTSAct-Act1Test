package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static SNAct1.SNAct1Mod.makePowerPath;

public class ArcadianVisionPower extends TwoAmountPower {
    public static final String POWER_ID = SNAct1Mod.makeID(ArcadianVisionPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied = false;

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("ArcadianVisionPower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("ArcadianVisionPower32.png"));

    public ArcadianVisionPower(AbstractCreature owner, int arcadianVisionAmount, int arcadianVisionTurns, boolean isSourceMonster) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = arcadianVisionTurns;
        this.amount2 = arcadianVisionAmount;
        if (isSourceMonster) { this.justApplied = true; }
        updateDescription();

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[2];
        }
    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL)
            return damage + this.amount2;
        return damage;
    }

    // for block gain boost logic please see patches
    public float onOwnerGainedBlock(float blockAmount) {
        float modifiedBlockAmount = blockAmount + amount2;
        return modifiedBlockAmount;
    }
}
