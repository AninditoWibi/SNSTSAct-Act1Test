package SNAct1.cards.playerSpecialCards;

import SNAct1.cards.AbstractSNActCard;
import SNAct1.powers.LightOfJudgementCardPower;
import SNAct1.powers.TranceCardPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;

import static SNAct1.SNAct1Mod.makeID;
import static SNAct1.util.Wiz.adp;
import static SNAct1.util.Wiz.atb;

public class TerraChoice extends AbstractSNActCard {
    public static final String ID = makeID(TerraChoice.class.getSimpleName());

    public static final int TRANCE_TURNS = 2;
    // these two are cosmetic/description only. for actual balancing, check the power class
    private final int DAMAGE_OUTGOING_MODIFIER = 100;
    private final int DAMAGE_REDUCTION_MODIFIER = 50;

    public TerraChoice() {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        magicNumber = baseMagicNumber = TRANCE_TURNS;
        secondMagicNumber = baseSecondMagicNumber = DAMAGE_OUTGOING_MODIFIER;
        thirdMagicNumber = baseThirdMagicNumber = DAMAGE_REDUCTION_MODIFIER;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        atb(new VFXAction(new BorderFlashEffect(Color.PURPLE, true)));
        atb(new VFXAction(adp(), new InflameEffect(adp()), 1.0F));
        atb(new ApplyPowerAction(adp(), adp(), new TranceCardPower(adp(), magicNumber)));
    }

    @Override
    public void upp() {

    }

    @Override
    public AbstractCard makeCopy() { return new TerraChoice(); }
}
