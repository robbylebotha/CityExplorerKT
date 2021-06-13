package it.ads.app.cityexplorerkt.interfaces;

import java.util.ArrayList;

import it.ads.app.cityexplorerkt.models.Shop;

public interface CallBack {

    void onSuccess(ArrayList<Shop> list);

    void onFail(String message);
}
