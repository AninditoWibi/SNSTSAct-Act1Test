package SNAct1.cards.enemyBossCards;

import SNAct1.cards.AbstractSNActCard;
import SNAct1.monsters.BossNinian;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static SNAct1.SNAct1Mod.makeID;

@AutoAdd.Ignore
public class BossEscapeRoute extends AbstractSNActCard {
    public final static String ID = makeID(BossEscapeRoute.class.getSimpleName());
    BossNinian cardOwner;

    public BossEscapeRoute(BossNinian cardOwner) {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, CardColor.COLORLESS);
        this.cardOwner = cardOwner;
        this.baseBlock = cardOwner.escapeRouteBlock;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { }

    @Override
    public void upp() { }

    @Override
    public AbstractCard makeCopy() {
        return new BossEscapeRoute(cardOwner);
    }
}
