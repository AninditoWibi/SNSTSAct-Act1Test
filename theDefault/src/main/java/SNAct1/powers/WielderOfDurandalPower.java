package SNAct1.powers;

import SNAct1.SNAct1Mod;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WielderOfDurandalPower extends AbstractPower {
    public static final String POWER_ID = SNAct1Mod.makeID("WielderOfDurandalPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public WielderOfDurandalPower(AbstractCreature owner, int durandalDamageBonus) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = durandalDamageBonus;
        updateDescription();
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
