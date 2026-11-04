/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.List
 *  java.util.function.BooleanSupplier
 *  java.util.function.Consumer
 *  java.util.function.IntConsumer
 *  java.util.function.IntFunction
 *  java.util.function.IntSupplier
 *  java.util.function.Supplier
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.SpawnerData;
import net.friendswithmoney.fwm.net.Spawners;
import net.friendswithmoney.fwm.ui.HudEditScreen;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.SpawnerCard;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.panel.SpawnerPanel;
import net.friendswithmoney.fwm.ui.widget.ButtonRow;
import net.friendswithmoney.fwm.ui.widget.ChipsRow;
import net.friendswithmoney.fwm.ui.widget.HexRow;
import net.friendswithmoney.fwm.ui.widget.PriceRow;
import net.friendswithmoney.fwm.ui.widget.SectionRow;
import net.friendswithmoney.fwm.ui.widget.SliderRow;
import net.friendswithmoney.fwm.ui.widget.TextInputRow;
import net.friendswithmoney.fwm.ui.widget.TextRow;
import net.friendswithmoney.fwm.ui.widget.ToggleRow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SpawnerHudPanel
extends RowsPanel {
    private String open = "prices";

    @Override
    public Component title() {
        return Lang.of("fwm.tab.spawnerhud", new Object[0]);
    }

    private static Component t(String de, String en) {
        return Component.literal((String)Lang.t(de, en));
    }

    private void heading(String de, String en) {
        this.rows.add((Object)new TextRow(SpawnerHudPanel.t(de, en)));
    }

    private void toggle(String de, String en, BooleanSupplier get, Consumer<Boolean> set) {
        this.rows.add((Object)new ToggleRow(SpawnerHudPanel.t(de, en), get, (Consumer<Boolean>)((Consumer)v -> {
            set.accept(v);
            ConfigManager.save();
        })));
    }

    private void slider(String de, String en, int min, int max, IntSupplier get, IntConsumer set, String unit) {
        this.rows.add((Object)new SliderRow(SpawnerHudPanel.t(de, en), min, max, get, v -> {
            set.accept(v);
            ConfigManager.save();
        }, (IntFunction<Component>)((IntFunction)v -> Component.literal((String)(v + unit)))));
    }

    private void color(String de, String en, IntSupplier get, IntConsumer set) {
        this.rows.add((Object)new HexRow(SpawnerHudPanel.t(de, en), get, v -> {
            set.accept(v);
            ConfigManager.save();
        }));
    }

    private void words(String de, String en, Supplier<String> get, Consumer<String> set, String empty) {
        this.rows.add((Object)new TextInputRow(SpawnerHudPanel.t(de, en), get, (Consumer<String>)((Consumer)v -> {
            set.accept(v);
            ConfigManager.save();
        }), empty, 32));
    }

    private void section(String id, String de, String en, String hintDe, String hintEn) {
        this.rows.add((Object)new SectionRow(SpawnerHudPanel.t(de, en), Lang.t(hintDe, hintEn), () -> id.equals((Object)this.open), () -> {
            this.open = id.equals((Object)this.open) ? "" : id;
            this.onResize();
        }));
    }

    @Override
    protected void buildRows() {
        String name;
        FwmConfig c = FwmConfig.INSTANCE;
        this.toggle("Anzeige im Spiel", "Show in game", () -> c.spawnerHudCard, (Consumer<Boolean>)((Consumer)v -> {
            c.spawnerHudCard = v;
        }));
        this.rows.add((Object)new ButtonRow(SpawnerHudPanel.t("Position und Gr\u00f6\u00dfe", "Position and size"), (Supplier<Component>)((Supplier)() -> SpawnerHudPanel.t("HUD anpassen", "Adjust HUD")), () -> Minecraft.getInstance().gui.setScreen((Screen)new HudEditScreen(Minecraft.getInstance().gui.screen()))));
        SpawnerData data = Spawners.data();
        List<SpawnerData.Item> items = data == null || data.items == null ? List.of() : SpawnerPanel.inShopOrder(data.items);
        this.section("prices", "Eigene Preise", "Own prices", "leer = Preis der Website", "empty = website price");
        if ("prices".equals((Object)this.open)) {
            if (items.isEmpty()) {
                this.heading("Spawner werden geladen \u2026", "Loading spawners \u2026");
            }
            for (SpawnerData.Item item : items) {
                name = item.name;
                this.rows.add((Object)new PriceRow((Component)Component.literal((String)name), (Supplier<String>)((Supplier)() -> item.buyPrice), (Supplier<String>)((Supplier)() -> item.sellPrice), (Supplier<String[]>)((Supplier)() -> (String[])c.spawnerHudPrices.get((Object)name)), (Consumer<String[]>)((Consumer)v -> {
                    boolean empty;
                    boolean bl = empty = !(v[0] != null && !v[0].isBlank() || v[1] != null && !v[1].isBlank());
                    if (empty) {
                        c.spawnerHudPrices.remove((Object)name);
                    } else {
                        c.spawnerHudPrices.put((Object)name, v);
                    }
                    ConfigManager.save();
                })));
            }
        }
        this.section("look", "Aussehen", "Look", "Stil, Hintergrund, Rahmen", "style, background, border");
        if ("look".equals((Object)this.open)) {
            this.rows.add((Object)new ChipsRow(SpawnerHudPanel.t("Stil", "Style"), (List<ChipsRow.Chip>)List.of((Object)((Object)new ChipsRow.Chip("FWM", 0)), (Object)((Object)new ChipsRow.Chip("Apple", 1)), (Object)((Object)new ChipsRow.Chip("Neon", 2)), (Object)((Object)new ChipsRow.Chip("Minimal", 3))), () -> c.spawnerHudStyle, v -> {
                SpawnerCard.applyStyle(v);
                ConfigManager.save();
            }));
            this.rows.add((Object)new ChipsRow(SpawnerHudPanel.t("Hintergrund", "Background"), (List<ChipsRow.Chip>)List.of((Object)((Object)new ChipsRow.Chip(Lang.t("Dunkel", "Solid"), 0)), (Object)((Object)new ChipsRow.Chip(Lang.t("Glas", "Glass"), 1)), (Object)((Object)new ChipsRow.Chip(Lang.t("Keiner", "None"), 2))), () -> c.spawnerHudBg, v -> {
                c.spawnerHudBg = v;
                ConfigManager.save();
            }));
            this.rows.add((Object)new ChipsRow(SpawnerHudPanel.t("Rahmen", "Border"), (List<ChipsRow.Chip>)List.of((Object)((Object)new ChipsRow.Chip(Lang.t("Aus", "Off"), 0)), (Object)((Object)new ChipsRow.Chip(Lang.t("Fein", "Thin"), 1)), (Object)((Object)new ChipsRow.Chip(Lang.t("Farbverlauf", "Gradient"), 2))), () -> c.spawnerHudBorder, v -> {
                c.spawnerHudBorder = v;
                ConfigManager.save();
            }));
            this.rows.add((Object)new ChipsRow((Component)Component.literal((String)"Layout"), (List<ChipsRow.Chip>)List.of((Object)((Object)new ChipsRow.Chip(Lang.t("Liste", "List"), 0)), (Object)((Object)new ChipsRow.Chip(Lang.t("Raster", "Grid"), 1))), () -> c.spawnerHudGrid ? 1 : 0, v -> {
                c.spawnerHudGrid = v == 1;
                ConfigManager.save();
            }));
        }
        this.section("content", "Inhalt", "Content", "Titel, Spalten, Bilder", "title, columns, pictures");
        if ("content".equals((Object)this.open)) {
            this.words("Titel", "Title", (Supplier<String>)((Supplier)() -> c.spawnerHudTitle), (Consumer<String>)((Consumer)v -> {
                c.spawnerHudTitle = v;
            }), Lang.t("kein Titel", "no title"));
            this.toggle("Kopfzeile", "Header", () -> c.spawnerHudHeader, (Consumer<Boolean>)((Consumer)v -> {
                c.spawnerHudHeader = v;
            }));
            this.words("Spalte Name", "Name column", (Supplier<String>)((Supplier)() -> c.spawnerHudLabelName), (Consumer<String>)((Consumer)v -> {
                c.spawnerHudLabelName = v;
            }), "Spawner");
            this.words("Spalte Kauf", "Buy column", (Supplier<String>)((Supplier)() -> c.spawnerHudLabelBuy), (Consumer<String>)((Consumer)v -> {
                c.spawnerHudLabelBuy = v;
            }), "Kauf");
            this.words("Spalte Verkauf", "Sell column", (Supplier<String>)((Supplier)() -> c.spawnerHudLabelSell), (Consumer<String>)((Consumer)v -> {
                c.spawnerHudLabelSell = v;
            }), "Verkauf");
            this.toggle("Kauf-Preise", "Buy prices", () -> c.spawnerHudShowBuy, (Consumer<Boolean>)((Consumer)v -> {
                c.spawnerHudShowBuy = v;
            }));
            this.toggle("Verkauf-Preise", "Sell prices", () -> c.spawnerHudShowSell, (Consumer<Boolean>)((Consumer)v -> {
                c.spawnerHudShowSell = v;
            }));
            this.toggle("Bilder der Spawner", "Spawner pictures", () -> c.spawnerHudIcons, (Consumer<Boolean>)((Consumer)v -> {
                c.spawnerHudIcons = v;
            }));
        }
        this.section("spawners", "Spawner ausw\u00e4hlen", "Choose spawners", items.size() - c.spawnerHudHidden.size() + " von " + items.size(), items.size() - c.spawnerHudHidden.size() + " of " + items.size());
        if ("spawners".equals((Object)this.open)) {
            for (SpawnerData.Item item : items) {
                name = item.name;
                this.rows.add((Object)new ToggleRow((Component)Component.literal((String)name), () -> !c.spawnerHudHidden.contains((Object)name), (Consumer<Boolean>)((Consumer)v -> {
                    if (v.booleanValue()) {
                        c.spawnerHudHidden.remove((Object)name);
                    } else if (!c.spawnerHudHidden.contains((Object)name)) {
                        c.spawnerHudHidden.add((Object)name);
                    }
                    ConfigManager.save();
                })));
            }
        }
        this.section("colors", "Farben", "Colours", "Kauf, Verkauf, Text \u2026", "buy, sell, text \u2026");
        if ("colors".equals((Object)this.open)) {
            this.color("Kauf", "Buy", () -> c.spawnerHudBuyColor, v -> {
                c.spawnerHudBuyColor = v;
            });
            this.color("Verkauf", "Sell", () -> c.spawnerHudSellColor, v -> {
                c.spawnerHudSellColor = v;
            });
            this.color("Namen", "Names", () -> c.spawnerHudTextColor, v -> {
                c.spawnerHudTextColor = v;
            });
            this.color("Kopfzeile", "Header", () -> c.spawnerHudHeadColor, v -> {
                c.spawnerHudHeadColor = v;
            });
            this.color("Hintergrund", "Background", () -> c.spawnerHudBgColor, v -> {
                c.spawnerHudBgColor = v;
            });
            this.color("Rahmen", "Border", () -> c.spawnerHudBorderColor, v -> {
                c.spawnerHudBorderColor = v;
            });
        }
        this.section("fine", "Feinschliff", "Fine-tuning", "Deckkraft, Ecken, Abst\u00e4nde \u2026", "opacity, corners, spacing \u2026");
        if ("fine".equals((Object)this.open)) {
            this.slider("Deckkraft", "Opacity", 10, 100, () -> c.spawnerHudOpacity, v -> {
                c.spawnerHudOpacity = v;
            }, " %");
            this.slider("Ecken", "Corners", 0, 12, () -> c.spawnerHudRadius, v -> {
                c.spawnerHudRadius = v;
            }, " px");
            this.slider("Innenabstand", "Padding", 2, 16, () -> c.spawnerHudPadding, v -> {
                c.spawnerHudPadding = v;
            }, " px");
            this.slider("Zeilenh\u00f6he", "Row height", 10, 20, () -> c.spawnerHudRowGap, v -> {
                c.spawnerHudRowGap = v;
            }, " px");
            this.slider("Rahmenst\u00e4rke", "Border width", 1, 3, () -> c.spawnerHudBorderWidth, v -> {
                c.spawnerHudBorderWidth = v;
            }, " px");
            this.slider("Tempo Farbverlauf", "Gradient speed", 1, 10, () -> c.spawnerHudGradientSpeed, v -> {
                c.spawnerHudGradientSpeed = v;
            }, "");
            this.toggle("Schatten unter der Karte", "Shadow under the card", () -> c.spawnerHudShadow, (Consumer<Boolean>)((Consumer)v -> {
                c.spawnerHudShadow = v;
            }));
            this.toggle("Textschatten", "Text shadow", () -> c.spawnerHudTextShadow, (Consumer<Boolean>)((Consumer)v -> {
                c.spawnerHudTextShadow = v;
            }));
            this.toggle("Trennlinie", "Divider line", () -> c.spawnerHudDivider, (Consumer<Boolean>)((Consumer)v -> {
                c.spawnerHudDivider = v;
            }));
            this.toggle("Kopfzeile fett und farbig", "Header bold and coloured", () -> c.spawnerHudHeadBold, (Consumer<Boolean>)((Consumer)v -> {
                c.spawnerHudHeadBold = v;
            }));
        }
    }
}
