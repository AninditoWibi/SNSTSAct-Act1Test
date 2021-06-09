package SNAct1.powers;

import SNAct1.SNAct1Mod;
import SNAct1.monsters.BossEliwood;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class InvisNinianWeaknessPower extends AbstractPower implements InvisiblePower {
    public static final String POWER_ID = SNAct1Mod.makeID(InvisNinianWeaknessPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    int extraDamageValue; // 50 = 50% more damage, 100 = 100% more damage, etc
    // doesn't need image cuz it's supposed to be invisible

    public InvisNinianWeaknessPower(AbstractCreature owner, int extraDamageValue) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = extraDamageValue;
        // updateDescription(); // apparently unnecessary
    }
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            if (info.owner != null && !info.owner.isPlayer) {
                String damageOriginId = info.owner.id;
                if (damageOriginId.equals(BossEliwood.ID)) {
                    return damageAmount * (1 + (amount / 100));
                }
            }
        }
        return damageAmount;
    }
}
