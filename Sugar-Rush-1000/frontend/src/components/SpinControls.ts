import Phaser from 'phaser';
import { Theme } from '../constants/theme';
import { LayoutMetrics } from '../constants/LayoutEngine';

/**
 * SpinControls — Industry-grade spin button, bet +/- buttons, and autoplay toggle.
 *
 * Design:
 *  - Spin button: layered candy-pink gradient with gold/chrome ring, glossy hemisphere
 *  - Bet +/- buttons: positioned vertically above/below spin in stacked mode (no overlap)
 *  - Autoplay: sleek pill beneath spin
 *  - All buttons never overlap bottom bar pills
 */
export class SpinControls {
  private scene: Phaser.Scene;

  // Spin button
  spinOuterRing!: Phaser.GameObjects.Graphics;
  spinInnerPulse!: Phaser.GameObjects.Graphics;
  spinGfx!: Phaser.GameObjects.Graphics;
  spinHit!: Phaser.GameObjects.Rectangle;
  spinLabel!: Phaser.GameObjects.Text;

  // Bet +/- buttons
  betMinusGfx!: Phaser.GameObjects.Graphics;
  betMinusHit!: Phaser.GameObjects.Rectangle;
  betPlusGfx!: Phaser.GameObjects.Graphics;
  betPlusHit!: Phaser.GameObjects.Rectangle;

  // Autoplay button
  autoGfx!: Phaser.GameObjects.Graphics;
  autoHit!: Phaser.GameObjects.Rectangle;
  autoTxt!: Phaser.GameObjects.Text;

  // Cached last layout
  private _lastSpinX = 0;
  private _lastSpinY = 0;
  private _lastSpinSize = 0;

  constructor(scene: Phaser.Scene) {
    this.scene = scene;
    this.create();
  }

  private create() {
    this.spinOuterRing = this.scene.add.graphics().setDepth(19);
    this.spinInnerPulse = this.scene.add.graphics().setDepth(20);
    this.spinGfx = this.scene.add.graphics().setDepth(20);

    // Continuous premium idle animations
    this.scene.tweens.add({
      targets: this.spinInnerPulse,
      alpha: { from: 0.1, to: 0.5 },
      yoyo: true,
      duration: 1200,
      repeat: -1,
      ease: 'Sine.easeInOut'
    });
    this.spinHit = this.scene.add.rectangle(0, 0, 150, 150, 0xffffff, 0)
      .setInteractive({ useHandCursor: true }).setDepth(21);
    this.spinLabel = this.scene.add.text(0, 0, '', {
      
      fontFamily: Theme.fonts.label.family,
      fontStyle: '700',
    }).setOrigin(0.5).setDepth(21);

    this.betMinusGfx = this.scene.add.graphics().setDepth(20);
    this.betMinusHit = this.scene.add.rectangle(0, 0, 44, 44)
      .setInteractive({ useHandCursor: true }).setAlpha(0.001).setDepth(21);
    this.betPlusGfx = this.scene.add.graphics().setDepth(20);
    this.betPlusHit = this.scene.add.rectangle(0, 0, 44, 44)
      .setInteractive({ useHandCursor: true }).setAlpha(0.001).setDepth(21);

    // Setup interactive hover/press states for tactile arcade feedback
    this.setupTactileFeedback(this.betMinusGfx, this.betMinusHit);
    this.setupTactileFeedback(this.betPlusGfx, this.betPlusHit);
    this.setupTactileFeedback(this.spinGfx, this.spinHit);

    this.autoGfx = this.scene.add.graphics().setDepth(21);
    this.autoHit = this.scene.add.rectangle(0, 0, 100, 30, 0xffffff, 0)
      .setInteractive({ useHandCursor: true }).setDepth(22);
    this.autoTxt = this.scene.add.text(0, 0, 'AUTO', {
      
      fontFamily: Theme.fonts.body.family,
      fontStyle: '800',
      color: '#ffffff',
      shadow: { offsetX: 0, offsetY: 1, color: '#000000', blur: 2, fill: true }
    }).setOrigin(0.5).setDepth(23);

    // Autoplay also gets tactile feedback
    this.setupTactileFeedback(this.autoGfx, this.autoHit);
  }

  /**
   * Binds high-fidelity micro-animations for hover scaling and active pressing click effects
   */
  private setupTactileFeedback(gfx: Phaser.GameObjects.Graphics, hit: Phaser.GameObjects.Rectangle) {
    hit.on('pointerover', () => {
      this.scene.tweens.killTweensOf([gfx, hit]);
      this.scene.tweens.add({
        targets: [gfx, hit],
        scaleX: 1.1,
        scaleY: 1.1,
        duration: 100,
        ease: 'Quad.easeOut'
      });
    });

    hit.on('pointerout', () => {
      this.scene.tweens.killTweensOf([gfx, hit]);
      this.scene.tweens.add({
        targets: [gfx, hit],
        scaleX: 1.0,
        scaleY: 1.0,
        duration: 100,
        ease: 'Quad.easeOut'
      });
    });

    hit.on('pointerdown', () => {
      this.scene.tweens.killTweensOf([gfx, hit]);
      this.scene.tweens.add({
        targets: [gfx, hit],
        scaleX: 0.9,
        scaleY: 0.9,
        duration: 50,
        ease: 'Quad.easeOut'
      });
    });

    hit.on('pointerup', () => {
      this.scene.tweens.killTweensOf([gfx, hit]);
      this.scene.tweens.add({
        targets: [gfx, hit],
        scaleX: 1.1, // return to hover scale
        scaleY: 1.1,
        duration: 80,
        ease: 'Quad.easeOut'
      });
    });
  }

  onSpin(cb: () => void) { this.spinHit.on('pointerdown', cb); }
  onBetMinus(cb: () => void) { this.betMinusHit.on('pointerdown', cb); }
  onBetPlus(cb: () => void) { this.betPlusHit.on('pointerdown', cb); }
  onAutoPlay(cb: () => void) { this.autoHit.on('pointerdown', cb); }

  /**
   * Responsive layout — uses pre-computed LayoutMetrics from the unified LayoutEngine.
   * All sizes are ratios of gridSize, guaranteeing proportional scaling.
   */
  layout(metrics: LayoutMetrics) {
    const { spinX, spinY, spinSize, betBtnSize, autoPillW, autoPillH, autoGap, autoY, w, mode } = metrics;

    this._lastSpinX = spinX;
    this._lastSpinY = spinY;
    this._lastSpinSize = spinSize;

    // ── Spin button ──
    this.spinHit.setPosition(spinX, spinY).setSize(spinSize * 1.3, spinSize * 1.3);
    this.spinGfx.setPosition(spinX, spinY);
    this.spinOuterRing.setPosition(spinX, spinY);
    this.spinInnerPulse.setPosition(spinX, spinY);
    this.drawSpinButton(0, 0, spinSize);

    // Scale spin label proportionally to button size
    const spinLabelFS = Math.max(10, Math.min(18, spinSize * 0.18));
    this.spinLabel.setFontSize(spinLabelFS);

    // ── AutoPlay pill ──
    const autoFS = Math.max(9, Math.min(14, spinSize * 0.15));
    this.autoHit.setVisible(true).setPosition(spinX, autoY).setSize(autoPillW, autoPillH);
    this.autoTxt.setVisible(true).setPosition(spinX, autoY).setFontSize(autoFS);
    this.autoGfx.setVisible(true);

    // ── Bet +/- buttons — always spinSize × 0.45, flanking the spin ──
    const bBtnSize = betBtnSize;
    const isPortrait = mode === 'portrait';

    if (isPortrait) {
      // Portrait: +/- flanking spin horizontally
      const betBtnOffset = spinSize / 2 + bBtnSize / 2 + Math.max(16, w * 0.05);
      const minusX = spinX - betBtnOffset;
      const plusX = spinX + betBtnOffset;

      this.betMinusHit.setPosition(minusX, spinY).setSize(bBtnSize * 1.4, bBtnSize * 1.4);
      this.drawBetButton(this.betMinusGfx, minusX, spinY, bBtnSize, false);
      this.betPlusHit.setPosition(plusX, spinY).setSize(bBtnSize * 1.4, bBtnSize * 1.4);
      this.drawBetButton(this.betPlusGfx, plusX, spinY, bBtnSize, true);
    } else {
      // Landscape: left & right of spin button
      const betBtnOffset = spinSize / 2 + bBtnSize / 2 + 14;
      const minusX = Math.max(bBtnSize + 4, spinX - betBtnOffset);
      const plusX = Math.min(w - bBtnSize - 4, spinX + betBtnOffset);
      this.betMinusHit.setPosition(minusX, spinY).setSize(bBtnSize * 1.4, bBtnSize * 1.4);
      this.drawBetButton(this.betMinusGfx, minusX, spinY, bBtnSize, false);
      this.betPlusHit.setPosition(plusX, spinY).setSize(bBtnSize * 1.4, bBtnSize * 1.4);
      this.drawBetButton(this.betPlusGfx, plusX, spinY, bBtnSize, true);
    }

    return { spinX, spinY, spinSize };
  }

  /** Draw the premium procedural spin button */
  /** Botão principal em esmalte roxo com anéis dourados.
   * Mantém o mesmo hitbox, callbacks e geometria do layout original.
   */
  drawSpinButton(x: number, y: number, size: number) {
    const g = this.spinGfx;
    g.clear();
    this.spinOuterRing.clear();
    this.spinInnerPulse.clear();
    const r = size / 2;
    // Halo controlado, sem o círculo verde fluorescente.
    this.spinInnerPulse.fillStyle(0xf9c45b, 0.17);
    this.spinInnerPulse.fillCircle(x, y, r + 8);
    this.spinOuterRing.lineStyle(Math.max(2, size * 0.035), 0xffd97b, 0.44);
    this.spinOuterRing.strokeCircle(x, y, r + 7);
    g.fillStyle(0x120629, 0.65);
    g.fillCircle(x + 1.5, y + 5, r + 2);
    g.fillStyle(0xffd98d, 1);
    g.fillCircle(x, y, r);
    g.fillGradientStyle(0x7e39ba, 0x6829a2, 0x3d126e, 0x310e60, 1);
    g.fillCircle(x, y, r - Math.max(3, r * 0.095));
    g.lineStyle(Math.max(1.4, r * 0.034), 0xffe9ad, 0.74);
    g.strokeCircle(x, y, r * 0.82);
    g.fillStyle(0xffffff, 0.14);
    g.fillEllipse(x - r * 0.15, y - r * 0.52, r * 0.84, r * 0.26);
    // Seta circular em ouro, desenhada por arco contínuo nítido.
    const iconR = r * 0.43;
    g.lineStyle(Math.max(4, r * 0.13), 0x2e0d54, 0.6);
    g.beginPath();
    g.arc(x, y + 2, iconR, -Math.PI * 0.74, Math.PI * 0.79, false);
    g.strokePath();
    g.lineStyle(Math.max(4, r * 0.12), 0xfff5c5, 1);
    g.beginPath();
    g.arc(x, y, iconR, -Math.PI * 0.74, Math.PI * 0.79, false);
    g.strokePath();
    const a = Math.PI * 0.79;
    const ex = x + iconR * Math.cos(a), ey = y + iconR * Math.sin(a);
    const tx = -Math.sin(a), ty = Math.cos(a);
    g.fillStyle(0xfff5c5, 1);
    g.fillTriangle(ex + tx * 2.5, ey + ty * 2.5,
      ex - tx * r * 0.20 + ty * r * 0.19, ey - ty * r * 0.20 - tx * r * 0.19,
      ex - tx * r * 0.20 - ty * r * 0.19, ey - ty * r * 0.20 + tx * r * 0.19);
  }

  drawStopButton(x: number, y: number, size: number) {
    const g = this.spinGfx;
    g.clear();
    this.spinOuterRing.clear();
    this.spinInnerPulse.clear();
    const r = size / 2;
    this.spinInnerPulse.fillStyle(0xffc77e, 0.13);
    this.spinInnerPulse.fillCircle(x, y, r + 8);
    this.spinOuterRing.lineStyle(Math.max(2, size * 0.035), 0xffd98d, 0.44);
    this.spinOuterRing.strokeCircle(x, y, r + 7);
    g.fillStyle(0x160728, 0.65);
    g.fillCircle(x + 2, y + 5, r + 2);
    g.fillStyle(0xffd98d, 1);
    g.fillCircle(x, y, r);
    g.fillGradientStyle(0xb84b74, 0x92295c, 0x6b1848, 0x4a103c, 1);
    g.fillCircle(x, y, r - Math.max(3, r * 0.095));
    g.lineStyle(Math.max(1.4, r * 0.034), 0xffe8b4, 0.7);
    g.strokeCircle(x, y, r * 0.82);
    const sq = size * 0.26;
    g.fillStyle(0x2f0d39, 0.55);
    g.fillRoundedRect(x - sq / 2 + 1, y - sq / 2 + 3, sq, sq, 5);
    g.fillStyle(0xfff1c8, 1);
    g.fillRoundedRect(x - sq / 2, y - sq / 2, sq, sq, 5);
  }

  /** Diminuir / aumentar aposta com acabamento a condizer com o centro. */
  drawBetButton(gfx: Phaser.GameObjects.Graphics, tx: number, ty: number, size: number, isPlus: boolean) {
    gfx.clear();
    gfx.setPosition(tx, ty);
    const r = size / 2;
    gfx.fillStyle(0x12072b, 0.6);
    gfx.fillCircle(1, 3, r + 1);
    gfx.fillStyle(0xffd79b, 0.98);
    gfx.fillCircle(0, 0, r);
    if (isPlus) {
      gfx.fillGradientStyle(0x835cc6, 0x7650bc, 0x442477, 0x331b65, 1);
    } else {
      gfx.fillGradientStyle(0x5c3c99, 0x52358b, 0x30155e, 0x26114c, 1);
    }
    gfx.fillCircle(0, 0, r - Math.max(2, r * 0.12));
    gfx.lineStyle(Math.max(1, r * 0.055), 0xffedc2, 0.6);
    gfx.strokeCircle(0, 0, r * 0.78);
    gfx.fillStyle(0xffffff, 0.13);
    gfx.fillEllipse(-r * 0.15, -r * 0.42, r * 0.8, r * 0.22);
    const arm = size * 0.22;
    const thick = Math.max(3, size * 0.105);
    gfx.fillStyle(0x2e0c51, 0.65);
    gfx.fillRoundedRect(-arm + 1, -thick / 2 + 2, arm * 2, thick, 2);
    if (isPlus) gfx.fillRoundedRect(-thick / 2 + 1, -arm + 2, thick, arm * 2, 2);
    gfx.fillStyle(0xffe9b1, 1);
    gfx.fillRoundedRect(-arm, -thick / 2, arm * 2, thick, 2);
    if (isPlus) gfx.fillRoundedRect(-thick / 2, -arm, thick, arm * 2, 2);
  }

  /** Auto giros em vidro escuro; muda para cobre quando está ativo. */
  drawAutoButton(isActive: boolean, remaining: number) {
    if (!this.autoGfx.visible) return;
    const x0 = this.autoHit.x, y0 = this.autoHit.y;
    this.autoGfx.clear();
    this.autoGfx.setPosition(x0, y0);
    this.autoTxt.setPosition(x0, y0);
    const bw = this.autoHit.width || 88;
    const bh = this.autoHit.height || 28;
    const r = Math.min(bh / 2, 17), x = -bw / 2, y = -bh / 2;
    this.autoGfx.fillStyle(0x100627, 0.46);
    this.autoGfx.fillRoundedRect(x + 1, y + 3, bw, bh, r);
    this.autoGfx.fillGradientStyle(
      isActive ? 0x954166 : 0x513287, isActive ? 0x85315d : 0x4d2b80,
      isActive ? 0x4a1848 : 0x2b1557, isActive ? 0x4a1848 : 0x28124c, 1
    );
    this.autoGfx.fillRoundedRect(x, y, bw, bh, r);
    this.autoGfx.lineStyle(2, 0xffd897, 0.82);
    this.autoGfx.strokeRoundedRect(x, y, bw, bh, r);
    this.autoGfx.fillStyle(0xffffff, 0.14);
    this.autoGfx.fillRoundedRect(x + 5, y + 2, bw - 10, Math.max(2, bh * 0.15), 2);
    this.autoTxt.setText(isActive ? (remaining > 0 ? 'PARAR (' + remaining + ')' : 'PARAR') : 'AUTO')
      .setColor('#fff1cf')
      .setShadow(0, 1, '#250a45', 2, false, true);
  }

  hideAll() {
    this.spinOuterRing.setVisible(false);
    this.spinInnerPulse.setVisible(false);
    this.spinGfx.setVisible(false);
    this.spinHit.setVisible(false);
    this.spinLabel.setVisible(false);
    this.betMinusGfx.setVisible(false);
    this.betMinusHit.setVisible(false);
    this.betPlusGfx.setVisible(false);
    this.betPlusHit.setVisible(false);
    this.autoGfx.setVisible(false);
    this.autoHit.setVisible(false);
    this.autoTxt.setVisible(false);
  }
}
