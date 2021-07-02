package SNAct1.cards.playerSpecialCards;

import SNAct1.cards.AbstractSNActCard;
import SNAct1.powers.LightOfJudgementCardPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;

import static SNAct1.SNAct1Mod.makeID;
import static SNAct1.util.Wiz.adp;
import static SNAct1.util.Wiz.atb;

public class KefkaChoice extends AbstractSNActCard {
    public static final String ID = makeID(KefkaChoice.class.getSimpleName());

    public static final int LIGHT_TRIGGER_TURNS = 3;
    // these two are cosmetic/description only. for actual balancing, check the power class
    public static final int LIGHT_DAMAGE = 4;
    public static final int LIGHT_HITS = 13;

    public KefkaChoice() {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        magicNumber = baseMagicNumber = LIGHT_TRIGGER_TURNS;
        this.baseDamage = LIGHT_DAMAGE;
        secondMagicNumber = baseSecondMagicNumber = LIGHT_HITS;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        atb(new VFXAction(new BorderFlashEffect(Color.GOLDENROD, true)));
        atb(new VFXAction(adp(), new MiracleEffect(Color.GOLD, Color.YELLOW, "BLOCK_GAIN_1"), 1.0F));
        atb(new ApplyPowerAction(adp(), adp(), new LightOfJudgementCardPower(adp(), magicNumber)));
    }

    @Override
    public void upp() {

    }

    @Override
    public AbstractCard makeCopy() { return new KefkaChoice(); }
}
