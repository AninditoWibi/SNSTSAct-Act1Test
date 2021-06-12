package SNAct1.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import SNAct1.cards.AbstractSNActCard;

import static SNAct1.SNAct1Mod.makeID;

public class SecondMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return makeID("SecondMagic");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractSNActCard) {
            return ((AbstractSNActCard) card).isSecondMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractSNActCard) {
            return ((AbstractSNActCard) card).secondMagicNumber;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractSNActCard) {
            ((AbstractSNActCard) card).isSecondMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractSNActCard) {
            return ((AbstractSNActCard) card).baseSecondMagicNumber;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractSNActCard) {
            return ((AbstractSNActCard) card).upgradedSecondMagic;
        }
        return false;
    }
}
