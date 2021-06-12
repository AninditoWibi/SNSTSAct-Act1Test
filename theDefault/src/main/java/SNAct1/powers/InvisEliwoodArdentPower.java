package SNAct1.powers;

import SNAct1.SNAct1Mod;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

import static SNAct1.util.Wiz.atb;

public class InvisEliwoodArdentPower extends AbstractPower implements InvisiblePower {
    public static final String POWER_ID = SNAct1Mod.makeID(InvisEliwoodArdentPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public InvisEliwoodArdentPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        // updateDescription(); // apparently unnecessary
    }

    public void atStartOfTurnPostDraw() {
        for (AbstractPower p : owner.powers) {
            if (p.ID.equals(StrengthPower.POWER_ID)) {
                if (p.amount > 0) {
                    atb(new ApplyPowerAction(owner, owner, new StrengthPower(owner, p.amount)));
                    atb(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, p.amount)));
                }
            }
            if (p.ID.equals(DexterityPower.POWER_ID)) {
                if (p.amount > 0) {
                    atb(new ApplyPowerAction(owner, owner, new DexterityPower(owner, p.amount)));
                    atb(new ApplyPowerAction(owner, owner, new LoseDexterityPower(owner, p.amount)));
                }
            }
        }
    }
}
