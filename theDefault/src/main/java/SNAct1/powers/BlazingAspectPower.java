package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static SNAct1.SNAct1Mod.makePowerPath;
import static SNAct1.util.Wiz.applyToTarget;
import static SNAct1.util.Wiz.applyToTargetNextTurn;

public class BlazingAspectPower extends AbstractPower {
    public static final String POWER_ID = SNAct1Mod.makeID(BlazingAspectPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TexLoader.getTexture(makePowerPath("BlazingAspectPower84.png"));
    private static final Texture tex32 = TexLoader.getTexture(makePowerPath("BlazingAspectPower32.png"));

    public BlazingAspectPower(AbstractCreature owner) {
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

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        AbstractPower ownStr = owner.getPower("StrengthPower");
        AbstractPower tgtStr = target.getPower("StrengthPower");
        int ownStrAmt;
        int tgtStrAmt;

        if (ownStr != null) {
            ownStrAmt = 0;
        } else {
            ownStrAmt = ownStr.amount;
        }

        if (tgtStr != null) {
            tgtStrAmt = 0;
        } else {
            tgtStrAmt = tgtStr.amount;
        }

        if (ownStrAmt > tgtStrAmt) {
            int bonusStr = ownStrAmt - tgtStrAmt;
            applyToTargetNextTurn(owner, new StrengthPower(owner, bonusStr));
            applyToTargetNextTurn(owner, new LoseStrengthPower(owner, bonusStr));
        }
    }
}
