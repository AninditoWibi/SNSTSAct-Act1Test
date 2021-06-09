package SNAct1.cards.enemyBossCards;

import SNAct1.cards.AbstractSNActCard;
import SNAct1.monsters.BossEliwood;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static SNAct1.SNAct1Mod.makeID;

@AutoAdd.Ignore
public class BossVisionsOfArcadia extends AbstractSNActCard {
    public final static String ID = makeID(BossVisionsOfArcadia.class.getSimpleName());
    BossEliwood cardOwner;

    public BossVisionsOfArcadia(BossEliwood cardOwner) {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, CardColor.COLORLESS);
        this.cardOwner = cardOwner;
        this.magicNumber = baseMagicNumber = cardOwner.dmgBlockBuffBonus;
        this.secondMagicNumber = baseSecondMagicNumber = cardOwner.dmgBlockBuffDuration;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { }

    @Override
    public void upp() { }

    @Override
    public AbstractCard makeCopy() {
        return new BossVisionsOfArcadia(cardOwner);
    }
}
