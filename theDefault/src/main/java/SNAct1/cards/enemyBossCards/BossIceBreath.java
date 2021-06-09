package SNAct1.cards.enemyBossCards;

import SNAct1.cards.AbstractSNActCard;
import SNAct1.monsters.BossNinian;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static SNAct1.SNAct1Mod.makeID;

@AutoAdd.Ignore
public class BossIceBreath extends AbstractSNActCard {
    public final static String ID = makeID(BossIceBreath.class.getSimpleName());
    BossNinian cardOwner;

    public BossIceBreath(BossNinian cardOwner) {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, CardColor.COLORLESS);
        this.cardOwner = cardOwner;
        this.baseDamage = cardOwner.iceBreathDmg;
        this.magicNumber = cardOwner.iceBreathDebuff;
        this.secondMagicNumber = baseSecondMagicNumber = cardOwner.iceBreathDebuffDuration;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { }

    @Override
    public void upp() { }

    @Override
    public AbstractCard makeCopy() {
        return new BossIceBreath(cardOwner);
    }
}
