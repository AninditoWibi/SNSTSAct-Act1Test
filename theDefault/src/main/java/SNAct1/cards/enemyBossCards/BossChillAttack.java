package SNAct1.cards.enemyBossCards;

import SNAct1.cards.AbstractSNActCard;
import SNAct1.monsters.BossEliwood;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static SNAct1.SNAct1Mod.makeID;

@AutoAdd.Ignore
public class BossChillAttack extends AbstractSNActCard {
    public final static String ID = makeID(BossChillAttack.class.getSimpleName());
    BossEliwood cardOwner;

    public BossChillAttack(BossEliwood cardOwner) {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, CardColor.COLORLESS);
        this.cardOwner = cardOwner;
        this.magicNumber = baseMagicNumber = cardOwner.chillAtkDebuff;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { }

    @Override
    public void upp() { }

    @Override
    public AbstractCard makeCopy() {
        return new BossChillAttack(cardOwner);
    }
}