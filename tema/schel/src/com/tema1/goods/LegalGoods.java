package com.tema1.goods;

public final class LegalGoods extends Goods {
    private final int kingBonus;
    private final int queenBonus;

    public LegalGoods(final int id, final int profit,
        final int penalty, final int kingBonus, final int queenBonus) {
        super(id, GoodsType.Legal, profit, penalty);
        this.kingBonus = kingBonus;
        this.queenBonus = queenBonus;
    }

    public int getKingBonus() {
        return kingBonus;
    }

    public int getQueenBonus() {
        return queenBonus;
    }
}
