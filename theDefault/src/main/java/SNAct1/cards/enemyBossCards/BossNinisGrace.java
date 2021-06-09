package SNAct1.cards.enemyBossCards;

import SNAct1.cards.AbstractSNActCard;
import SNAct1.monsters.BossNinian;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static SNAct1.SNAct1Mod.makeID;

@AutoAdd.Ignore
public class BossNinisGrace extends AbstractSNActCard {
    public final static String ID = makeID(BossNinisGrace.class.getSimpleName());
    BossNinian cardOwner;

    public BossNinisGrace(BossNinian cardOwner) {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, CardColor.COLORLESS);
        this.cardOwner = cardOwner;
        this.magicNumber = baseMagicNumber = cardOwner.blockBuffDuration;
        this.secondMagicNumber = baseSecondMagicNumber = cardOwner.blockBuffValue;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { }

    @Override
    public void upp() { }

    @Override
    public AbstractCard makeCopy() {
        return new BossNinisGrace(cardOwner);
    }
}