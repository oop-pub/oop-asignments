package com.tema1.goods;

public abstract class Goods {
    private final int id;
    private final GoodsType type;
    private final int profit;
    private final int penalty;

    public Goods(final int id, final GoodsType type, final int profit, final int penalty) {
        this.id = id;
        this.type = type;
        this.profit = profit;
        this.penalty = penalty;
    }

    public final int getId() {
        return id;
    }

    public final GoodsType getType() {
        return type;
    }

    public final int getProfit() {
        return profit;
    }

    public final int getPenalty() {
        return penalty;
    }
}
