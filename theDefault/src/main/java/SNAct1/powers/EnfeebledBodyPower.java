package SNAct1.powers;

import SNAct1.SNAct1Mod;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnMyBlockBrokenPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static SNAct1.util.Wiz.applyToTarget;

public class EnfeebledBodyPower extends AbstractPower implements OnMyBlockBrokenPower {
    public static final String POWER_ID = SNAct1Mod.makeID("EnfeebledBodyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public EnfeebledBodyPower(AbstractCreature owner, int weakAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = weakAmt;
        updateDescription();
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onMyBlockBroken() {
        // do inflict weak to owner here
        applyToTarget(owner, owner, new WeakPower(owner, amount, true));
    }
}
