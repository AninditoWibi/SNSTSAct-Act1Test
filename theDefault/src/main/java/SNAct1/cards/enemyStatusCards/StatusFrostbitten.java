package SNAct1.cards.enemyStatusCards;

import SNAct1.cards.AbstractSNActCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static SNAct1.SNAct1Mod.*;
import static SNAct1.util.Wiz.atb;
import static SNAct1.util.Wiz.att;

public class StatusFrostbitten extends AbstractSNActCard {
    public final static String ID = makeID(StatusFrostbitten.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;

    private static final int COST = 4;
    // at the end of your turn, if this is in your hand, take blockable damage = cost of this, then reduce cost by 1

    public StatusFrostbitten() {
        super(ID, COST, CardType.STATUS, CardRarity.SPECIAL, CardTarget.NONE);
        selfRetain = true;
        exhaust = true;
    }

    @Override
    public void upgrade() { }

    @Override
    public void upp() { }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard)
            att(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.costForTurn, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        // AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    public void onRetained() {
        if (this.costForTurn > 1) { atb(new ReduceCostAction(this)); }
    }

}
