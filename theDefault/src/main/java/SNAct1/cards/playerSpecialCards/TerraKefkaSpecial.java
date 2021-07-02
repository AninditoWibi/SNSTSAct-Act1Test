package SNAct1.cards.playerSpecialCards;

import SNAct1.cards.AbstractSNActCard;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static SNAct1.SNAct1Mod.makeID;
import static SNAct1.util.Wiz.atb;

public class TerraKefkaSpecial extends AbstractSNActCard {
    public final static String ID = makeID(TerraKefkaSpecial.class.getSimpleName());

    public static final int COST = 2;
    public static final int COST_UPGRADED = 1;
    public static final int TRANCE_TURNS = 2;
    public static final int LIGHT_TRIGGER_TURNS = 3;

    public TerraKefkaSpecial() {
        super(ID, COST, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, CardColor.COLORLESS);
        magicNumber = baseMagicNumber = TRANCE_TURNS;
        secondMagicNumber = baseSecondMagicNumber = LIGHT_TRIGGER_TURNS;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new TerraChoice());
        choices.add(new KefkaChoice());
        atb(new ChooseOneAction(choices));
    }

    @Override
    public void upp() {
        upgradeBaseCost(COST_UPGRADED);
    }
}
