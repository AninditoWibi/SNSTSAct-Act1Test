package SNAct1.cards.enemyBossCards;

import SNAct1.cards.AbstractSNActCard;
import SNAct1.monsters.BossEliwood;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static SNAct1.SNAct1Mod.makeID;

@AutoAdd.Ignore
public class BossDeathBlow extends AbstractSNActCard {
    public final static String ID = makeID(BossDeathBlow.class.getSimpleName());
    BossEliwood cardOwner;

    public BossDeathBlow(BossEliwood cardOwner) {
        super(ID, 2, AbstractCard.CardType.ATTACK, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY, AbstractCard.CardColor.COLORLESS);
        this.cardOwner = cardOwner;
        this.baseDamage = cardOwner.deathBlowDmg;
        this.magicNumber = baseMagicNumber = cardOwner.deathBlowStr;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { }

    @Override
    public void upp() { }

    @Override
    public AbstractCard makeCopy() {
        return new BossDeathBlow(cardOwner);
    }

}
