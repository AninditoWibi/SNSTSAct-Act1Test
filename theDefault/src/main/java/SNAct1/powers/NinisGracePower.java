package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static SNAct1.SNAct1Mod.makePowerPath;

public class NinisGracePower extends AbstractPower {
    public static final String POWER_ID = SNAct1Mod.makeID(NinisGracePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    int blockGainMult; // 50 = 50% more block, etc

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("NinisGracePower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("NinisGracePower32.png"));

    public NinisGracePower(AbstractCreature owner, int numOfBlocks, int blockMult) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = numOfBlocks;
        this.blockGainMult = blockMult;
        updateDescription();

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.blockGainMult + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3] + this.blockGainMult + DESCRIPTIONS[2];
        }
    }

    public float onOwnerGainedBlock(float blockAmount) {
        float modifiedBlockAmount = blockAmount * (1 + (blockGainMult / 100));
        amount--;

        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }

        return modifiedBlockAmount;
    }

}
