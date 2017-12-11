package com.favorite_store.model;

import java.util.List;

import com.store_profile.model.StoreProfileVO;

public class FavoriteStoreService {

	private Favorite_StoreDAO_interface dao;

	public FavoriteStoreService() {
		dao = new FavoriteStoreDAO();
	}

	public FavoriteStoreVO delFavoriteStore(String mem_num, String sto_num) {

		FavoriteStoreVO favoritestoreVO = new FavoriteStoreVO();

		favoritestoreVO.setMem_num(mem_num);
		favoritestoreVO.setSto_num(sto_num);
		favoritestoreVO.setIs_favo("N");

		dao.update(favoritestoreVO);

		return favoritestoreVO;
	}

	public List<StoreProfileVO> getFavoStoreList(String mem_num) {

		FavoriteStoreVO favoritestoreVO = new FavoriteStoreVO();

		favoritestoreVO.setMem_num(mem_num);
		favoritestoreVO.setIs_favo("Y");

		return dao.getFavoriteStore(favoritestoreVO);
	}
	
}
