package SNAct1.variables;

import SNAct1.cards.AbstractSNActCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static SNAct1.SNAct1Mod.makeID;

public class ThirdMagicNumber extends DynamicVariable {
    @Override
    public String key() {
        return makeID("ThirdMagic");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractSNActCard) {
            return ((AbstractSNActCard) card).isThirdMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractSNActCard) {
            return ((AbstractSNActCard) card).thirdMagicNumber;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractSNActCard) {
            ((AbstractSNActCard) card).isThirdMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractSNActCard) {
            return ((AbstractSNActCard) card).baseThirdMagicNumber;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractSNActCard) {
            return ((AbstractSNActCard) card).upgradedThirdMagic;
        }
        return false;
    }
}
