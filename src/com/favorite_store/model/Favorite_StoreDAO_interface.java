package com.favorite_store.model;

import java.util.*;

import com.store_profile.model.StoreProfileVO;

public interface Favorite_StoreDAO_interface {
	
	public void update(FavoriteStoreVO favoritestoreVO);
	public List<StoreProfileVO> getFavoriteStore(FavoriteStoreVO favoritestoreVO);
}
