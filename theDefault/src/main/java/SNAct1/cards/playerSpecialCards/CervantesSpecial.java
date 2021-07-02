package SNAct1.cards.playerSpecialCards;

import SNAct1.cards.AbstractSNActCard;
import SNAct1.powers.SoulChargePower;
import SNAct1.powers.SoulEdgesCallPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;

import static SNAct1.SNAct1Mod.makeID;
import static SNAct1.util.Wiz.adp;
import static SNAct1.util.Wiz.atb;

public class CervantesSpecial extends AbstractSNActCard {
    public final static String ID = makeID(CervantesSpecial.class.getSimpleName());

    public static final int COST = 2;
    public static final int COST_UPGRADED = 1;
    public static final int HP_COST = 6;
    public static final int SOULCHARGE_DURATION = 3;
    public static final int SOULCHARGE_BONUS = 33;

    public CervantesSpecial() {
        super(ID, COST, CardType.POWER, CardRarity.RARE, CardTarget.SELF, CardColor.COLORLESS);
        magicNumber = baseMagicNumber = HP_COST;
        secondMagicNumber = baseSecondMagicNumber = SOULCHARGE_BONUS;
        thirdMagicNumber = baseThirdMagicNumber = SOULCHARGE_DURATION;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        // recycling corruption's VFX
        atb(new VFXAction(abstractPlayer, new VerticalAuraEffect(Color.BLACK, abstractPlayer.hb.cX, abstractPlayer.hb.cY), 0.33F));
        atb(new SFXAction("ATTACK_FIRE"));
        atb(new VFXAction(abstractPlayer, new VerticalAuraEffect(Color.PURPLE, abstractPlayer.hb.cX, abstractPlayer.hb.cY), 0.33F));
        atb(new VFXAction(abstractPlayer, new VerticalAuraEffect(Color.CYAN, abstractPlayer.hb.cX, abstractPlayer.hb.cY), 0.0F));
        atb(new VFXAction(abstractPlayer, new BorderLongFlashEffect(Color.MAGENTA), 0.0F, true));
        //noncosmetic logic
        atb(new LoseHPAction(adp(), adp(), this.magicNumber));
        atb(new ApplyPowerAction(adp(), adp(), new SoulChargePower(adp(), thirdMagicNumber, secondMagicNumber, false)));
        atb(new ApplyPowerAction(adp(), adp(), new SoulEdgesCallPower(adp(), thirdMagicNumber, secondMagicNumber, magicNumber)));
    }

    @Override
    public void upp() {
        upgradeBaseCost(COST_UPGRADED);
    }
}
