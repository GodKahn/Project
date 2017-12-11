package com.store_comment.model;

import java.util.List;
import java.util.Map;

public interface StoreCommentDAO_interface {
	
	public void update(StoreCommentVO storeCommentVO);
	public List<List> getMyComment(StoreCommentVO storeCommentVO, int rows);
}
