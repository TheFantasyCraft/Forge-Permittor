package com.fantasycraft.forgepermittor.info;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by thomas on 8/18/2014.
 */

@ToString
@Data
public class ItemInfo {
    @Getter
    private int ID;
    @Getter
    private byte Meta;
    @Getter
    private ItemType itemtype;
}
