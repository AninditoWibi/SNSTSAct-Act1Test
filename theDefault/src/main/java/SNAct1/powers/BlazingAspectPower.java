package SNAct1.powers;

import SNAct1.SNAct1Mod;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static SNAct1.util.Wiz.applyToTarget;

public class BlazingAspectPower extends AbstractPower {
    public static final String POWER_ID = SNAct1Mod.makeID("BlazingAspectPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BlazingAspectPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
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
            applyToTarget(owner, owner, new StrengthPower(owner, bonusStr));
            applyToTarget(owner, owner, new LoseStrengthPower(owner, bonusStr));
        }
    }
}
