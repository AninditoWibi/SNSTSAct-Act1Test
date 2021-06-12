package SNAct1.cards.enemyBossCards;

import SNAct1.cards.AbstractSNActCard;
import SNAct1.monsters.BossNinian;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static SNAct1.SNAct1Mod.makeID;

@AutoAdd.Ignore
public class BossLightBreath extends AbstractSNActCard {
    public final static String ID = makeID(BossLightBreath.class.getSimpleName());
    BossNinian cardOwner;

    public BossLightBreath(BossNinian cardOwner) {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY, CardColor.COLORLESS);
        this.cardOwner = cardOwner;
        this.baseDamage = cardOwner.lightBreathDmg;
        this.baseBlock = cardOwner.lightBreathBlock;
        this.magicNumber = baseMagicNumber = cardOwner.lightBreathStr;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { }

    @Override
    public void upp() { }

    @Override
    public AbstractCard makeCopy() {
        return new BossLightBreath(cardOwner);
    }

}
