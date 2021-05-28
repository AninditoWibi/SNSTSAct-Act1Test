package SNAct1.powers;

import SNAct1.SNAct1Mod;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NinisGracePower extends AbstractPower {
    public static final String POWER_ID = SNAct1Mod.makeID("NinisGracePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    float blockGainMult; // in terms of 100%, so 1.5 means 50% more block / 0.6 means 60% less block etc.

    public NinisGracePower(AbstractCreature owner, int numOfBlocks, float blockMult) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = numOfBlocks;
        this.blockGainMult = blockMult;
        updateDescription();
    }

    public float getBlockGainMult() {
        return blockGainMult;
    }

    public void setBlockGainMult(float blockGainMult) {
        this.blockGainMult = blockGainMult;
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.blockGainMult + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3] + this.blockGainMult + DESCRIPTIONS[2];
        }
    }

}
